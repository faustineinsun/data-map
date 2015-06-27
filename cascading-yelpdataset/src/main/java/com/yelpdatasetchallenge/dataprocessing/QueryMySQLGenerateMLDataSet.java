package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author Fei Yu (@faustineinsun)
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import driven.com.fasterxml.jackson.core.JsonFactory;
import driven.com.fasterxml.jackson.core.JsonParseException;
import driven.com.fasterxml.jackson.core.JsonParser;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class QueryMySQLGenerateMLDataSet extends DataStoreMySQL {
  private PrintWriter pWriterTrain = new PrintWriter("src/main/resources/ml/dataset/yelp-dataset/business_categories_maxCount_train.csv", "UTF-8");
  private PrintWriter pWriterTest = new PrintWriter("src/main/resources/ml/dataset/yelp-dataset/business_categories_maxCount_test.csv", "UTF-8");
  private PrintWriter pWriterOthers = new PrintWriter("src/main/resources/ml/dataset/yelp-dataset/business_categories_maxCount_others.csv", "UTF-8"); // business that doesn't provide category information
  private PrintWriter pWriterAll = new PrintWriter("src/main/resources/ml/dataset/yelp-dataset/business_categories_maxCount_all.csv", "UTF-8"); // business that doesn't provide category information
  private HashMap<String, Integer> categoryIdxMap = new HashMap<String, Integer>();
  private int nCategories;

  public QueryMySQLGenerateMLDataSet(String logFilePath, String businessFilePath,
                                     String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private void indexCategories() throws SQLException {
    preparedStatement = connect.prepareStatement("SELECT Category FROM Categories");
    resultSet = preparedStatement.executeQuery();

    String titles = "BusinessId";
    int idx = 1;
    while (resultSet.next()) {
      String category = resultSet.getString("Category");

      titles += ","+category;
      categoryIdxMap.put(category, idx);

      idx++;
    }
    //titles += ",MaxCheckinDayInWeek";

    nCategories = categoryIdxMap.size();
    //System.out.println("size: "+nCategories);
    pWriterAll.println(titles+",MaxCheckinDayInWeek");
    pWriterTrain.println(titles+",MaxCheckinDayInWeek");
    pWriterTest.println(titles);
    pWriterOthers.println(titles);
  }

  private void queryDBLeftJoinBusinessesWithCheckin() throws SQLException {
    preparedStatement = connect.prepareStatement(
      "SELECT Businesses.`BusinessId`, Businesses.`Category`, Checkin.`MaxCheckinCountDayInWeek` "
          +"FROM  Businesses Left JOIN Checkin "
          +"ON Businesses.`BusinessId` = Checkin.`BusinessId`"
        );
    resultSet = preparedStatement.executeQuery();
  }

  private void generateMLDataSet() throws SQLException, JsonParseException, IOException {
    while (resultSet.next()) {
      String businessRecord = resultSet.getString("BusinessId");
      String categoryListStr = resultSet.getString("Category");
      String maxCheckinCountDayInWeek = resultSet.getString("MaxCheckinCountDayInWeek");
      System.out.println(businessRecord+": "+maxCheckinCountDayInWeek);

      ObjectMapper categoryListMapper = new ObjectMapper();
      JsonFactory categoryListFactory = categoryListMapper.getFactory();
      JsonParser categoryListParser = categoryListFactory.createParser(categoryListStr);
      @SuppressWarnings("unchecked")
      List<String> categoryList = categoryListMapper.readValue(categoryListParser, List.class);

      if (categoryList.size() <= 0 ) {
        // business doesn't provide category information
        pWriterOthers.println(businessRecord+","+String.valueOf(categoryListStr)); 
      } else {
        // business has provided category information
        HashSet<Integer> categoryIdxSet = new HashSet<Integer>();
        for (String category : categoryList) {
          // System.out.println("&&&&&&&& category: "+category);
          // save category label index into categoryIdxSet
          categoryIdxSet.add(categoryIdxMap.get(category));
        }

        String businessRecordStr = businessRecord;
        for (int i=1; i<=nCategories; i++) {
          if (categoryIdxSet.contains(i)) {
            businessRecordStr +=",1";
          } else {
            businessRecordStr +=",0";
          }
        }

        if (maxCheckinCountDayInWeek==null || maxCheckinCountDayInWeek.equals("-1")) {
          // maxCheckinCountDayInWeek.equals("null") means when a business hasn't had check-in information in the history
          // maxCheckinCountDayInWeek.equals("-1") means when a business happens to have multiple days with the same maxCount (max check-in count in history)
          pWriterTest.println(businessRecordStr); 
          pWriterAll.println(businessRecordStr+",-1");
        } else {
          businessRecordStr +=","+String.valueOf(maxCheckinCountDayInWeek);
          pWriterTrain.println(businessRecordStr); 
          pWriterAll.println(businessRecordStr);
        }
      }
    }
  }

  public void iterateAllBusinessesAndGenerateMLDataSet() throws SQLException, JsonParseException, IOException {

    this.indexCategories();
    this.queryDBLeftJoinBusinessesWithCheckin();
    this.generateMLDataSet();

    pWriterTrain.close();
    pWriterTest.close();
    pWriterOthers.close();
    pWriterAll.close();
  }

  @Override
  protected void mySQLDatabaseOperations(boolean resetDB, boolean autoCommit) throws Exception {
    this.iterateAllBusinessesAndGenerateMLDataSet();
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

    QueryMySQLGenerateMLDataSet gnrtMLDataSet = new QueryMySQLGenerateMLDataSet(
      logFilePath, businessFilePath, checkinFilePath);

    gnrtMLDataSet.run();
  }

}
