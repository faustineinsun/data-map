package com.yelpdatasetchallenge.objects;

import java.util.ArrayList;

/**
 * @author feiyu
 */

public class GeoJSONBusinessFeatureGeometry {
  private String type;
  private ArrayList<Float> coordinates;
  
  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type; 
  }

  public void setCoordinates(ArrayList<Float> coordinates) {
    this.coordinates = coordinates;
  }

  public ArrayList<Float> getCoordinates() {
    return this.coordinates; 
  }

  public String toString() {
    return "{"
        + "type: " + type 
        + ", coordinates: " + coordinates 
        + "}";
  }
}
