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

    import org.apache.fineract.cn.customer.api.v1.CustomerEventConstants;
    import org.apache.fineract.cn.customer.api.v1.domain.ContactDetail;
    import org.apache.fineract.cn.customer.api.v1.domain.Customer;
    import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
    import org.apache.fineract.cn.notification.service.ServiceConstants;
    import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
    import org.slf4j.Logger;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.jms.annotation.JmsListener;
    import org.springframework.messaging.handler.annotation.Header;
    import org.springframework.stereotype.Component;

    @SuppressWarnings("unused")
    @Component
    public class CustomerEventListener {
	    private final NotificationService notificationService;
	    private final Logger logger;
	
	    @Autowired
	    public CustomerEventListener(final NotificationService notificationService,
	                                 @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		    this.logger = logger;
		    this.notificationService = notificationService;
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_POST_CUSTOMER
	    )
	    public void customerCreatedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                     final String payload) {
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		    this.logger.info("{} has been invoked", "customerCreatedEvent");
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendSMS(receiverNumber, "customerCreatedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "customerCreatedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_PUT_CUSTOMER
	    )
	    public void customerUpdatedEvents(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                      final String payload) {
		    this.logger.info("{} has been invoked", "customerUpdatedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendSMS(receiverNumber,
								"customerUpdatedEvent"
								);
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "customerUpdatedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_ACTIVATE_CUSTOMER
	    )
	    public void customerActivatedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                       final String payload) {
		    this.logger.info("{} has been invoked", "customerActivatedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equalsIgnoreCase(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"customerActivatedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "customerActivatedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_LOCK_CUSTOMER
	    )
	    public void customerLockedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                    final String payload) {
		    this.logger.info("{} has been invoked", "customerLockedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"customerLockedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress, "customerLockedEvent",
						    payload);
			    }
		    });
	    }
	    
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_UNLOCK_CUSTOMER
	    )
	    public void customerUnlockedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                      final String payload) {
		    this.logger.info("{} has been invoked", "customerUnlockedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"customerUnlockedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "customerUnlockedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_CLOSE_CUSTOMER
	    )
	    public void customerClosedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                    final String payload) {
		    this.logger.info("{} has been invoked", "customerClosedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"customerClosedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress, "customerClosedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_REOPEN_CUSTOMER
	    )
	    public void customerReopenedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                      final String payload) {
		    this.logger.info("{} has been invoked", "customerReopenedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"customerReopenedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "customerReopenedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_PUT_CONTACT_DETAILS
	    )
	    public void contactDetailsChangedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                           final String payload) {
		    this.logger.info("{} has been invoked", "contactDetailsChangedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"contactDetailsChangedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "contactDetailsChangedEvent",
						    payload);
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_PUT_ADDRESS
	    )
	    public void addressChangedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                    final String payload) {
		    this.logger.info("{} has been invoked", "addressChangedEvent");
		
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
								"addressChangedEvent");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail(
						    emailAddress,
						    "addressChangedEvent",
						    payload);
			    }
		    });
	    }
    }
