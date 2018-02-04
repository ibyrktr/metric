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
package eu.dipherential.metricreader.db;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import eu.dipherential.metricreader.entity.MetricStatById;
import org.springframework.data.repository.query.Param;


public interface MetricStatByIdDAO extends CassandraRepository<MetricStatById> {
	@Query("select * from metric.hourly_metric_event_by_id where id= :id and year= :year and month = :month and day = :day and hour >= :hourFrom and hour <= :hourTo")
	public List<MetricStatById> findByIdHourlyRange(@Param("id") String id, @Param("year") int year,
			@Param("month") int month, @Param("day") int day, @Param("hourFrom") int hourFrom,
			@Param("hourTo") int hourTo);

	@Query("select * from metric.daily_metric_event_by_id where id= :id and year= :year and month = :month and day >= :dayFrom and day <= :dayTo")
	public List<MetricStatById> findByIdDailyRange(@Param("id") String id, @Param("year") int year,
			@Param("month") int month, @Param("dayFrom") int dayFrom, @Param("dayTo") int dayTo);

	@Query("select * from metric.monthly_metric_event_by_id where id= :id and year= :year and month >= :monthFrom and month <= :monthTo")
	public List<MetricStatById> findByIdMonthlyRange(@Param("id") String id, @Param("year") int year,
			@Param("monthFrom") int monthFrom, @Param("monthTo") int monthTo);

}
