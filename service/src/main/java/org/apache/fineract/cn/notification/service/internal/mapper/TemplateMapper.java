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

package org.apache.fineract.cn.notification.service.internal.mapper;

import org.apache.fineract.cn.notification.api.v1.domain.Template;
import org.apache.fineract.cn.notification.service.internal.repository.TemplateEntity;

public class TemplateMapper {
	
	private TemplateMapper() {
		super();
	}
	
	public static Template map(final TemplateEntity templateEntity) {
		final Template template = new Template();
		template.setTemplateIdentifier(templateEntity.getTemplateIdentifier());
		template.setSenderEmail(templateEntity.getSenderEmail());
		template.setSubject(templateEntity.getSubject());
		template.setMessage(templateEntity.getMessage());
		template.setUrl(templateEntity.getUrl());
		return template;
	}
	
	public static TemplateEntity map(final Template template) {
		final TemplateEntity templateEntity = new TemplateEntity();
		templateEntity.setTemplateIdentifier(template.getTemplateIdentifier());
		templateEntity.setSubject(template.getSubject());
		templateEntity.setSenderEmail(template.getSenderEmail());
		templateEntity.setMessage(template.getMessage());
		templateEntity.setUrl(template.getUrl());
		return templateEntity;
	}
}

