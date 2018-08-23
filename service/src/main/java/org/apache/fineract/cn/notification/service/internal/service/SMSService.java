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
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
	
	private final Logger logger;
	@Value("${smssender.accountSID}")
	private String ACCOUNT_SID;
	@Value("${smssender.authToken}")
	private String AUTH_TOKEN;
	@Value("${smssender.senderNumber}")
	private String SENDERNUMBER;
	
	@Autowired
	public SMSService(@Qualifier(ServiceConstants.LOGGER_NAME) Logger logger) {
		super();
		this.logger = logger;
	}
	
	public void configure(String accountSID,
	                      String authToken,
	                      String senderNumber) {
		ACCOUNT_SID = accountSID;
		AUTH_TOKEN = authToken;
		SENDERNUMBER = senderNumber;
	}
	
	public void sendSMS(String receiver, String template) {
		this.logger.debug("sendSMS invoked");
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		MessageCreator messageCreator = Message.creator(ACCOUNT_SID,
				new PhoneNumber(receiver),
				new PhoneNumber(SENDERNUMBER),
				template);
		Message message = messageCreator.create();
		System.out.println(message.getSid());
	}
}