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

import org.apache.fineract.cn.test.domain.ValidationTest;
import org.apache.fineract.cn.test.domain.ValidationTestCase;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

public class EmailConfigurationTest extends ValidationTest<EmailConfiguration> {
	
	public EmailConfigurationTest(ValidationTestCase<EmailConfiguration> testCase) {
		super(testCase);
	}
	
	@Parameterized.Parameters
	public static Collection testCases() {
		final Collection<ValidationTestCase> ret = new ArrayList<>();
		ret.add(new ValidationTestCase<EmailConfiguration>("basicCase")
				.adjustment(x -> {
				})
				.valid(true));
		ret.add(new ValidationTestCase<EmailConfiguration>("nullIdentifier")
				.adjustment(x -> x.setIdentifier(null))
				.valid(false));
		ret.add(new ValidationTestCase<EmailConfiguration>("tooShortIdentifier")
				.adjustment(x -> x.setIdentifier("z"))
				.valid(false));
		return ret;
	}
	
	
	protected EmailConfiguration createValidTestSubject() {
		return EmailConfiguration.create("EmailTest",
				"smtp.google.com",
				"123",
				"example",
				"egraham",
				"asdfaegw4t3rwg5w",
				"true",
				"true",
				"ACTIVE");
	}
	
	
}