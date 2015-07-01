package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author Fei Yu (@faustineinsun)
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class DataStoreMySQLSavePredictedCheckinArrayBatchSQL extends DataStoreMySQL {
  private String predictedAllFilePathXGBoost = "src/main/resources/ml/dataset/yelp-dataset/yelp_XGBoostModel_all.txt";
  private String predictedAllFilePathRandomForest = "src/main/resources/ml/dataset/yelp-dataset/yelp_RandomForest_all.txt";
  private String predictedAllFilePathH2ODeepLearning = "src/main/resources/ml/dataset/yelp-dataset/yelp_h2oDeepLearning_all.txt";

  public DataStoreMySQLSavePredictedCheckinArrayBatchSQL(String logFilePath,
                                                         String businessFilePath, String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private String[] reshapeDayInWeekProbAry(String line) {
    String[] map = new String[2];
    String dayInWeekProb[] = line.split(",");

    String chartDayInWeekPredictedCount = "[";
    for (int i=0; i<7; i++) {
      chartDayInWeekPredictedCount += "["+String.valueOf(i)+","+dayInWeekProb[i+1]+"]"+(i!=6?",":"");
    }
    chartDayInWeekPredictedCount +="]";

    map[0] = dayInWeekProb[0];
    map[1] = chartDayInWeekPredictedCount;
    return map;
  }

  private void save2MySQLbyBatch(String lineXGBoost, String lineRandomForest, String lineH2ODeepLearning, int numLines) throws SQLException {
    String[] chartDayInWeekPredictedCountXGBoost = this.reshapeDayInWeekProbAry(lineXGBoost);
    String[] chartDayInWeekPredictedCountRandomForest = this.reshapeDayInWeekProbAry(lineRandomForest);
    String[] chartDayInWeekPredictedCountH2ODeepLearning = this.reshapeDayInWeekProbAry(lineH2ODeepLearning);

    if (!chartDayInWeekPredictedCountXGBoost[0].equals(chartDayInWeekPredictedCountRandomForest[0]) 
        || !chartDayInWeekPredictedCountXGBoost[0].equals(chartDayInWeekPredictedCountH2ODeepLearning[0]) ) {
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! BusinessId from files are different");
    }

    System.out.println(chartDayInWeekPredictedCountXGBoost[0]);
    preparedStatement.setString(1, chartDayInWeekPredictedCountXGBoost[0]);
    preparedStatement.setString(2, chartDayInWeekPredictedCountXGBoost[1]);
    preparedStatement.setString(3, chartDayInWeekPredictedCountRandomForest[1]);
    preparedStatement.setString(4, chartDayInWeekPredictedCountH2ODeepLearning[1]);
    preparedStatement.addBatch();

    if ((numLines+1) % 2000 == 0) {
      preparedStatement.executeBatch();
      connect.commit();
    }
  }

  private void savePredictedCheckinArray2MySQL() throws SQLException, IOException {
    BufferedReader brXGBoost = new BufferedReader(new FileReader(predictedAllFilePathXGBoost));
    BufferedReader brRandomForest = new BufferedReader(new FileReader(predictedAllFilePathRandomForest));
    BufferedReader brH2ODeepLearning = new BufferedReader(new FileReader(predictedAllFilePathH2ODeepLearning));

    preparedStatement = connect
        .prepareStatement("INSERT IGNORE INTO CheckinPredicted("
            + "BusinessId, "
            + "CheckinTimeWindowArrayPredictedXGBoost, "
            + "CheckinTimeWindowArrayPredictedRandomForest, "
            + "CheckinTimeWindowArrayPredictedH2ODeepLearning"
            + ") VALUES(?,?,?,?);");

    try {
      String lineXGBoost = brXGBoost.readLine();
      String lineRandomForest = brRandomForest.readLine();
      String lineH2ODeepLearning = brH2ODeepLearning.readLine();
      int numLines = 0;
      while (lineXGBoost != null && lineRandomForest != null && lineH2ODeepLearning != null) {
        lineXGBoost = brXGBoost.readLine();
        lineRandomForest = brRandomForest.readLine();
        lineH2ODeepLearning = brH2ODeepLearning.readLine();

        if (lineXGBoost == null || lineRandomForest == null || lineH2ODeepLearning == null) {
          break;
        }

        this.save2MySQLbyBatch(lineXGBoost, lineRandomForest, lineH2ODeepLearning, numLines);

        numLines++;
        System.out.println(numLines);
      }

      preparedStatement.executeBatch();
      connect.commit();
    } finally {
      brXGBoost.close();
      brRandomForest.close();
      brH2ODeepLearning.close();
    }
  }

  @Override
  protected void mySQLDatabaseOperations(boolean resetDB, boolean autoCommit) throws Exception {
    this.savePredictedCheckinArray2MySQL(); 

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
