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
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.command.*;
import org.apache.fineract.cn.notification.service.internal.service.EmailService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/configuration/email")
public class EmailServiceRestController {
	
	private final Logger logger;
	private final CommandGateway commandGateway;
	private EmailService emailService;
	
	@Autowired
	public EmailServiceRestController(@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger,
	                                  final CommandGateway commandGateway,
	                                  final EmailService emailService) {
		super();
		this.logger = logger;
		this.commandGateway = commandGateway;
		this.emailService = emailService;
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			method = RequestMethod.GET,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	List<EmailConfiguration> findAllEmailConfigurationEntities() {
		return this.emailService.findAllEmailConfigurationEntities();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/{identifier}",
			method = RequestMethod.GET,
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<EmailConfiguration> findEmailConfigurationByIdentifier(@PathVariable("identifier") final String identifier) {
		return this.emailService.findEmailConfigurationByIdentifier(identifier)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> ServiceException.notFound("Email Gateway Configuration with identifier " + identifier + " doesn't exist."));
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> createEmailConfiguration(@RequestBody @Valid final EmailConfiguration emailConfiguration) throws InterruptedException {
		if (this.emailService.emailConfigurationExists(emailConfiguration.getIdentifier())) {
			throw ServiceException.conflict("Configuration {0} already exists.", emailConfiguration.getIdentifier());
		}
		
		this.commandGateway.process(new CreateEmailConfigurationCommand(emailConfiguration));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> updateEmailConfiguration(@RequestBody @Valid final EmailConfiguration emailConfiguration) {
		this.commandGateway.process(new UpdateEmailConfigurationCommand(emailConfiguration));
		return ResponseEntity.accepted().build();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			value = "/{identifier}",
			method = RequestMethod.DELETE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> deleteEmailConfiguration(@PathVariable @Valid final String identifier) {
		this.commandGateway.process(new DeleteEmailConfigurationCommand(identifier));
		return ResponseEntity.ok().build();
	}
}
