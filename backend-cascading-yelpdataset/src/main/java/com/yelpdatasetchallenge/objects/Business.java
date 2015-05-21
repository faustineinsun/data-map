package com.yelpdatasetchallenge.objects;

public class Business {
  private String business_id;
  private String check_in_count_time_window;

  public void setBusinessID(String business_id) {
    this.business_id = business_id;
  }

  public void setCheckInCountTimeWindow(String check_in_count_time_window) {
    this.check_in_count_time_window = check_in_count_time_window;
  }

  @Override
  public String toString() {
    return "{business_id: " + business_id +
        ", check_in_count_time_window: " + check_in_count_time_window + "}";
  }
}
