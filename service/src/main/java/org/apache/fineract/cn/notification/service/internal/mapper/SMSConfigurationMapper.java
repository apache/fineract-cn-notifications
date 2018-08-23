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
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SMSConfigurationMapper {
	
	private SMSConfigurationMapper() {
		super();
	}
	
	public static SMSConfiguration map(final SMSGatewayConfigurationEntity smsGatewayConfigurationEntity) {
		final SMSConfiguration smsConfiguration = new SMSConfiguration();
		smsConfiguration.setIdentifier(smsGatewayConfigurationEntity.getIdentifier());
		smsConfiguration.setAccount_sid(smsGatewayConfigurationEntity.getAccount_sid());
		smsConfiguration.setAuth_token(smsGatewayConfigurationEntity.getAuth_token());
		smsConfiguration.setSender_number(smsGatewayConfigurationEntity.getSender_number());
		smsConfiguration.setState(smsGatewayConfigurationEntity.getState());
		return smsConfiguration;
	}
	
	public static SMSGatewayConfigurationEntity map(final SMSConfiguration smsConfiguration) {
		final SMSGatewayConfigurationEntity smsGatewayConfigurationEntity = new SMSGatewayConfigurationEntity();
		smsGatewayConfigurationEntity.setIdentifier(smsConfiguration.getIdentifier());
		smsGatewayConfigurationEntity.setAccount_sid(smsConfiguration.getAccount_sid());
		smsGatewayConfigurationEntity.setAuth_token(smsConfiguration.getAuth_token());
		smsGatewayConfigurationEntity.setSender_number(smsConfiguration.getSender_number());
		smsGatewayConfigurationEntity.setState(smsConfiguration.getState());
		return smsGatewayConfigurationEntity;
	}
	
	public static List<SMSConfiguration> map(final List<SMSGatewayConfigurationEntity> smsGatewayConfigurationEntity) {
		final ArrayList<SMSConfiguration> smsConfigurationList = new ArrayList<>(smsGatewayConfigurationEntity.size());
		smsConfigurationList.addAll(smsGatewayConfigurationEntity.stream().map(SMSConfigurationMapper::map).collect(Collectors.toList()));
		return smsConfigurationList;
	}
}
