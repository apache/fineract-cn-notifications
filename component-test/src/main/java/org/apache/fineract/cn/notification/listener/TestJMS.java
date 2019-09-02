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
package org.apache.fineract.cn.notification.listener;

import org.apache.fineract.cn.notification.AbstractNotificationTest;
import org.apache.fineract.cn.notification.service.internal.service.EventHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants.TEST;

public class TestJMS extends AbstractNotificationTest {
	
	@Autowired
	EventHelper eventHelper;
	
	
	public TestJMS() {
		super();
	}
	
	@Test
	public void testJMSConcurrency() throws InterruptedException,IOException {
		this.logger.info("Send JMS event");
		eventHelper.sendEvent(TEST,"test-tenant","payload");
		Assert.assertTrue(eventRecorder.wait(TEST,"payload"));
	}
}
