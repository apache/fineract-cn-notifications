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
  private String organisation;
  @Length(max = 512)
  private String auth_token;
  @Length(max = 512)
  private String accountSid;
  @Length(max = 256)
  private String sender_number;
  @Length(max = 256)
  private String state;
  @Length(max = 256)
  private String type;

  private enum State {
    ACTIVE,
    DEACTIVATED;
    State() {
    }

  }

  private enum Type {
    EMAILSERVER,
    SMSGATEWAY;

    Type() {
    }
  }

  public SMSConfiguration() {
    super();

  }

  public static SMSConfiguration create(String identifier,
                                        String payload,
                                        String organisation,
                                        String auth_token,
                                        String accountSID,
                                        String state,
                                        String type) {
    SMSConfiguration smsconfiguration = new SMSConfiguration();
    smsconfiguration.setIdentifier(identifier);
    smsconfiguration.setOrganisation(organisation);
    smsconfiguration.setAuth_token(auth_token);
    smsconfiguration.setAccountSid(accountSID);
    smsconfiguration.setState(state);
    smsconfiguration.setType(type);
    return smsconfiguration;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
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

  public String getAccountSid() {
    return accountSid;
  }

  public void setAccountSid(String accountid) {
    this.accountSid = accountid;
  }

  public String getSender_number() {
    return sender_number;
  }

  public void setSender_number(String sender_number) {
    this.sender_number = sender_number;
  }

  public String getSate() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getType() {
    return null;
  }

  public void setType(String type) {

  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SMSConfiguration that = (SMSConfiguration) o;
    return Objects.equals(identifier, that.identifier) &&
            Objects.equals(organisation, that.organisation) &&
            Objects.equals(auth_token, that.auth_token) &&
            Objects.equals(accountSid, that.accountSid) &&
            Objects.equals(state, that.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, auth_token, organisation, auth_token, accountSid, state);
  }
}
