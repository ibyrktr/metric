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

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import eu.dipherential.metricconsumer.util.TimestampCodec;

/**
 * @author bayrak
 *
 */
@Table(name = "metric_event_by_id", keyspace = "metric")
public class MetricEventById implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4365854253772695938L;
	@PartitionKey
	private String id;
	private String type;
	@Column(codec = TimestampCodec.class)
	private Timestamp timestamp;
	private double value;
	private String group;

	public MetricEventById(RawMetricEvent m) {
		this.id = m.getId();
		this.group = m.getGroup();
		this.type = m.getType();
		this.timestamp = m.getTimestamp();
		this.value = m.getValue();

	}

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

	@Override
	public String toString() {
		return "MetricEventById [id=" + id + ", type=" + type + ", timestamp=" + timestamp + ", value=" + value
				+ ", group=" + group + "]";
	}

}
