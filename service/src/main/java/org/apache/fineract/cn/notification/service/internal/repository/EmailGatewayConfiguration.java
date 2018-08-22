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
package org.apache.fineract.cn.notification.service.internal.repository;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "email_gateway_configuration")
public class EmailGatewayConfiguration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "identifier")
  private String identifier;
  @Column(name = "host")
  private String host;
  @Column(name = "port")
  private String port;
  @Column(name = "username")
  private String username;
  @Column(name = "app_id")
  private String app_id;
  @Column(name = "smtp_auth")
  private String smtp_auth;
  @Column(name = "start_tls")
  private String start_tls;
  @Column(name = "option")
  private String option;

  public EmailGatewayConfiguration() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
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
}
