package com.ivolasek.sparkcourse.ingest;

import org.junit.Test;

import com.ivolasek.sparkcourse.ingest.support.SparkTestSupport;

public class DatasetPersistorTest extends SparkTestSupport {
    @Test
    public void persist() throws Exception {
        new DatasetPersistor("com.ivolasek.sparkcourse.ingest.support.ObscureDataFormat").persist(getTestDataset());
    }

}