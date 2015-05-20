package com.yelpdatasetchallenge.cascading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yelpdatasetchallenge.objects.Business;

import driven.com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import driven.com.fasterxml.jackson.annotation.PropertyAccessor;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;
import driven.com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author feiyu
 */

public class DataAnalysisMain {
  private Map<String, JsonNode> businessHM = new HashMap<String, JsonNode>(); 
  private Map<String, JsonNode> checkInHourWeekHM = new HashMap<String, JsonNode>();

  public void saveBusinessInfoIntoHashMap() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_business.json"));
    ObjectMapper mapper = new ObjectMapper();
    PrintWriter writer = new PrintWriter("src/main/resources/yelp-dataset/yelp_academic_dataset_business_output.txt", "UTF-8");

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode actualObj = mapper.readTree(line);
        JsonNode businessID = actualObj.get("business_id");
        //        System.out.println("key:"+businessID.asText());//+", value:"+actualObj);
        businessHM.put(businessID.asText(), actualObj);

        line = br.readLine();
      }
    } finally {
      br.close();
    }
    System.out.println(businessHM.get("6TPxhpHqFedjMvBuw6pF3w"));
    System.out.println("num of business: "+ businessHM.size());
    writer.println("num of business: "+ businessHM.size());
    writer.close();
  }

  public void saveCheckInInfoIntoHashMap() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json"));
    ObjectMapper mapper = new ObjectMapper();
    PrintWriter writer = new PrintWriter("src/main/resources/yelp-dataset/yelp_academic_dataset_checkin_output.txt", "UTF-8");

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode actualObj = mapper.readTree(line);
        JsonNode businessID = actualObj.get("business_id");
        JsonNode checkinInfoObj = actualObj.get("checkin_info");
        System.out.println(checkinInfoObj);

        // iterate check-in info of each business and put <hour-week:[{business_id:_,count:_,},{business_id:_,count:_,},..]> into checkInHourWeekHM
        Iterator<Map.Entry<String,JsonNode>> fields = checkinInfoObj.fields();
        while (fields.hasNext()) {
          Map.Entry<String,JsonNode> field = fields.next();
          String key = field.getKey();
          JsonNode value = field.getValue();

          Business b = new Business();
          b.setBusinessID(businessID.asText());
          b.setCheckInCountTimeWindow(value.asText());
          ObjectMapper businessMapper = new ObjectMapper();
          businessMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

          List<Business> businessAryList = new ArrayList<Business>();
          businessAryList.add(b);
          JsonNode businessJsonAry = businessMapper.valueToTree(businessAryList);

          if (checkInHourWeekHM.containsKey(key)) {
            businessJsonAry = checkInHourWeekHM.get(key); 
            //            System.out.println(businessJsonAry);
            ((ArrayNode)businessJsonAry).add(businessMapper.valueToTree(b));
          } 
          //          System.out.println(key+"-----"+businessJsonAry);
          checkInHourWeekHM.put(key, businessJsonAry);
        }
        line = br.readLine();
      }
    } finally {
      br.close();
    }
    System.out.println("value: "+checkInHourWeekHM.get("17-3"));
    writer.println("value: "+checkInHourWeekHM.get("17-3"));

    System.out.println("Size of checkInHourWeekHM: "+checkInHourWeekHM.size());
    writer.println("Size of checkInHourWeekHM: "+checkInHourWeekHM.size());
    //    Iterator it = checkInHourWeekHM.entrySet().iterator();
    //    while (it.hasNext()) {
    //        Map.Entry pair = (Map.Entry)it.next();
    //        System.out.println(pair.getKey() + " = " + pair.getValue());
    //        it.remove(); // avoids a ConcurrentModificationException
    //    }
    writer.close();
  }


  public static void main(String[] argv) throws IOException {
    DataAnalysisMain dataAnalysis = new DataAnalysisMain();
    dataAnalysis.saveBusinessInfoIntoHashMap();
    dataAnalysis.saveCheckInInfoIntoHashMap();
  }
}
