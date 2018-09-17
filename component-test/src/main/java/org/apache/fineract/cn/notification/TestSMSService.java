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
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.apache.fineract.cn.notification.util.DomainObjectGenerator;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestSMSService extends TestNotification {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	private EventRecorder eventRecorder;
	
	private String configIdentifier = "Twilio";
	
	public TestSMSService() {
		super();
	}
	
	
	@Test
	public void shouldCreateNewSMSConfigurationEntity() throws InterruptedException{
		logger.info("Create SMS Gateway configuration");
		this.notificationManager.createSMSConfiguration(DomainObjectGenerator.smsConfiguration());
		
		eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, SMSConfiguration.class);
	}
	
	@Test
	public void shouldTriggerCustomerCreated() throws InterruptedException{
		logger.info("Create SMS Gateway configuration");
		
		eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, SMSConfiguration.class);
	}
	
	@Test(expected = NotFoundException.class)
	public void smsConfigurationNotFound() throws CustomerNotFoundException {
		logger.info("SMS Gateway configuration Not Found");
		try {
			this.notificationManager.findSMSConfigurationByIdentifier(RandomStringUtils.randomAlphanumeric(8));
		} catch (final ConfigurationNotFoundException ex) {
			logger.info("Error Asserted");
		}
	}
	
	@Test
	public void sendSMS() {
		this.logger.info("Send SMS Notification");
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer\n\nTalk is cheap show me the code\n\nBest Regards\nYour MFI");
}
	
	@Test
	public void shouldCreateAndRetrieveSMSConfigurationEntity() {
		logger.info("Create and Retrieve SMS Gateway configuration");
		final SMSConfiguration smsConfiguration = DomainObjectGenerator.smsConfiguration();
		this.notificationManager.createSMSConfiguration(smsConfiguration);
		
		SMSConfiguration sampleRetrieved = this.notificationManager.findSMSConfigurationByIdentifier(configIdentifier);
		Assert.assertNotNull(sampleRetrieved);
		Assert.assertEquals(sampleRetrieved.getIdentifier(), configIdentifier);
	}
	
	@Test
	public void checkSMSConfigurationEntityExist() {
		logger.info("SMS Gateway configuration Exist");
		Assert.assertTrue(this.notificationService.smsConfigurationExists(configIdentifier));
	}
}
