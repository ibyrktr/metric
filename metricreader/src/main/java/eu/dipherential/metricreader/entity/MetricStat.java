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
package eu.dipherential.metricreader.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author bayrak
 *
 */
public class MetricStat {

    @ApiModelProperty(notes = "year of the timeframe", example= "2018")
    private int year;
    @ApiModelProperty(notes = "month of the timeframe", example= "1")
    private int month;
    @ApiModelProperty(notes = "day of the timeframe", example= "1")
    private int day;
    @ApiModelProperty(notes = "hour of the timeframe", example= "1")
    private int hour;
    @ApiModelProperty(notes = "average value of the metric of the given timeframe", example= "10.23")
    private double avg;
    @ApiModelProperty(notes = "min value of the metric of the given timeframe", example= "3.4")
    private double min;
    @ApiModelProperty(notes = "max value of the metric of the given timeframe", example= "25.23")
    private double max;
    @ApiModelProperty(notes = "median value of the metric of the given timeframe", example= "12.34")
    private double median;

	public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

}
