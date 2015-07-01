package com.yelpdatasetchallenge.cascading;

import java.io.File;
import java.io.IOException;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.local.LocalFlowConnector;
import cascading.pattern.pmml.PMMLPlanner;
import cascading.scheme.local.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.local.FileTap;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntryIterator;

/**
 * Reference: Cascading Pattern http://docs.cascading.org/tutorials/pattern/
 */

public class DataAnalysisMain {

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void runRFPMML() throws IOException {
    Tap inTap = new FileTap(new TextDelimited(true, ","), "src/main/resources/ml/dataset/yelp-dataset/business_categories_maxCount_all.csv", SinkMode.KEEP);
    Tap resultsTap = new FileTap(new TextDelimited(true, ","), "src/main/resources/ml/dataset/yelp-dataset/output/rf_yelpChallenge_out.txt", SinkMode.REPLACE);

    PMMLPlanner pmmlPlanner = new PMMLPlanner()
    .setPMMLInput(new File("src/main/resources/ml/rf_yelpChallenge.xml"))
    .retainOnlyActiveIncomingFields()
    .setDefaultPredictedField(new Fields("MaxCheckinDayInWeek", Integer.class));;

    FlowDef flowDef = FlowDef
        .flowDef()
        .setName("pmml rf flow")
        .addSource("rf_yelp", inTap)
        .addSink("results", resultsTap);
    flowDef.addAssemblyPlanner(pmmlPlanner);

    Flow flow = new LocalFlowConnector()
    .connect(flowDef);
    flow.complete();

    TupleEntryIterator iterator = resultsTap
        .openForRead(flow.getFlowProcess());
    while (iterator.hasNext()) {
      System.out.println(iterator.toString());
    }
    iterator.close();
  }

  public static void main(String[] args) throws Exception {
    DataAnalysisMain ds = new DataAnalysisMain();
    ds.runRFPMML();
  }
}