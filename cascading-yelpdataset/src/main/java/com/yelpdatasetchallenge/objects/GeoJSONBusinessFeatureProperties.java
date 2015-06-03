package com.yelpdatasetchallenge.objects;

/**
 * @author feiyu
 */

public class GeoJSONBusinessFeatureProperties {
  private String businessId; 
  private String businessName;
  private String businessAddress;
  private String businessCategories;
  private String dayInWeekCount;
  private String checkInCountTimeWindow; 
  private String timeWindow;

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public String getBusinessId() {
    return this.businessId; 
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getBusinessName() {
    return this.businessName; 
  }

  public void setBusinessAddress(String businessAddress) {
    this.businessAddress = businessAddress;
  }

  public String getBusinessAddress() {
    return this.businessAddress; 
  }

  public void setBusinessCategories(String businessCategories) {
    this.businessCategories = businessCategories;
  }

  public String getBusinessCategories() {
    return this.businessCategories; 
  }

  public void setDayInWeekCount(String dayInWeekCount) {
    this.dayInWeekCount = dayInWeekCount;
  }

  public String getDayInWeekCount() {
    return this.dayInWeekCount; 
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
        + "businessId: " + businessId 
        + ", businessName: " + businessName 
        + ", businessAddress: " + businessAddress 
        + ", businessCategories: " + businessCategories 
        + ", dayInWeekCount: " + dayInWeekCount 
        + ", checkInCountTimeWindow: " + checkInCountTimeWindow 
        + ", timeWindow: " + timeWindow 
        + "}";
  }
}