package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author feiyu
 * deprecated by DataStoreMySQLByPropertiesBatchSQL.java
 * since it can't handle large amount of insert operations
 * we need batch insert queries
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import driven.com.fasterxml.jackson.core.JsonFactory;
import driven.com.fasterxml.jackson.core.JsonParser;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class DataStoreMySQLByProperties extends DataStoreMySQL implements BusinessCheckInWindowInterface {

  public DataStoreMySQLByProperties(String logFilePath, String businessFilePath,
                                    String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private void sqlInsertBusinessInfo(JsonNode businessObj) throws SQLException, IOException {
    preparedStatement = connect
        .prepareStatement("INSERT INTO Businesses(BusinessId, Name, FullAddress, City, State, Latitude, Longitude, Category) "
            + "VALUES(?,?,?,?,?,?,?,?)");
    preparedStatement.setString(1, businessObj.get("business_id").asText());
    preparedStatement.setString(2, businessObj.get("name").asText());
    preparedStatement.setString(3, businessObj.get("full_address").toString());
    preparedStatement.setString(4, businessObj.get("city").asText());
    preparedStatement.setString(5, businessObj.get("state").asText());
    preparedStatement.setFloat(6, Float.valueOf(businessObj.get("latitude").asText()));
    preparedStatement.setFloat(7, Float.valueOf(businessObj.get("longitude").asText()));
    preparedStatement.setString(8, businessObj.get("categories").toString());

    preparedStatement.executeUpdate();

    this.sqlInsertCategoryInfo(businessObj);
  }

  private void sqlInsertCategoryInfo(JsonNode businessObj) throws IOException, SQLException {
    ObjectMapper categoryListMapper = new ObjectMapper();
    JsonFactory categoryListFactory = categoryListMapper.getFactory();
    JsonParser categoryListParser = categoryListFactory.createParser(businessObj.get("categories").toString());
    @SuppressWarnings("unchecked")
    List<String> categoryList = categoryListMapper.readValue(categoryListParser, List.class);
    for (String category : categoryList) {
      // System.out.println("&&&&&&&& "+category);

      preparedStatement = connect
          .prepareStatement("INSERT IGNORE INTO Categories(Category) VALUES(?)");
      preparedStatement.setString(1, category);
      preparedStatement.executeUpdate();

      preparedStatement = connect
          .prepareStatement("INSERT INTO Business_Category(BusinessId, State, Category) VALUES(?,?,?)");
      preparedStatement.setString(1, businessObj.get("business_id").asText());
      preparedStatement.setString(2, businessObj.get("state").asText());
      preparedStatement.setString(3, category);
      preparedStatement.executeUpdate();

      preparedStatement = connect
          .prepareStatement("INSERT IGNORE INTO Cities(City, State) VALUES(?,?)");
      preparedStatement.setString(1, businessObj.get("city").asText());
      preparedStatement.setString(2, businessObj.get("state").asText());
      preparedStatement.executeUpdate();
    }
  }

  private void sqlInsertCheckInInfo(JsonNode checkInObj) throws SQLException, ParseException {
    /*
      e.g.: checkinInfoObj: {"9-5":1,"7-5":1,"13-3":1,"17-6":1,"13-0":1,"17-3":1,"10-0":1,"18-4":1,"14-6":1}
      iterate through checkinInfoObj and get all of the <keyTimeWindow,valueCount> pairs, 
     */
    JsonNode checkinInfoObj = checkInObj.get("checkin_info");
    System.out.println(checkinInfoObj.toString());
    Iterator<Map.Entry<String,JsonNode>> checkinInfoFields = checkinInfoObj.fields();
    while (checkinInfoFields.hasNext()) {
      Map.Entry<String,JsonNode> checkinInfoField = checkinInfoFields.next();
      String keyTimeWindow = checkinInfoField.getKey();
      JsonNode valueCount = checkinInfoField.getValue();
      //      System.out.println(keyTimeWindow+": "+valueCount);

      String[] keyTimeWindowList = keyTimeWindow.split("-");

      preparedStatement = connect
          .prepareStatement("INSERT IGNORE INTO CheckinTimeWindow(HourWeekTimeWindow) VALUES(?)");
      preparedStatement.setString(1, keyTimeWindow);
      preparedStatement.executeUpdate();

      preparedStatement = connect
          .prepareStatement("INSERT INTO Business_Checkin(HourWeekTimeWindow, Hour, Week, BusinessId, Count) VALUES(?,?,?,?,?)");
      preparedStatement.setString(1, keyTimeWindow);
      preparedStatement.setInt(2, Integer.valueOf(keyTimeWindowList[0]));
      preparedStatement.setInt(3, Integer.valueOf(keyTimeWindowList[1]));
      preparedStatement.setString(4, checkInObj.get("business_id").asText());
      preparedStatement.setInt(5, Integer.valueOf(valueCount.asText()));
      preparedStatement.executeUpdate();
    }
  }

  @Override
  protected void mySQLDatabaseOperations(boolean resetDB, boolean autoCommit) throws Exception {
      if (resetDB) {
        this.resetDatabaseAndTables(); 
      } else {
        this.saveBusinessInfoToDataStore();
        this.getBusinessCheckInInfo();
        
        if (!autoCommit) {
          connect.commit();
        }
      }
  }

  @Override
  public void saveBusinessInfoToDataStore() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(businessFilePath));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        System.out.println(line);
        JsonNode businessObj = mapper.readTree(line);
        line = br.readLine();
        /*
        statement.addBatch("DELETE FROM Businesses");
        statement.addBatch("DELETE FROM Categories");
        statement.addBatch("DELETE FROM CheckinTimeWindow");
        statement.addBatch("DELETE FROM Cities");
        statement.addBatch("DELETE FROM Business_Category");
        statement.addBatch("DELETE FROM Business_Checkin");
         */
        this.sqlInsertBusinessInfo(businessObj);

      }
    } finally {
      br.close();
    }
  }


  @Override
  public void getBusinessCheckInInfo() throws IOException, SQLException, ParseException {
    BufferedReader br = new BufferedReader(new FileReader(checkinFilePath));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode checkInObj = mapper.readTree(line);
        line = br.readLine();

        statement.addBatch("DELETE FROM Checkin");
        this.sqlInsertCheckInInfo(checkInObj);

      }
    } finally {
      br.close();
    }
  }

  @Override
  public void callMethods() throws Exception {
    this.connectMySQL(true, true);
    this.connectMySQL(false, true);
  }

  public static void main(String[] argv) throws Exception {
    DataStoreMySQLByProperties dsMysqlProp = new DataStoreMySQLByProperties(
      "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt",
      "src/main/resources/yelp-dataset/yelp_academic_dataset_business_short.json",
        "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin_short.json");
    dsMysqlProp.run();
  }
}
