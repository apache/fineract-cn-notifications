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

import com.google.gson.Gson;
import org.apache.fineract.cn.command.util.CommandConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class EventHelper {
	private final Gson gson;
	private final JmsTemplate jmsTemplate;
	
	public EventHelper(final @Qualifier(CommandConstants.SERIALIZER) Gson gson, final JmsTemplate jmsTemplate) {
		this.gson = gson;
		this.jmsTemplate = jmsTemplate;
	}
	
	public void sendEvent(final String eventName, final String tenantIdentifier, final Object payload) {
		this.jmsTemplate.convertAndSend(
				this.gson.toJson(payload),
				message -> {
					message.setStringProperty(
							TenantHeaderFilter.TENANT_HEADER,
							tenantIdentifier);
					message.setStringProperty(
							NotificationEventConstants.SELECTOR_NAME,
							eventName
					);
					return message;
				}
		);
	}
}
