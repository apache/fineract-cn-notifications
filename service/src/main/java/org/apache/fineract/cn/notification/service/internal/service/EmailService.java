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

import org.apache.fineract.cn.command.annotation.CommandHandler;
import org.apache.fineract.cn.command.annotation.CommandLogLevel;
import org.apache.fineract.cn.command.annotation.EventEmitter;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.mapper.EmailConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.EmailGatewayConfigurationRepository;

import org.apache.fineract.cn.notification.service.internal.service.util.MailBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Component
public class EmailService {
	
	private final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository;
	boolean isConfigured;
	private JavaMailSenderImpl mailSender;
	private MailBuilder mailBuilder;
	private Logger logger;
	
	@Autowired
	public EmailService(final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository,
	                    final MailBuilder mailBuilder,
	                    @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		super();
		this.isConfigured = false;
		this.logger = logger;
		this.mailSender = new JavaMailSenderImpl();
		this.emailGatewayConfigurationRepository = emailGatewayConfigurationRepository;
		this.mailBuilder = mailBuilder;
	}
	
	public boolean configureEmailGatewayWithDefaultGateway() {
		EmailConfiguration configuration = getDefaultEmailConfigurationEntity().get();
		return setNewConfiguration(configuration);
	}
	
	public List<EmailConfiguration> findAllEmailConfigurationEntities() {
		return EmailConfigurationMapper.map(this.emailGatewayConfigurationRepository.findAll());
	}
	
	public Optional<EmailConfiguration> getDefaultEmailConfigurationEntity() {
		return this.emailGatewayConfigurationRepository.defaultGateway().map(EmailConfigurationMapper::map);
	}
	
	public Optional<EmailConfiguration> findEmailConfigurationByIdentifier(final String identifier) {
		return this.emailGatewayConfigurationRepository.findByIdentifier(identifier).map(EmailConfigurationMapper::map);
	}
	
	public Boolean emailConfigurationExists(final String identifier) {
		return this.emailGatewayConfigurationRepository.existsByIdentifier(identifier);
	}
	
	boolean setNewConfiguration(String identifier) {
		EmailConfiguration configuration = findEmailConfigurationByIdentifier(identifier).get();
		return setNewConfiguration(configuration);
	}
	
	private boolean setNewConfiguration(EmailConfiguration configuration) {
		try {
			this.mailSender.setHost(configuration.getHost());
			this.mailSender.setPort(Integer.parseInt(configuration.getPort()));
			this.mailSender.setUsername(configuration.getUsername());
			this.mailSender.setPassword(configuration.getApp_password());
			
			Properties properties = new Properties();
			properties.put(ServiceConstants.MAIL_TRANSPORT_PROTOCOL_PROPERTY, configuration.getProtocol());
			properties.put(ServiceConstants.MAIL_SMTP_AUTH_PROPERTY, configuration.getSmtp_auth());
			properties.put(ServiceConstants.MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, configuration.getStart_tls());
			this.mailSender.setJavaMailProperties(properties);
			this.isConfigured = true;
			return true;
		} catch (RuntimeException ignore) {
			logger.error("Failed to configure the Email Gateway");
		}
		return false;
	}
	
	public String sendPlainEmail(String to, String subject, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		
		try {
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(message);
			this.mailSender.send(mail);
			return to;
		} catch (MailException exception) {
			logger.debug("Caused by:" + exception.getCause().toString());
		}
		return to;
	}
	
	public String sendFormattedEmail(String to,
	                                 String subject,
	                                 Map<String, Object> message,
	                                 String emailTemplate) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			String content = mailBuilder.build(message, emailTemplate);
			messageHelper.setText(content, true);
		};
		try {
			this.mailSender.send(messagePreparator);
			return to;
		} catch (MailException e) {
			logger.error("Failed to send Formatted email{}", e.getMessage());
		}
		return null;
	}
}
