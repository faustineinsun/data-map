package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author feiyu
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DataStoreMySQL extends DataStore {

  public DataStoreMySQL(String logFilePath, String businessFilePath, String checkinFilePath) throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  private FileInputStream in = null;
  private Properties props = new Properties();
  protected Connection connect = null;
  protected Statement statement = null;
  protected PreparedStatement preparedStatement = null;
  protected ResultSet resultSet = null;

  protected void connectMySQL(boolean resetDB, boolean autoCommit) throws Exception {
    // read file 'database.properties'
    try {
      in = new FileInputStream("src/main/resources/database.properties");
      props.load(in);
    } catch (FileNotFoundException e) {
      logWriter.print("Can't find the file 'database.properties' !");
      e.printStackTrace();
      return;
    }

    // load the MySQL driver (each DB has its own driver)
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      logWriter.print("Please set your MySQL JDBC Driver.");
      e.printStackTrace();
      return;
    }
    System.out.println(DataStoreMySQL.class.getName()+": MySQL JDBC Driver Registered!");
    logWriter.print(DataStoreMySQL.class.getName()+": MySQL JDBC Driver Registered!");

    // connect with the DB
    String url = props.getProperty("db.url");
    String user = props.getProperty("db.user");
    String passwd = props.getProperty("db.passwd");
    try {
      connect = DriverManager
          .getConnection(url,user, passwd);
    } catch (SQLException e) {
      logWriter.print("Connection Failed! Check output console, please.");
      e.printStackTrace();
      return;
    }

    // sql operations
    try {
      statement = connect.createStatement();
      connect.setAutoCommit(autoCommit); 

      this.mySQLDatabaseOperations(resetDB, autoCommit);

    } catch (SQLException ex) {

      if (!autoCommit) {
        if (connect != null) {
          try {
            connect.rollback();
          } catch (SQLException ex1) {
            logWriter.print(DataStoreMySQL.class.getName()+" Error: connect.rollback()");
            ex1.printStackTrace();
          }
        }
      }

      logWriter.print(DataStoreMySQL.class.getName()+" SQL Exception");
    } finally {
      close();
    }

  }

  protected void mySQLDatabaseOperations(boolean resetDB, boolean autoCommit) throws Exception {
  }

  protected void resetDatabaseAndTables(){
    System.out.println("\n*******************************************\n"
        + "***Start of Reseting Database and Tables***");
    this.runSqlScript("src/main/resources/sql/CreatDB.sql");
    this.runSqlScript("src/main/resources/sql/CreatTables.sql");
    System.out.println("\n***End of Reseting Database and Tables***"
        + "\n*****************************************");
  }

  protected void runSqlScript(String sqlFilePath) {
    try {
      ScriptRunner sr = new ScriptRunner(connect);
      Reader reader = new BufferedReader(new FileReader(sqlFilePath));

      sr.runScript(reader);

    } catch (Exception e) {
      logWriter.print("Failed to Execute" + sqlFilePath
        + " The error is " + e.getMessage());
    }
  }

  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {
      logWriter.print("Error: close()");
    }
  }

  @Override
  public void callMethods() throws Exception {
  }
}
