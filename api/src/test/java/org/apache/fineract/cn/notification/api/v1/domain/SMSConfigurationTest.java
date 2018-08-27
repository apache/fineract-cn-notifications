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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.fineract.cn.test.domain.ValidationTest;
import org.apache.fineract.cn.test.domain.ValidationTestCase;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

public class SMSConfigurationTest extends ValidationTest<SMSConfiguration> {
	
	public SMSConfigurationTest(ValidationTestCase<SMSConfiguration> testCase) {
		super(testCase);
	}
	
	@Parameterized.Parameters
	public static Collection testCases() {
		final Collection<ValidationTestCase> ret = new ArrayList<>();
		ret.add(new ValidationTestCase<SMSConfiguration>("basicCase")
				.adjustment(x -> {
				})
				.valid(false));
		ret.add(new ValidationTestCase<SMSConfiguration>("nullIdentifier")
				.adjustment(x -> x.setIdentifier(null))
				.valid(false));
		ret.add(new ValidationTestCase<SMSConfiguration>("tooShortIdentifier")
				.adjustment(x -> x.setIdentifier("z"))
				.valid(false));
		ret.add(new ValidationTestCase<SMSConfiguration>("LongToken")
				.adjustment(x -> x.getAccount_sid())
				.valid(false));
		ret.add(new ValidationTestCase<SMSConfiguration>("tooLongSID")
				.adjustment(x -> x.getAccount_sid())
				.valid(false));
		return ret;
	}
	
	@Override
	protected SMSConfiguration createValidTestSubject() {
		return SMSConfiguration.create("test",
				RandomStringUtils.randomAlphanumeric(300),
				RandomStringUtils.randomAlphanumeric(300),
				"ACTIVE",
				"SMS");
	}
}