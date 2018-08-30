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
package org.apache.fineract.cn.notification.service.rest;

import org.apache.fineract.cn.anubis.annotation.AcceptedTokenType;
import org.apache.fineract.cn.anubis.annotation.Permittable;
import org.apache.fineract.cn.command.gateway.CommandGateway;
import org.apache.fineract.cn.lang.ServiceException;
import org.apache.fineract.cn.notification.api.v1.PermittableGroupIds;
import org.apache.fineract.cn.notification.api.v1.client.ConfigurationNotFoundException;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.command.CreateEmailConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.command.CreateSMSConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.command.InitializeServiceCommand;
import org.apache.fineract.cn.notification.service.internal.command.PostSMSCommand;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/")
public class NotificationRestController {
	
	private final Logger logger;
	private final CommandGateway commandGateway;
	private final NotificationService notificationService;
	
	@Autowired
	public NotificationRestController(@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger,
	                                  final CommandGateway commandGateway,
	                                  final NotificationService notificationService) {
		super();
		this.logger = logger;
		this.commandGateway = commandGateway;
		this.notificationService = notificationService;
	}
	
	@Permittable(value = AcceptedTokenType.SYSTEM)
	@RequestMapping(
			value = "/initialize",
			method = RequestMethod.POST,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> initialize() throws InterruptedException {
		this.commandGateway.process(new InitializeServiceCommand());
		return ResponseEntity.accepted().build();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/notification/sms/active",
			method = RequestMethod.GET,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	List<SMSConfiguration> findAllActiveSMSConfigurationEntities() {
		return this.notificationService.findAllActiveSMSConfigurationEntities();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/notification/email/active",
			method = RequestMethod.GET,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	List<EmailConfiguration> findAllActiveEmailConfigurationEntities() {
		return this.notificationService.findAllActiveEmailConfigurationEntities();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/notification/sms/{identifier}",
			method = RequestMethod.GET,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<SMSConfiguration> findSMSConfigurationByIdentifier(@PathVariable("identifier") final String identifier)
	throws ConfigurationNotFoundException {
		return this.notificationService.findSMSConfigurationByIdentifier(identifier)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> ServiceException.notFound("SMS Gateway Configuration with identifier " + identifier + " doesn't exist."));
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/notification/email/{identifier}",
			method = RequestMethod.GET,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<EmailConfiguration> findEmailConfigurationByIdentifier(@PathVariable("identifier") final String identifier)
			throws ConfigurationNotFoundException {
		return this.notificationService.findEmailConfigurationByIdentifier(identifier)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> ServiceException.notFound("Email Gateway Configuration with identifier " + identifier + " doesn't exist."));
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/notification/sms/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> createSMSConfiguration(@RequestBody @Valid final SMSConfiguration smsConfiguration) throws InterruptedException {
		if (this.notificationService.smsConfigurationExists(smsConfiguration.getIdentifier())) {
			throw ServiceException.conflict("Configuration {0} already exists.", smsConfiguration.getIdentifier());
		}
		
		this.commandGateway.process(new CreateSMSConfigurationCommand(smsConfiguration));
		return ResponseEntity.accepted().build();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/notification/email/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> createEmailConfiguration(@RequestBody @Valid final EmailConfiguration emailConfiguration) throws InterruptedException {
		if (this.notificationService.emailConfigurationExists(emailConfiguration.getIdentifier())) {
			throw ServiceException.conflict("Configuration {0} already exists.", emailConfiguration.getIdentifier());
		}
		this.commandGateway.process(new CreateEmailConfigurationCommand(emailConfiguration));
		return ResponseEntity.accepted().build();
	}
}
