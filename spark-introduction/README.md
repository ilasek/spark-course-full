# Spark Introduction
Walk through the [Spark introduction presentation](SparkIntroduction.pptx).

## Assignemnet 1: Log Analysis
 Complete the code in [LogAnalysis.java](src/main/java/com/ivolasek/sparkcourse/wordcount/LogAnalysis.java) so that
 the program walks through the log file stored in [data/sample.log](data/sample.log) and computes number of times each
 severity (INFO, WARNING, ERROR, DEBUG) appears in the log.

 ### Sample file
 ```
 WARNING This is WARNING message
 DEBUG This is DEBUG message
 DEBUG This is DEBUG message
 INFO This is INFO message
 WARNING This is WARNING message
 ERROR This is ERROR message
 ```

### Sample output
```
[(WARNING,2), (ERROR,1), (DEBUG,2), (INFO,1)]
```