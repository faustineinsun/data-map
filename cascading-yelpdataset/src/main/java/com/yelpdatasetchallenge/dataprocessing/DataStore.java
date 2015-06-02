package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author feiyu
 */

import java.io.PrintWriter;

abstract class DataStore  {
  protected PrintWriter logWriter;
  protected String logFilePath; 
  protected String businessFilePath; 
  protected String checkinFilePath;

  public DataStore (String logFilePath, String businessFilePath, String checkinFilePath) throws Exception {
    this.logWriter = new PrintWriter(logFilePath, "UTF-8");
    this.businessFilePath = businessFilePath;
    this.checkinFilePath = checkinFilePath;
  }

  public void run() throws Exception{

    callMethods();

    System.out.println("\nDone!!");
    logWriter.close();
  }

  abstract public void callMethods() throws Exception;
}
