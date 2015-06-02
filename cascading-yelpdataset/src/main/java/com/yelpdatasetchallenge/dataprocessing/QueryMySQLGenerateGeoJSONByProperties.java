package com.yelpdatasetchallenge.dataprocessing;

import java.sql.SQLException;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;

import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeature;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureCollection;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureGeometry;
import com.yelpdatasetchallenge.objects.GeoJSONBusinessFeatureProperties;

import driven.com.fasterxml.jackson.databind.ObjectMapper;
import driven.com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author feiyu
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
 * 
 * there are 18 states info in the Yelp Dataset:
 * BW, SCB, MLN, SC, IL, ELN, NV, QC, WI, AZ, CA, KHL, ON, FIF, WA, EDH, PA, NC
 */

public class QueryMySQLGenerateGeoJSONByProperties extends DataStoreMySQL implements GenerateGeoJSONInterface {
  private String[] stateAry = {"BW", "SCB", "MLN", "SC", "IL", "ELN", "NV", "QC", "WI", "AZ", "CA", "KHL", "ON", "FIF", "WA", "EDH", "PA", "NC"};
  private String[] weekAry = {"Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
  private int maxNumBsnsInStateTimeWindow = Integer.MIN_VALUE;
  private int numJsonFileGernerated = 0;
  private Jedis jedis = new Jedis("localhost");

  public QueryMySQLGenerateGeoJSONByProperties(String logFilePath, String businessFilePath,
                                               String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private void queryDB(String State, String Hour, String Week) throws SQLException {
    preparedStatement = connect.prepareStatement(
      "SELECT Businesses.*, Business_checkin.`Count`, Business_checkin.`Hour`,  Business_checkin.`Week` " 
          +"FROM  Businesses INNER JOIN Business_checkin "
          +"ON Businesses.`BusinessId` = Business_checkin.`BusinessId` "
          +"WHERE Businesses.`State` = ? "
          +"AND Business_checkin.`Hour` = ? AND Business_checkin.`Week` = ?");
    preparedStatement.setString(1, State);
    preparedStatement.setString(2, Hour);
    preparedStatement.setString(3, Week);
    // System.out.println("------"+preparedStatement.toString());

    resultSet = preparedStatement.executeQuery();

  }

  public void generateGeoJSON(String State, String Hour, String Week) throws Exception {
    this.queryDB(State, Hour, Week);

    // Build GeoJSON for Leaflet  
    // FeatureCollection level of GeoJSON
    GeoJSONBusinessFeatureCollection businessFeatureClctn = new GeoJSONBusinessFeatureCollection();
    // features array of GeoJSON
    ArrayList<GeoJSONBusinessFeature> businessFeaturesAryList = new ArrayList<GeoJSONBusinessFeature>();

    int numBsnsInStateTimeWindow=0;
    while (resultSet.next()) {

      GeoJSONBusinessFeatureProperties geoJsonFeatureProp = new GeoJSONBusinessFeatureProperties();
      geoJsonFeatureProp.setBusinessName(resultSet.getString("Name"));
      geoJsonFeatureProp.setBusinessCategories(resultSet.getString("Category"));
      geoJsonFeatureProp.setBusinessAddress(resultSet.getString("FullAddress"));
      geoJsonFeatureProp.setCheckInCountTimeWindow(resultSet.getString("Count"));
      geoJsonFeatureProp.setTimeWindow(resultSet.getString("Hour")+":"+resultSet.getString("Week"));
      // System.out.println("geoJsonFeatureProp: "+geoJsonFeatureProp.toString());

      GeoJSONBusinessFeatureGeometry geoJsonFeatureGeo = new GeoJSONBusinessFeatureGeometry();
      geoJsonFeatureGeo.setType("Point");
      // business Coordinates save in ArrayList<Float> 
      ArrayList<Float> businessCoordinates = new ArrayList<Float>();
      businessCoordinates.add(Float.valueOf(resultSet.getString("Longitude")));
      businessCoordinates.add(Float.valueOf(resultSet.getString("Latitude")));
      geoJsonFeatureGeo.setCoordinates(businessCoordinates);
      // System.out.println("geoJsonFeatureGeo: "+geoJsonFeatureGeo.toString());

      GeoJSONBusinessFeature geoJsonFeature = new GeoJSONBusinessFeature();
      geoJsonFeature.setType("Feature");
      geoJsonFeature.setProperties(geoJsonFeatureProp);
      geoJsonFeature.setGeometry(geoJsonFeatureGeo);

      // add GeoJSONBusinessFeature object into the features array of GeoJSON
      businessFeaturesAryList.add(geoJsonFeature);

      ++numBsnsInStateTimeWindow;
    }


    if (numBsnsInStateTimeWindow>0) {

      // generate the outer level of this GeoJSON file
      businessFeatureClctn.setType("FeatureCollection");
      businessFeatureClctn.setFeatures(businessFeaturesAryList);

      // save generated GeoJSON file on local machine
      /*
       * Do this later for demo only
      ObjectMapper businessFeatureClctnMapper = new ObjectMapper();
      businessFeatureClctnMapper.writeValue(new File("src/main/resources/yelp-dataset/json/businessFeatureClctn"
        +"_"+State
        +"_"+Hour
        +"_"+Week
        +".json"), businessFeatureClctn);
       */

      // save generated GeoJSON file to Redis in-memory database 
      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String businessFeatureClctnJson = ow.writeValueAsString(businessFeatureClctn);
      jedis.set("businessGeoJSON"+"_"+State+"_"+Hour+"_"+weekAry[Integer.valueOf(Week)], businessFeatureClctnJson);
      //System.out.println("businessGeoJSON"+"_"+State+"_"+Hour+"_"+weekAry[Integer.valueOf(Week)]+":"+jedis.get("businessGeoJSON"+"_"+State+"_"+Hour+"_"+weekAry[Integer.valueOf(Week)]));

      // Statistics
      System.out.println(State+":"+Hour+":"+ Week +" -> "+numBsnsInStateTimeWindow);
      if (numBsnsInStateTimeWindow==8889) {
        this.logWriter.println("\n"+State+":"+Hour+":"+ Week +" -> "+numBsnsInStateTimeWindow);
        /*
        businessFeatureClctnMapper.writeValue(new File("src/main/resources/yelp-dataset/json/businessFeatureClctn"
            +"_"+State
            +"_"+Hour
            +"_"+Week
            +".json"), businessFeatureClctn);
         * 
         */
      }
      this.maxNumBsnsInStateTimeWindow = Math.max(maxNumBsnsInStateTimeWindow, numBsnsInStateTimeWindow);
      this.numJsonFileGernerated++;
    }
  }

  @Override
  public void queryDBAndGenerateGeoJSON() throws Exception {
    System.out.println("Do Something here!");

    for (String stateInitial : stateAry) {
      for (int hour=0; hour<24; hour++) {
        for (int week=0; week<7; week++) {
          this.generateGeoJSON(stateInitial, String.valueOf(hour), String.valueOf(week));
        }
      }
    }

    System.out.println("maxNumBsnsInStateTimeWindow:"+this.maxNumBsnsInStateTimeWindow+" "
        + ", numJsonFileGernerated:"+this.numJsonFileGernerated);
    System.out.println("GeoJSON file now has been generated! "); 
  }

  @Override
  protected void mySQLDatabaseOperations(boolean resetDB, boolean autoCommit) throws Exception {
    this.queryDBAndGenerateGeoJSON();
    if (!autoCommit) {
      connect.commit();
    }
  }

  @Override
  public void callMethods() throws Exception {
    jedis.flushAll();

    this.connectMySQL(false, true); // boolean resetDB, autoCommit

    jedis.close();
  }

  public static void main(String[] argv) throws Exception {
    String logFilePath = "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt";
    String businessFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_business.json";
    String checkinFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json";

    // save data to MySQL
    DataStoreMySQLByPropertiesBatchSQL dsMysqlPropBatch = new DataStoreMySQLByPropertiesBatchSQL(
      logFilePath, businessFilePath, checkinFilePath);

    boolean saveData2DB = false;  //@@@
    if (saveData2DB) {
      dsMysqlPropBatch.run();
    }

    // Query MySQL and generate GeoJSON
    QueryMySQLGenerateGeoJSONByProperties gnrtGeoJSON = new QueryMySQLGenerateGeoJSONByProperties(
      logFilePath, businessFilePath, checkinFilePath);

    gnrtGeoJSON.run();
  }
}
