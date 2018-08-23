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

import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.service.internal.repository.EmailGatewayConfigurationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmailConfigurationMapper {
	
	private EmailConfigurationMapper() {
		super();
	}
	
	public static EmailConfiguration map(final EmailGatewayConfigurationEntity emailGatewayConfigurationEntity) {
		final EmailConfiguration emailConfiguration = new EmailConfiguration();
		emailConfiguration.setIdentifier(emailGatewayConfigurationEntity.getIdentifier());
		emailConfiguration.setHost(emailGatewayConfigurationEntity.getHost());
		emailConfiguration.setPort(emailGatewayConfigurationEntity.getPort());
		emailConfiguration.setUsername(emailGatewayConfigurationEntity.getUsername());
		emailConfiguration.setApp_password(emailGatewayConfigurationEntity.getApp_password());
		return emailConfiguration;
	}
	
	public static EmailGatewayConfigurationEntity map(final EmailConfiguration emailConfiguration) {
		final EmailGatewayConfigurationEntity emailGatewayConfigurationEntity = new EmailGatewayConfigurationEntity();
		emailGatewayConfigurationEntity.setIdentifier(emailConfiguration.getIdentifier());
		emailGatewayConfigurationEntity.setHost(emailConfiguration.getHost());
		emailGatewayConfigurationEntity.setPort(emailConfiguration.getPort());
		emailGatewayConfigurationEntity.setProtocol(emailConfiguration.getProtocol());
		emailGatewayConfigurationEntity.setApp_password(emailConfiguration.getApp_password());
		emailGatewayConfigurationEntity.setUsername(emailConfiguration.getUsername());
		emailGatewayConfigurationEntity.setSmtp_auth(emailConfiguration.getSmtp_auth());
		emailGatewayConfigurationEntity.setStart_tls(emailConfiguration.getStart_tls());
		emailGatewayConfigurationEntity.setState(emailConfiguration.getState());
		return emailGatewayConfigurationEntity;
	}
	
	public static List<EmailConfiguration> map(final List<EmailGatewayConfigurationEntity> emailGatewayConfigurationEntity) {
		final ArrayList<EmailConfiguration> emailConfigurationList = new ArrayList<>(emailGatewayConfigurationEntity.size());
		emailConfigurationList.addAll(emailGatewayConfigurationEntity.stream().map(EmailConfigurationMapper::map).collect(Collectors.toList()));
		return emailConfigurationList;
	}
	
}

