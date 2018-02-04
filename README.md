#IoT data pipeline fullstack 

This is a pretty straight-forward fullstack data pipeline application for collecting continuous data sent out by IoT devices e.g thermostat, heart rate meter, car fuel readings, etc.

The fullstack solution uses Apache Kafka, Spark and Cassandra for streaming and a Springboot app on Jetty with rest apis providing access to the data and the aggregated results.

* metricproducer: IOT device simulator app sending data to kafka every second.
* metricconsumer: Streaming data from Kafka to Cassandra and collect aggregated results 
* metricreader: REST API provider to access to the aggregated results

#Requirements/Specifications

##Basic generic event data generation and definition:
Event
 - group
 - type thermostat, heart rate meter, car fuel readings
 - id
 - value
 - timestamp

## Collect average/median/max/min on the aggregated data over a particular timeframe

##API: 
querying average/median/max/min values for specific sensors or groups of sensors for a specific timeframe

* input: group/id - timeframe
* output: avg/min/max/median

#Prerequisites
* Java 8
* Maven
* Docker (17.12.0-ce) and docker machine (0.13.0)

#Instructions
* Clone the repository

##Run
* Browse to metricparent
* Run docker compose file (TODO)


##Build
* Browse to metricparent
Docker machine ip update on maven
Dont change the cassandra image name.

* Run mvn package
* Run mvn docker:start

You will see containers running for 3 java apps (metricproducer1-3), kafka, cassandra, spark and Springboot app on Jetty (metricreader).


#Build per package linkssssover here

