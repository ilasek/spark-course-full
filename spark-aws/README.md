# Spark deployment on AWS
## Preparation for AWS deployment
### Application master setting
Remove master setting from your code:

```java
.master("local[*]")
```
And run the code with following JVM variable locally:
```
-Dspark.master=local[*]
```

### Reference all files via arguments
```java
Dataset<Row> flights = spark.read()
        .option("inferSchema", true)
        // No hardcoded path here
        .csv(args[0])
        .withColumnRenamed("_c6", "src")
        .withColumnRenamed("_c8", "dst")
        .withColumnRenamed("_c16", "distance");
```

### Adjust your pom to build a fat jar
See [parent pom.xml](../pom.xml) for the full code.
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>2.3</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <filters>
                    <filter>
                        <artifact>*:*</artifact>
                        <excludes>
                            <exclude>META-INF/*.SF</exclude>
                            <exclude>META-INF/*.DSA</exclude>
                            <exclude>META-INF/*.RSA</exclude>
                        </excludes>
                    </filter>
                </filters>
                <finalName>${project.artifactId}-${project.version}-jar-with-dependencies</finalName>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Copy built jar to the server

```
scp -i ~/.ssh/your-private-key.pem target/spark-graph-1.0-SNAPSHOT-jar-with-dependencies.jar hadoop@aws-emr-master-node.compute-1.amazonaws.com:
```

### Submit the Spark job
```bash
spark-submit --master yarn --num-executors 20 --executor-cores 1 --executor-memory 1G\
    --class com.ivolasek.sparkcourse.graph.FlightAnalysis \
    spark-graph-1.0-SNAPSHOT-jar-with-dependencies.jar \
    "s3://spark-course-deployment/us_flights.csv" 20
```

## Assignment
Deploy your [graph processing job](../spark-graph/src/main/java/com/ivolasek/sparkcourse/graph/FlightAnalysis.java) on AWS and run PageRank with 20 iterations on the cluster.
