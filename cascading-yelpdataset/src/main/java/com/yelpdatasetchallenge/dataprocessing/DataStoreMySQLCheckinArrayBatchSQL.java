package com.yelpdatasetchallenge.dataprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import driven.com.fasterxml.jackson.core.JsonProcessingException;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class DataStoreMySQLCheckinArrayBatchSQL extends DataStoreMySQL implements BusinessCheckInWindowInterface  {

  public DataStoreMySQLCheckinArrayBatchSQL(String logFilePath, String businessFilePath,
                                            String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  @Override
  public void saveBusinessInfoToDataStore() throws Exception {
  }

  private void mapCheckinArrayWithBusinussID(JsonNode checkInObj, int numLines) throws SQLException, JsonProcessingException {
    /*
    e.g.: checkinInfoObj: {"9-5":1,"7-5":1,"13-3":1,"17-6":1,"13-0":1,"17-3":1,"10-0":1,"18-4":1,"14-6":1}
    iterate through checkinInfoObj and get all of the <keyTimeWindow,valueCount> pairs, 
     */
    JsonNode checkinInfoObj = checkInObj.get("checkin_info");
    System.out.println(checkinInfoObj.toString());

    int[] countByDayInWeekAry = new int[7];
    Iterator<Map.Entry<String,JsonNode>> checkinInfoFields = checkinInfoObj.fields();
    while (checkinInfoFields.hasNext()) {
      Map.Entry<String,JsonNode> checkinInfoField = checkinInfoFields.next();
      String keyTimeWindow = checkinInfoField.getKey();
      JsonNode valueCount = checkinInfoField.getValue();
      //System.out.println(keyTimeWindow+": "+valueCount);

      String[] keyTimeWindowList = keyTimeWindow.split("-");
      int dayInWeek = Integer.valueOf(keyTimeWindowList[1]);
      countByDayInWeekAry[dayInWeek] += valueCount.asInt();
    }

    String chartDataDayCount = "[";
    for (int i=0; i<7; i++) {
      int[] dayCount = new int[2];
      dayCount[0] = i;
      dayCount[1] = countByDayInWeekAry[i];
      chartDataDayCount += "["+dayCount[0]+","+dayCount[1]+"]"+(i!=6?",":"");
    }
    chartDataDayCount +="]";

    //System.out.println(chartDataDayCount);

    preparedStatement.setString(1, checkInObj.get("business_id").asText());
    preparedStatement.setString(2, chartDataDayCount);
    preparedStatement.addBatch();

    if ((numLines+1) % 2000 == 0) {
      preparedStatement.executeBatch();
      connect.commit();
    }
  }

  @Override
  public void getBusinessCheckInInfo() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(checkinFilePath));
    ObjectMapper mapper = new ObjectMapper();

    preparedStatement = connect
        .prepareStatement("INSERT IGNORE INTO Checkin(BusinessId, CheckinTimeWindowArray) VALUES(?,?);");

    try {
      String line = br.readLine();
      int numLines = 0;
      while (line != null) {
        JsonNode checkInObj = mapper.readTree(line);
        line = br.readLine();

        this.mapCheckinArrayWithBusinussID(checkInObj, numLines);

        numLines++;
        System.out.println(numLines);
      }

      preparedStatement.executeBatch();
      connect.commit();
    } finally {
      br.close();
    }
  }

  @Override
  protected void mySQLDatabaseOperations(boolean resetDB, boolean autoCommit) throws Exception {
    this.getBusinessCheckInInfo();

    if (!autoCommit) {
      connect.commit();
    }
  }

  @Override
  public void callMethods() throws Exception {
    this.connectMySQL(false, false); // resetDB = false, autoCommit = false
  }

  public static void main(String[] argv) throws Exception {
    DataStoreMySQLCheckinArrayBatchSQL dsCheckinBatch = new DataStoreMySQLCheckinArrayBatchSQL(
      "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt",
      "src/main/resources/yelp-dataset/yelp_academic_dataset_business.json",
        "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json");
    dsCheckinBatch.run();
  }
}
