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
package eu.dipherential.metricreader;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import eu.dipherential.metricreader.swagger.conf.Config;

@SpringBootApplication
public class MetricReaderApplication extends SpringBootServletInitializer {

	@Autowired
	Config config;
	
	public static void main(String[] args) {
		SpringApplication.run(MetricReaderApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		JettyEmbeddedServletContainerFactory scf = new JettyEmbeddedServletContainerFactory(config.getContextPath(), config.getPort());
		scf.setSessionTimeout(10, TimeUnit.MINUTES);
		return scf;
	}

	public @Bean Session session() {
		Cluster cluster = Cluster.builder().addContactPoints(config.getCassandraHost()).build();
		return cluster.connect();
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
