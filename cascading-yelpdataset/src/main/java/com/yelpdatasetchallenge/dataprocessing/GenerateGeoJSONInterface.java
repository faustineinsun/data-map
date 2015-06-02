package com.yelpdatasetchallenge.dataprocessing;

import java.sql.SQLException;

/**
 * @author feiyu
 */

interface GenerateGeoJSONInterface {
  void queryDBAndGenerateGeoJSON() throws SQLException, Exception;
}
