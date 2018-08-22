package org.apache.fineract.cn.notification.util;
/*
ebenezergraham created on 8/20/18
*/

import org.apache.commons.lang.RandomStringUtils;
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;

public class DomainObjectGenerator {
	
	public static EmailConfiguration emailConfiguration() {
		EmailConfiguration emailConfiguration = EmailConfiguration.create("EmailTest",
				"smtp.google.com",
				"1233",
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
				"ACTIVE",
				"SMS");
		
		return smsConfiguration;
	}
}
