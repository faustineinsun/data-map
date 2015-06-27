package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author Fei Yu (@faustineinsun)
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class DataStoreMySQLSavePredictedCheckinArrayBatchSQL extends DataStoreMySQL {
  private String predictedAllFilePath = "src/main/resources/ml/dataset/yelp-dataset/XGBoostModel_yelp_all.txt";

  public DataStoreMySQLSavePredictedCheckinArrayBatchSQL(String logFilePath,
                                                         String businessFilePath, String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private void save2MySQLbyBatch(String line, int numLines) throws SQLException {
    String dayInWeekProb[] = line.split(",");

    String chartDayInWeekPredictedCount = "[";
    for (int i=0; i<7; i++) {
      chartDayInWeekPredictedCount += "["+String.valueOf(i)+","+dayInWeekProb[i+1]+"]"+(i!=6?",":"");
    }
    chartDayInWeekPredictedCount +="]";
    System.out.println(line);
    
    preparedStatement.setString(1, dayInWeekProb[0]);
    preparedStatement.setString(2, chartDayInWeekPredictedCount);
    preparedStatement.addBatch();

    if ((numLines+1) % 2000 == 0) {
      preparedStatement.executeBatch();
      connect.commit();
    }
  }

  private void savePredictedCheckinArray2MySQL(String filePath) throws SQLException, IOException {
    BufferedReader br = new BufferedReader(new FileReader(filePath));

    preparedStatement = connect
        .prepareStatement("INSERT IGNORE INTO CheckinPredicted(BusinessId, CheckinTimeWindowArrayPredicted) VALUES(?,?);");

    try {
      String line = br.readLine();
      int numLines = 0;
      while (line != null) {
        line = br.readLine();
        if (line == null) {
          break;
        }

        this.save2MySQLbyBatch(line, numLines);

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
    this.savePredictedCheckinArray2MySQL(predictedAllFilePath); 

    if (!autoCommit) {
      connect.commit();
    }
  }

  @Override
  public void callMethods() throws Exception {
    this.connectMySQL(false, false); // resetDB = false, autoCommit = false
  }

  public static void main(String[] argv) throws Exception {
    DataStoreMySQLSavePredictedCheckinArrayBatchSQL predictedCheckinAry = new DataStoreMySQLSavePredictedCheckinArrayBatchSQL(
      "src/main/resources/yelp-dataset/log_mysql_properties_yelp_academic_dataset.txt",
      "src/main/resources/yelp-dataset/yelp_academic_dataset_business.json",
        "src/main/resources/yelp-dataset/yelp_academic_dataset_checkin.json");
    predictedCheckinAry.run();
  }
}
