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
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.Template;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.command.CreateSMSConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.command.CreateTemplateCommand;
import org.apache.fineract.cn.notification.service.internal.command.DeleteSMSConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.command.UpdateSMSConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.service.SMSService;
import org.apache.fineract.cn.notification.service.internal.service.TemplateService;
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
@RequestMapping("/template")
public class TemplateRestController {
	
	private final Logger logger;
	private final CommandGateway commandGateway;
	private final TemplateService templateService;
	
	@Autowired
	public TemplateRestController(@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger,
	                              final CommandGateway commandGateway,
	                              final TemplateService templateService) {
		super();
		this.logger = logger;
		this.commandGateway = commandGateway;
		this.templateService = templateService;
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
	ResponseEntity<Template> findTemplateByIdentifier(@PathVariable("identifier") final String identifier) {
		return this.templateService.findTemplateWithIdentifier(identifier)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> ServiceException.notFound("Template with identifier " + identifier + " doesn't exist."));
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> createTemplate(@RequestBody @Valid final Template template) throws InterruptedException {
		if (this.templateService.templateExists(template.getTemplateIdentifier())) {
			throw ServiceException.conflict("Template {0} already exists.", template.getTemplateIdentifier());
		}
		
		this.commandGateway.process(new CreateTemplateCommand(template));
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
	ResponseEntity<Void> updateTemplate(@RequestBody @Valid final Template template) {
		this.commandGateway.process(template);
		return ResponseEntity.accepted().build();
	}
	
	@Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SELF_MANAGEMENT)
	@RequestMapping(value = "/{identifier}",
			method = RequestMethod.DELETE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public
	@ResponseBody
	ResponseEntity<Void> deleteTemplate(@PathVariable @Valid final String identifier) {
		this.commandGateway.process(identifier);
		return ResponseEntity.ok().build();
	}
}
