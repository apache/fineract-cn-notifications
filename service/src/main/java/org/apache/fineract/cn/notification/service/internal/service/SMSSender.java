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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix ="smssender")
public class SMSSender {

    //@Value(value = "${smssender.accountSID}")
    public String ACCOUNT_SID = "AC1fde2c6f26f367b93231c5fdb944c908";

    //@Value("${smssender.authToken}")
    public String AUTH_TOKEN = "bc9a53e41745b8471e0ecafc859d86aa";

    //@Value("${smssender.senderNumber}")
    public String sender = "+1 510-944-1898";

    //@Value("${fineract.customer.accountcreated}")

    public String template = "Test from the demo-server";

    public void sendSMS(String receiver, String template) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        MessageCreator messageCreator = Message.creator(ACCOUNT_SID,new PhoneNumber(receiver), new PhoneNumber(sender), template);
        Message message = messageCreator.create();
        System.out.println(message.getSid());
        System.out.println(message.getStatus());
    }
}