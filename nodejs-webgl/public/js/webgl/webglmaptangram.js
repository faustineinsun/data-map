var map = L.map('map');
var layer = Tangram.leafletLayer({
    scene: 'assets/scene.yaml',
    attribution: '<a href="https://mapzen.com/tangram" target="_blank">Tangram</a> '
    +'| &copy; OSM contributors '
    +'| <a href="https://mapzen.com/" target="_blank">Mapzen</a> '
    +'| <a href="https://github.com/tangrams/tangram-sandbox" target="_blank">tangram-sandbox</a> '
    +'| <a href="http://wheelnavjs.softwaretailoring.net/" target="_blank">Wheelnav.js</a>'
});

layer.addTo(map);
//map.setView([37.7871,-122.4247], 14); // San Fransico
//map.setView([39.7357,-104.9992], 14); // Geojson on Leaflet example
//map.setView([36.1348,-115.0344], 11); // State NV in USA
map.setView([40.78,-14.06], 3)

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

var isWithoutML = false;
var isXGBoost = false;
var isNeuralNetworks = false;

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
          backgroundColor: "LavenderBlush",
          opacity: 0.8,
          padding: "8px",
          border: "1px solid #ccc"
        }
      });
      // Insert a headline into that popup
      var addressText = feature.properties.businessAddress.split("\\n").join(" ");;
      var hed = $("<div></div>", {
        text: addressText,
        css: {fontSize: "16px", marginBottom: "3px", color: "SlateBlue"}
      }).appendTo(popup);

      $("#showDayInWeekCountName").text("Name: "+feature.properties.businessName);
      $("#showDayInWeekCountCategory").text("Category: "+ feature.properties.businessCategories);

      //console.log("Status: "+isWithoutML+" "+isXGBoost+" "+isNeuralNetworks);
      var data;
      if (isWithoutML) {
        data = feature.properties.dayInWeekCount;
        showDayInWeekCountChartWithoutML(data);
      } else if (isXGBoost) {
        data = feature.properties.dayInWeekCountPredictedProb;
        showDayInWeekCountChartXGBoost(data);
      } else if (isNeuralNetworks) {
        data = [[0,9],[1,3],[3,5],[4,7],[5,3],[6,0]];
        showDayInWeekCountNeuralNetworks(data);
      } else {
        data = feature.properties.dayInWeekCount;
        showDayInWeekCountChartWithoutML(data);
      }

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
      case "BW": map.setView([49.0062,8.4801], 12); break;
      case "SCB": map.setView([55.8975,-3.0089], 11); break;
      case "MLN": map.setView([55.9210,-3.0975], 12); break;
      case "SC": map.setView([35.0268,-80.8813], 12); break;
      case "IL": map.setView([40.1056,-88.1838], 13); break;
      case "ELN": map.setView([55.9617,-3.0000], 11); break;
      case "NV": map.setView([36.1187,-115.0063], 11); break;
      case "QC": map.setView([45.5066,-73.5713], 11); break;
      case "WI": map.setView([43.0686,-89.2426], 11); break;
      case "AZ": map.setView([33.4492,-111.7021], 10); break;
      case "CA": map.setView([36.1538,-115.1017], 12); break;
      case "KHL": map.setView([55.9621,-3.0782], 11); break;
      case "ON": map.setView([43.4378,-80.3955], 12); break;
      case "FIF": map.setView([56.0419,-3.2952], 11); break;
      case "WA": map.setView([36.1187,-114.9795], 11); break;
      case "EDH": map.setView([55.9458,-3.1723], 13); break;
      case "PA": map.setView([40.4212,-79.9032], 12); break;
      case "NC": map.setView([35.1943,-80.7111], 11); break;
      default: map.setView([40.78,-14.06], 3);
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
          "dayInWeekCountPredictedProb":[[0,0.06256],[1,0.09311],[2,0.11299],[3,0.13450],[4,0.13550],[5,0.38106],[6,0.08028]],
          "MaxCheckinCountDayInWeek":"2",
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
          "dayInWeekCount":[[0,900],[1,3000],[2,100],[3,5000],[4,700],[5,30],[6,5000]],
          "dayInWeekCountPredictedProb":[[0,0.03828],[1,0.05920],[2,0.04781],[3,0.08197],[4,0.29545],[5,0.40551],[6,0.07179]],
          "MaxCheckinCountDayInWeek":"-1",
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
