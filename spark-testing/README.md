# Spark Application Testing

## Assignment
There is a simple ingest job implemented by [FileToDbIngest](src/main/java/com/ivolasek/sparkcourse/ingest/FileToDbIngest.java). Write tests for classes implementing the ingest:
* [FileLoaderTest](src/test/java/com/ivolasek/sparkcourse/ingest/FileLoaderTest.java)
* [DatasetManipulatorTest](src/test/java/com/ivolasek/sparkcourse/ingest/DatasetManipulatorTest.java)
* [DatasetPersistorTest](src/test/java/com/ivolasek/sparkcourse/ingest/DatasetPersistorTest.java)

The DatasetPersistor can use a mock implementation of the  [ObscureDataFormat](src/test/java/com/ivolasek/sparkcourse/ingest/support/ObscureDataFormat.java).

Sample testing dataset can be found in the [sample.csv](src/test/resources/sample.csv).