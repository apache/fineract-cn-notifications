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
package org.apache.fineract.cn.notification;

import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.fineract.cn.notification.api.v1.client.NotificationManager;
import org.apache.fineract.cn.notification.api.v1.domain.SMSConfiguration;
import org.apache.fineract.cn.notification.api.v1.events.NotificationEventConstants;
import org.apache.fineract.cn.notification.util.DomainObjectGenerator;
import org.apache.fineract.cn.test.listener.EventRecorder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SmsApiDocumentation extends AbstractNotificationTest {
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/doc/generated-snippets/document-sms");

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Autowired
  private NotificationManager notificationManager;

  @Autowired
  private EventRecorder eventRecorder;

  @Before
  public void setUp() {

    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .apply(documentationConfiguration(this.restDocumentation))
            .build();
  }

  @Test
  public void documentCreateSMSConfiguration() throws Exception {
    final SMSConfiguration smsConfiguration = DomainObjectGenerator.smsConfiguration();

    Gson gson = new Gson();
    this.mockMvc.perform(post("/notification/sms/create")
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(gson.toJson(smsConfiguration)))
            .andExpect(status().isAccepted())
            .andDo(document("document-create-smsconfiguration", preprocessRequest(prettyPrint()),
                    requestFields(
                            fieldWithPath("identifier").description("SMSConfiguration's identifier"),
                            fieldWithPath("auth_token").description("SMSConfiguration's auth_token"),
                            fieldWithPath("account_sid").description("SMSConfiguration's account_sid"),
                            fieldWithPath("sender_number").description("Sender's number"),
                            fieldWithPath("state").description("SMSConfiguration's state")
                    )
            ));
  }

  @Test
  public void documentFindSMSConfiguration() throws Exception {

    final SMSConfiguration smsConfiguration = SMSConfiguration.create(RandomStringUtils.randomAlphanumeric(4),
            RandomStringUtils.randomAlphanumeric(8),
            RandomStringUtils.randomAlphanumeric(8),
            "+309483932",
            "ACTIVE");

    this.notificationManager.createSMSConfiguration(smsConfiguration);
    this.eventRecorder.wait(NotificationEventConstants.POST_SMS_CONFIGURATION, SMSConfiguration.class);

    this.mockMvc.perform(get("/notification/sms/" + smsConfiguration.getIdentifier())
            .accept(MediaType.ALL_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("document-find-smsconfiguration", preprocessRequest(prettyPrint()),
                    responseFields(
                            fieldWithPath("type").description("SMSConfiguration's type"),
                            fieldWithPath("identifier").description("SMSConfiguration's identifier"),
                            fieldWithPath("auth_token").description("SMSConfiguration's auth_token"),
                            fieldWithPath("account_sid").description("SMSConfiguration's account_sid"),
                            fieldWithPath("sender_number").description("Sender's number"),
                            fieldWithPath("state").description("SMSConfiguration's state")
                    )
            ));
  }
}
