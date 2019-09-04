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
import org.apache.fineract.cn.notification.api.v1.client.NotificationManager;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.apache.fineract.cn.notification.service.internal.service.SMSService;
import org.apache.fineract.cn.notification.util.DomainObjectGenerator;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestSMSService extends AbstractNotificationTest {
	
	final SMSConfiguration smsConfiguration;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private SMSService smsService;
	@Autowired
	private NotificationManager notificationManager;
	@Autowired
	private EventRecorder eventRecorder;
	
	public TestSMSService() {
		super();
		this.smsConfiguration = DomainObjectGenerator.smsConfiguration();
	}
	
	@Test(expected = NotFoundException.class)
	public void smsConfigurationNotFound() {
		super.logger.info("SMS Gateway configuration Not Found");
		this.notificationManager.findSMSConfigurationByIdentifier(RandomStringUtils.randomAlphanumeric(8));
	}
	
	@Test
	public void shouldCreateAndRetrieveSMSConfigurationEntity() throws InterruptedException {
		logger.info("Create and Retrieve SMS Gateway configuration");
		this.notificationManager.createSMSConfiguration(smsConfiguration);
		
		Assert.assertTrue(this.eventRecorder.wait(NotificationEventConstants.POST_SMS_CONFIGURATION, smsConfiguration.getIdentifier()));
		
		SMSConfiguration sampleRetrieved = this.notificationManager.findSMSConfigurationByIdentifier(smsConfiguration.getIdentifier());
		Assert.assertEquals(sampleRetrieved.getIdentifier(), smsConfiguration.getIdentifier());
	}
	
	@Test
	public void shouldFindActiveGateway() {
		this.logger.info("Find Active Gateway");
		Assert.assertNotNull(this.smsService.getDefaultSMSConfiguration());
	}
	
	@Test
	public void checkIfConfigurationAlreadyExists() throws InterruptedException{
		logger.info("SMS Gateway configuration Exist");
		
		this.notificationManager.createSMSConfiguration(smsConfiguration);
		this.eventRecorder.wait(NotificationEventConstants.POST_SMS_CONFIGURATION, smsConfiguration.getIdentifier());
		
		Assert.assertTrue(this.smsService.smsConfigurationExists(this.smsConfiguration.getIdentifier()));
	}
	
	@Test
	public void shouldSendAnSMS(){
		this.logger.info("Send SMS Notification");
		int to = this.notificationService.sendSMS(SMS_TEST_NUMBER,
				TEST_TEMPLATE);
		Assert.assertNotNull(to);
	}
	
	@Test
	public void deleteConfiguration() throws InterruptedException {
		logger.info("Delete SMS configuration");
		
		notificationManager.createSMSConfiguration(smsConfiguration);
		Assert.assertTrue(eventRecorder.wait(NotificationEventConstants.POST_SMS_CONFIGURATION, smsConfiguration.getIdentifier()));
		
		notificationManager.deleteSMSConfiguration(smsConfiguration.getIdentifier());
		Assert.assertTrue(eventRecorder.wait(NotificationEventConstants.DELETE_SMS_CONFIGURATION, smsConfiguration.getIdentifier()));
	}
}
