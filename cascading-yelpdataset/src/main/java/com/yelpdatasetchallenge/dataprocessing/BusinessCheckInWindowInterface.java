package com.yelpdatasetchallenge.dataprocessing;
/**
 * @author feiyu
 */

import java.io.IOException;

interface BusinessCheckInWindowInterface extends BusinessWindowInterface {
  void getBusinessCheckInInfo() throws IOException;
}
