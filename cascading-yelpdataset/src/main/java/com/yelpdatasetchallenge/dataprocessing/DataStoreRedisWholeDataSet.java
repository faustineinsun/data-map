package com.yelpdatasetchallenge.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeature;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureCollection;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureGeometry;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureProperties;

import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;
import driven.com.fasterxml.jackson.databind.ObjectWriter;

public class DataStoreRedisWholeDataSet extends DataStoreRedis {

  @Override
  public void getBusinessCheckInInfo() throws IOException {
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
            // System.out.println(geoJsonFeatureGeo.toString());

            GeoJSONBusinessFeature geoJsonFeature = new GeoJSONBusinessFeature();
            geoJsonFeature.setType("Feature");
            geoJsonFeature.setProperties(geoJsonFeatureProp);
            geoJsonFeature.setGeometry(geoJsonFeatureGeo);

            // add GeoJSONBusinessFeature object into the features array of GeoJSON
            businessFeaturesAryList.add(geoJsonFeature);
            break;  //@@@@@ if delete this, error message "OutOfMemoryError: Java heap space" will occur
            // therefore, we need to show them by properties like state, city, category, hour-week time window
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

    businessFeatureClctnMapper.writeValue(new File("src/main/resources/yelp-dataset/businessFeatureClctnWhole.json"), businessFeatureClctn);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String businessFeatureClctnJson = ow.writeValueAsString(businessFeatureClctn);
    jedis.set("businessGeoJSON", businessFeatureClctnJson);
    // System.out.println(jedis.get("businessGeoJSON"));
  }

  @Override
  public void callMethods() throws IOException {
    this.initJedis("localhost");
    this.clearJedis();

    this.saveBusinessInfoToDataStore();
    this.getBusinessCheckInInfo();

    this.closeJedis();
  }

  public static void main(String[] argv) throws Exception {
    DataStoreRedisWholeDataSet dsRedisWhole = new DataStoreRedisWholeDataSet();
    dsRedisWhole.run("src/main/resources/yelp-dataset/log_redis_whole_yelp_academic_dataset.txt");
  }
}
