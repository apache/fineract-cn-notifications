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
package org.apache.fineract.cn.notification.api.v1.domain;

import org.apache.fineract.cn.lang.validation.constraints.ValidIdentifier;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@SuppressWarnings({"WeakerAccess", "unused"})
public class EmailConfiguration {
  @ValidIdentifier
  private String identifier;
  @Length(max = 512)
  private String payload;
  @Length(max = 512)
  private String host;
  @Length(max = 512)
  private String port;
  @Length(max = 512)
  private String username;
  @Length(max = 512)
  private String app_id;
  @Length(max = 512)
  private String smtp_auth;
  @Length(max = 512)
  private String start_tls;
  @Length(max = 512)
  private String option;

  public EmailConfiguration(){
    super();
  }

  public static EmailConfiguration create (String identifier, String payload,
                            String host, String port,
                            String username, String app_id,
                            String smtp_auth, String start_tls,
                            String option) {
    EmailConfiguration emailConfiguration = new EmailConfiguration();
    emailConfiguration.setIdentifier(identifier);
    emailConfiguration.setPayload(payload);
    emailConfiguration.setHost(host);
    emailConfiguration.setPort(port);
    emailConfiguration.setUsername(username);
    emailConfiguration.setApp_id(app_id);
    emailConfiguration.setSmtp_auth(smtp_auth);
    emailConfiguration.setStart_tls(start_tls);
    emailConfiguration.setOption(option);
    return emailConfiguration;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getApp_id() {
    return app_id;
  }

  public void setApp_id(String app_id) {
    this.app_id = app_id;
  }

  public String getSmtp_auth() {
    return smtp_auth;
  }

  public void setSmtp_auth(String smtp_auth) {
    this.smtp_auth = smtp_auth;
  }

  public String getStart_tls() {
    return start_tls;
  }

  public void setStart_tls(String start_tls) {
    this.start_tls = start_tls;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmailConfiguration that = (EmailConfiguration) o;
    return Objects.equals(identifier, that.identifier) &&
            Objects.equals(payload, that.payload) &&
            Objects.equals(host, that.host) &&
            Objects.equals(port, that.port) &&
            Objects.equals(username, that.username) &&
            Objects.equals(app_id, that.app_id) &&
            Objects.equals(smtp_auth, that.smtp_auth) &&
            Objects.equals(start_tls, that.start_tls) &&
            Objects.equals(option, that.option);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, payload, host, port, username,
            app_id, smtp_auth, start_tls, option);
  }
}
