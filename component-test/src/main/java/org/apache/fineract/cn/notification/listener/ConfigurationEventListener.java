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

import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class ConfigurationEventListener {
	private final EventRecorder eventRecorder;
	private final Logger logger;
	
	@Autowired
	public ConfigurationEventListener(final EventRecorder eventRecorder,
	                                  @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		this.logger = logger;
		this.eventRecorder = eventRecorder;
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_POST_EMAIL_CONFIGURATION
	)
	public void postEmailConfiguration(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                 final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.POST_EMAIL_CONFIGURATION, payload, String.class);
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_POST_SMS_CONFIGURATION
	)
	public void postSMSConfiguration(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                   final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.POST_SMS_CONFIGURATION, payload, String.class);
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_SEND_EMAIL_NOTIFICATION
	)
	public void onSendEmailTrigger(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                   final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.SEND_EMAIL_NOTIFICATION, payload, String.class);
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_SEND_SMS_NOTIFICATION
	)
	public void onSendSmsTrigger(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                               final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.SEND_SMS_NOTIFICATION, payload, String.class);
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_DELETE_EMAIL_CONFIGURATION
	)
	public void onDeleteEmailConfiguration(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                           final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.DELETE_EMAIL_CONFIGURATION, payload, String.class);
		logger.info("onDeleteEmailConfiguration received");
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_DELETE_SMS_CONFIGURATION
	)
	public void onDeleteSmsConfiguration(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                       final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.DELETE_SMS_CONFIGURATION, payload, String.class);
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_UPDATE_EMAIL_CONFIGURATION
	)
	public void onUpdateEmailConfiguration(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                     final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.UPDATE_EMAIL_CONFIGURATION, payload, String.class);
	}
	
	@JmsListener(
			subscription = NotificationEventConstants.DESTINATION,
			destination = NotificationEventConstants.DESTINATION,
			selector = NotificationEventConstants.SELECTOR_UPDATE_SMS_CONFIGURATION
	)
	public void onUpdateSmsConfiguration(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                       final String payload) {
		this.eventRecorder.event(tenant, NotificationEventConstants.UPDATE_SMS_CONFIGURATION, payload, String.class);
	}
		
		@JmsListener(
				subscription = NotificationEventConstants.DESTINATION,
				destination = NotificationEventConstants.DESTINATION,
				selector = NotificationEventConstants.SELECTOR_POST_TEMPLATE
		)
		public void onPostTemplate(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
		final String payload) {
			this.eventRecorder.event(tenant, NotificationEventConstants.POST_TEMPLATE, payload, String.class);
	}
	
}
