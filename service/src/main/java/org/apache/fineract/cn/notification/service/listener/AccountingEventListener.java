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
/*
        package org.apache.fineract.cn.notification.service.listener;

        import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
        import org.apache.fineract.cn.accounting.service.internal.service.AccountService;
        import org.apache.fineract.cn.notification.service.internal.service.EmailSender;
        import org.apache.fineract.cn.notification.service.internal.service.SMSSender;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.jms.annotation.JmsListener;
        import org.springframework.messaging.handler.annotation.Header;
        import org.springframework.stereotype.Component;
        import org.apache.fineract.cn.accounting.api.v1.EventConstants;


@SuppressWarnings("unused")
@Component
public class AccountingEventListener {


    @Autowired
    public AccountingEventListener( final AccountService accountingManager) {
        this.accountService = accountingManager;
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_POST_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onCreateAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant, final String payload) {
        accountService.findAccount(tenant);
        String receiver = "";
        String template = "";
        smsSender.sendSMS(receiver,template);
        emailSender.sendEmail(receiver,"Hello", "You account has been created");
    }


    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onChangeAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                final String payload) {
        this.logger.debug("Account modified.");
        this.eventRecorder.event(tenant, EventConstants.PUT_ACCOUNT, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_CLOSE_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onCloseAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                               final String payload) {
        this.logger.debug("Account closed.");
        this.eventRecorder.event(tenant, EventConstants.CLOSE_ACCOUNT, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_LOCK_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onLockAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                              final String payload) {
        this.logger.debug("Account locked.");
        this.eventRecorder.event(tenant, EventConstants.LOCK_ACCOUNT, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_UNLOCK_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onUnlockAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                final String payload) {
        this.logger.debug("Account unlocked.");
        this.eventRecorder.event(tenant, EventConstants.UNLOCK_ACCOUNT, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_REOPEN_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onReopenAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                final String payload) {
        this.logger.debug("Account reopened.");
        this.eventRecorder.event(tenant, EventConstants.REOPEN_ACCOUNT, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_DELETE_ACCOUNT,
            subscription = EventConstants.DESTINATION
    )
    public void onDeleteAccount(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                final String payload) {
        this.logger.debug("Account deleted.");
        this.eventRecorder.event(tenant, EventConstants.DELETE_ACCOUNT, payload, String.class);
    }

}
 */