/*
 * Copyright © 2017 Merck Sharp & Dohme Corp., a subsidiary of Merck & Co., Inc.
 * All rights reserved.
 */
package com.ivolasek.sparkcourse.ingest.support;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Before;
import org.spark_project.guava.collect.ImmutableList;

import com.ivolasek.sparkcourse.ingest.dto.Survey;

public class SparkTestSupport {
    private SparkSession spark;

    private Dataset<Row> testDataset;

    @Before
    public void setUp() {
        spark = SparkSession.builder().master("local[*]").appName("TestFileLoader").getOrCreate();
        testDataset = getSpark().createDataFrame(ImmutableList.of(
                new Survey(1, "Praha", 450),
                new Survey(2, "Brno", 350)
        ), Survey.class);
    }

    protected SparkSession getSpark() {
        return spark;
    }

    protected  Dataset<Row> getTestDataset() {
        return testDataset;
    }
}
