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

import org.apache.fineract.cn.notification.api.v1.client.NotificationManager;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
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
	private NotificationManager testSubject;
	
	@Autowired
	private EventRecorder eventRecorder;
	
	private String configIdentifier = "Twilio";
	
	public TestSMSService() {
		super();
	}
	
	@Test
	public void sendSMS() {
		this.logger.info("Send SMS Notification");
//		this.notificationService.sendSMS("+23058409206",
//				"Talk is cheap show me the code");
	}
	
	@Test
	public void shouldCreateAndRetrieveSMSConfigurationEntity() {
		logger.info("Create and Retrieve SMS Gateway configuration");
		final SMSConfiguration smsConfiguration = DomainObjectGenerator.smsConfiguration();
		this.testSubject.createSMSConfiguration(smsConfiguration);
		
		SMSConfiguration sampleRetrieved = this.testSubject.findSMSConfigurationByIdentifier(configIdentifier);
		Assert.assertNotNull(sampleRetrieved);
		Assert.assertEquals(sampleRetrieved.getIdentifier(), configIdentifier);
	}
	
	@Test
	public void checkSMSConfigurationEntityExist() {
		logger.info("SMS Gateway configuration Exist");
		Assert.assertTrue(this.notificationService.smsConfigurationExists(configIdentifier));
	}
	
}
