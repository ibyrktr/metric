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
package eu.dipherential.metricconsumer.util;

import java.io.Serializable;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bayrak
 */
public class SparkHelper implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2025766266543027193L;
	
	private static SparkConf sparkConf;
    private static SparkSession sparkSession;
    private static SparkContext sparkContext;
    
    private static final String APP = "MetricConsumer";
    private static String KAFKA_HOST;
    
    public static final String KAFKA_TOPIC = "metrics";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SparkHelper.class);
    
    public static SparkConf getConf() {
        if (sparkConf == null) {
            sparkConf = new SparkConf(true).setAppName(APP);
        }
        LOGGER.debug(sparkConf.toDebugString());
        return sparkConf;
    }

    public static SparkSession getSession() {
        if (sparkSession == null) {
            sparkSession = SparkSession
                    .builder().config(getConf())
                    .getOrCreate();
        }
        return sparkSession;
    }

    public static SparkContext getContext() {
        if (sparkContext == null) {
            sparkContext = getSession().sparkContext();
        }
        return sparkContext;
    }
    
    public static String getKafkaHost(){
        
        if (KAFKA_HOST == null) {
            KAFKA_HOST = getConf().get("spark.kafka.bootstrap.servers");
        }
        return KAFKA_HOST;
    }
}
