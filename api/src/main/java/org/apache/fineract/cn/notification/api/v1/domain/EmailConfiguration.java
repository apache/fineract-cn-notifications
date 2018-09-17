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
	@Length(max = 11)
	private String identifier;
	@Length(max = 45)
	private String host;
	@Length(max = 45)
	private String port;
	@Length(max = 45)
	private String protocol;
	@Length(max = 45)
	private String username;
	@Length(max = 255)
	private String app_password;
	@Length(max = 45)
	private String smtp_auth;
	@Length(max = 45)
	private String start_tls;
	@Length(max = 45)
	private String state;
	
	public EmailConfiguration() {
		super();
	}
	
	public static EmailConfiguration create(String identifier,
	                                        String host, String port,
	                                        String protocol, String username,
	                                        String app_password, String smtp_auth,
	                                        String start_tls, String state) {
		EmailConfiguration emailConfiguration = new EmailConfiguration();
		emailConfiguration.setIdentifier(identifier);
		emailConfiguration.setHost(host);
		emailConfiguration.setPort(port);
		emailConfiguration.setProtocol(protocol);
		emailConfiguration.setUsername(username);
		emailConfiguration.setApp_password(app_password);
		emailConfiguration.setSmtp_auth(smtp_auth);
		emailConfiguration.setStart_tls(start_tls);
		emailConfiguration.setState(state);
		return emailConfiguration;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
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
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getApp_password() {
		return app_password;
	}
	
	public void setApp_password(String app_password) {
		this.app_password = app_password;
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
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmailConfiguration that = (EmailConfiguration) o;
		return Objects.equals(identifier, that.identifier) &&
				Objects.equals(host, that.host) &&
				Objects.equals(port, that.port) &&
				Objects.equals(protocol, that.protocol) &&
				Objects.equals(username, that.username) &&
				Objects.equals(app_password, that.app_password) &&
				Objects.equals(smtp_auth, that.smtp_auth) &&
				Objects.equals(start_tls, that.start_tls) &&
				Objects.equals(state, that.state);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(identifier, host, port, protocol, username, app_password, smtp_auth, start_tls, state);
	}
	
	@Override
	public String toString() {
		return "EmailConfiguration{" +
				"identifier='" + identifier + '\'' +
				", host='" + host + '\'' +
				", port='" + port + '\'' +
				", protocol='" + protocol + '\'' +
				", username='" + username + '\'' +
				", app_password='" + app_password + '\'' +
				", smtp_auth='" + smtp_auth + '\'' +
				", start_tls='" + start_tls + '\'' +
				", state='" + state + '\'' +
				'}';
	}
}
