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
package eu.dipherential.metricproducer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.dipherential.metricproducer.entity.MetricEvent;
import eu.dipherential.metricproducer.util.Config;
import eu.dipherential.metricproducer.util.Helper;

/**
 * @author bayrak
 *
 */
public class MessageProducer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

	private Config c;
	
	public MessageProducer(Config c) {
		super();
		this.c=c;
	}

	@Override
	public void run() {
		
		String key = c.getId();
		String value= new MetricEvent(c.getGroup(), c.getId(), c.getType(), BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10, 100)).setScale(2,
				RoundingMode.HALF_EVEN), new Date()).toString();
		 
		LOGGER.debug(value);

		Future<RecordMetadata> future = Helper.getInstance(c.getKafkaHost()).send(new ProducerRecord<String, String>("metrics", key, value));
		try {
			RecordMetadata recordMetadata = future.get();
			LOGGER.info("Published into partition >> "+recordMetadata.partition()+" offset >> "+recordMetadata.offset());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("", e);
		}
	}
}
