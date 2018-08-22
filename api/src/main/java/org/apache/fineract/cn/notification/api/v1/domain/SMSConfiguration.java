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
public class SMSConfiguration {
  @ValidIdentifier
  private String identifier;
  @Length(max = 512)
  private String payload;
  @Length(max = 512)
  private String organisation;
  @Length(max = 512)
  private String auth_token;
  @Length(max = 512)
  private String accountid;
  @Length(max = 256)
  private String option;

  public SMSConfiguration() {
    super();
  }

  public static SMSConfiguration create(String identifier, String payload, String organisation, String auth_token, String accountid, String option) {
    SMSConfiguration smsconfiguration = new SMSConfiguration();
    smsconfiguration.setIdentifier(identifier);
    smsconfiguration.setPayload(payload);
    smsconfiguration.setOrganisation(organisation);
    smsconfiguration.setAuth_token(auth_token);
    smsconfiguration.setAccountid(accountid);
    smsconfiguration.setOption(option);
    return smsconfiguration;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SMSConfiguration that = (SMSConfiguration) o;
    return Objects.equals(identifier, that.identifier) &&
            Objects.equals(payload, that.payload) &&
            Objects.equals(organisation, that.organisation) &&
            Objects.equals(auth_token, that.auth_token) &&
            Objects.equals(accountid, that.accountid) &&
            Objects.equals(option, that.option);
  }

  @Override
  public int hashCode() {

    return Objects.hash(identifier, payload, organisation, auth_token, accountid, option);
  }
}
