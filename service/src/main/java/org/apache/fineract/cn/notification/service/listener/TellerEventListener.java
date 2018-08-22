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
package org.apache.fineract.cn.teller.listener;

import org.apache.fineract.cn.teller.AbstractTellerTest;
import org.apache.fineract.cn.teller.api.v1.EventConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class TellerEventListener {

    private final Logger logger;
    private final EventRecorder eventRecorder;

    public TellerEventListener(@Qualifier(AbstractTellerTest.LOGGER_NAME) final Logger logger,
                               final EventRecorder eventRecorder) {
        super();
        this.logger = logger;
        this.eventRecorder = eventRecorder;
    }


    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_POST_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onCreate(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                         final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.POST_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onChange(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                         final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.PUT_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_OPEN_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onOpen(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                       final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.OPEN_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_CLOSE_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onClose(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                        final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.CLOSE_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_AUTHENTICATE_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onAuthenticate(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                               final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.AUTHENTICATE_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PAUSE_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onPause(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                        final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.PAUSE_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_CONFIRM_TRANSACTION,
            subscription = EventConstants.DESTINATION
    )
    public void confirmTransaction(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                   final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.CONFIRM_TRANSACTION, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_DELETE_TELLER,
            subscription = EventConstants.DESTINATION
    )
    public void onDeleteTeller(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                               final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.DELETE_TELLER, payload, String.class);
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_SAVE_DENOMINATION,
            subscription = EventConstants.DESTINATION
    )
    public void onSaveDenomination(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                   final String payload) {
        this.logger.debug("Teller {} created.", payload);
        this.eventRecorder.event(tenant, EventConstants.SAVE_DENOMINATION, payload, String.class);
    }

}
*/
