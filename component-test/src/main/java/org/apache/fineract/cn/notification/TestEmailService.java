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
import org.apache.fineract.cn.customer.api.v1.client.CustomerNotFoundException;
import org.apache.fineract.cn.notification.api.v1.client.ConfigurationNotFoundException;
import org.apache.fineract.cn.notification.api.v1.client.NotificationManager;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.service.EmailService;
import org.apache.fineract.cn.notification.service.internal.service.EventHelper;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.apache.fineract.cn.notification.util.DomainObjectGenerator;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestEmailService extends AbstractNotificationTest {
	
	final EmailConfiguration emailConfiguration;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private NotificationManager notificationManager;
	
	public TestEmailService() {
		super();
		emailConfiguration = DomainObjectGenerator.emailConfiguration();
	}
	
	
	@Test
	public void sendEmail() throws InterruptedException {
		this.logger.info("Send Email Notification");
		String messageHash = notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
				"egraham15@alustudent.com",
				"Address Details Changed",
				"Dear Valued Customer," +
						"\n\nYour address has been changed successfully" +
						"\nStreet: Test Street" +
						"\nCity: Test City" +
						"\nState: Test State" +
						"\nCountry: Mauritius" +
						"\n\nBest Regards" +
						"\nMFI");
		
		Assert.assertNotNull(messageHash);
	}
	
	@Test(expected = NotFoundException.class)
	public void emailConfigurationNotFound() throws ConfigurationNotFoundException {
		logger.info("Configuration not found");
		try {
			this.notificationManager.findEmailConfigurationByIdentifier(RandomStringUtils.randomAlphanumeric(8));
		} catch (final ConfigurationNotFoundException ex) {
			logger.info("Error Asserted");
		}
	}
	
	@Test
	public void shouldCreateAndRetrieveEmailConfigurationEntity() throws InterruptedException {
		logger.info("Create and Retrieve Email Gateway configuration");
		this.notificationManager.createEmailConfiguration(emailConfiguration);
		
		this.eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier());
		
		EmailConfiguration sampleRetrieved = this.notificationManager.findEmailConfigurationByIdentifier(emailConfiguration.getIdentifier());
		Assert.assertNotNull(sampleRetrieved);
		Assert.assertEquals(sampleRetrieved.getIdentifier(), emailConfiguration.getIdentifier());
	}
	
	@Test
	public void checkEmailConfigurationEntityExist() throws InterruptedException {
		logger.info("Email Gateway configuration Exist");
		this.notificationManager.createEmailConfiguration(emailConfiguration);
		super.eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier());
		
		Assert.assertTrue(this.emailService.emailConfigurationExists(emailConfiguration.getIdentifier()));
	}
	
	@Test
	public void shouldFindActiveGateway() {
		this.logger.info("Find Active Gateway");
		Assert.assertNotNull(this.emailService.findActiveEmailConfigurationEntity());
	}
}
