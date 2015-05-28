package com.yelpdatasetchallenge.dataprocessing;

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

  public QueryMySQLGenerateGeoJSONByProperties(String logFilePath, String businessFilePath,
                                               String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private void queryDB(String State, String Hour, String Week) {

  }

  public void generateGeoJSON() {

  }


  @Override
  public void queryDBAndGenerateGeoJSON() {
    this.queryDB("NV", "19", "5");
    this.generateGeoJSON();
    System.out.println("Do Something here!");
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
