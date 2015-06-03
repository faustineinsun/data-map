var map = L.map('map');
var layer = Tangram.leafletLayer({
    scene: 'assets/scene.yaml',
    attribution: '<a href="https://mapzen.com/tangram" target="_blank">Tangram</a> | &copy; OSM contributors | <a href="https://mapzen.com/" target="_blank">Mapzen</a>'
});

layer.addTo(map);
//map.setView([37.7871,-122.4247], 14); // San Fransico
//map.setView([39.7357,-104.9992], 14); // Geojson on Leaflet example
//map.setView([36.1348,-115.0344], 11); // State NV in USA
map.setView([45.89,-30.76], 3);

var hash = new L.Hash(map);

var busineslayer;

// Geojson on Leaflet
// reference http://leafletjs.com/examples/geojson.html
// reference http://palewi.re/posts/2012/03/26/leaflet-recipe-hover-events-features-and-polygons/
var defaultStyle = {
  radius: 7,
  fillColor: "LightSeaGreen",
  color: "PaleGreen",
  weight: 0.5,
  opacity: 0.5,
  fillOpacity: 0.8
};
var highlightStyle = {
  radius: 8.2,
  fillColor: "SlateBlue",
  color: "PaleGreen",
  weight: 0.5,
  opacity: 0.5,
  fillOpacity: 0.8
};

var onEachFeature = function(feature, layer) {
  // mouseover and mouseout
  (function(layer, properties) {
    layer.on("mouseover", function (e) {
      layer.setStyle(highlightStyle);

      // Create a popup with a unique ID linked to this record
      var popup = $("<div></div>", {
        id: "popup-"+feature.geometry.businessId,
        css: {
          position: "absolute",
          bottom: "50px",
          left: "50px",
          zIndex: 1002,
          backgroundColor: "LightCyan",
          opacity: 0.85,
          padding: "8px",
          border: "1px solid #ccc"
        }
      });
      // Insert a headline into that popup
      var hed = $("<div></div>", {
        text: feature.properties.businessAddress,
        css: {fontSize: "16px", marginBottom: "3px", color: "SlateBlue"}
      }).appendTo(popup);

      $("#showDayInWeekCount").text(feature.properties.businessName);
      var data = feature.properties.dayInWeekCount;

      showDayInWeekCountChart(data);

      popup.appendTo("#map");
    });
    layer.on("mouseout", function (e) {
      layer.setStyle(defaultStyle);
      $("#popup-" + feature.geometry.businessId).remove();
    });
  })(layer, feature.properties);

  // click points
  /*
  var popupContent = "<p><b>" + feature.properties.businessName +"</b></p>"
  +"<b>Address</b>: " +feature.properties.businessAddress
  +"<br><b>Latitude and Longitude</b>: " + feature.geometry.coordinates
  +"<br><b>Categories</b>: " +feature.properties.businessCategories
  +"<br>In time window <b>"+feature.properties.timeWindow+"</b> there are <b>"+feature.properties.checkInCountTimeWindow + "</b> check-in count";
  layer.bindPopup(popupContent);
  */
};

//$.getJSON("assets/yelpgeojson/businessFeatureClctn.json", function(json) {
//});
function showYelpDataGeoJSONOverlay(geojsonfile, stateTextContent) {
    // Clean layer
    if (busineslayer!=null) {
      //console.log("Clean busineslayer");
      map.removeLayer(busineslayer);
    }

    switch(stateTextContent) {
      case "BW": map.setView([48.9987,8.3331], 11); break;
      case "SCB": map.setView([55.8930,-3.0597], 9); break;
      case "MLN": map.setView([55.9169,-3.1258], 12); break;
      case "SC": map.setView([35.0246,-80.9339], 12); break;
      case "IL": map.setView([40.1078,-88.2266], 13); break;
      case "ELN": map.setView([55.9269,-3.3673], 9); break;
      case "NV": map.setView([36.1243,-115.0907], 11); break;
      case "QC": map.setView([45.5051,-73.5892], 11); break;
      case "WI": map.setView([43.0982,-89.3333], 11); break;
      case "AZ": map.setView([33.3431,-111.7804], 9); break;
      case "CA": map.setView([36.1538,-115.1017], 12); break;
      case "KHL": map.setView([55.9792,-3.1647], 10); break;
      case "ON": map.setView([43.4205,-80.3622], 10); break;
      case "FIF": map.setView([56.0164,-3.3285], 9); break;
      case "WA": map.setView([36.0855,-115.2054], 11); break;
      case "EDH": map.setView([55.9458,-3.1723], 13); break;
      case "PA": map.setView([40.4053,-79.9221], 12); break;
      case "NC": map.setView([35.1758,-80.8017], 11); break;
      default: map.setView([45.89,-30.76], 3);
    }

    // Show GeoJSON overlay
    var businessLocation=JSON.parse(geojsonfile);
    /*
  var businessLocation={
    "type": "FeatureCollection",
    "features": [
      {
        "type": "Feature",
        "properties": {
          "businessId":"adfa]239r90a9df",
          "businessName": "Checkers Rally's",
          "businessAddress": "\"7210 S Durango Dr\\nSouthwest\\nLas Vegas, NV 89113\"",
          "businessCategories": "[\"Burgers\",\"Fast Food\",\"Sandwiches\",\"Restaurants\"]",
          "dayInWeekCount":[[0,9],[1,3],[2,100],[3,5],[4,7],[5,3],[6,0]],
          "checkInCountTimeWindow":"6",
          "timeWindow":"17-3"
        },
        "geometry": {
          "type": "Point",
          "coordinates": [-115.279,36.0572]
        }
      },{
        "type": "Feature",
        "properties": {
          "businessId":"adsafadffa]239r90a9df",
          "businessName": "Red Rice",
          "businessAddress": "\"9400 S  Eastern Ave\\nSte 106A\\nSoutheast\\nLas Vegas, NV 89123\"",
          "businessCategories": "[\"Food\",\"Ethnic Food\",\"Specialty Food\"]",
          "dayInWeekCount":[[0,900],[1,3000],[2,100],[3,5000],[4,700],[5,30],[6,1000]],
          "checkInCountTimeWindow":"6",
          "timeWindow":"17-3"
        },
        "geometry": {
          "type": "Point",
          "coordinates": [-115.118,36.0184]
        }
      }
    ]
  };
*/

    //console.log(geojsonfile);
    busineslayer = L.geoJson(businessLocation, {
        style: function (feature) {
                   return feature.properties && feature.properties.style;
               },

        onEachFeature: onEachFeature,

        pointToLayer: function (feature, latlng) {
            return L.circleMarker(latlng, defaultStyle);
        }
    }).addTo(map);
}
