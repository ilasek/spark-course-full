package com.ivolasek.sparkcourse.streaming;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>This sample code demonstrates Spark streaming capabilities by reading data from a socket simulating a Twitter api
 * and sending them to a Kafka sink.</p>
 */
public class SocketStreaming {

    /**
     * Spark Streaming demo.
     * @param args This code doesn't take any arguments.
     * @throws IOException in case of a missing secrets/twitter.properties file.
     */
    public static void main(String[] args) throws StreamingQueryException {
        SparkSession spark = SparkSession.builder().appName("SocketStreaming")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> lines = spark
                .readStream()
                .format("socket")
                .option("host", "localhost")
                .option("port", 9999)
                .load();

        Dataset<String> tweets = lines.as(Encoders.STRING())
                .map(
                        (MapFunction<String, Tweet>) line ->
                                new Tweet(UUID.randomUUID().toString(), new Date(), line),
                        Encoders.bean(Tweet.class))
                .map((MapFunction<Tweet, String>) tweet -> {
                    ObjectMapper jacksonMapper = new ObjectMapper();
                    return jacksonMapper.writeValueAsString(tweet);
                }, Encoders.STRING());

//        StreamingQuery query = tweets.writeStream()
//                .format("console")
//                .start();

        StreamingQuery query = tweets.writeStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("topic", "tweets")
                .option("startingOffsets", "latest")
                .option("checkpointLocation", "spark-streaming/streaming-checkpoint")
                .start();

        query.awaitTermination();
    }
}
