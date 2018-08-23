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
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestEmailService extends TestNotification {
	
	private final String configIdentifier = "Gmail";
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NotificationManager testSubject;
	@Autowired
	private EventRecorder eventRecorder;
	
	public TestEmailService() {
		super();
	}
	
	@Test
	public void sendEmail() {
		this.logger.info("Send Email Notification");
		this.notificationService.sendEmail("fineractcnnotificationdemo@gmail.com ",
				"egraham15@alustudent.com",
				"Talk is cheap, show me the code",
				"Component test  \n Next Line");
	}
	
	@Test
	public void shouldRetrieveEmailConfigurationEntity() {
		logger.info("Create and retrieve Email Gateway configuration");
		EmailConfiguration sampleRetrieved = this.testSubject.findEmailConfigurationByIdentifier(configIdentifier);
		Assert.assertNotNull(sampleRetrieved);
		Assert.assertEquals(sampleRetrieved.getIdentifier(), configIdentifier);
	}
	
	@Test
	public void checkEmailConfigurationEntityExist() {
		logger.info("Email Gateway configuration Exist");
		Assert.assertTrue(this.notificationService.emailConfigurationExists(configIdentifier));
	}
}
