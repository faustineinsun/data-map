package com.yelpdatasetchallenge.dataprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import driven.com.fasterxml.jackson.core.JsonFactory;
import driven.com.fasterxml.jackson.core.JsonParser;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class DataStoreMySQLByProperties extends DataStoreMySQL {

  @Override
  public void saveBusinessInfoToDataStore() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_business.json"));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        System.out.println(line);
        JsonNode businessObj = mapper.readTree(line);
        line = br.readLine();

        statement.addBatch("DELETE FROM Businesses");
        statement.addBatch("DELETE FROM Categories");
        this.sqlInsertBusinessInfo(businessObj);

      }
//      this.showResults();
    } finally {
      br.close();
    }
  }

  private void sqlInsertBusinessInfo(JsonNode businessObj) throws SQLException, IOException {
    preparedStatement = connect
        .prepareStatement("INSERT INTO Businesses(BusinessId, Name, FullAddress, City, State, Latitude, Longitude) "
            + "VALUES(?,?,?,?,?,?,?)");
    preparedStatement.setString(1, businessObj.get("business_id").asText());
    preparedStatement.setString(2, businessObj.get("name").asText());
    preparedStatement.setString(3, businessObj.get("full_address").toString());
    preparedStatement.setString(4, businessObj.get("city").asText());
    preparedStatement.setString(5, businessObj.get("state").asText());
    preparedStatement.setFloat(6, Float.valueOf(businessObj.get("latitude").asText()));
    preparedStatement.setFloat(7, Float.valueOf(businessObj.get("longitude").asText()));
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
          .prepareStatement("INSERT INTO Categories(Category, BusinessId) "
              + "VALUES(?,?)");
      preparedStatement.setString(1, category);
      preparedStatement.setString(2, businessObj.get("business_id").toString());
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void getBusinessCheckInInfo() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_checkin_short.json"));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode actualObj = mapper.readTree(line);
        line = br.readLine();

        // Map business_id with it's json info
        JsonNode businessID = actualObj.get("business_id");
      }
    } finally {
      br.close();
    }
  }

  @Override
  public void callMethods() throws Exception {
//    this.mySQLDatabaseOperations(true);
    this.mySQLDatabaseOperations(false);
  }
  
  @SuppressWarnings("unused")
  private void showResults() throws SQLException {
    System.out.println("//////////////////////////");

    System.out.println("\nBusinesses table");
    preparedStatement = connect
        .prepareStatement("SELECT * from Businesses");
    resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      System.out.println("-------");
      System.out.println("BusinessId: " + resultSet.getString("BusinessId"));
      System.out.println("Name: " + resultSet.getString("Name"));
      System.out.println("FullAddress: " + resultSet.getString("FullAddress"));
      System.out.println("City: " + resultSet.getString("City"));
      System.out.println("State: " + resultSet.getString("State"));
      System.out.println("Latitude: " + resultSet.getString("Latitude"));
      System.out.println("Longitude: " + resultSet.getString("Longitude"));
    }

    System.out.println("\nCategories table");
    preparedStatement = connect
        .prepareStatement("SELECT * from Categories");
    resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      System.out.println("-------");
      System.out.println("Category: " + resultSet.getString("Category"));
      System.out.println("BusinessId: " + resultSet.getString("BusinessId"));
    }
  }



  public static void main(String[] argv) throws Exception {
    DataStoreMySQLByProperties dsMysqlProp = new DataStoreMySQLByProperties();
    dsMysqlProp.run("src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt");
  }
}
