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
function onEachFeature(feature, layer) {
    var popupContent = "<p><b>" + feature.properties.businessName +"</b></p>"
        +"<b>Address</b>: " +feature.properties.businessAddress
        +"<br><b>Latitude and Longitude</b>: " + feature.geometry.coordinates
        +"<br><b>Categories</b>: " +feature.properties.businessCategories
        +"<br>In time window <b>"+feature.properties.timeWindow+"</b> there are <b>"+feature.properties.checkInCountTimeWindow + "</b> check-in count";

    layer.bindPopup(popupContent);
}

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

    //console.log(geojsonfile);
    busineslayer = L.geoJson(businessLocation, {
        style: function (feature) {
                   return feature.properties && feature.properties.style;
               },

        onEachFeature: onEachFeature,

        pointToLayer: function (feature, latlng) {
            return L.circleMarker(latlng, {
                radius: 8,
            fillColor: "#FF00E1",
            color: "#000",
            weight: 1,
            opacity: 1,
            fillOpacity: 0.8
            });
        }
    }).addTo(map);
}
