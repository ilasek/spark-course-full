/*
 * Copyright © 2017 Merck Sharp & Dohme Corp., a subsidiary of Merck & Co., Inc.
 * All rights reserved.
 */
package com.ivolasek.sparkcourse.ingest;

import static org.apache.spark.sql.functions.expr;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DatasetManipulator {

    public Dataset<Row> doSophisticatedCalculation(Dataset<Row> dataset) {
        return dataset.withColumn("realVotes", expr("votes * 1000"));
    }

}
