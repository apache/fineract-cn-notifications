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
import org.apache.fineract.cn.lang.AutoTenantContext;
import org.apache.fineract.cn.notification.service.internal.service.NotificationService;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.customer.api.v1.domain.ContactDetail;
import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.apache.fineract.cn.notification.service.internal.service.EmailService;
import org.apache.fineract.cn.notification.service.internal.service.SMSService;
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
    private AutoTenantContext autoTenantContext;

        @Autowired
        public CustomerEventListener(final NotificationService notificationService,
                                     @Qualifier(ServiceConstants.LOGGER_NAME)
                                     final Logger logger ) {
            this.logger = logger;
            this.notificationService = notificationService;

        }

        @JmsListener(
                destination = CustomerEventConstants.DESTINATION,
                selector = CustomerEventConstants.SELECTOR_POST_CUSTOMER
        )
        public void customerCreatedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                         final String payload) {


            this.autoTenantContext = new AutoTenantContext(tenant);
            Customer customer = this.notificationService.findCustomer(payload).get();
	        this.logger.info("This is the customer created: " + customer.hashCode());


            if (customer.getContactDetails().size() > 0) {
                customer.getContactDetails().forEach(contactDetail -> {
                    if (contactDetail.getType().equals(ContactDetail.Type.PHONE)) {
                        String receiverNumber = customer.getContactDetails().get(0).getValue();
                        // TODO: pass receiver number for templating and localization.
                        notificationService.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been created");
                    } else if (contactDetail.getType().equals(ContactDetail.Type.EMAIL)) {
                        String emailAddress = customer.getContactDetails().get(0).getValue();
                        // TODO: pass email address for templating and localization.
                        notificationService.sendEmail("akyencorp@gmail.com",emailAddress, "Account created", "Dear Valued Customer, Your account has been created");
                    }
                });
            }

        }

    @JmsListener(
            destination = CustomerEventConstants.DESTINATION,
            selector = CustomerEventConstants.SELECTOR_ACTIVATE_CUSTOMER
    )
    public void customerActivatedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                       final String payload) {
        Customer customer = this.notificationService.findCustomer(payload).get();
        notificationService.sendSMS("+230 58409206","manager worked: "+customer.getCurrentState());
        this.logger.debug("This is the customer created: "+ customer.hashCode());

        if(customer.getCurrentState().equalsIgnoreCase("ACTIVE")){
            customer.getContactDetails().forEach(contact -> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                String receiverNumber = customer.getContactDetails().get(0).getValue();
                notificationService.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Activated");
            } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                String emailAddress = customer.getContactDetails().get(0).getValue();
                // TODO: pass email address for templating and localization.
                notificationService.sendEmail("akyencorp@gmail.com",emailAddress, "Account created", "Dear Valued Customer, Your account has been Activated");
            }
            });
        }
    }

    @JmsListener(
            destination = CustomerEventConstants.DESTINATION,
            selector = CustomerEventConstants.SELECTOR_LOCK_CUSTOMER
    )
    public void customerLockedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                    final String payload) {
        Customer customer = this.notificationService.findCustomer(payload).get();
        this.logger.info("This is the customer created: "+ customer.hashCode());

        if(customer.getCurrentState().equalsIgnoreCase("LOCKED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    notificationService.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Locked");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    notificationService.sendEmail("akyencorp@gmail.com",emailAddress, "Account created", "Dear Valued Customer, Your account has been Locked");
                }
            });
        }
    }

    @JmsListener(
            destination = CustomerEventConstants.DESTINATION,
            selector = CustomerEventConstants.SELECTOR_UNLOCK_CUSTOMER
    )
    public void customerUnlockedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                      final String payload) {
        Customer customer = this.notificationService.findCustomer(payload).get();
        this.logger.info("This is the customer created: "+ customer.hashCode());

        if(customer.getCurrentState().equalsIgnoreCase("LOCKED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    notificationService.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Unlocked");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    notificationService.sendEmail("akyencorp@gmail.com",emailAddress, "Account created", "Dear Valued Customer, Your account has been Unlocked");
                }
            });
        }
    }

    @JmsListener(
            destination = CustomerEventConstants.DESTINATION,
            selector = CustomerEventConstants.SELECTOR_CLOSE_CUSTOMER
    )
    public void customerClosedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                    final String payload) {
        Customer customer = this.notificationService.findCustomer(payload).get();
        this.logger.info("This is the customer created: "+ customer.hashCode());


        if(customer.getCurrentState().equalsIgnoreCase("CLOSED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    notificationService.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Closed");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    notificationService.sendEmail("akyencorp@gmail.com",emailAddress, "Account created", "Dear Valued Customer, Your account has been Closed");
                }
            });
        }
    }

    @JmsListener(
            destination = CustomerEventConstants.DESTINATION,
            selector = CustomerEventConstants.SELECTOR_REOPEN_CUSTOMER
    )
    public void customerReopenedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                      final String payload) {
        Customer customer = this.notificationService.findCustomer(payload).get();
        this.logger.info("This is the customer created: "+ customer.hashCode());
        if(customer.getCurrentState().equalsIgnoreCase("LOCKED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    notificationService.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been reopened");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    notificationService.sendEmail("akyencorp@gmail.com",emailAddress, "Account Reopened", "Dear Valued Customer, Your account has been reopened");
                }
            });
        }
    }
}
