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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.dipherential.metricconsumer.stream.entity;

import java.io.Serializable;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import com.datastax.driver.mapping.annotations.Table;

/**
 * @author bayrak
 *
 */
@Table(name = "daily_metric_event_by_id", keyspace = "metric")
public class DailyMetricStatsById extends MonthlyMetricStatsById implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static transient StructType schema = new StructType().add("id", DataTypes.StringType, true)
			.add("year", DataTypes.IntegerType, true).add("month", DataTypes.IntegerType, true)
			.add("day", DataTypes.IntegerType, true).add("avg", DataTypes.DoubleType, true)
			.add("min", DataTypes.DoubleType, true).add("max", DataTypes.DoubleType, true)
			.add("median", DataTypes.DoubleType, true);

	private int day;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
