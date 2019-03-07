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
package org.apache.fineract.cn.notification.service.internal.identity;

import org.apache.fineract.cn.api.context.AutoUserContext;
import org.apache.fineract.cn.api.util.InvalidTokenException;
import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.lang.AutoTenantContext;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.config.NotificationProperties;
import org.apache.fineract.cn.permittedfeignclient.service.ApplicationAccessTokenService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationAuthentication {
	private final ApplicationAccessTokenService applicationAccessTokenService;
	private Logger logger;
	private CustomerPermittedClient customerPermittedClient;
	private NotificationProperties notificationProperties;
	
	@Autowired
	public NotificationAuthentication(final NotificationProperties notificationPropertities,
	                                  final CustomerPermittedClient customerPermittedClient,
	                                  @SuppressWarnings("SpringJavaAutowiringInspection")final ApplicationAccessTokenService applicationAccessTokenService,
	                                  @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		this.logger = logger;
		this.customerPermittedClient = customerPermittedClient;
		this.notificationProperties = notificationPropertities;
		this.applicationAccessTokenService = applicationAccessTokenService;
	}
	
	public Optional<Customer> getCustomer(String tenantIdentifier, String customerId) {
			try (final AutoTenantContext ignored = new AutoTenantContext(tenantIdentifier)) {
				final String accessToken = applicationAccessTokenService.getAccessToken(notificationProperties.getUser(), tenantIdentifier);
				logger.debug("Access token: {}", accessToken);
				try (final AutoUserContext ignored2 = new AutoUserContext(notificationProperties.getUser(), accessToken)) {
					try {
						logger.debug("Getting Customer {}", customerId);
						return Optional.of(this.customerPermittedClient.findCustomer(customerId));
					} catch (final InvalidTokenException e) {
						logger.error("Failed to get customer with id {}, in tenant {} because notification does not have permission to access identity.", customerId, tenantIdentifier, e);
					}
				}
				return Optional.empty();
			}
	}
}
