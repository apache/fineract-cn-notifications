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
package org.apache.fineract.cn.notification.service.internal.service;

import org.apache.fineract.cn.command.annotation.Aggregate;
import org.apache.fineract.cn.command.annotation.CommandHandler;
import org.apache.fineract.cn.command.annotation.CommandLogLevel;
import org.apache.fineract.cn.command.annotation.EventEmitter;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.mapper.EmailConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.EmailGatewayConfigurationRepository;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
@Aggregate
public class EmailService {
	
	static boolean isConfigured;
	
	private final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository;
	private JavaMailSenderImpl mailSender;
	
	private Logger logger;
	private String host;
	private String email;
	private int port;
	private String password;
	
	@Autowired
	public EmailService(final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository,
	                    @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		super();
		this.isConfigured = false;
		this.logger = logger;
		this.mailSender = new JavaMailSenderImpl();
		this.emailGatewayConfigurationRepository = emailGatewayConfigurationRepository;
	}
	
	//@PostConstruct
	public void init() {
		if (findActiveEmailConfigurationEntity().isPresent()){
			configureEmailGatewayWithActiveConfiguration();
		}else{
			//Todo: Send an alert on the interface to configure the service
		}
	}
	
	public boolean configureEmailGatewayWithActiveConfiguration() {
		EmailConfiguration configuration = findActiveEmailConfigurationEntity().get();
		
		this.host = configuration.getHost();
		this.email = configuration.getUsername();
		this.port = Integer.parseInt(configuration.getPort());
		this.password = configuration.getApp_password();
		return this.isConfigured = setJavaMailSender();
	}
	
	public boolean customConfiguration(String identifier) {
		return this.isConfigured = setCustomProperties(identifier);
	}
	
	public List<EmailConfiguration> findAllActiveEmailConfigurationEntities() {
		return EmailConfigurationMapper.map(this.emailGatewayConfigurationRepository.findAll());
	}
	
	public Optional<EmailConfiguration> findActiveEmailConfigurationEntity() {
		return this.emailGatewayConfigurationRepository.active().map(EmailConfigurationMapper::map);
	}
	
	public Optional<EmailConfiguration> findEmailConfigurationByIdentifier(final String identifier) {
		return this.emailGatewayConfigurationRepository.findByIdentifier(identifier).map(EmailConfigurationMapper::map);
	}
	
	public Boolean emailConfigurationExists(final String identifier) {
		return this.emailGatewayConfigurationRepository.existsByIdentifier(identifier);
	}
	
	public boolean setJavaMailSender() {
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(email);
		mailSender.setPassword(password);
		
		switch (host.toLowerCase()) {
			case ServiceConstants.GOOGLE_MAIL_SERVER:
				return setProperties();
			case ServiceConstants.YAHOO_MAIL_SERVER:
				return setProperties();
		}
		return false;
	}
	
	public boolean setProperties() {
		Properties properties = new Properties();
		properties.put(ServiceConstants.MAIL_TRANSPORT_PROTOCOL_PROPERTY,
				ServiceConstants.MAIL_TRANSPORT_PROTOCOL_VALUE);
		properties.put(ServiceConstants.MAIL_SMTP_AUTH_PROPERTY,
				ServiceConstants.MAIL_SMTP_AUTH_VALUE);
		properties.put(ServiceConstants.MAIL_SMTP_STARTTLS_ENABLE_PROPERTY,
				ServiceConstants.MAIL_SMTP_STARTTLS_ENABLE_VALUE);
		this.mailSender.setJavaMailProperties(properties);
		return true;
	}
	
	public boolean setCustomProperties(String identifier) {
		EmailConfiguration configuration = findEmailConfigurationByIdentifier(identifier).get();
		this.mailSender.setHost(configuration.getHost());
		this.mailSender.setPort(Integer.parseInt(configuration.getPort()));
		this.mailSender.setUsername(configuration.getUsername());
		this.mailSender.setPassword(configuration.getApp_password());
		
		Properties properties = new Properties();
		properties.put(ServiceConstants.MAIL_TRANSPORT_PROTOCOL_PROPERTY, configuration.getProtocol());
		properties.put(ServiceConstants.MAIL_SMTP_AUTH_PROPERTY, configuration.getSmtp_auth());
		properties.put(ServiceConstants.MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, configuration.getStart_tls());
		//properties.put(ServiceConstants.MAIL_SMTP_TIMEOUT_PROPERTY, ServiceConstants.MAIL_SMTP_TIMEOUT_VALUE);
		this.mailSender.setJavaMailProperties(properties);
		return true;
	}
	
	@CommandHandler(logStart = CommandLogLevel.INFO, logFinish = CommandLogLevel.INFO)
	@Transactional
	@EventEmitter(selectorName = NotificationEventConstants.SELECTOR_NAME, selectorValue = NotificationEventConstants.POST_SEND_EMAIL_NOTIFICATION)
	public String sendEmail(String from, String to, String subject, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		
		try {
			mail.setFrom(from);
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(message);
			
			this.mailSender.send(mail);
		} catch (MailException exception) {
			logger.debug("Caused by:" + exception.getCause().toString());
		}
		return to.concat(" - " + mailSender.hashCode());
	}
}