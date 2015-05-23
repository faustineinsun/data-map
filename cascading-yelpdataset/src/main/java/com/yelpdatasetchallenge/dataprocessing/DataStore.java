package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author feiyu
 */

import java.io.IOException;
import java.io.PrintWriter;

abstract class DataStore  {
  protected PrintWriter logWriter;

  public void run(String logFilePath) throws IOException{
    logWriter = new PrintWriter(logFilePath, "UTF-8");

    callMethods();

    System.out.println("\nDone!!");
    logWriter.close();
  }

  abstract public void callMethods() throws IOException;
}
