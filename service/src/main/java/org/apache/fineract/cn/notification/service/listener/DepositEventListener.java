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

package org.apache.fineract.cn.deposit.listener;

import org.apache.fineract.cn.deposit.api.v1.EventConstants;
import org.apache.fineract.cn.lang.config.TenantHeaderFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class DepositEventListener {


    @Autowired
    public DepositEventListener() {
        super();
    }

    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_INTEREST_ACCRUED,
            subscription = EventConstants.DESTINATION
    )
    public void onAccrual(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                          final String payload) {
    }
/*
    @JmsListener(
            destination = EventConstants.DESTINATION,
            selector = EventConstants.SELECTOR_DIVIDEND_DISTRIBUTION,
            subscription = EventConstants.DESTINATION
    )
    public void onDividendDistribution(@Header(TenantHeaderFilter.TENANT_HEADER) final String tenant,
                                       final String payload) {
        this.logger.debug("Dividend distributed for product {}.", payload);
        this.eventRecorder.event(tenant, EventConstants.DIVIDEND_DISTRIBUTION, payload, String.class);
    }
*/
}
