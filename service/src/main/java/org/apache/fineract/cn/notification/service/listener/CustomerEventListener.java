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
		    this.logger.debug("{} has been invoked", "customerCreatedEvent");
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendSMS(receiverNumber, "Dear Valued Customer," +
						    "\n\nYour account has been created" +
						    "\n\nBest Regards," +
						    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Account created",
						    "Dear Valued Customer," +
								    "\n\nYour account has been created" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_PUT_CUSTOMER
	    )
	    public void customerUpdatedEvents(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                      final String payload) {
		    this.logger.debug("{} has been invoked", "customerUpdatedEvents");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
								    "\n\nYour account has been Updated" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Account created",
						    "Dear Valued Customer," +
								    "\n\nYour account has been Updated" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_ACTIVATE_CUSTOMER
	    )
	    public void customerActivatedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                       final String payload) {
		    this.logger.debug("{} has been invoked", "customerActivatedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equalsIgnoreCase(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
								    "\n\nYour account has been Activated" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Account created",
						    "Dear Valued Customer," +
								    "\n\nYour account has been Activated" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_LOCK_CUSTOMER
	    )
	    public void customerLockedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                    final String payload) {
		    this.logger.debug("{} has been invoked", "customerLockedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
								    "\n\nYour account has been Locked" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress, "Account created",
						    "Dear Valued Customer," +
								    "\n\nYour account has been Locked" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	    
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_UNLOCK_CUSTOMER
	    )
	    public void customerUnlockedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                      final String payload) {
		    this.logger.debug("{} has been invoked", "customerUnlockedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
						    "\n\nYour account has been Unlocked" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Account created",
						    "Dear Valued Customer," +
								    "\n\nYour account has been Unlocked" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_CLOSE_CUSTOMER
	    )
	    public void customerClosedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                    final String payload) {
		    this.logger.debug("{} has been invoked", "customerClosedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
						    "\n\nYour account has been Closed" +
								    "\n\nBest Regards" +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress, "Account created",
						    "Dear Valued Customer," +
								    "\n\nYour account has been Closed" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_REOPEN_CUSTOMER
	    )
	    public void customerReopenedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                      final String payload) {
		    this.logger.debug("{} has been invoked", "customerReopenedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
								    "\n\nYour account has been reopened" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Account Reopened",
						    "Dear Valued Customer," +
								    "\n\nYour account has been reopened" +
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_PUT_CONTACT_DETAILS
	    )
	    public void contactDetailsChangedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                           final String payload) {
		    this.logger.debug("{} has been invoked", "contactDetailsChangedEvent");
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
								    "\n\nYour contact has been changed successfully" +
								    "\nNew Contact: "+receiverNumber+
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Contact Details Changed",
						    "Dear Valued Customer," +
								    "\n\nYour contact has been changed successfully" +
								    "\n\tNew Contact: "+emailAddress+
								    "\n\nBest Regards" +
								    "\nYour MFI");
			    }
		    });
	    }
	
	    @JmsListener(
			    destination = CustomerEventConstants.DESTINATION,
			    selector = CustomerEventConstants.SELECTOR_PUT_ADDRESS
	    )
	    public void addressChangedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
	                                    final String payload) {
		    this.logger.debug("{} has been invoked", "addressChangedEvent");
		
		    Customer customer = this.notificationService.findCustomer(payload, tenant).get();
		
		    customer.getContactDetails().forEach(contact -> {
			    if (contact.getType().equals(ContactDetail.Type.PHONE.toString())) {
				    String receiverNumber = contact.getValue();
				    notificationService.sendSMS(receiverNumber,
						    "Dear Valued Customer," +
								    "\n\nYour address has been changed successfully" +
								    "\n\tStreet: "+ customer.getAddress().getStreet()+
								    "\n\tCity: "+ customer.getAddress().getCity()+
								    "\n\tState: "+ customer.getAddress().getRegion()+
								    "\n\tCountry: "+ customer.getAddress().getCountry()+
								    "\n\nBest Regards," +
								    "\nYour MFI");
			    } else if (contact.getType().equals(ContactDetail.Type.EMAIL.toString())) {
				    String emailAddress = contact.getValue();
				    // TODO: Localize message
				    // TODO: Pass message to template
				    notificationService.sendEmail("fineractcnnotificationdemo@gmail.com",
						    emailAddress,
						    "Contact Details Changed" +
								    "New Contact: "+emailAddress,
						    "Dear Valued Customer," +
								    "\n\nYour address has been changed successfully" +
								    "\nStreet: "+ customer.getAddress().getStreet() +
								    "\nCity: "+ customer.getAddress().getCity() +
								    "\nState: "+ customer.getAddress().getRegion() +
								    "\nCountry: "+ customer.getAddress().getCountry() +
								    "\n\nBest Regards" +
								    "\nYour MFI");
			    }
		    });
	    }
    }