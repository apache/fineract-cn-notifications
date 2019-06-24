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
package org.apache.fineract.cn.notification;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.fineract.cn.api.util.NotFoundException;
import org.apache.fineract.cn.customer.api.v1.domain.Address;
import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.notification.api.v1.client.ConfigurationNotFoundException;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.service.EmailService;
import org.apache.fineract.cn.notification.util.DomainObjectGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestEmailService extends AbstractNotificationTest {
	
	final EmailConfiguration emailConfiguration;
	@Autowired
	private EmailService emailService;
	
	
	public TestEmailService() {
		super();
		emailConfiguration = DomainObjectGenerator.emailConfiguration();
	}
	
	@Test
	public void shouldSendAnEmail() throws InterruptedException,IOException {
		this.logger.info("Send Email Notification");
		notificationService.sendEmail(
				TEST_ADDRESS,
				TEST_TEMPLATE,
				null);
	}
	
	@Test
	public void shouldSendFormattedEmail() throws InterruptedException, IOException {
		this.logger.info("Send Email Notification");
		
		Customer customerPayload = new Customer();
		customerPayload.setGivenName("Test");
		customerPayload.setSurname("User");
		Address address = new Address();
		address.setCity("Cape Coast");
		address.setCity("Street");
		address.setCountry("Ghana");
		address.setCountryCode("GH");
		address.setRegion("Central Region");
		address.setPostalCode("T22022");
		customerPayload.setAddress(address);
		
		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put(customerPayload.getClass().getName().toLowerCase(),customerPayload);
		
		notificationService.sendFormattedEmail(
				TEST_ADDRESS,
				TEST_TEMPLATE,
				templateVariables
		);
	}
	
	@Test(expected = NotFoundException.class)
	public void emailConfigurationNotFound() throws ConfigurationNotFoundException {
		logger.info("Configuration not found");
		try {
			notificationManager.findEmailConfigurationByIdentifier(RandomStringUtils.randomAlphanumeric(8));
		} catch (final ConfigurationNotFoundException ex) {
			logger.info("Error Asserted");
		}
	}
	
	@Test
	public void shouldCreateAndRetrieveEmailConfigurationEntity() throws InterruptedException {
		logger.info("Create and Retrieve Email Gateway configuration");
		notificationManager.createEmailConfiguration(emailConfiguration);
		
		Assert.assertTrue(this.eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier()));
		
		EmailConfiguration sampleRetrieved = notificationManager.findEmailConfigurationByIdentifier(emailConfiguration.getIdentifier());
		Assert.assertEquals(sampleRetrieved,emailConfiguration);
	}
	
	@Test
	public void checkEmailConfigurationEntityExist() throws InterruptedException {
		logger.info("Email Gateway configuration Exist");
		notificationManager.createEmailConfiguration(emailConfiguration);
		Assert.assertTrue(eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier()));
		Assert.assertTrue(this.emailService.emailConfigurationExists(emailConfiguration.getIdentifier()));
	}
	
	@Test
	public void deleteConfiguration() throws InterruptedException {
		logger.info("Delete Email configuration");
		
		notificationManager.createEmailConfiguration(emailConfiguration);
		Assert.assertTrue(eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier()));
		
		notificationManager.deleteEmailConfiguration(emailConfiguration.getIdentifier());
		Assert.assertTrue(eventRecorder.wait(NotificationEventConstants.DELETE_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier()));
	}
	
	@Test
	public void shouldFindDefaultGateway() {
		this.logger.info("Find Active Gateway");
		Assert.assertNotNull(this.emailService.getDefaultEmailConfigurationEntity());
	}
}
