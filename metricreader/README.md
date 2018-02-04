# IoT data reader - Generic Metric Statistics API

This is a component servicing REST APIs over the collected and agreegated data stored on Cassandra.

## Decisions
* Application built with spring boot framework and running on jetty server.
* APIs are secured with OAuth2 and grant type resource owner password credentials with an in-memory database. 
* For documentation and practical purposes a swagger ui module is generated by the application for the rest apis.
* Swagger ui can get an access token. See side notes in deliverables section below for access.


## Deliverables
* The package when build, delivers a packaged jar with an embeded jettty and a image to serve the APIs. When run, it will run the application in the container.

### Side notes: 
* OAuth info: 
** Authentication info: username/password: bayrak/bayrak
** Authorization info (one registered in the in-memory store): client/secret: metric-reader-client/12345

## Prerequisites
* API application requires Cassandra.

## Run
* Browse to metricreader
```
mvn docker:start
```

When run, please check out the url for Swagger UI and API definitions:
```
http://localhost:8079/metricreader/swagger-ui.html 
```

In the Swagger UI via Authorization, please make sure to get an access token with the details provided in the side notes above.

Alternatively,
```
curl -X POST --user "metric-reader-client:12345" -d "grant_type=password&username=bayrak&password=bayrak" http://localhost:8079/metricreader/oauth/token

curl -i -H "Accept: application/json" -H "Authorization: Bearer <put access token here>" -X GET http://localhost:8079/metricreader/api/v1/metrics/id/1/year/2018/month/1/1
```

Alternatively, you can query the cassandra tables

```
docker exec -it bayraktar-metriccassandra cql
cql> select * from relay42.daily_metric_event_by_id;
cql> select * from relay42.daily_metric_event_by_group;
```

## Build
* Browse to metricreader

```
mvn package
mvn docker:start
```

## TODO: 
* SSL/TLS support for MITM, also check for XSS and CSRF
* Unit tests
