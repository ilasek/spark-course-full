package com.ivolasek.sparkcourse.ingest.support;

import static org.junit.Assert.assertEquals;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.CreatableRelationProvider;
import org.apache.spark.sql.sources.RelationProvider;
import org.apache.spark.sql.sources.SchemaRelationProvider;
import org.apache.spark.sql.types.StructType;

import scala.collection.immutable.Map;

/**
 * Mock implementation of a Spark data provider.
 */
public class ObscureDataFormat implements RelationProvider, SchemaRelationProvider, CreatableRelationProvider {
    @Override
    public BaseRelation createRelation(SQLContext sqlContext, SaveMode mode, Map<String, String> parameters, Dataset<Row> data) {
        assertEquals("There is a correct number of records persisted", 2, data.count());

        return null;
    }

    @Override
    public BaseRelation createRelation(SQLContext sqlContext, Map<String, String> parameters) {
        return null;
    }

    @Override
    public BaseRelation createRelation(SQLContext sqlContext, Map<String, String> parameters, StructType schema) {
        return null;
    }
}
