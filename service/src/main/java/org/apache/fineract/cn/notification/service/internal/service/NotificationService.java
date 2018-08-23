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

import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.mapper.EmailConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.mapper.SMSConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.EmailGatewayConfigurationRepository;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationRepository;
import org.apache.fineract.cn.notification.service.internal.service.helperservice.CustomerAdaptor;
import org.apache.fineract.cn.notification.service.internal.service.helperservice.NotificationAuthentication;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
	private final SMSService smsService;
	private final EmailService emailService;
	
	private final SMSGatewayConfigurationRepository smsGatewayConfigurationRepository;
	private final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository;
	
	private final NotificationAuthentication notificationAuthentication;
	private final CustomerAdaptor customerAdaptor;
	private final Logger logger;
	
	private final String configureIdentifier = "Twilio";
	
	@Autowired
	public NotificationService(final SMSGatewayConfigurationRepository smsGatewayConfigurationRepository,
	                           final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository,
	                           final CustomerAdaptor customerAdaptor,
	                           final SMSService smsService,
	                           final EmailService emailService,
	                           final NotificationAuthentication notificationAuthentication,
	                           @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger
	) {
		super();
		this.smsGatewayConfigurationRepository = smsGatewayConfigurationRepository;
		this.emailGatewayConfigurationRepository = emailGatewayConfigurationRepository;
		this.customerAdaptor = customerAdaptor;
		this.smsService = smsService;
		this.emailService = emailService;
		this.notificationAuthentication = notificationAuthentication;
		this.logger = logger;
		this.logger.debug("{} has been initiated", this.getClass());
	}
	
	
	public List<SMSConfiguration> findAllActiveSMSConfigurationEntities() {
		return SMSConfigurationMapper.map(this.smsGatewayConfigurationRepository.findAll());
	}
	
	public List<EmailConfiguration> findAllActiveEmailConfigurationEntities() {
		return EmailConfigurationMapper.map(this.emailGatewayConfigurationRepository.findAll());
	}
	
	public Optional<SMSConfiguration> findSMSConfigurationByIdentifier(final String identifier) {
		return this.smsGatewayConfigurationRepository.findByIdentifier(identifier).map(SMSConfigurationMapper::map);
	}
	
	
	public Optional<EmailConfiguration> findEmailConfigurationByIdentifier(final String identifier) {
		return this.emailGatewayConfigurationRepository.findByIdentifier(identifier).map(EmailConfigurationMapper::map);
	}
	
	public Optional<Customer> findCustomer(final String customerIdentifier, String tenant) {
		notificationAuthentication.authenticate(tenant);
		return this.customerAdaptor.findCustomer(customerIdentifier);
	}
	
	public Boolean smsConfigurationExists(final String identifier) {
		return this.smsGatewayConfigurationRepository.existsByIdentifier(identifier);
	}
	
	public Boolean emailConfigurationExists(final String identifier) {
		return this.emailGatewayConfigurationRepository.existsByIdentifier(identifier);
	}
	
	public void configureSMSSender() {
		SMSConfiguration configuration = findSMSConfigurationByIdentifier(configureIdentifier).get();
		smsService.configure(configuration.getAccount_sid(),
				configuration.getAuth_token(),
				configuration.getSender_number());
	}
	
	public void sendSMS(String receiver, String template) {
		this.smsService.sendSMS(receiver, template);
	}
	
	public void sendEmail(String from, String to, String subject, String message) {
		this.emailService.sendEmail(from, to, subject, message);
	}
}
