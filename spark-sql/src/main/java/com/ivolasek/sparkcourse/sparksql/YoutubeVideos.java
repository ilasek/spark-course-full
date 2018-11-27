package com.ivolasek.sparkcourse.sparksql;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import scala.Tuple2;

/**
 * Reads trending YouTube videos and categories, flattens nested categories JSON structure to a form of an ordinary table
 * and retrieves top 10 most popular categories.
 *
 * Parses tags out of the video dataset and stores them as a separate dataset in Avro format.
 */
public class YoutubeVideos {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("YoutubeVideos").master("local[*]").getOrCreate();

        Dataset<Row> categories = spark.read().option("multiline", true).json("spark-sql/data/US_category_id.json");

        Dataset<Row> videos = spark.read().parquet("spark-sql/data/USVideos.parquet");
        videos.show();

        Dataset<Row> flatCategories = flattenCategories(categories);

        videos.registerTempTable("videos");
        flatCategories.registerTempTable("categories");

        spark.sql("SELECT SUM(views) AS total_views, category_id, category_name FROM videos v JOIN categories c ON(v.category_id = c.id) " +
                "GROUP BY category_id, category_name ORDER BY SUM(views) DESC LIMIT 10").show();

        // Databricks spark-avro implementation has problems writing timestamps correctly to Avro
        // https://github.com/databricks/spark-avro/issues/229
        videos.select("video_id", "tags").limit(2).show(false);
        normalizeTags(videos)
                .write()
                .format("com.databricks.spark.avro")
                .mode(SaveMode.Overwrite)
                .save("spark-sql/data/Tags.avro");

        categories.write().mode(SaveMode.Overwrite).json("spark-sql/data/categories_single_line.json");
    }

    /**
     * Transforms nested structures from snippet to a flat table.
     * https://stackoverflow.com/questions/38753898/how-to-flatten-a-struct-in-a-spark-dataframe/43355059#43355059
     *
     * @param categories Categories dataset with nested data
     * @return Flattened categories
     */
    private static Dataset<Row> flattenCategories(Dataset<Row> categories) {
        return categories.select(
                categories.col("id"), categories.col("kind"),
                categories.col("snippet.assignable"), categories.col("snippet.channelId"),
                categories.col("snippet.title").as("category_name"));
    }

    /**
     * Extracts tags from a tags column and stores them in a separate Dataset together with video_id.
     *
     * @param videos Videos with tags embedded in a tag column
     * @return Table with tag and video_id columns
     */
    private static Dataset<Row> normalizeTags(Dataset<Row> videos) {
         return videos.select("video_id", "tags").flatMap((FlatMapFunction<Row, Tuple2<String, String>>) row -> {
                    String tagRow = row.getString(row.fieldIndex("tags"));
                    if (tagRow == null || tagRow.isEmpty()) {
                        return new ArrayList<Tuple2<String, String>>().iterator();
                    } else {
                        String[] tags = tagRow.split("\\|");
                        String videoId = row.getString(row.fieldIndex("video_id"));

                        return Arrays.stream(tags)
                                .map(tag -> new Tuple2<>(tag.replaceAll("^\"+", "").replaceAll("\"+$", ""), videoId))
                                .iterator();
                    }
                },
                Encoders.tuple(Encoders.STRING(), Encoders.STRING())).toDF("tag", "video_id").distinct();
    }

    public static void csvToParquet(SparkSession spark) {
        StructField[] fields = {
                new StructField("video_id", DataTypes.StringType, false, Metadata.empty()),
                new StructField("trending_date", DataTypes.StringType, true, Metadata.empty()),
                new StructField("title", DataTypes.StringType, true, Metadata.empty()),
                new StructField("channel_title", DataTypes.StringType, true, Metadata.empty()),
                new StructField("category_id", DataTypes.LongType, true, Metadata.empty()),
                new StructField("publish_time", DataTypes.TimestampType, true, Metadata.empty()),
                new StructField("tags", DataTypes.StringType, true, Metadata.empty()),
                new StructField("views", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("likes", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("dislikes", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("comment_count", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("thumbnail_link", DataTypes.StringType, true, Metadata.empty()),
                new StructField("comments_disabled", DataTypes.BooleanType, true, Metadata.empty()),
                new StructField("ratings_disabled", DataTypes.BooleanType, true, Metadata.empty()),
                new StructField("video_error_or_removed", DataTypes.BooleanType, true, Metadata.empty()),
                new StructField("decsription", DataTypes.StringType, true, Metadata.empty())
        };
        Dataset<Row> videos = spark.read().format("csv").option("header", true)
                .schema(new StructType(fields)).load("spark-sql/data/USvideos.csv");

        videos.printSchema();
        videos.show();
        videos.write().parquet("spark-sql/data/USvideos.parquet");

    }
}
