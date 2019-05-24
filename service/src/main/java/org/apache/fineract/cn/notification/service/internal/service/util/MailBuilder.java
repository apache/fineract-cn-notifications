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
package org.apache.fineract.cn.notification.service.internal.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
/*
ebenezergraham created on 5/12/19
*/
@Service
public class MailBuilder {
		private TemplateEngine templateEngine;
		
		@Autowired
		public MailBuilder(TemplateEngine templateEngine) {
			this.templateEngine = templateEngine;
		}
		
		public String build(Map<String, Object> message, String template) {
			Context context = new Context();
			for(Map.Entry m:message.entrySet()){
				context.setVariable(m.getKey().toString(), m.getValue().toString());
			}
			return templateEngine.process(template, context);
		}
}
