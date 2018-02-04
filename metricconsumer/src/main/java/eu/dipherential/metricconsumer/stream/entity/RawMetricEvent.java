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
package eu.dipherential.metricconsumer.stream.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

/**
 * @author bayrak
 *
 */

public class RawMetricEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8628423040933084016L;

	public static transient StructType schema = new StructType()
			.add("group", DataTypes.StringType, true)
			.add("id", DataTypes.StringType, true)
			.add("type", DataTypes.StringType, true)
			.add("timestamp", DataTypes.TimestampType, true)
			.add("value", DataTypes.DoubleType, true);

	private String id;
	private String type;
	private Timestamp timestamp;
	private double value;
	private String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
