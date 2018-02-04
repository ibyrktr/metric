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
package eu.dipherential.metricconsumer.store;

import org.apache.spark.sql.ForeachWriter;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.spark.connector.cql.CassandraConnector;

import eu.dipherential.metricconsumer.stream.entity.DailyMetricStatsById;
import eu.dipherential.metricconsumer.util.SparkHelper;

/**
 *
 * @author bayrak
 */
public class DailyMetricStatsByIdSink extends ForeachWriter<DailyMetricStatsById> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -215059558369968316L;

	private static CassandraConnector connector = CassandraConnector
			.apply(SparkHelper.getConf().set("spark.cassandra.connection.host", "bayraktar-metriccassandra"));
	private Mapper<DailyMetricStatsById> map;
	private Session session;

	public DailyMetricStatsByIdSink() {
		super();
	}

	@Override
	public boolean open(long l, long l1) {
		// @FIXME : Although spark default conf contains
		// spark.cassandra.connection.host,
		// it forgets the settings. So here it is set manually.

		session = connector.openSession();
		MappingManager manager = new MappingManager(session);
		map = manager.mapper(DailyMetricStatsById.class);
		return true;
	}

	@Override
	public void process(DailyMetricStatsById m) {
		map.save(m);

	}

	@Override
	public void close(Throwable thrwbl) {
		session.close();
	}
}
