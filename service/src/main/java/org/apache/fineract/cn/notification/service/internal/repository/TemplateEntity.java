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
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "wada_templates")
public class TemplateEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "identifier")
	private String identifier;
	@Column(name = "event")
	private String event;
	@Column(name = "url")
	private String url;
	
	
	public TemplateEntity() {
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
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TemplateEntity that = (TemplateEntity) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(identifier, that.identifier) &&
				Objects.equals(event, that.event) &&
				Objects.equals(url, that.url);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, identifier, event, url);
	}
	
	@Override
	public String toString() {
		return "TemplateEntity{" +
				"id=" + id +
				", identifier='" + identifier + '\'' +
				", event='" + event + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
