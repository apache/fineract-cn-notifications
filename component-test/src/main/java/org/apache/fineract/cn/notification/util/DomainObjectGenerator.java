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
package org.apache.fineract.cn.notification.util;
/*
ebenezergraham created on 8/20/18
*/

import org.apache.commons.lang.RandomStringUtils;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;

public class DomainObjectGenerator {
	
	public static EmailConfiguration emailConfiguration() {
		EmailConfiguration emailConfiguration = EmailConfiguration.create("emailtest",
				"smtp.google.com",
				"1233",
				"smtp",
				"example",
				RandomStringUtils.randomAlphanumeric(16),
				"true",
				"true",
				"ACTIVE");
		return emailConfiguration;
	}
	
	public static SMSConfiguration smsConfiguration() {
		SMSConfiguration smsConfiguration = SMSConfiguration.create(
				RandomStringUtils.randomAlphanumeric(8),
				RandomStringUtils.randomAlphanumeric(16),
				RandomStringUtils.randomAlphanumeric(16),
				"+309483932",
				"ACTIVE");
		
		return smsConfiguration;
	}
}
