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
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.internal.command.CreateSMSConfigurationCommand;
import org.apache.fineract.cn.notification.service.internal.command.PostSMSCommand;
import org.apache.fineract.cn.notification.service.internal.mapper.SMSConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationEntity;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Aggregate
public class EventsAggregate {
	
	public EventsAggregate() {
		super();
	}
	
	@CommandHandler(logStart = CommandLogLevel.INFO, logFinish = CommandLogLevel.INFO)
	@Transactional
	@EventEmitter(selectorName = NotificationEventConstants.SELECTOR_NAME, selectorValue = NotificationEventConstants.POST_SMS_NOTIFICATION)
	public String postSMS(final PostSMSCommand postSMSCommand) {
		return postSMSCommand.getIdentifier();
	}
}
