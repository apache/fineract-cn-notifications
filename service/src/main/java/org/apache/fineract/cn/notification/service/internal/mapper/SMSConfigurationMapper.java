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
package org.apache.fineract.cn.notification.service.internal.mapper;

import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SMSConfigurationMapper {

  private SMSConfigurationMapper() {
    super();
  }

  public static SMSConfiguration map(final SMSGatewayConfiguration smsGatewayConfiguration) {
    final SMSConfiguration smsConfiguration = new SMSConfiguration();
    smsConfiguration.setIdentifier(smsGatewayConfiguration.getIdentifier());
    smsConfiguration.setAccountid(smsGatewayConfiguration.getAccountid());
    smsConfiguration.setAuth_token(smsGatewayConfiguration.getAuth_token());
    return smsConfiguration;
  }

//  public static List<SMSGatewayConfiguration> map(final List<SMSGatewayConfiguration> smsGatewayConfiguration) {
//    final ArrayList<SMSGatewayConfiguration> entities = new ArrayList<>(smsGatewayConfiguration.size());
//    entities.addAll(smsGatewayConfiguration.stream().map(SMSConfigurationMapper::map).collect(Collectors.toList()));
//    return entities;
//  }
}
