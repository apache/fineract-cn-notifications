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
import com.twilio.type.PhoneNumber;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.service.internal.mapper.SMSConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.SMSGatewayConfigurationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SMSService {

    private final SMSGatewayConfigurationEntityRepository smsGatewayConfigurationEntityRepository;
    private final SMSConfiguration smsConfiguration;

    //@Value("${smssender.accountSID}")
    public final String ACCOUNT_SID;

    //@Value("${smssender.authToken}")
    public final String AUTH_TOKEN;

    //@Value("${smssender.senderNumber}")
    public final String SENDERNUMBER;

    @Autowired
    public SMSService(SMSGatewayConfigurationEntityRepository smsGatewayConfigurationEntityRepository, String account){
        super();
        this.smsGatewayConfigurationEntityRepository = smsGatewayConfigurationEntityRepository;
        this.smsConfiguration = SMSConfigurationMapper.map(smsGatewayConfigurationEntityRepository.findByIdentifier(account).get());
        this.ACCOUNT_SID = this.smsConfiguration.getAccountSid();
        this.AUTH_TOKEN = this.smsConfiguration.getAuth_token();
        this.SENDERNUMBER = this.smsConfiguration.getSender_number();
    }

    public Optional<SMSConfiguration> findSMSConfiguration (final String identifier) {
        return smsGatewayConfigurationEntityRepository.findByIdentifier(identifier).map(SMSGatewayConfiguration -> {
            final SMSConfiguration configuration = SMSConfigurationMapper.map(SMSGatewayConfiguration);
        return configuration;
        });
    }

    public void sendSMS(String receiver, String template) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        MessageCreator messageCreator = Message.creator(ACCOUNT_SID,new PhoneNumber(receiver), new PhoneNumber(SENDERNUMBER), template);
        Message message = messageCreator.create();
        System.out.println(message.getSid());
    }
}