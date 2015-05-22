package com.yelpdatasetchallenge.util;

/**
 * @author feiyu
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeature;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureCollection;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureGeometry;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureProperties;

import redis.clients.jedis.Jedis;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;
import driven.com.fasterxml.jackson.databind.ObjectWriter;

public class GetAllTimeWindowOfBusinessCheckinCountByStateRedis {
  HashSet<String> stateSet = new HashSet<String>();

  public void saveBusinessInfoIntoRedisByID(Jedis jedis, PrintWriter logWriter) throws IOException   {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_business.json"));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode actualObj = mapper.readTree(line);
        line = br.readLine();

        // Map business_id with it's json info
        JsonNode businessID = actualObj.get("business_id");
        jedis.set(businessID.asText(), actualObj.toString());
      }
    } finally {
      br.close();
    }
    // check if everything is right
    logWriter.print("DB Size: "+jedis.dbSize());
    logWriter.print("\n"+jedis.get("6TPxhpHqFedjMvBuw6pF3w"));
  }

  /**
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
   * @param jedis
   * @param logWriter
   * @throws IOException
   */
  public void buildGeoJSONForLeafletSaveToRedis(Jedis jedis, PrintWriter logWriter, boolean getWholeData, String stateSelected) throws IOException  {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json"));
    ObjectMapper checkInMapper = new ObjectMapper();

    // Build GeoJSON for Leaflet  
    // FeatureCollection level of GeoJSON
    GeoJSONBusinessFeatureCollection businessFeatureClctn = new GeoJSONBusinessFeatureCollection();
    // features array of GeoJSON
    ArrayList<GeoJSONBusinessFeature> businessFeaturesAryList = new ArrayList<GeoJSONBusinessFeature>();

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

          JsonNode businessName = businessObj.get("name");
          JsonNode businessCategories = businessObj.get("categories");
          JsonNode businessAddress = businessObj.get("full_address");
          JsonNode businessLatitude = businessObj.get("latitude");
          JsonNode businessLongitude = businessObj.get("longitude");

          JsonNode stateObj = businessObj.get("state");
          stateSet.add(stateObj.asText());
          if (!getWholeData && !stateObj.asText().equals(stateSelected)){
            continue;
          }

          System.out.println(businessName.asText());

          // Get chech-in info
          JsonNode checkinInfoObj = checkInObj.get("checkin_info");

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

            GeoJSONBusinessFeatureProperties geoJsonFeatureProp = new GeoJSONBusinessFeatureProperties();
            geoJsonFeatureProp.setBusinessName(businessName.asText());
            geoJsonFeatureProp.setBusinessCategories(businessCategories.toString());
            geoJsonFeatureProp.setBusinessAddress(businessAddress.asText());
            geoJsonFeatureProp.setCheckInCountTimeWindow(valueCount.asText());
            geoJsonFeatureProp.setTimeWindow(keyTimeWindow);
            // System.out.println(geoJsonFeatureProp.toString());

            GeoJSONBusinessFeatureGeometry geoJsonFeatureGeo = new GeoJSONBusinessFeatureGeometry();
            geoJsonFeatureGeo.setType("Point");
            // business Coordinates save in ArrayList<Float> 
            ArrayList<Float> businessCoordinates = new ArrayList<Float>();
            businessCoordinates.add(Float.valueOf(businessLongitude.asText()));
            businessCoordinates.add(Float.valueOf(businessLatitude.asText()));
            geoJsonFeatureGeo.setCoordinates(businessCoordinates);
            //            System.out.println(geoJsonFeatureGeo.toString());

            GeoJSONBusinessFeature geoJsonFeature = new GeoJSONBusinessFeature();
            geoJsonFeature.setType("Feature");
            geoJsonFeature.setProperties(geoJsonFeatureProp);
            geoJsonFeature.setGeometry(geoJsonFeatureGeo);

            // add GeoJSONBusinessFeature object into the features array of GeoJSON
            businessFeaturesAryList.add(geoJsonFeature);
            break;
          }
        } else {
          logWriter.print("\nCan't get info for business "+businessID.asText());
        }
      }
    } finally {
      br.close();
    }
    // generate the outer level of this GeoJSON file
    businessFeatureClctn.setType("FeatureCollection");
    businessFeatureClctn.setFeatures(businessFeaturesAryList);
    ObjectMapper businessFeatureClctnMapper = new ObjectMapper();

    if (getWholeData) {
      businessFeatureClctnMapper.writeValue(new File("src/main/resources/yelp-dataset/businessFeatureClctn.json"), businessFeatureClctn);
    } else {
      businessFeatureClctnMapper.writeValue(new File("src/main/resources/yelp-dataset/businessFeatureClctn_"+stateSelected+".json"), businessFeatureClctn);
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String businessFeatureClctnJson = ow.writeValueAsString(businessFeatureClctn);
    jedis.set("businessGeoJSON", businessFeatureClctnJson);
    // System.out.println(jedis.get("businessGeoJSON"));

    System.out.println("\nState set: "+ stateSet.size());
    Iterator<String> it = stateSet.iterator();
    while(it.hasNext()) {
      System.out.print(it.next().toString()+" ");  
    }
  } 

  public static void main(String[] argv) throws IOException {
    GetAllTimeWindowOfBusinessCheckinCountByStateRedis byState = new GetAllTimeWindowOfBusinessCheckinCountByStateRedis();
    PrintWriter logWriter = new PrintWriter("src/main/resources/yelp-dataset/log_yelp_academic_dataset.txt", "UTF-8");
    Jedis jedis = new Jedis("localhost");
    //    jedis.flushAll();

    //    byState.saveBusinessInfoIntoRedisByID(jedis, logWriter);
    byState.buildGeoJSONForLeafletSaveToRedis(jedis, logWriter, false, "NV"); 
    // getWholeData=false means only get state data (there are 18 states info in the Yelp Dataset)
    // BW, SCB, MLN, SC, IL, ELN, NV, QC, WI, AZ, CA, KHL, ON, FIF, WA, EDH, PA, NC

    System.out.println("\nDone!!");
    jedis.close();
    logWriter.close();
  }
}
