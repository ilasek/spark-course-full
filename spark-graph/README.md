# Spark Graph processing with GraphFrames

## Introduction
Check a [Graph processing basics on Edureka](https://www.edureka.co/blog/spark-graphx/). But in this assignment we will use the new GraphFrames Spark API. You can walk through its [introduction by Databricks](https://databricks.com/blog/2016/03/03/introducing-graphframes.html).

[Graph Analysis Tutorial](https://docs.databricks.com/spark/latest/graph-analysis/graphframes/graph-analysis-tutorial.html) has some useful examples, too.

[Full GraphFrames user guide](https://graphframes.github.io/user-guide.html).

## Assignment
1. Load [US flights data](data/us_flights.csv) into a GraphFrame.
2. Find airports with the highest number of incoming flights.
    ```
    Sample output:
    +-----+--------+----+
    |   id|inDegree|name|
    +-----+--------+----+
    |10397|     152| ATL|
    |13930|     145| ORD|
    |11298|     143| DFW|
    |11292|     132| DEN|
    |12266|     107| IAH|
    |13487|      96| MSP|
    |12892|      82| LAX|
    |11618|      82| EWR|
    |11433|      81| DTW|
    |14869|      80| SLC|
    |14771|      75| SFO|
    |14107|      75| PHX|
    |12889|      74| LAS|
    |13204|      70| MCO|
    |12264|      69| IAD|
    |13232|      63| MDW|
    |11057|      61| CLT|
    |10821|      61| BWI|
    |12478|      60| JFK|
    |14747|      58| SEA|
    +-----+--------+----+
    ```
3. Compute a PageRank for all airports and print the top most important airports according to PageRank.
    ```
    Sample output:
    +-----+----+------------------+----+
    |   id|name|          pagerank|name|
    +-----+----+------------------+----+
    |10397| ATL| 7.234050832613434| ATL|
    |13930| ORD| 7.213862152577017| ORD|
    |11298| DFW|  6.99162406929604| DFW|
    |11292| DEN|6.0307665266001855| DEN|
    |13487| MSP| 5.699448887212467| MSP|
    |12266| IAH| 4.563637687922999| IAH|
    |14869| SLC| 4.532801763893138| SLC|
    |14771| SFO| 4.378177322174111| SFO|
    |12892| LAX| 3.959653111825886| LAX|
    |14107| PHX|  3.64874186757224| PHX|
    |11433| DTW|3.6381163185335703| DTW|
    |14747| SEA|3.5537512572539374| SEA|
    |12889| LAS|3.4010250081790137| LAS|
    |10299| ANC| 3.373902656779621| ANC|
    |11618| EWR| 3.297535703729323| EWR|
    |13204| MCO|  3.12048307355465| MCO|
    |12264| IAD|2.8850557573090514| IAD|
    |12478| JFK|2.8735170540540067| JFK|
    |11057| CLT| 2.831765828358933| CLT|
    |10821| BWI|2.6949881310942163| BWI|
    +-----+----+------------------+----+
    ```
4. Find airports that you can travel between in a cycle: Airport1 -> Airpot2 -> Airport1 and the total travel distance is greater than 10300 miles.
    ```
    Sample output:
    +-----+----------------+-----+----------------+-----+----------------+
    |    A|              e1|    B|              e2|    C|              e3|
    +-----+----------------+-----+----------------+-----+----------------+
    |[HNL]|[HNL, IAH, 3904]|[IAH]|[IAH, JFK, 1417]|[JFK]|[JFK, HNL, 4983]|
    |[JFK]|[JFK, IAH, 1417]|[IAH]|[IAH, HNL, 3904]|[HNL]|[HNL, JFK, 4983]|
    |[HNL]|[HNL, JFK, 4983]|[JFK]|[JFK, IAH, 1417]|[IAH]|[IAH, HNL, 3904]|
    |[IAH]|[IAH, JFK, 1417]|[JFK]|[JFK, HNL, 4983]|[HNL]|[HNL, IAH, 3904]|
    |[IAH]|[IAH, HNL, 3904]|[HNL]|[HNL, JFK, 4983]|[JFK]|[JFK, IAH, 1417]|
    |[JFK]|[JFK, HNL, 4983]|[HNL]|[HNL, IAH, 3904]|[IAH]|[IAH, JFK, 1417]|
    +-----+----------------+-----+----------------+-----+----------------+
    ```