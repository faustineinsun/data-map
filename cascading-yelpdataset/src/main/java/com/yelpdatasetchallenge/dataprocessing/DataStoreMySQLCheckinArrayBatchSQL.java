package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author Fei Yu (@faustineinsun)
 * accumulate check-in count by day in week for each business
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.HashMap;
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

    /*
     * @@@ what if multiple days have the same count and it happens to be the maxCount? 
     * Solution: in order to increase the accuracy, this business record should be removed from the training data set, 
     *    To exclude those kind of records, 
     *      we can use a hashmap to record the mapping between the count and how much times this count occurs in the array
     */
    int maxCount = -1; 
    int maxCountIdx = -1;
    HashMap<Integer, Integer> countHappenTimesMap = new HashMap<Integer, Integer>();
    String chartDataDayCount = "[";
    for (int i=0; i<7; i++) {
      int countByDayInWeek = countByDayInWeekAry[i];

      // generate [dayInWeek, count], e.g. [5,26] means there are totally 26 check-in count on Friday of this business
      int[] dayCount = new int[2];
      dayCount[0] = i;
      dayCount[1] = countByDayInWeek;
      chartDataDayCount += "["+dayCount[0]+","+dayCount[1]+"]"+(i!=6?",":"");

      // map the count with how much times this count occurs in the array 
      // e.g. in array [[0,50],[1,23],[2,50],[3,23],[4,23],[5,50],[6,7]]
      // countHappenTimesMap is looks like <50,3>, <23,3>, <7,1>
      int countHappenTimes = 1;
      if (countHappenTimesMap.containsKey(countByDayInWeek)) {
        countHappenTimes = countHappenTimesMap.get(countByDayInWeek)+1;
      } 
      countHappenTimesMap.put(countByDayInWeek, countHappenTimes);

      // find the maxCount and maxCountIdx
      if (countByDayInWeekAry[i] >= maxCount) {
        maxCount = countByDayInWeekAry[i];
        maxCountIdx = i;
      }
    }
    chartDataDayCount +="]";

    // check if there are multiple days have the maxCount
    if (countHappenTimesMap.get(maxCount) != 1) {
      // mark each business as -1 if it's maxCount happens in multiple days in a week
      maxCountIdx = -1; 
    }

    //System.out.println(chartDataDayCount);

    preparedStatement.setString(1, checkInObj.get("business_id").asText());
    preparedStatement.setString(2, chartDataDayCount);
    preparedStatement.setString(3, String.valueOf(maxCountIdx));
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
        .prepareStatement("INSERT IGNORE INTO Checkin(BusinessId, CheckinTimeWindowArray, MaxCheckinCountDayInWeek) VALUES(?,?,?);");

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
