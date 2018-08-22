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

import org.apache.fineract.cn.identity.api.v1.events.EventConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class IdentityEventListener {

    private IdentityManager identityManager;

    @Autowired
    public IdentityEventListener( final IdentityManager identityManager) {
        this.identityManager = identityManager;
    }


    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_POST_USER
    )
    public void onCreateUser(
            @Header(TenantHeaderFilter.TENANT_HEADER)final String tenant,
            final String payload) throws Exception {
        eventRecorder.event(tenant, EventConstants.OPERATION_POST_USER, payload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_USER_ROLEIDENTIFIER
    )
    public void onChangeUserRole(
            @Header(TenantHeaderFilter.TENANT_HEADER)final String tenant,
            final String payload) throws Exception {
        eventRecorder.event(tenant, EventConstants.OPERATION_PUT_USER_ROLEIDENTIFIER, payload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_USER_PASSWORD
    )
    public void onChangeUserPassword(
            @Header(TenantHeaderFilter.TENANT_HEADER)final String tenant,
            final String payload) throws Exception {
        eventRecorder.event(tenant, EventConstants.OPERATION_PUT_USER_PASSWORD, payload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_AUTHENTICATE
    )
    public void onAuthentication(
            @Header(TenantHeaderFilter.TENANT_HEADER)final String tenant,
            final String payload) throws Exception {
        eventRecorder.event(tenant, EventConstants.OPERATION_AUTHENTICATE, payload, String.class);
    }


}
*/
