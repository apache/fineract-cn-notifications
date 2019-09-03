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
package org.apache.fineract.cn.notification.service.internal.service;


import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.fineract.cn.command.annotation.Aggregate;
import org.apache.fineract.cn.command.annotation.CommandHandler;
import org.apache.fineract.cn.command.annotation.CommandLogLevel;
import org.apache.fineract.cn.command.annotation.EventEmitter;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.mapper.SMSConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Aggregate
public class SMSService {
	
	static boolean isConfigured;
	private final SMSGatewayConfigurationRepository smsGatewayConfigurationRepository;
	private final Logger logger;
	private String accountSid;
	private String authToken;
	private String senderNumber;
	
	@Autowired
	public SMSService(final SMSGatewayConfigurationRepository smsGatewayConfigurationRepository,
	                  @Qualifier(ServiceConstants.LOGGER_NAME) Logger logger) {
		super();
		this.logger = logger;
		this.isConfigured = false;
		this.smsGatewayConfigurationRepository = smsGatewayConfigurationRepository;
	}
	
	//@PostConstruct
	public void init() {
		if (getDefaultSMSConfiguration().isPresent()){
			configureServiceWithDefaultGateway();
		}else{
			//Todo: Send an alert on the interface to configure the service
		}
	}
	
	public boolean configureServiceWithDefaultGateway() {
		SMSConfiguration configuration = getDefaultSMSConfiguration().get();
		this.accountSid = configuration.getAccount_sid();
		this.authToken = configuration.getAuth_token();
		this.senderNumber = configuration.getSender_number();
		return this.isConfigured = true;
	}
	
	public boolean customConfiguration(String identifier) {
		SMSConfiguration configuration = findSMSConfigurationByIdentifier(identifier).get();
		this.accountSid = configuration.getAccount_sid();
		this.authToken = configuration.getAuth_token();
		this.senderNumber = configuration.getSender_number();
		return this.isConfigured = true;
	}
	
	public Optional<SMSConfiguration> getDefaultSMSConfiguration() {
		return this.smsGatewayConfigurationRepository.defaultGateway().map(SMSConfigurationMapper::map);
	}
	
	public Boolean smsConfigurationExists(final String identifier) {
		return this.smsGatewayConfigurationRepository.existsByIdentifier(identifier);
	}
	
	public Optional<SMSConfiguration> findSMSConfigurationByIdentifier(final String identifier) {
		return this.smsGatewayConfigurationRepository.findByIdentifier(identifier).map(SMSConfigurationMapper::map);
	}
	
	public List<SMSConfiguration> findAllSMSConfigurationEntities() {
		return SMSConfigurationMapper.map(this.smsGatewayConfigurationRepository.findAll());
	}
	
	@CommandHandler(logStart = CommandLogLevel.INFO, logFinish = CommandLogLevel.INFO)
	@Transactional
	@EventEmitter(selectorName = NotificationEventConstants.SELECTOR_NAME, selectorValue = NotificationEventConstants.SEND_SMS_NOTIFICATION)
	public int sendSMS(String receiver, String template) {
		Twilio.init(this.accountSid, this.authToken);
		MessageCreator messageCreator = Message.creator(this.accountSid,
				new PhoneNumber(receiver),
				new PhoneNumber(this.senderNumber),
				template);
		Message message = null;
		try{
			message = messageCreator.create();

		}catch (ApiException apiException){
			logger.error("Error: {}" ,apiException.getMoreInfo());
		}

		return message.hashCode();
	}
}
