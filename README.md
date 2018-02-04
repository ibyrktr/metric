# IoT data pipeline fullstack 

This is a pretty straight-forward fullstack data pipeline application for collecting continuous data sent out by IoT devices e.g thermostat, heart rate meter, car fuel readings, etc.

The fullstack solution uses Apache Kafka, Spark and Cassandra for streaming and a Springboot app on Jetty with rest apis providing access to the data and the aggregated results.

# Requirements/Specifications

## Basic generic event data generation and definition:
### Event
 - group
 - type: thermostat, heart rate meter, car fuel readings
 - id
 - value
 - timestamp

## Collect average/median/max/min on the aggregated data over a particular timeframe

## API: 
querying average/median/max/min values for specific sensors or groups of sensors for a specific timeframe

* input: group/id - timeframe
* output: avg/min/max/median

# Packages

* metricproducer: IOT device simulator app sending data to kafka every second.
* metricconsumer: Streaming data from Kafka to Cassandra and collect aggregated results 
* metricreader: REST API provider to access to the aggregated results

# Prerequisites
* Java 8
* Maven
* Docker (17.12.0-ce) and docker machine (0.13.0)

# Deliverables
* Every package when built, delivers a jar and and docker images.

# Instructions
* Clone the repository

## Run
* Browse to metricparent
* Run the command below to download the docker images
```
mvn docker:start
```
You will see containers running for 3 java apps (metricproducer1-3), kafka, cassandra, spark and Springboot app on Jetty (metricreader).

## Build
* Browse to metricparent
* Run the command below to build/start the docker images
```
mvn package
mvn docker:start
```

## Build per component

[metricproducer](metricproducer/README.md)

[metricconsumer](metricconsumer/README.md)

[metricreader](metricreader/README.md)

