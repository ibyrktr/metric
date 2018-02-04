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
package eu.dipherential.metricproducer.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bayrak
 */
public class KafkaUtil {

	private static final String KAFKA_SERVERS = "127.0.0.1:9092";
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUtil.class);

	public static void main(String[] args) throws InterruptedException {
		deleteTopic("metrics");
		Thread.sleep(30000);
		createTopic("metrics");

	}

	public static void deleteTopic(String topic) {
		AdminClient admin = getAdminClient();
		final DeleteTopicsResult deleteTopics = admin.deleteTopics(Arrays.asList(new String[] { topic }));
		deleteTopics.values().entrySet().forEach((entry) -> {
			try {
				entry.getValue().get();
				LOGGER.info("topic deleted "+ entry.getKey());
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.error("topic error", e);
			}
		});

	}

	public static void createTopic(String topic) {
		AdminClient admin = getAdminClient();
		NewTopic nt = new NewTopic(topic, 3, (short) 2);
		final CreateTopicsResult result = admin.createTopics(Collections.singleton(nt));

		result.values().entrySet().forEach((entry) -> {
			try {
				entry.getValue().get();
				LOGGER.info("topic created "+ entry.getKey());
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.error("topic error", e);
			}
		});

	}

	private static AdminClient getAdminClient() {
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVERS);

		AdminClient admin = AdminClient.create(props);
		return admin;
	}
}
