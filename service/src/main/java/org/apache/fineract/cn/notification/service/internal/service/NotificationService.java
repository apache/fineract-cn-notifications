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
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.identity.CustomerPermittedClient;
import org.apache.fineract.cn.notification.service.internal.identity.NotificationAuthentication;
import org.apache.fineract.cn.notification.service.internal.service.externalServiceClients.CustomerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {
	
	private final SMSService smsService;
	private final EmailService emailService;
	
	private final NotificationAuthentication notificationAuthentication;
	private final CustomerService customerService;
	private final Logger logger;
	private final CustomerPermittedClient customerPermittedClient;
	
	@Autowired
	
	public NotificationService(final CustomerService customerService,
	                           final SMSService smsService,
	                           final EmailService emailService,
	                           final NotificationAuthentication notificationAuthentication,
	                           final CustomerPermittedClient customerPermittedClient,
	                           @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger
	) {
		super();
		this.customerService = customerService;
		this.smsService = smsService;
		this.emailService = emailService;
		this.notificationAuthentication = notificationAuthentication;
		this.customerPermittedClient = customerPermittedClient;
		this.logger = logger;
	}
	
	public Optional<Customer> findCustomer(final String customerIdentifier, String tenant) {
		return notificationAuthentication.getCustomer(tenant,customerIdentifier);
		//return Optional.of(this.customerPermittedClient.findCustomer(customerIdentifier));
	}
	
	//SMS Related Operations
	public SMSService setNewSMSService(SMSService smsService, String configurationId){
		smsService.customConfiguration(configurationId);
		return smsService;
	}
	
	public String sendSMS(String receiver, String template) {
		if (!this.smsService.isConfigured) this.smsService.configureSMSGatewayWithActiveConfiguration();
		return this.smsService.sendSMS(receiver, template);
	}
	
	//Email Related Operations
	public String sendEmail(String from, String to, String subject, String message) {
		if (!emailService.isConfigured) emailService.configureEmailGatewayWithActiveConfiguration();
		return this.emailService.sendEmail(from, to, subject, message);
	}
	
	public EmailService setNewEmailService(EmailService emailService, String configurationId){
		emailService.customConfiguration(configurationId);
		return emailService;
	}
}
