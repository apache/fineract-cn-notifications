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


import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class EmailService {
	
	private Logger logger;
	
	@Autowired
	public EmailService(@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		super();
		this.logger = logger;
	}
	
	public void sendEmail(String from, String to, String subject, String message) {
		this.logger.debug("sendSMS invoked");
		JavaMailSender sender = getJavaMailSender();
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(from);
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(message);
			sender.send(mail);
		} catch (MailException exception) {
			logger.debug("Caused by:" + exception.getCause().toString());
		}
	}
	
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		mailSender.setUsername("fineractcnnotificationdemo@gmail.com");
		mailSender.setPassword("pnuugpwmcibipdpw");
		
		Properties properties = mailSender.getJavaMailProperties();
		properties.put(ServiceConstants.MAIL_TRANSPORT_PROTOCOL_PROPERTY,
				ServiceConstants.MAIL_TRANSPORT_PROTOCOL_VALUE);
		properties.put(ServiceConstants.MAIL_SMTP_AUTH_PROPERTY,
				ServiceConstants.MAIL_SMTP_AUTH_VALUE);
		properties.put(ServiceConstants.MAIL_SMTP_STARTTLS_ENABLE_PROPERTY,
				ServiceConstants.MAIL_SMTP_STARTTLS_ENABLE_VALUE);
		mailSender.setJavaMailProperties(properties);
		
		return mailSender;
	}
}
