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

import org.apache.fineract.cn.notification.api.v1.PermittableGroupIds;
import org.apache.fineract.cn.notification.api.v1.domain.Sample;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.command.InitializeServiceCommand;
import org.apache.fineract.cn.notification.service.internal.command.SampleCommand;
import org.apache.fineract.cn.notification.service.internal.service.SampleService;
import java.util.List;
import javax.validation.Valid;
import org.apache.fineract.cn.anubis.annotation.AcceptedTokenType;
import org.apache.fineract.cn.anubis.annotation.Permittable;
import org.apache.fineract.cn.command.gateway.CommandGateway;
import org.apache.fineract.cn.lang.ServiceException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/")
public class NotificationRestController {

  private final Logger logger;
  private final CommandGateway commandGateway;
  private final SampleService sampleService;

  @Autowired
  public NotificationRestController(@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger,
                              final CommandGateway commandGateway,
                              final SampleService sampleService) {
    super();
    this.logger = logger;
    this.commandGateway = commandGateway;
    this.sampleService = sampleService;
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

  @Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SAMPLE_MANAGEMENT)
  @RequestMapping(
          value = "/sample",
          method = RequestMethod.GET,
          consumes = MediaType.ALL_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public
  @ResponseBody
  List<Sample> findAllEntities() {
    return this.sampleService.findAllEntities();
  }

  @Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SAMPLE_MANAGEMENT)
  @RequestMapping(
          value = "/sample/{identifier}",
          method = RequestMethod.GET,
          consumes = MediaType.ALL_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public
  @ResponseBody
  ResponseEntity<Sample> getEntity(@PathVariable("identifier") final String identifier) {
    return this.sampleService.findByIdentifier(identifier)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> ServiceException.notFound("Instance with identifier " + identifier + " doesn't exist."));
  }

  @Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SAMPLE_MANAGEMENT)
  @RequestMapping(
      value = "/sample",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public
  @ResponseBody
  ResponseEntity<Void> createEntity(@RequestBody @Valid final Sample instance) throws InterruptedException {
    this.commandGateway.process(new SampleCommand(instance));
    return ResponseEntity.accepted().build();
  }
}
