# Spark SQL
## Spark SQL introduction
Walk through the [official Spark SQL documentation](https://spark.apache.org/docs/latest/sql-programming-guide.html).

## Data formats
### Avro
[Apache Avro](https://avro.apache.org/)
Avro is a remote procedure call and data serialization framework developed within Apache's Hadoop project. It uses JSON for defining data types and protocols, and serializes data in a compact binary format. Its primary use is in Apache Hadoop, where it can provide both a serialization format for persistent data, and a wire format for communication between Hadoop nodes, and from client programs to the Hadoop services.

#### Sample Avro schema definition
Avro schemas are defined using JSON. Schemas are composed of primitive types (null, boolean, int, long, float, double, bytes, and string) and complex types (record, enum, array, map, union, and fixed).

```json
 {
   "namespace": "example.avro",
   "type": "record",
   "name": "User",
   "fields": [
      {"name": "name", "type": "string"},
      {"name": "favorite_number",  "type": ["int", "null"]},
      {"name": "favorite_color", "type": ["string", "null"]}
   ]
 }
```

### Parquet
[Apache Parquet](https://parquet.apache.org/)


Apache Parquet is a free and open-source column-oriented data store of the Apache Hadoop ecosystem. It is similar to the other columnar-storage file formats available in Hadoop namely RCFile and Optimized RCFile. It is compatible with most of the data processing frameworks in the Hadoop environment. It provides efficient data compression and encoding schemes with enhanced performance to handle complex data in bulk.
```
4-byte magic number "PAR1"
<Column 1 Chunk 1 + Column Metadata>
<Column 2 Chunk 1 + Column Metadata>
...
<Column N Chunk 1 + Column Metadata>
<Column 1 Chunk 2 + Column Metadata>
<Column 2 Chunk 2 + Column Metadata>
...
<Column N Chunk 2 + Column Metadata>
...
<Column 1 Chunk M + Column Metadata>
<Column 2 Chunk M + Column Metadata>
...
<Column N Chunk M + Column Metadata>
File Metadata
4-byte length in bytes of file metadata
4-byte magic number "PAR1"
```

##Assignemnt 2: Analyse Youtoube trending videos dataset

1. Read trending videos from [data/USvideos.parquet](data/USvideos.parquet)
2. Read corresponding categories from [data/US_category_id.json](data/US_category_id.json) to the form of a flat table (no nested structures). The output should look like this:
```
+---+--------------------+----------+--------------------+--------------------+
| id|                kind|assignable|           channelId|       category_name|
+---+--------------------+----------+--------------------+--------------------+
|  1|youtube#videoCate...|      true|UCBR8-60-B28hp2Bm...|    Film & Animation|
|  2|youtube#videoCate...|      true|UCBR8-60-B28hp2Bm...|    Autos & Vehicles|
| 10|youtube#videoCate...|      true|UCBR8-60-B28hp2Bm...|               Music|
| 15|youtube#videoCate...|      true|UCBR8-60-B28hp2Bm...|      Pets & Animals|
...
```
3. Find top ten most viewed category names (sum of views of all trending videos in the category). Sample output:
```
+-----------+-----------+--------------------+
| sum(views)|category_id|       category_name|
+-----------+-----------+--------------------+
|40132892190|         10|               Music|
|20604388195|         24|       Entertainment|
| 7284156721|          1|    Film & Animation|
| 5117426208|         23|              Comedy|
| 4917191726|         22|      People & Blogs|
| 4404456673|         17|              Sports|
| 4078545064|         26|       Howto & Style|
| 3487756816|         28|Science & Technology|
| 2141218625|         20|              Gaming|
| 1473765704|         25|     News & Politics|
+-----------+-----------+--------------------+
```
4. Normalize tags and store the result in an Avro file. Tags are stored in videos Dataset in the tags column delimited by ```|```. Create a new dataset that will contain only tags and corresponding video_ids. Transform this type of Dataset:
```
+-----------+--------------------------------------------------------------------------------------------------------+
|video_id   |tags                                                                                                    |
+-----------+--------------------------------------------------------------------------------------------------------+
|2kyS6SvSYSE|SHANtell martin                                                                                         |
|1ZAPwfrtAFY|"last week tonight trump presidency"|"last week tonight donald trump"|"john oliver trump"|"donald trump"|
+-----------+--------------------------------------------------------------------------------------------------------+
```
To this type of Dataset:
```
+--------------------+-----------+
|               tags |   video_id|
+--------------------+-----------+
|     SHANtell martin|2kyS6SvSYSE|
|last week tonight...|1ZAPwfrtAFY|
|last week tonight...|1ZAPwfrtAFY|
|   john oliver trump|1ZAPwfrtAFY|
|        donald trump|1ZAPwfrtAFY|
+--------------------+-----------+
```

Data source [Kaggle Trending Youtube video statistics](https://www.kaggle.com/datasnaek/youtube-new/version/113)

### Bonus
Try to store videos datasets in all three formats (CSV, Avro, Parquet) and compare sizes of individual files/directories.

### Hints
[How to process JSON?](https://docs.databricks.com/spark/latest/data-sources/read-json.html)