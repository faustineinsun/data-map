package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author Fei Yu (@faustineinsun)
 */

import java.io.File;
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
 * @author Fei Yu (@faustineinsun)
 * Example Code of GeoJSON
{
    "type": "FeatureCollection",
    "features": [
      {
        "type": "Feature",
        "properties": {
          "businessId":"adfa]239r90a9df",
          "businessName": "Checkers Rally's",
          "businessAddress": "\"7210 S Durango Dr\\nSouthwest\\nLas Vegas, NV 89113\"",
          "businessCategories": "[\"Burgers\",\"Fast Food\",\"Sandwiches\",\"Restaurants\"]",
          "dayInWeekCount":[[0,9],[1,3],[2,100],[3,5],[4,7],[5,3],[6,0]],
          "dayInWeekCountPredictedProb":[[0,0.06256],[1,0.09311],[2,0.11299],[3,0.13450],[4,0.13550],[5,0.38106],[6,0.08028]],
          "MaxCheckinCountDayInWeek":"2",
          "checkInCountTimeWindow":"6",
          "timeWindow":"17-3"
        },
        "geometry": {
          "type": "Point",
          "coordinates": [-115.279,36.0572]
        }
      },{
        "type": "Feature",
        "properties": {
          "businessId":"adsafadffa]239r90a9df",
          "businessName": "Red Rice",
          "businessAddress": "\"9400 S  Eastern Ave\\nSte 106A\\nSoutheast\\nLas Vegas, NV 89123\"",
          "businessCategories": "[\"Food\",\"Ethnic Food\",\"Specialty Food\"]",
          "dayInWeekCount":[[0,900],[1,3000],[2,100],[3,5000],[4,700],[5,30],[6,5000]],
          "dayInWeekCountPredictedProb":[[0,0.03828],[1,0.05920],[2,0.04781],[3,0.08197],[4,0.29545],[5,0.40551],[6,0.07179]],
          "MaxCheckinCountDayInWeek":"-1",
          "checkInCountTimeWindow":"6",
          "timeWindow":"17-3"
        },
        "geometry": {
          "type": "Point",
          "coordinates": [-115.118,36.0184]
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
      "SELECT Businesses.*, Checkin.`CheckinTimeWindowArray`, Checkin.`MaxCheckinCountDayInWeek`, CheckinPredicted.`CheckinTimeWindowArrayPredicted`, Business_checkin.`HourWeekTimeWindow`, Business_checkin.`Hour`, Business_checkin.`Week`, Business_checkin.`Count` " 
          +"FROM  Businesses LEFT JOIN Checkin "
          +"ON Businesses.`BusinessId` = Checkin.`BusinessId` "
          +"LEFT JOIN CheckinPredicted "
          +"ON Businesses.`BusinessId` = CheckinPredicted.`BusinessId` "
          +"LEFT JOIN Business_checkin "
          +"ON Businesses.`BusinessId` = Business_checkin.`BusinessId` "
          +"WHERE Businesses.`State` = ? "
          +"AND Business_checkin.`Hour` = ? AND Business_checkin.`Week` = ?");
    preparedStatement.setString(1, State);
    preparedStatement.setString(2, Hour);
    preparedStatement.setString(3, Week);
    //System.out.println("------"+preparedStatement.toString());

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
      geoJsonFeatureProp.setBusinessId(resultSet.getString("BusinessId"));
      geoJsonFeatureProp.setBusinessName(resultSet.getString("Name"));
      geoJsonFeatureProp.setBusinessAddress(resultSet.getString("FullAddress"));
      geoJsonFeatureProp.setBusinessCategories(resultSet.getString("Category"));
      geoJsonFeatureProp.setDayInWeekCount(resultSet.getString("CheckinTimeWindowArray"));
      geoJsonFeatureProp.setDayInWeekCountPredictedProb(resultSet.getString("CheckinTimeWindowArrayPredicted"));
      geoJsonFeatureProp.setMaxCheckinCountDayInWeek(resultSet.getString("MaxCheckinCountDayInWeek"));
      geoJsonFeatureProp.setCheckInCountTimeWindow(resultSet.getString("Count"));
      geoJsonFeatureProp.setTimeWindow(resultSet.getString("Hour")+":"+resultSet.getString("Week"));
      //System.out.println("geoJsonFeatureProp: "+geoJsonFeatureProp.toString());

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
       */
      if (State.equals("NV")&&Hour.equals("13")&&Week.equals("5")) {
        ObjectMapper businessFeatureClctnMapper = new ObjectMapper();
        businessFeatureClctnMapper.writeValue(new File("src/main/resources/yelp-dataset/json/businessFeatureClctn"
            +"_"+State
            +"_"+Hour
            +"_"+Week
            +".json"), businessFeatureClctn);
      }

      // save generated GeoJSON file to Redis in-memory database 
      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String businessFeatureClctnJson = ow.writeValueAsString(businessFeatureClctn);
      jedis.set("businessGeoJSON"+"_"+State+"_"+Hour+"_"+weekAry[Integer.valueOf(Week)], businessFeatureClctnJson);
      //System.out.println("businessGeoJSON"+"_"+State+"_"+Hour+"_"+weekAry[Integer.valueOf(Week)]+":"+jedis.get("businessGeoJSON"+"_"+State+"_"+Hour+"_"+weekAry[Integer.valueOf(Week)]));

      // Statistics
      System.out.println(State+":"+Hour+":"+ Week +" -> "+numBsnsInStateTimeWindow);
      if (numBsnsInStateTimeWindow==8889) {
        /*
        this.logWriter.println("\n"+State+":"+Hour+":"+ Week +" -> "+numBsnsInStateTimeWindow);
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

    // Query MySQL and generate GeoJSON
    QueryMySQLGenerateGeoJSONByProperties gnrtGeoJSON = new QueryMySQLGenerateGeoJSONByProperties(
      logFilePath, businessFilePath, checkinFilePath);

    gnrtGeoJSON.run();
  }
}
