package com.yelpdatasetchallenge.objects;

/**
 * @author feiyu
 */

public class GeoJSONBusinessFeature {
  private String type;
  private GeoJSONBusinessFeatureProperties properties;
  private GeoJSONBusinessFeatureGeometry geometry;

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type; 
  }

  public void setProperties(GeoJSONBusinessFeatureProperties properties) {
    this.properties = properties;
  }

  public GeoJSONBusinessFeatureProperties getProperties() {
    return this.properties; 
  }

  public void setGeometry(GeoJSONBusinessFeatureGeometry geometry) {
    this.geometry = geometry;
  }

  public GeoJSONBusinessFeatureGeometry getGeometry() {
    return this.geometry; 
  }

  public String toString() {
    return "{"
        + "type: " + type 
        + ", properties: " + properties 
        + ", geometry: " + geometry 
        + "}";
  }
}
