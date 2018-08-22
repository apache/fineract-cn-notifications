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

import org.apache.fineract.cn.notification.service.internal.service.EmailSender;
import org.apache.fineract.cn.notification.service.internal.service.SMSSender;
import org.apache.fineract.cn.office.api.v1.EventConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.office.api.v1.client.OrganizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class OfficeEventListener {

	private OrganizationManager organizationManager;
	private SMSSender smsSender;
	private EmailSender emailSender;

	@Autowired
	public OfficeEventListener(final OrganizationManager organizationManager, SMSSender smsSender,
	                           final EmailSender emailSender) {
		this.organizationManager = organizationManager;
		this.smsSender = smsSender;
		this.emailSender = emailSender;
	}

	@JmsListener(
			subscription = EventConstants.DESTINATION,
			destination = EventConstants.DESTINATION,
			selector = EventConstants.SELECTOR_POST_OFFICE
	)
	public void onCreateOffice(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                           final String payload) {
		//for testing purposes
		String office = this.organizationManager.findOfficeByIdentifier(payload).getName();
		if (office.equalsIgnoreCase("Head Office")) {
			smsSender.sendSMS("+23058409206", "Test From Demo");
		}
	}
}

/*
	@JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_POST_EMPLOYEE
    )
    public void onCreateEmployee(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                 final String eventPayload) throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_POST_EMPLOYEE, eventPayload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_EMPLOYEE
    )
    public void onUpdateEmployee(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                 final String eventPayload) throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_PUT_EMPLOYEE, eventPayload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_DELETE_EMPLOYEE
    )
    public void onDeleteEmployee(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                 final String eventPayload) throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_DELETE_EMPLOYEE, eventPayload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_CONTACT_DETAIL
    )
    public void onSetContactDetail(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                   final String eventPayload) throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_PUT_CONTACT_DETAIL, eventPayload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_DELETE_CONTACT_DETAIL
    )
    public void onDeleteContactDetail(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                      final String eventPayload) throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_DELETE_CONTACT_DETAIL, eventPayload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_DELETE_OFFICE
    )
    public void onDeleteOffice(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                               final String payload)
            throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_DELETE_OFFICE, payload, String.class);
    }

    @JmsListener(
            subscription = EventConstants.DESTINATION,
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_PUT_OFFICE
    )
    public void onUpdateOffice(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                               final String payload)
            throws Exception {
        this.eventRecorder.event(tenant, EventConstants.OPERATION_PUT_OFFICE, payload, String.class);
    }

}
*/