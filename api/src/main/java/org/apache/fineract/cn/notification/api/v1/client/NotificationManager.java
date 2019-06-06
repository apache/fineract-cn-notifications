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
package org.apache.fineract.cn.notification.api.v1.client;

import org.apache.fineract.cn.api.annotation.ThrowsException;
import org.apache.fineract.cn.api.annotation.ThrowsExceptions;
import org.apache.fineract.cn.api.util.CustomFeignClientsConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.Template;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SuppressWarnings("unused")
@FeignClient(value = "notification-v1", path = "/notification/v1", configuration = CustomFeignClientsConfiguration.class)
public interface NotificationManager {
	
	@RequestMapping(
			value = "/configuration/sms/active",
			method = RequestMethod.GET,
			produces = MediaType.ALL_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	SMSConfiguration findAllActiveSMSConfigurationEntities();
	
	@RequestMapping(
			value = "/configuration/email/active",
			method = RequestMethod.GET,
			produces = MediaType.ALL_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	EmailConfiguration findAllActiveEmailConfigurationEntities();
	
	@RequestMapping(
			value = "/configuration/sms/create",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ThrowsExceptions({
			@ThrowsException(status = HttpStatus.NOT_FOUND, exception = ConfigurationNotFoundException.class)
	})
	String createSMSConfiguration(final SMSConfiguration smsConfiguration);
	
	@RequestMapping(
			value = "/configuration/email/create",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	String createEmailConfiguration(final EmailConfiguration emailConfiguration);
	
	@RequestMapping(
			value = "/template/create",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ThrowsExceptions({
			@ThrowsException(status = HttpStatus.UNPROCESSABLE_ENTITY, exception = TemplateAlreadyExistException.class)
	})
	String createTemplate(final Template template);
	
	@RequestMapping(
			value = "/configuration/sms/{identifier}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	SMSConfiguration findSMSConfigurationByIdentifier(@PathVariable("identifier") final String identifier);
	
	@RequestMapping(
			value = "/configuration/email/{identifier}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	EmailConfiguration findEmailConfigurationByIdentifier(@PathVariable("identifier") final String identifier);
	
	@RequestMapping(value = "/configuration/sms/update",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	void updateSMSConfiguration(final SMSConfiguration smsConfiguration);
	
	@RequestMapping(value = "/configuration/email/update",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	void updateEmailConfiguration(final EmailConfiguration emailConfiguration);
	
	@RequestMapping(value = "/configuration/sms/delete/{identifier}",
			method = RequestMethod.DELETE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	void deleteSMSConfiguration(@PathVariable("identifier") final String identifier);
	
	@RequestMapping(value = "/configuration/email/delete/{identifier}",
			method = RequestMethod.DELETE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	void deleteEmailConfiguration(@PathVariable("identifier") final String identifier);
	
}
