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
/**
 * 
 */
package eu.dipherential.metricproducer.util;

import com.beust.jcommander.Parameter;

/**
 * @author bayrak
 *
 */
public class Config {

	@Parameter(names = "-kafkaHost", description = "Kafka host")
	public String kafkaHost;
	@Parameter(names = "-group", description = "Unique group id of the device")
	private String group;
	@Parameter(names = "-id", description = "Unique id of the device")
	private String id;
	@Parameter(names = "-type", description = "Type: enter THERM for thermostat, HEARTRATE for heart rate meter, FUEL for car fuel readings")
	private String type;
	
	public String getKafkaHost() {
		return kafkaHost;
	}

	public void setKafkaHost(String kafkaHost) {
		this.kafkaHost = kafkaHost;
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

	@Override
	public String toString() {
		return "Config [kafkaHost=" + kafkaHost + ", group=" + group + ", id=" + id + ", type=" + type + "]";
	}
	
}
