package com.yelpdatasetchallenge.dataprocessing;

/**
 * @author feiyu
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import redis.clients.jedis.Jedis;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class DataStoreRedis extends DataStore implements BusinessCheckInWindowInterface {
  
  public DataStoreRedis(String logFilePath, String businessFilePath, String checkinFilePath)
      throws Exception {
    super(logFilePath, businessFilePath, checkinFilePath);
  }

  Jedis jedis;

  @Override
  public void saveBusinessInfoToDataStore() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(businessFilePath));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode actualObj = mapper.readTree(line);
        line = br.readLine();

        // Map business_id with it's json info
        JsonNode businessID = actualObj.get("business_id");
        jedis.set(businessID.asText(), actualObj.toString());
      }
    } finally {
      br.close();
    }
    // check if everything is right
    logWriter.print("DB Size: "+jedis.dbSize());
    logWriter.print("\n"+jedis.get("6TPxhpHqFedjMvBuw6pF3w"));    
  }

  @Override
  public void getBusinessCheckInInfo() throws IOException {
  }

  @Override
  public void callMethods() throws IOException {
  }

  protected void initJedis(String path){
    jedis = new Jedis(path);
  }

  protected void clearJedis() {
    jedis.flushAll();
  }

  protected void closeJedis() {
    jedis.close();
  }
}
