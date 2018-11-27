package com.ivolasek.sparkcourse.wordcount;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

/**
 * Computes number of severities of log messages in data/sample.log file.
 *
 * Example output:
 * <pre>
 *     [(WARNING,235), (ERROR,253), (DEBUG,252), (INFO,260)]
 * </pre>
 *
 * @author Ivo Lasek
 */
public class LogAnalysis {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("LogAnalysis").master("local[*]").getOrCreate();

        JavaRDD<String> logFile = spark.read().textFile("spark-introduction/data/sample.log").javaRDD();

        List<Tuple2<String, Integer>> counts = logFile
                .mapToPair(line -> new Tuple2<>(line.substring(0, line.indexOf(" ")), 1))
                .reduceByKey((count1, count2) -> count1 + count2)
                .collect();

        System.out.println(counts);
    }
}
