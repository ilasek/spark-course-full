/*
 * Copyright © 2017 Merck Sharp & Dohme Corp., a subsidiary of Merck & Co., Inc.
 * All rights reserved.
 */
package com.ivolasek.sparkcourse.streaming;

import static org.apache.spark.sql.functions.expr;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.apache.spark.sql.functions.*;


public class SentimentAnalysis {

    public static void main(String[] args) throws StreamingQueryException {
        SparkSession spark = SparkSession.builder().appName("SentimentAnalysis")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> tweets = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "tweets")
                .load();

        // Load sentiment dataset
        String[] schema = {"word", "sentiment"};
        Dataset<Row> sentiments = spark.read()
                .option("header", "false")
                .option("delimiter", "\t")
                .option("inferSchema", "true")
                .csv("spark-streaming/data/sentiment.tsv")
                .toDF(schema);
        sentiments.createOrReplaceTempView("sentiments");

        StreamingQuery query = tweets
                .selectExpr("CAST(value AS STRING)")
                .as(Encoders.STRING())
                .map((MapFunction<String, Tweet>) json -> new ObjectMapper().readValue(json, Tweet.class), Encoders.bean(Tweet.class))
                .selectExpr("id", "CAST(text AS STRING) AS text", "CAST(createdAt.time / 1000 AS TIMESTAMP) AS createdAt")
                .join(sentiments, expr("text LIKE CONCAT('%', word, '%')"), "leftOuter")
                .groupBy("id", "text", "createdAt")
                .sum("sentiment")
                .writeStream()
                .outputMode("update")
                .option("truncate", false)
                .format("console")
                .start();

        query.awaitTermination();
    }
}
