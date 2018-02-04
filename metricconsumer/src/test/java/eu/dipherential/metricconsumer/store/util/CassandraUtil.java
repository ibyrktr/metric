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
package eu.dipherential.metricconsumer.store.util;

import java.io.Serializable;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7543256416889233729L;

	public static void main(String[] args) {
		createTableAndKeyspace();
	}

	public static void createTableAndKeyspace() {

		// Prepare the schema
		try
		(
			Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withCredentials("cassandra", "cassandra")
				.build(); 
			Session session = cluster.connect();
		) 
		{
			session.execute("DROP KEYSPACE IF EXISTS metric");
			session.execute(
					"CREATE KEYSPACE metric WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");
			session.execute("DROP TABLE IF EXISTS metric.metric_event_by_group");
			session.execute("CREATE TABLE metric.metric_event_by_group ("
					+ "    group text,    						"
					+ "    timestamp timestamp,              	"
					+ "    id text,    							"
					+ "    value double,               			"
					+ "    type text,    						"
					+ "    PRIMARY KEY ((group), timestamp, id)) WITH CLUSTERING ORDER BY (timestamp DESC)");
			session.execute("DROP TABLE IF EXISTS metric.metric_event_by_id");
			session.execute("CREATE TABLE metric.metric_event_by_id ("
					+ "    id text,    							"
					+ "    timestamp timestamp,              	"
					+ "    value double,               			"
					+ "    group text,    						"
					+ "    type text,    						"
					+ "    PRIMARY KEY ((id), timestamp)) WITH CLUSTERING ORDER BY (timestamp DESC)");
			session.execute("DROP TABLE IF EXISTS metric.hourly_metric_event_by_id");
			session.execute("CREATE TABLE metric.hourly_metric_event_by_id (" + 
					"   id text,    							" + 
					"   year int,							" + 
					"   month int,							" + 
					"   day int,								" + 
					"   hour int,							" + 
					"   avg double,               			" + 
					"   min double,    						" + 
					"   max double,    						" + 
					"   median double,    					" + 
					"   PRIMARY KEY ((id), year, month, day, hour)" + 
					") WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC, hour DESC)");
		}
	}

}
