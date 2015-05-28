package com.yelpdatasetchallenge.dataprocessing;

import java.sql.SQLException;

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
  private int maxNumBsnsInStateTimeWindow = Integer.MIN_VALUE;
  private int numJsonFileGernerated = 0;

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

    int numBsnsInStateTimeWindow=0;
    while (resultSet.next()) {
      // System.out.println(resultSet.getString("BusinessId"));
      ++numBsnsInStateTimeWindow;
    }
    if (numBsnsInStateTimeWindow>0) {
      System.out.println(State+":"+Hour+":"+ Week +" -> "+numBsnsInStateTimeWindow);
      if (numBsnsInStateTimeWindow==8889) {
        this.logWriter.println("\n"+State+":"+Hour+":"+ Week +" -> "+numBsnsInStateTimeWindow);
      }
      this.maxNumBsnsInStateTimeWindow = Math.max(maxNumBsnsInStateTimeWindow, numBsnsInStateTimeWindow);
      this.numJsonFileGernerated++;
    }
  }

  public void generateGeoJSON() {

  }

  @Override
  public void queryDBAndGenerateGeoJSON() throws SQLException {
    System.out.println("Do Something here!");

    for (String stateInitial : stateAry) {
      for (int hour=0; hour<24; hour++) {
        for (int week=0; week<7; week++) {
          this.queryDB(stateInitial, String.valueOf(hour), String.valueOf(week));
        }
      }
    }

    this.generateGeoJSON();
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
    this.connectMySQL(false, true); // boolean resetDB, autoCommit
  }

  public static void main(String[] argv) throws Exception {
    String logFilePath = "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt";
    String businessFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_business.json";
    String checkinFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json";

    // save data to MySQL
    DataStoreMySQLByPropertiesBatchSQL dsMysqlPropBatch = new DataStoreMySQLByPropertiesBatchSQL(
      logFilePath, businessFilePath, checkinFilePath);

    boolean saveData2DB = false;
    if (saveData2DB) {
      dsMysqlPropBatch.run();
    }

    // Query MySQL and generate GeoJSON
    QueryMySQLGenerateGeoJSONByProperties gnrtGeoJSON = new QueryMySQLGenerateGeoJSONByProperties(
      logFilePath, businessFilePath, checkinFilePath);

    gnrtGeoJSON.run();
  }
}
