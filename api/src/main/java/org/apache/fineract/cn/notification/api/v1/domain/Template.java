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
/*
ebenezergraham created on 5/22/19
*/

import java.util.Objects;

public class Template {
	private String templateIdentifier;
	private String subject;
	private String senderEmail;
	private String message;
	private String url;
	
	public Template(String templateIdentifier, String senderEmail, String subject, String message, String url) {
		this.templateIdentifier = templateIdentifier;
		this.senderEmail = senderEmail;
		this.subject = subject;
		this.message = message;
		this.url = url;
	}
	
	public Template() {
	}
	
	public String getTemplateIdentifier() {
		return templateIdentifier;
	}
	
	public void setTemplateIdentifier(String templateIdentifier) {
		this.templateIdentifier = templateIdentifier;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSenderEmail() {
		return senderEmail;
	}
	
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Template{" +
				"templateIdentifier='" + templateIdentifier + '\'' +
				", subject='" + subject + '\'' +
				", senderEmail='" + senderEmail + '\'' +
				", message='" + message + '\'' +
				", url='" + url + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Template template = (Template) o;
		return Objects.equals(templateIdentifier, template.templateIdentifier) &&
				Objects.equals(subject, template.subject) &&
				Objects.equals(message, template.message) &&
				Objects.equals(url, template.url);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(templateIdentifier, subject, message, senderEmail,url);
	}
}
