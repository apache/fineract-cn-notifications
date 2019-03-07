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
import org.apache.fineract.cn.notification.service.internal.identity.NotificationAuthentication;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TestApplicationAuthentication extends TestNotification {
	
	private final String applicationIdentifier = "customer-v1";
	
	@Autowired
	NotificationAuthentication notificationAuthenticationMock;
	
	
	public TestApplicationAuthentication() {
		super();
	}
	
	@Test
	public void createApplicationPermissionForCustomerService() {
		//Todo : look at it later
		//Assert.assertFalse(this.notificationAuthenticationMock.authenticateWithCustomerService(super.tenantDataStoreContext.getTenantName()));
	}
	
}
