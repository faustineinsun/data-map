package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author Fei Yu (@faustineinsun)
 */

public class YelpDataProcessing {
  String logFilePath = "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt";
  String businessFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_business.json";
  String checkinFilePath = "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json";

  private void saveData2DB() throws Exception {
    // Save business and check-in info into MySQL
    DataStoreMySQLByPropertiesBatchSQL dsMysqlPropBatch = new DataStoreMySQLByPropertiesBatchSQL(
      logFilePath, businessFilePath, checkinFilePath);
    dsMysqlPropBatch.run();

    // Map business_id with CheckinTimeWindowArray and maximum check-in count by day in week
    DataStoreMySQLCheckinArrayBatchSQL dsCheckinBatch = new DataStoreMySQLCheckinArrayBatchSQL(
      logFilePath, businessFilePath, checkinFilePath);
    dsCheckinBatch.run();
  }

  private void generateMLDataSet() throws Exception {
    // generates the training and testing data for machine learning part
    QueryMySQLGenerateMLDataSet gnrtMLDataSet = new QueryMySQLGenerateMLDataSet(
      logFilePath, businessFilePath, checkinFilePath);
    gnrtMLDataSet.run();
  }

  private void generateGeoJSON() throws Exception {
    // save predicted check-in info into MySQL, the predicted info is produced by machine learning algorithm XGBoost
    // e.g. --XBxRlD92RaV6TyUnP8Ow, [[0,0.10942],[1,0.08489],[2,0.09964],[3,0.09180],[4,0.21017],[5,0.34499],[6,0.05908]]
    // [5,0.34499] means business "--XBxRlD92RaV6TyUnP8Ow" has the probably of 0.34499 to get more check-in from client at Friday
    DataStoreMySQLSavePredictedCheckinArrayBatchSQL predictedCheckinAry = new DataStoreMySQLSavePredictedCheckinArrayBatchSQL(
      logFilePath, businessFilePath, checkinFilePath);
    predictedCheckinAry.run();

    // Query MySQL, generate GeoJSON, and save GeoJSON to Redis,
    // so that the front-end can query Redis and show GeoJSON on Map
    QueryMySQLGenerateGeoJSONByProperties gnrtGeoJSON = new QueryMySQLGenerateGeoJSONByProperties(
      logFilePath, businessFilePath, checkinFilePath);
    gnrtGeoJSON.run();
  }

  public static void main(String[] argv) throws Exception {

    YelpDataProcessing yelpDP = new YelpDataProcessing();

    // Since pmml doesn't support XGBoost so far, we can't transfer XGBoost Model to PMML and thus can't use Cascading Pattern right now
    // therefore these three steps should be run separately
    // in step 2, we need to run R script in R IDE like RStudio
    int step = 3;
    switch(step) {
      case 1: 
        // Step 1: save data to DB and generate ML Data Set
        yelpDP.saveData2DB();
        yelpDP.generateMLDataSet();
        break;
      case 2: 
        // Step 2: train XGBoost & Random Forest & H2O Deep Learning in R IDE
        // cascading pattern (Sadly pmml doesn't contain XGBoost and h2o.deeplearning so far)
        System.out.println("Step 2: train XGBoost & Random Forest & H2O Deep Learning in R IDE");
        break;
      case 3: 
        // Step 3: generate GeoJSON
        yelpDP.generateGeoJSON();
        break;
      default: 
        System.out.println("Nothing is chosen!! Choose step 1, 2, or 3");
        break;
    }
  }
}
