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
package org.apache.fineract.cn.notification.service.listener;

import org.apache.fineract.cn.individuallending.api.v1.events.IndividualLoanEventConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author Ebenezer Graham
 */
@SuppressWarnings("unused")
@Component
public class PortfolioEventListener {
	
	private final NotificationService notificationService;
	private final Logger logger;
	
	@Autowired
	public PortfolioEventListener(
			final NotificationService notificationService,
			@Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		super();
		this.logger = logger;
		this.notificationService = notificationService;
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_OPEN_INDIVIDUALLOAN_CASE
	)
	public void onOpen(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                   final String payload) {
		logger.debug("Payload: " + payload+ "Tenant" +tenant);
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour loan request has been denied" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_DENY_INDIVIDUALLOAN_CASE
	)
	public void onDeny(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                   final String payload) {
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour loan request has been denied" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_APPROVE_INDIVIDUALLOAN_CASE
	)
	public void onApprove(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                      final String payload) {
		logger.info(payload);
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour loan has been Approved and waiting disbursal" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_DISBURSE_INDIVIDUALLOAN_CASE
	)
	public void onDisburse(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                       final String payload) {
		logger.info(payload.toString());
		this.notificationService.sendSMS("+23058409206",
			"Dear Valued Customer," +
					"\n\nYour loan has been disbursed" +
					"\n\nBest Regards" +
					"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_ACCEPT_PAYMENT_INDIVIDUALLOAN_CASE
	)
	public void onAcceptPayment(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                            final String payload) {
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour payment has been accepted" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_MARK_LATE_INDIVIDUALLOAN_CASE
	)
	public void onMarkLate(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                       final String payload) {
		
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour payment for your loan is late" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_MARK_IN_ARREARS_INDIVIDUALLOAN_CASE
	)
	public void onMarkInArrears(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                            final String payload) {
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour payment has been marked as arrears for next payment" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_CLOSE_INDIVIDUALLOAN_CASE
	)
	public void onClose(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                    final String payload) {
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour loan has been closed" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
	
	@JmsListener(
			subscription = IndividualLoanEventConstants.DESTINATION,
			destination = IndividualLoanEventConstants.DESTINATION,
			selector = IndividualLoanEventConstants.SELECTOR_RECOVER_INDIVIDUALLOAN_CASE
	)
	public void onRecover(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                      final String payload) {
		this.notificationService.sendSMS("+23058409206",
				"Dear Valued Customer," +
						"\n\nYour arrears have been recovered" +
						"\n\nBest Regards" +
						"\nYour MFI");
	}
}