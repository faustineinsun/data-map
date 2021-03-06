// reference: http://bl.ocks.org/mikehadlow/93b471e569e31af07cd3
// modified by faustineinsun


/*
var currentValue = 100;
var random = d3.random.normal(0, 20.0);

for (var i = 0; i < 7; i++) {
var timeWindow = i;
console.log(timeWindow + " : " + currentValue);
data.push([i, currentValue]);
currentValue = currentValue + random();
} */

//var data = [[0,9],[1,3],[3,5],[4,7],[5,3],[6,0]];

var drawLineGraph = function(containerHeight, containerWidth, data, yLabel, xLabel, axisColorTag) {

  var svg = d3.select("#chartGraphJS").append("svg")
  .attr("width", containerWidth)
  .attr("height", containerHeight);

  var margin = {
    top: 15,
    left: 38,
    right: 20,
    bottom: 25
  };

  var height = containerHeight - margin.top - margin.bottom;
  var width = containerWidth - margin.left - margin.right;

  var xDomain = d3.extent(data, function(d) {
    return d[0];
  })
  var yDomain = d3.extent(data, function(d) {
    return d[1];
  });

  var xScale = d3.scale.linear().range([0, width]).domain(xDomain);
  var yScale = d3.scale.linear().range([height, 0]).domain(yDomain);

  var xAxis = d3.svg.axis().scale(xScale).orient('bottom').ticks(7);
  var yAxis = d3.svg.axis().scale(yScale).orient('left');

  var line = d3.svg.line()
  .x(function(d) {
    return xScale(d[0]);
  })
  .y(function(d) {
    return yScale(d[1]);
  });

  var area = d3.svg.area()
  .x(function(d) {
    return xScale(d[0]);
  })
  .y0(function(d) {
    return yScale(d[1]);
  })
  .y1(height);

  var g = svg.append('g').attr('transform', 'translate(' + margin.left + ', ' + margin.top + ')');

  var xAxisColor;
  var yAxisColor;

  if (axisColorTag=="withoutML") {
    xAxisColor='x axisWithoutML';
    yAxisColor='x axisWithoutML';
  } else if (axisColorTag=="XGBoost") {
    xAxisColor='x axisXGBoost';
    yAxisColor='y axisXGBoost';
  } else if (axisColorTag=="RandomForest") {
    xAxisColor='x axisRandomForest';
    yAxisColor='y axisRandomForest';
  } else if (axisColorTag=="H2ODeepLearning") {
    xAxisColor='x axisH2ODeepLearning';
    yAxisColor='y axisH2ODeepLearning';
  }

  g.append('path')
  .datum(data)
  .attr('class', 'area')
  .attr('d', area);

  g.append('g')
  .attr('class', xAxisColor)
  .attr('transform', 'translate(0, ' + height + ')')
  .call(xAxis)
  .append('text')
  .attr('text-anchor', 'middle')
  .attr('x', 150)
  .attr('y', -5)
  .text(xLabel);

  g.append('g')
  .attr('class', yAxisColor)
  .call(yAxis)
  .append('text')
  .attr('transform', 'rotate(-90)')
  .attr('x', -150)
  .attr('y', 6)
  .attr('dy', '.71em')
  .attr('text-anchor', 'end')
  .text(yLabel);

  g.append('path')
  .datum(data)
  .attr('class', 'line')
  .attr('d', line);

  g.selectAll('circle').data(data).enter().append('circle')
  .attr('cx', function(d) {
    return xScale(d[0]);
  })
  .attr('cy', function(d) {
    return yScale(d[1]);
  })
  .attr('r', 5)
  .attr('class', 'circle');

  // focus tracking

  var focus = g.append('g').style('display', 'none');

  focus.append('line')
  .attr('id', 'focusLineX')
  .attr('class', 'focusLine');
  focus.append('line')
  .attr('id', 'focusLineY')
  .attr('class', 'focusLine');
  focus.append('circle')
  .attr('id', 'focusCircle')
  .attr('r', 5)
  .attr('class', 'circle focusCircle');

  var bisectDate = d3.bisector(function(d) {
    return d[0];
  }).left;

  g.append('rect')
  .attr('class', 'overlay')
  .attr('width', width)
  .attr('height', height)
  .on('mouseover', function() {
    focus.style('display', null);
  })
  .on('mouseout', function() {
    focus.style('display', 'none');
  })
  .on('mousemove', function() {
    var mouse = d3.mouse(this);
    var mouseDate = xScale.invert(mouse[0]);
    var i = bisectDate(data, mouseDate); // returns the index to the current data item
    var d0 = data[i - 1]
    var d1 = data[i];
    // work out which date value is closest to the mouse
    var d;
    if (typeof d0 === 'undefined') {
      d=d1;
    }else {
      d = mouseDate - d0[0] > d1[0] - mouseDate ? d1 : d0;
    }

    var x = xScale(d[0]);
    var y = yScale(d[1]);

    focus.select('#focusCircle')
    .attr('cx', x)
    .attr('cy', y);
    focus.select('#focusLineX')
    .attr('x1', x).attr('y1', yScale(yDomain[0]))
    .attr('x2', x).attr('y2', yScale(yDomain[1]));
    focus.select('#focusLineY')
    .attr('x1', xScale(xDomain[0])).attr('y1', y)
    .attr('x2', xScale(xDomain[1])).attr('y2', y);
  });
};

function showDayInWeekCountChartXGBoost(data) {
  data = JSON.parse(data);
  $("#chartGraphJS").empty();
  drawLineGraph(380, 400, data, "XGBoost Prob", "Day in Week", "XGBoost");
}

function showDayInWeekCountRandomForest(data) {
  data = JSON.parse(data);
  $("#chartGraphJS").empty();
  drawLineGraph(380, 400, data, "Random Forest Prob", "Day in Week", "RandomForest");
}

function showDayInWeekCountH2ODeepLearning(data) {
  data = JSON.parse(data);
  $("#chartGraphJS").empty();
  drawLineGraph(380, 400, data, "H2O Deep Learning Prob", "Day in Week", "H2ODeepLearning");
}

function showDayInWeekCountChartWithoutML(data) {
  //var data = [[0,9],[1,3],[3,5],[4,7],[5,3],[6,0]];
  data = JSON.parse(data);
  $("#chartGraphJS").empty();
  drawLineGraph(380, 400, data, "History Check-in Count", "Day in Week", "withoutML");
}
