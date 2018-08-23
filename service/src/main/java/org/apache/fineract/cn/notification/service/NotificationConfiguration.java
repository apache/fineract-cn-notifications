/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.cn.notification.service;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.fineract.cn.identity.api.v1.client.IdentityManager;
import org.apache.fineract.cn.anubis.config.EnableAnubis;
import org.apache.fineract.cn.async.config.EnableAsync;
import org.apache.fineract.cn.cassandra.config.EnableCassandra;
import org.apache.fineract.cn.command.config.EnableCommandProcessing;
import org.apache.fineract.cn.customer.api.v1.client.CustomerManager;
import org.apache.fineract.cn.lang.ApplicationName;
import org.apache.fineract.cn.lang.config.EnableServiceException;
import org.apache.fineract.cn.lang.config.EnableTenantContext;
import org.apache.fineract.cn.mariadb.config.EnableMariaDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("WeakerAccess")
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableAsync
@EnableTenantContext
@EnableCassandra
@EnableMariaDB
@EnableCommandProcessing
@EnableAnubis
@EnableServiceException
@EnableJms
@EnableFeignClients(
		clients = {
				CustomerManager.class,
				IdentityManager.class
		}
)
@ComponentScan({
		"org.apache.fineract.cn.notification.service.rest",
		"org.apache.fineract.cn.notification.service.listener",
		"org.apache.fineract.cn.notification.service.internal.service",
		"org.apache.fineract.cn.notification.service.internal.repository",
		"org.apache.fineract.cn.notification.service.internal.command.handler",
}
)
@EnableJpaRepositories({
		"org.apache.fineract.cn.notification.service.internal.repository"
})
@EntityScan(basePackages = "org.apache.fineract.cn.notification.service.internal.repository")
public class NotificationConfiguration extends WebMvcConfigurerAdapter {
	
	private final Environment environment;
	
	public NotificationConfiguration(Environment environment) {
		super();
		this.environment = environment;
	}
	
	
	@Override
	public void configurePathMatch(final PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(Boolean.FALSE);
	}
	
	@Bean
	public PooledConnectionFactory jmsFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(this.environment.getProperty("activemq.brokerUrl", "vm://localhost?broker.persistent=falseac"));
		pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
		return pooledConnectionFactory;
	}
	
	@Bean
	public JmsListenerContainerFactory jmsListenerContainerFactory(PooledConnectionFactory jmsFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setPubSubDomain(true);
		factory.setConnectionFactory(jmsFactory);
		factory.setErrorHandler(ex -> {
			loggerBean().error(ex.getCause().toString());
		});
		factory.setConcurrency(this.environment.getProperty("activemq.concurrency", "1-1"));
		return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(ApplicationName applicationName, PooledConnectionFactory jmsFactory) {
		ActiveMQTopic activeMQTopic = new ActiveMQTopic(applicationName.toString());
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.setConnectionFactory(jmsFactory);
		jmsTemplate.setDefaultDestination(activeMQTopic);
		return jmsTemplate;
	}
	
	@Bean(
			name = {ServiceConstants.LOGGER_NAME}
	)
	public Logger loggerBean() {
		return LoggerFactory.getLogger(ServiceConstants.LOGGER_NAME);
	}
	
}
