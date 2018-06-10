package org.apache.fineract.cn.notification.service.listener;/*
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

import org.apache.fineract.cn.individuallending.api.v1.events.IndividualLoanEventConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.apache.fineract.cn.portfolio.api.v1.client.PortfolioManager;

@SuppressWarnings("unused")
@Component
public class PortfolioEventListener {

    @Autowired
    public PortfolioEventListener() {
        super();
        //PortfolioManager portfolioManager = new PortfolioManager();
    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_DENY_INDIVIDUALLOAN_CASE
    )
    public void onDeny(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                       final String payload) {

    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_APPROVE_INDIVIDUALLOAN_CASE
    )
    public void onApprove(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                          final String payload) {

    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_ACCEPT_PAYMENT_INDIVIDUALLOAN_CASE
    )
    public void onAcceptPayment(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                final String payload) {
    }


    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_DISBURSE_INDIVIDUALLOAN_CASE
    )
    public void onDisburse(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                           final String payload) {

    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_CHECK_LATE_INDIVIDUALLOAN_CASE
    )
    public void onCheckLate(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                            final String payload) {

    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_OPEN_INDIVIDUALLOAN_CASE
    )
    public void onOpen(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                       final String payload) {

    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_MARK_LATE_INDIVIDUALLOAN_CASE
    )
    public void onMarkLate(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                           final String payload) {

    }

    @JmsListener(
            subscription = IndividualLoanEventConstants.DESTINATION,
            destination = IndividualLoanEventConstants.DESTINATION,
            selector = IndividualLoanEventConstants.SELECTOR_CLOSE_INDIVIDUALLOAN_CASE
    )
    public void onClose(//@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                        final String payload) {
    }

    /*

        @JmsListener(
                subscription = EventConstants.DESTINATION,
                destination = EventConstants.DESTINATION,
                selector = EventConstants.SELECTOR_POST_GROUP
        )
        public void onGroupCreated(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                   final String payload) {
            this.eventRecorder.event(tenant, EventConstants.POST_GROUP, payload, String.class);
        }

        @JmsListener(
                subscription = EventConstants.DESTINATION,
                destination = EventConstants.DESTINATION,
                selector = EventConstants.SELECTOR_ACTIVATE_GROUP
        )
        public void onGroupActivated(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                     final String payload) {
            this.eventRecorder.event(tenant, EventConstants.ACTIVATE_GROUP, payload, String.class);
        }

        @JmsListener(
                subscription = EventConstants.DESTINATION,
                destination = EventConstants.DESTINATION,
                selector = EventConstants.SELECTOR_PUT_GROUP
        )
        public void onGroupUpdated(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                   final String payload) {
            this.eventRecorder.event(tenant, EventConstants.PUT_GROUP, payload, String.class);
        }
         */
    }
