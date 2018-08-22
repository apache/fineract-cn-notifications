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

import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.notification.api.v1.client.NotificationManager;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.apache.fineract.cn.notification.util.DomainObjectGenerator;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TestEmailService extends TestNotification {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationManager testSubject;
	
	private final String configIdentifier = "Gmail";
	
	@Autowired
	private EventRecorder eventRecorder;

	public TestEmailService() {
		super();
	}
	
	@Test
	public void sendEmail(){
		this.logger.info("Send Email Notification");
		this.notificationService.sendEmail("fineractcnnotificationdemo@gmail.com ",
				"egraham15@alustudent.com",
				"Talk is cheap, show me the code",
				"Component test  \n");
	}
	
	@Test
	public void shouldCreateEmailConfigurationEntity() throws InterruptedException {
		logger.info("Create and retrieve Email Gateway configuration");
		final EmailConfiguration emailConfiguration = DomainObjectGenerator.emailConfiguration();
		this.testSubject.createEmailConfiguration(emailConfiguration);
		
		Assert.assertTrue(this.eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, emailConfiguration.getIdentifier()));
		
		final EmailConfiguration createdSample = this.testSubject.findEmailConfigurationByIdentifier(emailConfiguration.getIdentifier());
		Assert.assertEquals(emailConfiguration, createdSample);
	}
	
	@Test
	public void retrieveEmailConfiguration(){
		logger.info("Retrieving stored config from repositiory");
		EmailConfiguration emailConfiguration = this.testSubject.findEmailConfigurationByIdentifier(configIdentifier);
		
		Assert.assertNotNull(emailConfiguration);
		Assert.assertEquals(emailConfiguration.getIdentifier(),configIdentifier);
	}
	
	private void prepareMocks(final String customerIdentifier) {
		Mockito
				.doAnswer(invocation -> Optional.of(new Customer()))
				.when(this.notificationService)
				.findCustomer(Matchers.eq(customerIdentifier),
						tenantDataStoreContext.getTenantName());
		
	}
}
