# IoT data pipeline - streaming

This is a component streaming IoT data with Apache Kafka, Spark and Cassandra. The application uses Spark structured streaming to store raw and aggregate data to Cassandra.

## Phsical Data Definition
Specifications for Cassandra tables storing raw and aggregated data can be found here: [schema] (metricconsumer/src/main/resources/setup.cql)

## Decisions
* Spark structured streaming used to build the solution instead of having a real-streaming and a batch processing application. 
** Application relies on spark checkpoint for fault-tolerancy. 
** Same applies for scalability. Spark handles the workload distribution towards the worker threads per kafka topic partition.
* The application has in total 8 streaming queries running: 2 real-time and 6 for hour/day/month aggregated data.
* Kafka default retention must be increased from 168 to 750 hours.
* Streaming queries uses foreach writer to write to Cassandra tables. There are 6 cassandra tables: 2 for raw event data and 6 for aggregates.

## Deliverables
* The package when build, delivers a spark application jar and three docker images: Kafka, Cassandra and Spark. When run, it will run the application in spark container.

* CAUTION: 
** Do not modify the cassandra hostname in maven configuration. Changing the hostname will require a change on spark application code. See issue raised: (Spark application does not respect to cassandra host registered in the default SpakConf).  
** Cassandra docker image environment variable CASSANDRA_BROADCAST_ADDRESS in the maven pom uses default docker-machine ip 192.168.99.100. Please adjust it based on your settings if needed.

## Prerequisites
* Spark application requires Kafka and Cassandra.

## Run
* Browse to metricconsumer
```
mvn docker:start
```

When run, please check out the spark log: 

```
docker exec -it bayraktar-metricspark tail -f /opt/apache-spark/log/spark.log
```

## Build
* Browse to metricparent
* Keep an eye on the caution items above.

```
mvn package
mvn docker:start
```
## TODO: 
* Review/Revise Kafka, spark and cassandra config for the producer for production set up
* Unit tests


