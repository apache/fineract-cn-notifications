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
import org.apache.fineract.cn.notification.api.v1.domain.Template;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.identity.CustomerPermittedClient;
import org.apache.fineract.cn.notification.service.internal.identity.NotificationAuthentication;
import org.apache.fineract.cn.notification.service.internal.service.externalServiceClients.CustomerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {
	
	private final SMSService smsService;
	private final EmailService emailService;
	private final TemplateService templateService;
	
	private final NotificationAuthentication notificationAuthentication;
	private final CustomerService customerService;
	private final Logger logger;
	private final CustomerPermittedClient customerPermittedClient;
	
	@Autowired
	
	public NotificationService(final CustomerService customerService,
	                           final SMSService smsService,
	                           final EmailService emailService,
	                           final TemplateService templateService,
	                           final NotificationAuthentication notificationAuthentication,
	                           final CustomerPermittedClient customerPermittedClient,
	                           @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger
	) {
		super();
		this.customerService = customerService;
		this.smsService = smsService;
		this.emailService = emailService;
		this.templateService = templateService;
		this.notificationAuthentication = notificationAuthentication;
		this.customerPermittedClient = customerPermittedClient;
		this.logger = logger;
	}
	
	public Optional<Customer> findCustomer(final String customerIdentifier, String tenant) {
		notificationAuthentication.authenticate(tenant);
		//return notificationAuthentication.getCustomer(tenant,customerIdentifier);
		return customerService.findCustomer(customerIdentifier);
	}
	
	public String sendSMS(String receiver, String template) {
		if (!this.smsService.isConfigured) this.smsService.configureServiceWithDefaultGateway();
		return this.smsService.sendSMS(receiver, template);
	}
	
	/*To be used as a backup should Formatted email fail*/
	public void sendEmail(String to, String templateIdentifier,Object payload) {
		Template template = this.templateService.findTemplateWithIdentifier(templateIdentifier).get();
		if (!this.emailService.isConfigured) {
			this.emailService.setNewConfiguration(template.getSenderEmail());
		}
		this.emailService.sendPlainEmail(to, template.getSubject(), template.getMessage());
	}
	
	public void sendFormattedEmail(String to, String templateIdentifier, Map<String,Object> variables) {
		Template template = this.templateService.findTemplateWithIdentifier(templateIdentifier).get();
		if (!this.emailService.isConfigured) {
			this.emailService.setNewConfiguration(template.getSenderEmail());
		}
		
		this.emailService.sendFormattedEmail(to, template.getSubject(), variables,template.getUrl());
	}
}
