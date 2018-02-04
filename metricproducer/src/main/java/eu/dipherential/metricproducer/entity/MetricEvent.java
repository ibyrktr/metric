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
package eu.dipherential.metricproducer.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bayrak
 *
 */
public class MetricEvent {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	
	private String group;
	private String id;
	private String type;
	private Date timestamp;
	private BigDecimal value;

	public MetricEvent(String group, String id, String type, BigDecimal value, Date timestamp) {
		super();
		this.group = group;
		this.id = id;
		this.type = type;
		this.timestamp = timestamp;
		this.value = value;
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestampStr() {
		if (timestamp != null) {
			return sdf.format(timestamp);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "{\"group\":\"" + group + "\", \"id\":\"" + id + "\", \"type\":\"" + type
				+ "\", \"timestamp\":\""+getTimestampStr()+"\", \"value\":" + value + "}";
	}

}
