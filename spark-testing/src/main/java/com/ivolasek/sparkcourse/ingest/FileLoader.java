package com.ivolasek.sparkcourse.ingest;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * CSV file loader.
 */
public class FileLoader {

    private final SparkSession spark;

    public FileLoader(SparkSession spark) {
        this.spark = spark;
    }

    /**
     * Loads data from a CSV file.
     *
     * @param filePath Path to the file to be loaded.
     * @return Loaded dataset with infered header and schema.
     */
    public Dataset<Row> loadFile(String filePath) {
        return spark.read()
                .option("header", true)
                .option("inferSchema", true)
                .csv(filePath);
    }
}
