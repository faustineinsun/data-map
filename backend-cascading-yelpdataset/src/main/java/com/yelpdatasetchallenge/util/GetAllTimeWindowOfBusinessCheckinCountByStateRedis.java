package com.yelpdatasetchallenge.util;

/**
 * @author feiyu
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import redis.clients.jedis.Jedis;
import driven.com.fasterxml.jackson.databind.JsonNode;
import driven.com.fasterxml.jackson.databind.ObjectMapper;

public class GetAllTimeWindowOfBusinessCheckinCountByStateRedis {

  public void saveBusinessInfoIntoRedis(Jedis jedis) throws IOException   {
    BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yelp-dataset/yelp_academic_dataset_business.json"));
    ObjectMapper mapper = new ObjectMapper();

    try {
      String line = br.readLine();
      while (line != null) {
        JsonNode actualObj = mapper.readTree(line);
        JsonNode businessID = actualObj.get("business_id");
        jedis.set(businessID.asText(), actualObj.toString());

        line = br.readLine();
      }
    } finally {
      br.close();
    }
    System.out.println("DB Size: "+jedis.dbSize());
    System.out.println(jedis.get("6TPxhpHqFedjMvBuw6pF3w"));
  }

  public static void main(String[] argv) throws IOException {
    GetAllTimeWindowOfBusinessCheckinCountByStateRedis byState = new GetAllTimeWindowOfBusinessCheckinCountByStateRedis();
    Jedis jedis = new Jedis("localhost");
    jedis.flushAll();

    byState.saveBusinessInfoIntoRedis(jedis);

    jedis.close();
  }
}
