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
package eu.dipherential.metricproducer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;

import eu.dipherential.metricproducer.util.Config;
import eu.dipherential.metricproducer.util.Helper;

public class Run {
	private static final Logger LOGGER = LoggerFactory.getLogger(Run.class);
	private static final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	private static Config c = new Config();
	
	public static void main(String[] args) throws InterruptedException {
		
		JCommander.newBuilder().addObject(c).build().parse(args);
		ses.scheduleAtFixedRate(new MessageProducer(c), 1, 1, TimeUnit.SECONDS);
		
		Thread.sleep(600000);
		shutdownAndAwaitTermination();
		
		LOGGER.info("Run ended!");
	}

	public static void shutdownAndAwaitTermination() {
		ses.shutdown();
		try {
			if (!ses.awaitTermination(1, TimeUnit.MINUTES)) {
				ses.shutdownNow();
				Helper.getInstance(c.getKafkaHost()).close();
				if (!ses.awaitTermination(1, TimeUnit.MINUTES))
					LOGGER.error("ScheduledExecutorService did not terminate!!");
			}
		} catch (InterruptedException ie) {
			ses.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
