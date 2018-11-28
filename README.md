# Introduction to Apache Spark

## Annotation
Apache Spark has quickly become one of the most popular frameworks for Big Data processing. Its flexible and easy to use API as well as a wide range of analytical tools provided on top of Spark make it an ideal choice for many data processing use cases. This course introduces Apache Spark in the context of the whole Big Data ecosystem. We will walk through all major aspects of Spark – ranging from ordinary Spark SQL based ETL jobs, over Spark support for streaming data to DataScience and Machine Learning toolset. We will start with the overview of basic Big Data concepts and Spark principles, so no previous experience with Big Data technologies is needed.

## Audience
* Software developers
* Software architects
* Data analysts, Data scientists
* IT professionals

## Goals
This workshop is designed to familiarize attendees with Apache Spark – a well-known engine for fast Big Data processing. Attendees do not need to have prior knowledge of Apache Spark or Hadoop.

## Agenda
1. [Theoretical introduction](spark-introduction/README.md) *1.5h*
    1. Introduction
        * What is Apache Spark?
        * What was before?
        * Hadoop ecosystem
        * Big picture
    2. Spark Basic Concepts
        * RDD, DataFrame, Dataset
        * DAG – Directed Acyclic Graph
        * Spark ecosystem
    3. Assignment 1: Wordcount
2. [Spark SQL](spark-sql/README.md) *1h*
    1. Basic constructs
    2. Data formats (JSON, Parquet, Avro)
    3. Datsets
    4. Assignemnt 2: Preprocess and join two datasets
3. [Spark for data scientists](spark-datascience/README.md) *45min*
    1. Assignment 4: Data exploration with Zeppelin notebook
4. [Spark Streaming](spark-streaming/README.md) *1.5h*
    1. Streaming in general
    2. Lambda and Kappa architectures
    3. Streaming and Kafka
    4. Assignment 3: Sentiment analysis on Twitter
5. [Machine Learning](spark-ml/README.md) *1.5h*
    1. Assignment 5: Write a basic regression model
6. [Spark application testing](spark-testing/README.md) *45mins*
    1. Spark session
    2. Mocking
    3. Assignment 6: Write a test for an application
7. [Graph processing](spark-graph/README.md) *45mins*
    1. Assignment 7: GraphFrames based application
8. [Deployment](spark-aws/README.md) *45 mins*
    1. Running in cloud
    2. Spark UI
    3. S3 integration
    4. Assignment 8: Run your app in EMR
9. Spark Core Internals
    * Shuffling algorithms [Link](https://0x0fff.com/spark-architecture-shuffle/)
    * Spark memory model [Link](https://medium.com/@pang.xin/spark-study-notes-core-concepts-visualized-5256c44e4090)

## Instructor
**Ivo Lašek (Solution Architect Big Data at MSD IT Global Innovation Center s.r.o.)** https://www.linkedin.com/in/ivolasek

Ivo works as an architect in Big Data team at MSD. Before joining MSD he co-founded several sartups successfully sold to companies like Mlada fronta or Seznam.cz. He has worked with Apache based big data technologies for more than 8 years (Apache Solr, later Elastic, Hadoop since version 0.2 and since 2015 Apache Spark). Ivo gained PhD in Semantic web and web data processing at Faculty of Information Technologies at Czech Technical University in Prague.