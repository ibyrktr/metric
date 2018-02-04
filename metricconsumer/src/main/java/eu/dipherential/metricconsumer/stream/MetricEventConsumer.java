/*******************************************************************************
 * Copyright 2018 Ismail Bayraktar
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.dipherential.metricconsumer.stream;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.dayofmonth;
import static org.apache.spark.sql.functions.hour;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.min;
import static org.apache.spark.sql.functions.month;
import static org.apache.spark.sql.functions.window;
import static org.apache.spark.sql.functions.year;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;

import eu.dipherential.metricconsumer.store.DailyMetricStatsByGroupSink;
import eu.dipherential.metricconsumer.store.DailyMetricStatsByIdSink;
import eu.dipherential.metricconsumer.store.ForeachSink;
import eu.dipherential.metricconsumer.store.HourlyMetricStatsByGroupSink;
import eu.dipherential.metricconsumer.store.HourlyMetricStatsByIdSink;
import eu.dipherential.metricconsumer.store.MonthlyMetricStatsByGroupSink;
import eu.dipherential.metricconsumer.store.MonthlyMetricStatsByIdSink;
import eu.dipherential.metricconsumer.stream.entity.DailyMetricStatsByGroup;
import eu.dipherential.metricconsumer.stream.entity.DailyMetricStatsById;
import eu.dipherential.metricconsumer.stream.entity.HourlyMetricStatsByGroup;
import eu.dipherential.metricconsumer.stream.entity.HourlyMetricStatsById;
import eu.dipherential.metricconsumer.stream.entity.MonthlyMetricStatsByGroup;
import eu.dipherential.metricconsumer.stream.entity.MonthlyMetricStatsById;
import eu.dipherential.metricconsumer.stream.entity.RawMetricEvent;
import eu.dipherential.metricconsumer.util.SparkHelper;

public class MetricEventConsumer implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3667407976766144517L;

	public static void main(String[] args) throws StreamingQueryException {
		System.out.println(SparkHelper.getConf().toDebugString());
		Dataset<Row> json = SparkHelper.getSession()
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", SparkHelper.getKafkaHost())
                .option("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .option("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .option("subscribe", SparkHelper.KAFKA_TOPIC)
                .option("enable.auto.commit", false)
                .load()
                .selectExpr("CAST(value AS STRING) as kafkaRaw")
                .select(functions.from_json(functions.col("kafkaRaw"), RawMetricEvent.schema).as("kafkaRecord"))
                .select("kafkaRecord.*");
		
		Dataset<RawMetricEvent> rawMetrics = json
                .as(Encoders.bean(RawMetricEvent.class));
		
		Dataset<HourlyMetricStatsById> hourlyMetricsById = json
				.withWatermark("timestamp", "1 minutes")
                .groupBy(col("id")
                        ,year(col("timestamp")).alias("year")
                        ,month(col("timestamp")).alias("month")
                        ,dayofmonth(col("timestamp")).alias("day")
                        ,hour(col("timestamp")).alias("hour")
                			,window(col("timestamp"), "1 hours")
                        )
                .agg(avg("value").alias("avg"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        callUDF("percentile_approx", col("value"), lit(0.5)).alias("median"))
                .drop("window")
                .as(Encoders.bean(HourlyMetricStatsById.class));
		
		Dataset<HourlyMetricStatsByGroup> hourlyMetricsByGroup = json
				.withWatermark("timestamp", "1 minutes")
                .groupBy(col("group")
                        ,year(col("timestamp")).alias("year")
                        ,month(col("timestamp")).alias("month")
                        ,dayofmonth(col("timestamp")).alias("day")
                        ,hour(col("timestamp")).alias("hour")
                			,window(col("timestamp"), "1 hours")
                        )
                .agg(avg("value").alias("avg"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        callUDF("percentile_approx", col("value"), lit(0.5)).alias("median"))
                .drop("window")
                .as(Encoders.bean(HourlyMetricStatsByGroup.class));

		Dataset<DailyMetricStatsById> dailyMetricsById = json
				.withWatermark("timestamp", "5 minutes")
                .groupBy(col("id")
                        ,year(col("timestamp")).alias("year")
                        ,month(col("timestamp")).alias("month")
                        ,dayofmonth(col("timestamp")).alias("day")
                			,window(col("timestamp"), "1 days")
                        )
                .agg(avg("value").alias("avg"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        callUDF("percentile_approx", col("value"), lit(0.5)).alias("median"))
                .drop("window")
                .as(Encoders.bean(DailyMetricStatsById.class));

		Dataset<DailyMetricStatsByGroup> dailyMetricsByGroup = json
				.withWatermark("timestamp", "5 minutes")
                .groupBy(col("group")
                        ,year(col("timestamp")).alias("year")
                        ,month(col("timestamp")).alias("month")
                        ,dayofmonth(col("timestamp")).alias("day")
                			,window(col("timestamp"), "1 days")
                        )
                .agg(avg("value").alias("avg"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        callUDF("percentile_approx", col("value"), lit(0.5)).alias("median"))
                .drop("window")
                .as(Encoders.bean(DailyMetricStatsByGroup.class));

		Dataset<MonthlyMetricStatsById> monthlyMetricsById = json
				.withWatermark("timestamp", "2 days")
                .groupBy(col("id")
                        ,year(col("timestamp")).alias("year")
                        ,month(col("timestamp")).alias("month")
                			,window(col("timestamp"), "30 days", "1 days")
                        )
                .agg(avg("value").alias("avg"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        callUDF("percentile_approx", col("value"), lit(0.5)).alias("median"))
                .drop("window")
                .as(Encoders.bean(MonthlyMetricStatsById.class));

		Dataset<MonthlyMetricStatsByGroup> monthlyMetricsByGroup = json
				.withWatermark("timestamp", "2 days")
                .groupBy(col("group")
                        ,year(col("timestamp")).alias("year")
                        ,month(col("timestamp")).alias("month")
                			,window(col("timestamp"), "30 days", "1 days")
                        )
                .agg(avg("value").alias("avg"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        callUDF("percentile_approx", col("value"), lit(0.5)).alias("median"))
                .drop("window")
                .as(Encoders.bean(MonthlyMetricStatsByGroup.class));
	
		StreamingQuery rawMetricQuery = rawMetrics
                .writeStream()
                .queryName("RawMetrics")
                .foreach(new ForeachSink())
                .option("checkpointLocation", "checkpoint/raw")
                .outputMode("append")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.SECONDS))
                .start();
		
		StreamingQuery hourlyMetricByIdQuery = hourlyMetricsById
                .writeStream()
                .queryName("HourlyMetricsById")
                .foreach(new HourlyMetricStatsByIdSink())
                .option("checkpointLocation", "checkpoint/id/hourly")
                .outputMode("update")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
                .start();
		
		StreamingQuery dailyMetricByIdQuery = dailyMetricsById
                .writeStream()
                .queryName("DailyMetricsById")
                .foreach(new DailyMetricStatsByIdSink())
                .option("checkpointLocation", "checkpoint/id/daily")
                .outputMode("update")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
                .start();
		
		StreamingQuery monthlyMetricByIdQuery = monthlyMetricsById
                .writeStream()
                .queryName("MonthlyMetricsById")
                .foreach(new MonthlyMetricStatsByIdSink())
                .option("checkpointLocation", "checkpoint/id/monthly")
                .outputMode("update")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
                .start();

		StreamingQuery hourlyMetricByGroupQuery = hourlyMetricsByGroup
                .writeStream()
                .queryName("HourlyMetricsByGroup")
                .foreach(new HourlyMetricStatsByGroupSink())
                .option("checkpointLocation", "checkpoint/group/hourly")
                .outputMode("update")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
                .start();
		
		StreamingQuery dailyMetricByGroupQuery = dailyMetricsByGroup
                .writeStream()
                .queryName("DailyMetricsByGroup")
                .foreach(new DailyMetricStatsByGroupSink())
                .option("checkpointLocation", "checkpoint/group/daily")
                .outputMode("update")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
                .start();
		
		StreamingQuery monthlyMetricByGroupQuery = monthlyMetricsByGroup
                .writeStream()
                .queryName("MonthlyMetricsByGroup")
                .foreach(new MonthlyMetricStatsByGroupSink())
                .option("checkpointLocation", "checkpoint/group/monthly")
                .outputMode("update")
                .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
                .start();
		

		hourlyMetricByIdQuery.awaitTermination();
		dailyMetricByIdQuery.awaitTermination();
		monthlyMetricByIdQuery.awaitTermination();
		hourlyMetricByGroupQuery.awaitTermination();
		dailyMetricByGroupQuery.awaitTermination();
		monthlyMetricByGroupQuery.awaitTermination();
        rawMetricQuery.awaitTermination();

    }

}    
