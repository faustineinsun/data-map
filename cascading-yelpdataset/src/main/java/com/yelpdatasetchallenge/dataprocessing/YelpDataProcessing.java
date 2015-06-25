package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author Fei Yu (@faustineinsun)
 */

public class YelpDataProcessing {
  public static void main(String[] argv) throws Exception {
    String logFilePath = "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt";
    String businessFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_business.json";
    String checkinFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json";

    boolean saveData2DB = false;  //@@@ or true
    boolean generateMLDataSet = true;
    boolean generateGeoJSON = true;

    if (saveData2DB) {
      // Save business and check-in info into MySQL
      DataStoreMySQLByPropertiesBatchSQL dsMysqlPropBatch = new DataStoreMySQLByPropertiesBatchSQL(
        logFilePath, businessFilePath, checkinFilePath);
      dsMysqlPropBatch.run();

      // Map business_id with CheckinTimeWindowArray and maximum check-in count by day in week
      DataStoreMySQLCheckinArrayBatchSQL dsCheckinBatch = new DataStoreMySQLCheckinArrayBatchSQL(
        logFilePath, businessFilePath, checkinFilePath);
      dsCheckinBatch.run();
    }

    if (generateMLDataSet) {
      // generates the training and testing data for machine learning part
      QueryMySQLGenerateMLDataSet gnrtMLDataSet = new QueryMySQLGenerateMLDataSet(
        logFilePath, businessFilePath, checkinFilePath);
      gnrtMLDataSet.run();
    }

    if (generateGeoJSON) {
      // Query MySQL, generate GeoJSON, and save GeoJSON to Redis,
      // so that the front-end can query Redis and show GeoJSON on Map
      QueryMySQLGenerateGeoJSONByProperties gnrtGeoJSON = new QueryMySQLGenerateGeoJSONByProperties(
        logFilePath, businessFilePath, checkinFilePath);
      gnrtGeoJSON.run();
    }
  }
}
