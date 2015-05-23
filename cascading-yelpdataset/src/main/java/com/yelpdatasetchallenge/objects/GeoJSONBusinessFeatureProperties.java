package com.yelpdatasetchallenge.objects;

/**
 * @author feiyu
 */

public class GeoJSONBusinessFeatureProperties {
  private String businessName;
  private String businessCategories;
  private String businessAddress;
  private String checkInCountTimeWindow; 
  private String timeWindow;

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getBusinessName() {
    return this.businessName; 
  }

  public void setBusinessCategories(String businessCategories) {
    this.businessCategories = businessCategories;
  }

  public String getBusinessCategories() {
    return this.businessCategories; 
  }

  public void setBusinessAddress(String businessAddress) {
    this.businessAddress = businessAddress;
  }

  public String getBusinessAddress() {
    return this.businessAddress; 
  }

  public void setCheckInCountTimeWindow(String checkInCountTimeWindow) {
    this.checkInCountTimeWindow = checkInCountTimeWindow;
  }

  public String getCheckInCountTimeWindow() {
    return this.checkInCountTimeWindow; 
  }

  public void setTimeWindow(String timeWindow) {
    this.timeWindow = timeWindow;
  }

  public String getTimeWindow() {
    return this.timeWindow; 
  }

  public String toString() {
    return "{"
        + "businessName: " + businessName 
        + ", businessCategories: " + businessCategories 
        + ", businessAddress: " + businessAddress 
        + ", checkInCountTimeWindow: " + checkInCountTimeWindow 
        + ", timeWindow: " + timeWindow 
        + "}";
  }
}