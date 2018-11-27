package com.ivolasek.sparkcourse.ingest;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Saves a Dataset into an obscure location, using a custom persistor.
 */
public class DatasetPersistor {

    private final String persistFormat;

    public DatasetPersistor(String persistFormat) {
        this.persistFormat = persistFormat;
    }

    /**
     * Saves a dataset into an obscure format. Note the format is not hardcoded but passed as a parameter to the constructor.
     *
     * @param dataset Data to bo persisted.
     */
    public void persist(Dataset<Row> dataset) {
        dataset.write().format(persistFormat).save();
    }
}
