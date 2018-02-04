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
package eu.dipherential.metricreader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.dipherential.metricreader.db.MetricStatByGroupDAO;
import eu.dipherential.metricreader.db.MetricStatByIdDAO;
import eu.dipherential.metricreader.entity.MetricStatByGroup;
import eu.dipherential.metricreader.entity.MetricStatById;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"Generic Metric Statistics API"}, value=" ", description=" ")
@RestController
@RequestMapping("/api/v1")
class MetricReaderController {

	@Autowired
	private MetricStatByGroupDAO mg;
	@Autowired
	private MetricStatByIdDAO mi;

	@ApiOperation(value = "View hourly metric statistics per device group", response = MetricStatByGroup.class, responseContainer = "List" )
	@RequestMapping(value = "/metrics/group/{group}/year/{year}/month/{month}/day/{day}/hour/{hourFrom}/{hourTo}", method = RequestMethod.GET, produces = "application/json")
	List<MetricStatByGroup> getMetricStatsByGroupHourlyRange(@PathVariable String group, @PathVariable int year,
			@PathVariable int month, @PathVariable int day, @PathVariable int hourFrom, @PathVariable int hourTo) {
		List<MetricStatByGroup> list = mg.findByGroupHourlyRange(group, year, month, day, hourFrom, hourTo);
		list.forEach(System.out::println);
		return list;
	}

	@ApiOperation(value = "View daily metric statistics per device group", response = MetricStatByGroup.class, responseContainer = "List")
	@RequestMapping(value = "/metrics/group/{group}/year/{year}/month/{month}/day/{dayFrom}/{dayTo}", method = RequestMethod.GET, produces = "application/json")
	List<MetricStatByGroup> getMetricsStatsByGroupDailyRange(@PathVariable String group, @PathVariable int year,
			@PathVariable int month, @PathVariable int dayFrom, @PathVariable int dayTo) {
		List<MetricStatByGroup> list = mg.findByGroupDailyRange(group, year, month, dayFrom, dayTo);
		list.forEach(System.out::println);
		return list;
	}

	@ApiOperation(value = "View monthly metric statistics per device group", response = MetricStatByGroup.class, responseContainer = "List")
	@RequestMapping(value = "/metrics/group/{group}/year/{year}/month/{monthFrom}/{monthTo}", method = RequestMethod.GET, produces = "application/json")
	List<MetricStatByGroup> getMetricsStatsByGroupMonthlyRange(@PathVariable String group, @PathVariable int year,
			@PathVariable int monthFrom, @PathVariable int monthTo) {
		List<MetricStatByGroup> list = mg.findByGroupMonthlyRange(group, year, monthFrom, monthTo);
		list.forEach(System.out::println);
		return list;
	}

	@ApiOperation(value = "View hourly metric statistics per device", response = MetricStatById.class, responseContainer = "List" )
	@RequestMapping(value = "/metrics/id/{id}/year/{year}/month/{month}/day/{day}/hour/{hourFrom}/{hourTo}", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successful"),
	        @ApiResponse(code = 401, message = "Not authorized"),
	        @ApiResponse(code = 403, message = "Forbidden"),
	        @ApiResponse(code = 404, message = "Not found")})
	List<MetricStatById> getMetricStatsByIdHourlyRange(@PathVariable String id, @PathVariable int year,
			@PathVariable int month, @PathVariable int day, @PathVariable int hourFrom, @PathVariable int hourTo) {
		List<MetricStatById> list = mi.findByIdHourlyRange(id, year, month, day, hourFrom, hourTo);
		list.forEach(System.out::println);
		return list;
	}

	@ApiOperation(value = "View daily metric statistics per device", response = MetricStatById.class, responseContainer = "List" )
	@RequestMapping(value = "/metrics/id/{id}/year/{year}/month/{month}/day/{dayFrom}/{dayTo}", method = RequestMethod.GET, produces = "application/json")
	List<MetricStatById> getMetricsStatsByIdDailyRange(@PathVariable String id, @PathVariable int year,
			@PathVariable int month, @PathVariable int dayFrom, @PathVariable int dayTo) {
		List<MetricStatById> list = mi.findByIdDailyRange(id, year, month, dayFrom, dayTo);
		list.forEach(System.out::println);
		return list;
	}

	@ApiOperation(value = "View monthly metric statistics per device", response = MetricStatById.class, responseContainer = "List" )
	@RequestMapping(value = "/metrics/id/{id}/year/{year}/month/{monthFrom}/{monthTo}", method = RequestMethod.GET, produces = "application/json")
	List<MetricStatById> getMetricsStatsByIdMonthlyRange(@PathVariable String id, @PathVariable int year,
			@PathVariable int monthFrom, @PathVariable int monthTo) {
		List<MetricStatById> list = mi.findByIdMonthlyRange(id, year, monthFrom, monthTo);
		list.forEach(System.out::println);
		return list;
	}
}
