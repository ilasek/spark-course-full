package com.ivolasek.sparkcourse.graph;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.graphframes.GraphFrame;

import static org.apache.spark.sql.functions.desc;

/**
 */
public class FlightAnalysis {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("FlightAnalysis").getOrCreate();

        Dataset<Row> flights = spark.read()
                .option("inferSchema", true)
                .csv(args[0])
                .withColumnRenamed("_c6", "src")
                .withColumnRenamed("_c8", "dst")
                .withColumnRenamed("_c16", "distance");

        Dataset<Row> airports = flights.select("src").withColumnRenamed("src", "id").distinct();
        Dataset<Row> edges = flights.select("src", "dst", "distance").distinct();

//        edges.show();


        GraphFrame graph = GraphFrame.apply(airports, edges);
//        graph.inDegrees().join(airports, "id").sort(desc("inDegree")).show();
        graph.pageRank().maxIter(new Integer(args[1])).run().vertices().join(airports, "id").sort(desc("pagerank")).show();
//        graph.find("(A)-[e1]->(B); (B)-[e2]->(C); (C)-[e3]->(A)")
//                .selectExpr("e1.distance + e2.distance + e3.distance AS dist")
//                .sort(desc("dist"))
//                .show();
//                .filter("e1.distance + e2.distance + e3.distance > 10300").show();
    }
}