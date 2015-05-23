package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author feiyu
 * 
 * Example Code of GeoJSON
{
    "type": "FeatureCollection",
    "features": [
    {
        "type": "Feature",
        "properties": {
            "businessName": "business name 1",
            "businessCategories":"category 1, category 2",
            "businessAddress":"address 1",
            "checkInCountTimeWindow":"6",
            "timeWindow":"17-3"
        },
        "geometry": {
            "type": "Point",
            "coordinates": [-104.98999178409578, 39.74683938093909]
        }
    },{
        "type": "Feature",
        "properties": {
            "businessName": "business name 2",
            "businessCategories":"category 3, category 2",
            "businessAddress":"address 2",
            "checkInCountTimeWindow":"2",
            "timeWindow":"15-4"
        },
        "geometry": {
            "type": "Point",
            "coordinates": [-104.98689115047450, 39.747924136466561]
        }
    }
    ]
};
 * http://wiki.fasterxml.com/JacksonInFiveMinutes
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import driven.com.fasterxml.jackson.core.JsonFactory;
import driven.com.fasterxml.jackson.core.JsonGenerationException;
import driven.com.fasterxml.jackson.core.JsonParser;
import driven.com.fasterxml.jackson.databind.JsonMappingException;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class DataStoreRedisByProperties extends DataStoreRedis {
  private void getStateCityCategoryHourWeekBusinessMapping() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json"));
    ObjectMapper checkInMapper = new ObjectMapper();
    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode checkInObj = checkInMapper.readTree(line);
        line = br.readLine();

        // get checkin info for each business_id in yelp_academic_dataset_checkin.json
        JsonNode businessID = checkInObj.get("business_id");
        String businessInfo = jedis.get(businessID.asText());

        if (businessInfo != null) {
          // fetch business Info from Redis and map the string result to JSON
          ObjectMapper businessInfoMapper = new ObjectMapper();
          JsonNode businessObj = businessInfoMapper.readTree(businessInfo);

          // Get chech-in info
          JsonNode checkinInfoObj = checkInObj.get("checkin_info");

          this.propertyCategory(businessObj, checkinInfoObj);

        } else {
          logWriter.print("\nCan't get info for business "+businessID.asText());
        }
      }
    } finally {
      br.close();
    }
  } 

  private void propertyCategory(JsonNode businessObj,JsonNode checkinInfoObj) throws IOException {
    ObjectMapper categoryListMapper = new ObjectMapper();
    JsonFactory categoryListFactory = categoryListMapper.getFactory();
    JsonParser categoryListParser = categoryListFactory.createParser(businessObj.get("categories").toString());
    @SuppressWarnings("unchecked")
    List<String> categoryList = categoryListMapper.readValue(categoryListParser, List.class);
    for (String category : categoryList) {
      this.propertyHourWeekTimeWindow(businessObj, checkinInfoObj, category);
    }
  }

  private void propertyHourWeekTimeWindow(JsonNode businessObj, JsonNode checkinInfoObj, String category) 
      throws JsonGenerationException, JsonMappingException, IOException {
    /*
    e.g.: checkinInfoObj: {"9-5":1,"7-5":1,"13-3":1,"17-6":1,"13-0":1,"17-3":1,"10-0":1,"18-4":1,"14-6":1}
    iterate through checkinInfoObj and get all of the <keyTimeWindow,valueCount> pairs, 
    and generate the GeoJSONBusinessFeature object of each pair
     */
    Iterator<Map.Entry<String,JsonNode>> checkinInfoFields = checkinInfoObj.fields();
    while (checkinInfoFields.hasNext()) {
      Map.Entry<String,JsonNode> checkinInfoField = checkinInfoFields.next();
      String keyTimeWindow = checkinInfoField.getKey();
      JsonNode valueCount = checkinInfoField.getValue();
      // System.out.println(keyTimeWindow+": "+valueCount);

      String wholeKey = businessObj.get("state").asText()+":"
          +businessObj.get("city").asText()+":"
          +category+":"
          +keyTimeWindow; 
      String wholeValue = businessObj.get("business_id").asText()+":"
          + valueCount.asText();
      System.out.println(wholeKey+" <----> "+wholeValue);

      ObjectMapper businessCountMapper = new ObjectMapper();
      Map<String, String> businessCountMap = new HashMap<String,String>();
      businessCountMap.put(businessObj.get("business_id").asText(), valueCount.asText());
      String businessCountMapValue = businessCountMapper.writeValueAsString(businessCountMap);

      jedis.set(wholeKey, businessCountMapValue);
    }
  }; 

  @Override
  public void callMethods() throws IOException {
    this.initJedis("localhost");

    this.clearJedis();
    this.mapBusinessIDWithItsJson();
    this.getStateCityCategoryHourWeekBusinessMapping();

    Set<String> keys = jedis.keys("PA:*:Bars:23-5");
    System.out.println("=====================================");
    for (String key:keys) {
      System.out.print(key+" <--> ");
      System.out.println(jedis.get(key));
    }

    //    this.getBusinessCheckInInfo();

    this.closeJedis();
  }

  public static void main(String[] argv) throws IOException {
    DataStoreRedisByProperties dsRedisByProp = new DataStoreRedisByProperties();
    dsRedisByProp.run("src/main/resources/yelp-dataset/log_redis_properties_yelp_academic_dataset.txt");
  }
}
