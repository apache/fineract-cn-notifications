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

import java.util.List;
import java.util.Objects;

public class EmailPage {
	
	private List<EmailConfiguration> emailConfigurations;
	private Integer totalPages;
	private Long totalElements;
	
	public EmailPage() {
		super();
	}
	
	public List<EmailConfiguration> getEmailConfiguration() {
		return this.emailConfigurations;
	}
	
	public void setEmailConfiguration(final List<EmailConfiguration> emailConfiguration) {
		this.emailConfigurations = emailConfigurations;
	}
	
	public Integer getTotalPages() {
		return this.totalPages;
	}
	
	public void setTotalPages(final Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	public Long getTotalElements() {
		return this.totalElements;
	}
	
	public void setTotalElements(final Long totalElements) {
		this.totalElements = totalElements;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmailPage emailPage = (EmailPage) o;
		return Objects.equals(emailConfigurations, emailPage.emailConfigurations) &&
				Objects.equals(totalPages, emailPage.totalPages) &&
				Objects.equals(totalElements, emailPage.totalElements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(emailConfigurations, totalPages, totalElements);
	}
	
	@Override
	public String toString() {
		return "EmailPage{" +
				"emailConfigurations=" + emailConfigurations +
				", totalPages=" + totalPages +
				", totalElements=" + totalElements +
				'}';
	}
}
