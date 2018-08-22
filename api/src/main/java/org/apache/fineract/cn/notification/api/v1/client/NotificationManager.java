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
import org.apache.fineract.cn.api.util.CustomFeignClientsConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@SuppressWarnings("unused")
@FeignClient(value="notification-v1", path="/notification/v1", configuration = CustomFeignClientsConfiguration.class)
public interface NotificationManager {

  @RequestMapping(
          value = "/notification",
          method = RequestMethod.GET,
          produces = MediaType.ALL_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE
  )
  List<SMSConfiguration> findAllEntities();
  
  @RequestMapping(
      value = "/notification/",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsException(status = HttpStatus.I_AM_A_TEAPOT, exception = IamATeapotException.class)
  void createEntity(final SMSConfiguration smsConfiguration);

  @RequestMapping(
          value = "/notification/sms/{identifier}",
          method = RequestMethod.GET,
          produces = MediaType.ALL_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  SMSConfiguration findAllActiveSMSConfigurationEntities(@PathVariable("identifier") final String identifier);
  
  @RequestMapping(
      value = "/notification/email/active/{identifier}",
      method = RequestMethod.GET,
      produces = MediaType.ALL_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  EmailConfiguration findAllActiveEmailConfigurationEntities(@PathVariable("identifier") final String identifier);
  
  @RequestMapping(
      value = "/notification/sms/create",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsException(status = HttpStatus.I_AM_A_TEAPOT, exception = IamATeapotException.class)
  void createSMSConfiguration(final SMSConfiguration smsConfiguration);
  
  @RequestMapping(
      value = "/notification/sms/{identifier}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsException(status = HttpStatus.I_AM_A_TEAPOT, exception = IamATeapotException.class)
  SMSConfiguration findSMSConfigurationByIdentifier(@PathVariable("identifier") final String identifier);
  
  @RequestMapping(
      value = "/notification/email/{identifier}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsException(status = HttpStatus.I_AM_A_TEAPOT, exception = IamATeapotException.class)
  EmailConfiguration findEmailConfigurationByIdentifier(@PathVariable("identifier") final String identifier);
  
  @RequestMapping(
      value = "/notification/email/create",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsException(status = HttpStatus.I_AM_A_TEAPOT, exception = IamATeapotException.class)
  void createEmailConfiguration(final EmailConfiguration emailConfiguration);
}
