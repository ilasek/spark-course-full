package com.ivolasek.sparkcourse.ingest;

import static org.junit.Assert.*;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.ivolasek.sparkcourse.ingest.support.SparkTestSupport;

public class FileLoaderTest extends SparkTestSupport {

    @org.junit.Test
    public void loadFile() throws Exception {
        Dataset<Row> survey = new FileLoader(getSpark()).loadFile("src/test/resources");

        assertEquals("Correct number of results", 4, survey.count());
        assertEquals("Contains Prague", "Praha", survey.collectAsList().get(0).getString(1));
    }

}