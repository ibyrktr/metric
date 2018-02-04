# Metric Producer - IoT data generator
A simple java process that sends MetricEvent messages to Apache Kafka.

## Data Definition

It uses basic generic event data definition for different device types:

### MetricEvent
 - timestamp
 - group : Unique group id of the device to get readings for a collection of devices.
 - type: Type id: thermostat, heart rate meter, car fuel readings
 - id: Unique id of the device
 - value: Snapshot value for the device every second

## Decisions:

- Decided to add the group attribute to MetricEvent to satisfy the req: groups of sensors
- EventData kept simple. No separate entity for the device and a registration process for the group, type and id of the device, just get it as argument. No nested structures.
- Use of docker containers to simulate an isolated event data generation per device 
- Use the id of the device as key in the producer message for partitioning to scale out.

## Prerequisites
*Apache Kafka is required.

## Run
```
    docker run bayraktar/metricproducer -it java -jar /opt/tools/metricproducer.jar -group home1 -id room3 -type THERM -kafkaHost relay42-kafka:9092 --log-driver json-file --log-opt max-size=10m max-file=5
```

** CAUTION Please specify the kafka host above.

## Build
*Browse to metricproducer directory

```
    mvn package
    mvn docker:start
```
## TODO: 
* Review/Revise Kafka config for the producer for production set up
* Unit tests



