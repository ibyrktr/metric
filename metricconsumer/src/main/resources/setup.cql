DROP KEYSPACE IF EXISTS metric;
CREATE KEYSPACE metric WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
DROP TABLE IF EXISTS metric.metric_event_by_group;
CREATE TABLE metric.metric_event_by_group (
	group text,    						
	timestamp timestamp,              	
	id text,    							
	value double,               			
	type text,    						
	PRIMARY KEY ((group), timestamp, id)) WITH CLUSTERING ORDER BY (timestamp DESC);
DROP TABLE IF EXISTS metric.metric_event_by_id;
CREATE TABLE metric.metric_event_by_id (
	id text,    							
	timestamp timestamp,              	
	value double,               			
	group text,    						
	type text,    						
	PRIMARY KEY ((id), timestamp)) WITH CLUSTERING ORDER BY (timestamp DESC);
DROP TABLE IF EXISTS metric.hourly_metric_event_by_id;
CREATE TABLE metric.hourly_metric_event_by_id ( 
	id text,    							 
	year int,							 
	month int,							 
	day int,								 
	hour int,							 
	avg double,               			 
	min double,    						 
	max double,    						 
	median double,    					 
	PRIMARY KEY ((id), year, month, day, hour) 
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC, hour DESC);
DROP TABLE IF EXISTS metric.daily_metric_event_by_id;
CREATE TABLE metric.daily_metric_event_by_id ( 
	id text,    							 
	year int,							 
	month int,							 
	day int,								 
	avg double,               			 
	min double,    						 
	max double,    						 
	median double,    					 
	PRIMARY KEY ((id), year, month, day) 
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC);
DROP TABLE IF EXISTS metric.monthly_metric_event_by_id;
CREATE TABLE metric.monthly_metric_event_by_id ( 
	id text,    							 
	year int,							 
	month int,							 
	avg double,               			 
	min double,    						 
	max double,    						 
	median double,    					 
	PRIMARY KEY ((id), year, month) 
) WITH CLUSTERING ORDER BY (year DESC, month DESC);
DROP TABLE IF EXISTS metric.hourly_metric_event_by_group;
CREATE TABLE metric.hourly_metric_event_by_group ( 
	group text,    							 
	year int,							 
	month int,							 
	day int,								 
	hour int,							 
	avg double,               			 
	min double,    						 
	max double,    						 
	median double,    					 
	PRIMARY KEY ((group), year, month, day, hour) 
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC, hour DESC);
DROP TABLE IF EXISTS metric.daily_metric_event_by_group;
CREATE TABLE metric.daily_metric_event_by_group ( 
	group text,    							 
	year int,							 
	month int,							 
	day int,								 
	avg double,               			 
	min double,    						 
	max double,    						 
	median double,    					 
	PRIMARY KEY ((group), year, month, day) 
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC);
DROP TABLE IF EXISTS metric.monthly_metric_event_by_group;
CREATE TABLE metric.monthly_metric_event_by_group ( 
	group text,    							 
	year int,							 
	month int,							 
	avg double,               			 
	min double,    						 
	max double,    						 
	median double,    					 
	PRIMARY KEY ((group), year, month) 
) WITH CLUSTERING ORDER BY (year DESC, month DESC);
DROP TABLE IF EXISTS metric.users;
CREATE TABLE metric.users(username text, password text, primary key(username));
insert into metric.users(username, password) values ('bayrak', 'bayrak');