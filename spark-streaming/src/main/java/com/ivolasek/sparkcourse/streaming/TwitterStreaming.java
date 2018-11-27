package com.ivolasek.sparkcourse.streaming;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.Properties;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;

import twitter4j.Status;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

/**
 * <p>This sample code demonstrates Spark streaming capabilities by performing a sentiment analysis on a live stream of
 * tweets containing a word sick.</p>
 *
 * <p>In order to run it on your computer, you need to provide a <code>secrets/twitter.properties</code> file with
 * following structure:</p>
 *
 *<pre>
 * consumerKey=YOUR_TWITTER_CONSUMER_KEY
 * consumerSecret=YOUR_TWITTER_CONSUMER_SECRET
 * accessToken=YOUR_TWITTER_ACCESS_TOKEN
 * accessTokenSecret=YOUR_TWITTER_TOKEN_SECRET
 *</pre>
 */
public class TwitterStreaming {

    /**
     * Path to a file containing Twitter secrets.
     */
    private static final String TWITTER_SECRETS_FILE = "secrets/twitter.properties";

    /**
     * Spark Streaming demo.
     * @param args This code doesn't take any arguments.
     * @throws IOException in case of a missing secrets/twitter.properties file.
     */
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder().appName("TwitterStreaming")
                .master("local[1]")
                .getOrCreate();

        JavaStreamingContext ssc = new JavaStreamingContext(JavaSparkContext.fromSparkContext(spark.sparkContext()), new Duration(10000));

        // We will filter Tweets containing a word sick
        String[] filters = {"sick"};
        JavaReceiverInputDStream<Status> stream = TwitterUtils.createStream(ssc, auth(), filters);

        JavaDStream<Tweet> tweets = stream.map(status -> new Tweet(new Time(status.getCreatedAt().getTime()), status.getText()));
        printStream(tweets, spark);
//        writeToKafka(tweets, spark);

        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void printStream(JavaDStream<Tweet> tweets, SparkSession spark) {
        tweets.foreachRDD(rdd -> {
            rdd.foreach(tweet -> System.out.println(tweet + ""));
            Dataset<Row> dataSet = spark.createDataFrame(rdd, Tweet.class);
            dataSet.createOrReplaceTempView("tweets");
            spark.sql("SELECT COUNT(*) FROM tweets").show();
        });
    }

    private static void writeToKafka(JavaDStream<Tweet> tweets, SparkSession spark) {

        tweets.foreachRDD(rdd -> {
            Dataset<Tweet> dataset = spark.createDataset(rdd.rdd(), Encoders.bean(Tweet.class));
            dataset.write()
                    .format("kafka")
                    .option("kafka.bootstrap.servers", "localhost:9092")
                    .option("topic", "tweets")
                    .save();
        });
    }

    /**
     * Generates OAuth configuration for Twitter API based on Twitter credentials stored in a properties file {@link #TWITTER_SECRETS_FILE}.
     * @return OAuth configuration for Twitter API.
     * @throws IOException in case of a missing file with Twitter secrets.
     */
    private static OAuthAuthorization auth() throws IOException {
        Properties twitterSecrets = loadTwitterSecretsProperties();
        return new OAuthAuthorization(new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterSecrets.getProperty("consumerKey"))
                .setOAuthConsumerSecret(twitterSecrets.getProperty("consumerSecret"))
                .setOAuthAccessToken(twitterSecrets.getProperty("accessToken"))
                .setOAuthAccessTokenSecret(twitterSecrets.getProperty("accessTokenSecret"))
                .build());
    }

    /**
     * Loads Twitter credentials from a properties file.
     * @return Map of Twitter credentials consumerKey, consumerSecret, accessToken, accessTokenSecret
     * @throws IOException In case of a missing {@link #TWITTER_SECRETS_FILE}.
     */
    private static Properties loadTwitterSecretsProperties() throws IOException {
        try(InputStream is = new FileInputStream(TWITTER_SECRETS_FILE)) {
            Properties prop = new Properties();
            prop.load(is);
            return prop;
        }
    }
}
