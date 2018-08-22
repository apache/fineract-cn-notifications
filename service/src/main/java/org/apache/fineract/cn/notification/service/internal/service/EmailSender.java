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


import org.apache.fineract.cn.notification.service.internal.mapper.EmailConfigurationMapper;
import org.apache.fineract.cn.notification.service.internal.repository.EmailGatewayConfiguration;
import org.apache.fineract.cn.notification.service.internal.repository.EmailGatewayConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailSender {

    @Autowired
    @Qualifier("gmail")
    private JavaMailSender sender;

    private final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository;

    @Autowired
    public EmailSender(final EmailGatewayConfigurationRepository emailGatewayConfigurationRepository) {
        super();
        this.emailGatewayConfigurationRepository = emailGatewayConfigurationRepository;
    }

//    public Optional<EmailGatewayConfiguration> findByIdentifier(final String identifier) {
//        return this.emailGatewayConfigurationRepository.findByIdentifier(identifier).map(EmailGatewayConfiguration::map);
//    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        sender.send(mail);
    }

}