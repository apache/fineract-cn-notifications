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

public class SMSPage {
	
	private List<SMSConfiguration> smsConfigurations;
	private Integer totalPages;
	private Long totalElements;
	
	public SMSPage() {
		super();
	}
	
	public List<SMSConfiguration> getSmsConfigurations() {
		return this.smsConfigurations;
	}
	
	public void setSmsConfigurations(final List<SMSConfiguration> smsConfigurations) {
		this.smsConfigurations = smsConfigurations;
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
		SMSPage smsPage = (SMSPage) o;
		return Objects.equals(smsConfigurations, smsPage.smsConfigurations) &&
				Objects.equals(totalPages, smsPage.totalPages) &&
				Objects.equals(totalElements, smsPage.totalElements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(smsConfigurations, totalPages, totalElements);
	}
	
	@Override
	public String toString() {
		return "SMSPage{" +
				"smsConfigurations=" + smsConfigurations +
				", totalPages=" + totalPages +
				", totalElements=" + totalElements +
				'}';
	}
}
