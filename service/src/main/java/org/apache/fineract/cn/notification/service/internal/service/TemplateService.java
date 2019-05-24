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
package org.apache.fineract.cn.notification.service.internal.service;

import org.apache.fineract.cn.command.annotation.Aggregate;
import org.apache.fineract.cn.notification.api.v1.domain.Template;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.mapper.TemplateMapper;
import org.apache.fineract.cn.notification.service.internal.repository.TemplateRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aggregate
public class TemplateService {
	
	
	private final TemplateRepository templateRepository;
	private Logger logger;
	
	@Autowired
	public TemplateService(final TemplateRepository templateRepository,
	                       @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		super();
		this.logger = logger;
		this.templateRepository = templateRepository;
	}
	public Optional<Template> findTemplateWithIdentifier(final String identifier) {
		return this.templateRepository.findByTemplateIdentifier(identifier).map(TemplateMapper::map);
	}
	
	public Boolean templateExists(final String identifier) {
		return this.templateRepository.existsByTemplateIdentifier(identifier);
	}
	
	public Boolean deleteTemplate(final String identifier) {
		//Todo: Remove html template and template record from repository
		return false;
	}
}
