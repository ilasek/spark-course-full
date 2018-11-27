package com.ivolasek.sparkcourse.ingest;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class FileToDbIngest {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("MyCrazyLoader").master("local[*]").getOrCreate();

        Dataset<Row> survey = new FileLoader(spark).loadFile(args[1]);

        new DatasetPersistor("obscureDataFormat").persist(
            new DatasetManipulator().doSophisticatedCalculation(survey)
        );
    }
}
