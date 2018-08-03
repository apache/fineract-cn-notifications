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

import com.netflix.ribbon.proxy.annotation.Http;
import org.apache.fineract.cn.anubis.annotation.AcceptedTokenType;
import org.apache.fineract.cn.anubis.annotation.Permittable;
import org.apache.fineract.cn.command.gateway.CommandGateway;
import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.lang.ServiceException;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.notification.api.v1.PermittableGroupIds;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.command.InitializeServiceCommand;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationEntity;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/")
public class HelperRestController {

  private final Logger logger;
  private final CommandGateway commandGateway;
  private final NotificationService notificationService;

  @Autowired
  public HelperRestController(@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger,
                              final CommandGateway commandGateway,
                              final NotificationService notificationService) {
    super();
    this.logger = logger;
    this.commandGateway = commandGateway;
    this.notificationService = notificationService;
  }

  @Permittable(value = AcceptedTokenType.TENANT, groupId = PermittableGroupIds.SAMPLE_MANAGEMENT)
  @RequestMapping(
		  value = "/customers/{identifier}",
          method = RequestMethod.GET,
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public ResponseEntity<Customer> findCustomer(@PathVariable(value = "identifier") final String customerIdentifier,
                                                      @RequestBody @Valid final Customer customer) {
    this.notificationService.findCustomer(customerIdentifier)
            .orElseThrow(() -> ServiceException
                    .notFound("Customer {0} not available.", customerIdentifier)
            );
    return ResponseEntity.accepted().body(customer);
  }
}
