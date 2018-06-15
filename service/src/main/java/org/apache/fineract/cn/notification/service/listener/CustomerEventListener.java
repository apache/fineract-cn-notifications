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
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.apache.fineract.cn.customer.api.v1.client.CustomerManager;
import org.apache.fineract.cn.customer.api.v1.domain.ContactDetail;
import org.apache.fineract.cn.customer.api.v1.domain.Customer;
import org.apache.fineract.cn.notification.service.internal.service.EmailSender;
import org.apache.fineract.cn.notification.service.internal.service.SMSSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class CustomerEventListener {

        private CustomerManager customerManager;
        private SMSSender smsSender;
        private EmailSender emailSender;

        @Autowired
        public CustomerEventListener( final CustomerManager customerManager, SMSSender smsSender, EmailSender emailSender ) {
            this.customerManager = customerManager;
            this.smsSender = smsSender;
            this.emailSender = emailSender;
            smsSender.sendSMS("+23058409206","just to be sure listen has been instantiated");
        }

        @JmsListener(
                destination = CustomerEventConstants.DESTINATION,
                selector = CustomerEventConstants.SELECTOR_POST_CUSTOMER
        )
        public void customerCreatedEvent(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                         final String payload) {
            System.out.println(payload);
            Customer customer = customerManager.findCustomer(payload);
            System.out.println("This is the customer created: " + customer.getGivenName());
            if (customer.getContactDetails().size() > 0) {
                customer.getContactDetails().forEach(contactDetail -> {
                    if (contactDetail.getType().equals(ContactDetail.Type.PHONE)) {
                        String receiverNumber = customer.getContactDetails().get(0).getValue();
                        // TODO: pass receiver number for templating and localization.
                        smsSender.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been created");
                    } else if (contactDetail.getType().equals(ContactDetail.Type.EMAIL)) {
                        String emailAddress = customer.getContactDetails().get(0).getValue();
                        // TODO: pass email address for templating and localization.
                        emailSender.sendEmail(emailAddress, "Account created", "Dear Valued Customer, Your account has been created");
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
        Customer customer = customerManager.findCustomer(payload);
        if(customer.getCurrentState().equalsIgnoreCase("ACTIVE")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                String receiverNumber = customer.getContactDetails().get(0).getValue();
                smsSender.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Activated");
            } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                String emailAddress = customer.getContactDetails().get(0).getValue();
                // TODO: pass email address for templating and localization.
                emailSender.sendEmail(emailAddress, "Account created", "Dear Valued Customer, Your account has been Activated");
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
        Customer customer = customerManager.findCustomer(payload);
        if(customer.getCurrentState().equalsIgnoreCase("LOCKED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    smsSender.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Locked");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    emailSender.sendEmail(emailAddress, "Account created", "Dear Valued Customer, Your account has been Locked");
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
        Customer customer = customerManager.findCustomer(payload);
        if(customer.getCurrentState().equalsIgnoreCase("LOCKED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    smsSender.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Unlocked");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    emailSender.sendEmail(emailAddress, "Account created", "Dear Valued Customer, Your account has been Unlocked");
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
        Customer customer = customerManager.findCustomer(payload);
        if(customer.getCurrentState().equalsIgnoreCase("CLOSED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    smsSender.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been Closed");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    emailSender.sendEmail(emailAddress, "Account created", "Dear Valued Customer, Your account has been Closed");
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
        Customer customer = customerManager.findCustomer(payload);
        if(customer.getCurrentState().equalsIgnoreCase("LOCKED")){
            customer.getContactDetails().forEach(contact-> {
                if (contact.getType().equals(ContactDetail.Type.PHONE)) {
                    String receiverNumber = customer.getContactDetails().get(0).getValue();
                    smsSender.sendSMS(receiverNumber, "Dear Valued Customer, Your account has been reopened");
                } else if (contact.getType().equals(ContactDetail.Type.EMAIL)) {
                    String emailAddress = customer.getContactDetails().get(0).getValue();
                    // TODO: pass email address for templating and localization.
                    emailSender.sendEmail(emailAddress, "Account Reopened", "Dear Valued Customer, Your account has been reopened");
                }
            });
        }
    }
}
