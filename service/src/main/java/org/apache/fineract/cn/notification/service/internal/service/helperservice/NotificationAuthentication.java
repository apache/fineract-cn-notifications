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
package org.apache.fineract.cn.notification.service.internal.service.helperservice;

/*
 ebenezergraham created on 8/4/18
*/

import org.apache.fineract.cn.api.util.UserContextHolder;
import org.apache.fineract.cn.identity.api.v1.client.IdentityManager;
import org.apache.fineract.cn.identity.api.v1.domain.Authentication;
import org.apache.fineract.cn.lang.TenantContextHolder;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class NotificationAuthentication {
	private final String USER_PASSWORD = "init1@l";
	private final String USER_IDENTIFIER = "operator";
	
	private IdentityManager identityManager;
	private Logger logger;
	
	@Autowired
	public NotificationAuthentication(IdentityManager identityManager,
	                                 @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		this.identityManager = identityManager;
		this.logger = logger;
		}
	
	public void authenticate(String tenant) {
		TenantContextHolder.clear();
		TenantContextHolder.setIdentifier(tenant);
		
		final Authentication authentication =
				this.identityManager.login(USER_IDENTIFIER, Base64Utils.encodeToString(USER_PASSWORD.getBytes()));
		UserContextHolder.clear();
		UserContextHolder.setAccessToken(USER_IDENTIFIER, authentication.getAccessToken());
	}
}