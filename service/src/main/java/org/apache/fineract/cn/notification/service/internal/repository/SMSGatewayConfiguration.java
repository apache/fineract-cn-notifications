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
@Table(name = "sms_gateway_configuration")
public class SMSGatewayConfiguration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "identifier")
  private String identifier;
  @Column(name = "organisation")
  private String organisation;
  @Column(name = "auth_token")
  private String auth_token;
  @Column(name = "accountid")
  private String accountid;
  @Column(name = "option")
  private String option;

  public SMSGatewayConfiguration() {
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

  public String getOrganisation() {
    return organisation;
  }

  public void setOrganisation(String organisation) {
    this.organisation = organisation;
  }

  public String getAuth_token() {
    return auth_token;
  }

  public void setAuth_token(String auth_token) {
    this.auth_token = auth_token;
  }

  public String getAccountid() {
    return accountid;
  }

  public void setAccountid(String accountid) {
    this.accountid = accountid;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }
}
