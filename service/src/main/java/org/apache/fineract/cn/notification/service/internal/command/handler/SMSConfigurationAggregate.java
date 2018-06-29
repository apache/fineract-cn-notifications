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
package org.apache.fineract.cn.notification.service.internal.command.handler;

import org.apache.fineract.cn.command.annotation.Aggregate;
import org.apache.fineract.cn.command.annotation.CommandHandler;
import org.apache.fineract.cn.command.annotation.CommandLogLevel;
import org.apache.fineract.cn.command.annotation.EventEmitter;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.command.SMSConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationEntity;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Aggregate
public class SMSConfigurationAggregate {

  private final SMSGatewayConfigurationEntityRepository smsGatewayConfigurationEntityRepository;

  @Autowired
  public SMSConfigurationAggregate(SMSGatewayConfigurationEntityRepository smsGatewayConfigurationEntityRepository) {
    super();
    this.smsGatewayConfigurationEntityRepository = smsGatewayConfigurationEntityRepository;
  }

  @CommandHandler(logStart = CommandLogLevel.INFO, logFinish = CommandLogLevel.INFO)
  @Transactional
  @EventEmitter(selectorName = NotificationEventConstants.SELECTOR_NAME, selectorValue = NotificationEventConstants.POST_SAMPLE)
  public String saveSMSServiceConfiguration(final SMSConfigurationCommand smsConfigurationCommand) {

    final SMSGatewayConfigurationEntity entity = new SMSGatewayConfigurationEntity();
    entity.setAccountSid(smsConfigurationCommand.getSMSConfiguration().getAccountSid());
    entity.setAuth_token(smsConfigurationCommand.getSMSConfiguration().getAuth_token());
    entity.setSender_number(smsConfigurationCommand.getSMSConfiguration().getSender_number());
    this.smsGatewayConfigurationEntityRepository.save(entity);

    return smsConfigurationCommand.getSMSConfiguration().getIdentifier();
  }
}
