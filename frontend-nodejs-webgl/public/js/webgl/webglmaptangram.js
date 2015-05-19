var map = L.map('map');
var layer = Tangram.leafletLayer({
    scene: 'assets/scene.yaml',
    attribution: '<a href="https://mapzen.com/tangram" target="_blank">Tangram</a> | &copy; OSM contributors | <a href="https://mapzen.com/" target="_blank">Mapzen</a>'
});

layer.addTo(map);
map.setView([37.7749300, -122.4194200], 14);

var hash = new L.Hash(map);
