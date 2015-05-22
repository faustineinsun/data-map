package com.yelpdatasetchallenge.objects;

import java.util.ArrayList;

/**
 * @author feiyu
 */

public class GeoJSONBusinessFeatureCollection {
  private String type;
  private ArrayList<GeoJSONBusinessFeature> features;

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type; 
  }

  public void setFeatures(ArrayList<GeoJSONBusinessFeature> features) {
    this.features = features;
  }

  public ArrayList<GeoJSONBusinessFeature> getFeatures() {
    return this.features; 
  }

  public String toString() {
    return "{"
        + "type: " + type 
        + ", features: " + features.toString() 
        + "}";
  }
}
