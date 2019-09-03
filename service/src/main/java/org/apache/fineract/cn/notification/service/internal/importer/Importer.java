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
package org.apache.fineract.cn.notification.service.internal.importer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.fineract.cn.notification.api.v1.client.NotificationManager;
import org.apache.fineract.cn.notification.api.v1.client.TemplateAlreadyExistException;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.Template;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Ebenezer Graham
 */
@SuppressWarnings("unused")
public class Importer {
  private static final String TEMPLATE_IDENTIFIER_COLUMN = "template_identifier";
  private static final String SENDER_EMAIL_COLUMN = "sender_email";
  private static final String SUBJECT_COLUMN = "subject";
  private static final String MESSAGE_COLUMN = "message";
  private static final String URL_COLUMN = "url";

  private static final String IDENTIFIER_COLUMN = "identifier";
  private static final String ACCOUNT_SID_COLUMN = "account_sid";
  private static final String AUTH_TOKEN_COLUMN = "auth_token";
  private static final String SENDER_NUMBER_COLUMN = "sender_number";
  private static final String STATE_COLUMN = "state";

  private static final String HOST_EMAIL_COLUMN = "host";
  private static final String PORT_COLUMN = "port";
  private static final String USERNAME_COLUMN = "username";
  private static final String APP_PASSWORD_COLUMN = "app_password";
  private static final String PROTOCOL_COLUMN = "protocol";
  private static final String SMTP_AUTH_COLUMN = "smtp_auth";
  private static final String START_TLS_COLUMN = "start_tls";

  private final NotificationManager notificationManager;
  private final Logger logger;

  public Importer(final NotificationManager notificationManager, final Logger logger) {
    this.notificationManager = notificationManager;
    this.logger = logger;
  }

  public void importTemplateCSV(final URL toImport) throws IOException {
    final CSVParser parser = CSVParser.parse(toImport, StandardCharsets.UTF_8, CSVFormat.RFC4180.withHeader().withCommentMarker('-'));
    final List<Template>templatesList = StreamSupport.stream(parser.spliterator(), false)
            .map(this::toTemplate)
            .collect(Collectors.toList());
    templatesList.forEach(this::createTemplate);
  }

  public void importSmsConfigurationCSV(final URL toImport) throws IOException {
    final CSVParser parser = CSVParser.parse(toImport, StandardCharsets.UTF_8, CSVFormat.RFC4180.withHeader().withCommentMarker('-'));
    final List<SMSConfiguration>list = StreamSupport.stream(parser.spliterator(), false)
        .map(this::toSmsConfiguration)
        .collect(Collectors.toList());
    list.forEach(this::createSmsConfiguration);
  }

  public void importEmailConfigurationCSV(final URL toImport) throws IOException {
    final CSVParser parser = CSVParser.parse(toImport, StandardCharsets.UTF_8, CSVFormat.RFC4180.withHeader().withCommentMarker('-'));
    final List<EmailConfiguration>list = StreamSupport.stream(parser.spliterator(), false)
        .map(this::toEmailConfiguration)
        .collect(Collectors.toList());
    list.forEach(this::createEmailConfiguration);
  }

  private void createTemplate(final Template toCreate) {
    try {
      notificationManager.createTemplate(toCreate);
    }
    catch (final TemplateAlreadyExistException ignored) {
      logger.error("Creation of template {} failed, because a template with the same identifier but different properties already exists {}", toCreate.getTemplateIdentifier(),toCreate.toString());
    }
  }

  private void createSmsConfiguration(final SMSConfiguration toCreate) {
    try {
      notificationManager.createSMSConfiguration(toCreate);
    }
    catch (final Exception ignored) {
      logger.error("Creation of sms configuration {} failed, because a template with the same identifier but different properties already exists {}", toCreate.getIdentifier(),toCreate.toString());
    }
  }

  private void createEmailConfiguration(final EmailConfiguration toCreate) {
    try {
      notificationManager.createEmailConfiguration(toCreate);
    }
    catch (final Exception ignored) {
      logger.error("Creation of email configuration {} failed, because a template with the same identifier but different properties already exists {}", toCreate.getIdentifier(),toCreate.toString());
    }
  }

  private Template toTemplate(final CSVRecord csvRecord) {
    try {
      final String templateIdentifier = csvRecord.get(TEMPLATE_IDENTIFIER_COLUMN);
      final String subject = csvRecord.get(SUBJECT_COLUMN);
      final String senderEmail = csvRecord.get(SENDER_EMAIL_COLUMN);
      final String url = csvRecord.get(URL_COLUMN);
      String message;
      try {
        message = csvRecord.get(MESSAGE_COLUMN);
      }
      catch (final NullPointerException e) {
        message = "Do not reply, This is a computer generate message.";
      }
      return new Template(templateIdentifier,senderEmail,subject,message,url);
    }
    catch (final IllegalArgumentException e) {
      logger.warn("Parsing failed on record {}", csvRecord.getRecordNumber());
      throw e;
    }
  }

  private EmailConfiguration toEmailConfiguration(final CSVRecord csvRecord) {
    try {
      final String identifier = csvRecord.get(IDENTIFIER_COLUMN);
      final String host = csvRecord.get(HOST_EMAIL_COLUMN);
      final String username = csvRecord.get(USERNAME_COLUMN);
      final String port = csvRecord.get(PORT_COLUMN);
      final String app_password = csvRecord.get(APP_PASSWORD_COLUMN);
      final String protocol = csvRecord.get(PROTOCOL_COLUMN);
      final String smtp_auth = csvRecord.get(SMTP_AUTH_COLUMN);
      final String start_tls = csvRecord.get(START_TLS_COLUMN);
      final String state = csvRecord.get(STATE_COLUMN);

      return EmailConfiguration.create(identifier,host,port,protocol,username,app_password,smtp_auth,start_tls,state);
    }
    catch (final IllegalArgumentException e) {
      logger.warn("Parsing failed on record {}", csvRecord.getRecordNumber());
      throw e;
    }
  }

  private SMSConfiguration toSmsConfiguration(final CSVRecord csvRecord) {
    try {
      final String identifier = csvRecord.get(IDENTIFIER_COLUMN);
      final String account_sid = csvRecord.get(ACCOUNT_SID_COLUMN);
      final String auth_token = csvRecord.get(AUTH_TOKEN_COLUMN);
      final String sender_number = csvRecord.get(SENDER_NUMBER_COLUMN);
      final String state = csvRecord.get(STATE_COLUMN);
      return SMSConfiguration.create(identifier,account_sid,auth_token,sender_number,state);
    }
    catch (final IllegalArgumentException e) {
      logger.warn("Parsing failed on record {}", csvRecord.getRecordNumber());
      throw e;
    }
  }
}
