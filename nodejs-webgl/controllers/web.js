var express = require('express')

// Redis
var redis = require("redis");
var redisclient = redis.createClient();

// find static files in ./public
var app = express();
app.set('views', __dirname + '/../views');
app.use(express.static(__dirname + '/../public'));
app.engine('html', require('ejs').renderFile);

// Routes

app.get('/', function(req, res) {
    res.render('index.html');
});

// Redis
app.post('/redis', function(request, response) {
    redisclient.on("error", function (err) {
        console.log("Redis -- error " + err);
    });
    redisclient.get("businessGeoJSON", function(err, geoJSONfile) {
        if (geoJSONfile == null) {
            console.log("Redis -- key: "+ request.query.rediskey +" is missing");
        } else {
            response.send(geoJSONfile);
            //console.log("\nRedis -- GeoJOSN: "+geoJSONfile);
            //GeoJSONfile is very large
            console.log("Redis -- get GeoJOSN file");
        }
    });
});

// port
app.set('port', (process.env.PORT || 5000))
app.listen(app.get('port'), function() {
    console.log("Node app is running at localhost:" + app.get('port'))
})

