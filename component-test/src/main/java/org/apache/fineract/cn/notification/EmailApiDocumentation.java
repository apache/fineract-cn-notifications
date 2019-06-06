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
import org.apache.fineract.cn.notification.api.v1.domain.EmailConfiguration;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmailApiDocumentation extends AbstractNotificationTest {
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/doc/generated-snippets/document-email");

  @Autowired
  private WebApplicationContext context;
	private Gson gson = new Gson();
	
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
  public void documentCreateEmailConfiguration() throws Exception {
    final EmailConfiguration emailConfiguration = DomainObjectGenerator.emailConfiguration();

    this.mockMvc.perform(post("/configuration/email")
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(gson.toJson(emailConfiguration)))
            .andExpect(status().isCreated())
            .andDo(document("document-create-emailconfiguration", preprocessRequest(prettyPrint()),
                    requestFields(
                            fieldWithPath("identifier").description("EmailConfiguration's identifier"),
                            fieldWithPath("host").description("EmailConfiguration's host"),
                            fieldWithPath("port").description("EmailConfiguration's port"),
                            fieldWithPath("protocol").description("EmailConfiguration's protocol"),
                            fieldWithPath("username").description("EmailConfiguration's username"),
                            fieldWithPath("app_password").description("EmailConfiguration's app_password"),
                            fieldWithPath("smtp_auth").description("EmailConfiguration's smtp authentication"),
                            fieldWithPath("start_tls").description("EmailConfiguration's start tls"),
                            fieldWithPath("state").description("EmailConfiguration's state")
                    )
            ));
  }

  @Test
  public void documentFindEmailConfiguration() throws Exception {
    final EmailConfiguration emailConfiguration = EmailConfiguration.create("emailtesty",
            "smtp.google.com",
            "1233",
            "smtp",
            "example",
            RandomStringUtils.randomAlphanumeric(16),
            "true",
            "true",
            "ACTIVE");

    this.notificationManager.createEmailConfiguration(emailConfiguration);
    eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, EmailConfiguration.class);

    this.mockMvc.perform(get("/configuration/email/" + emailConfiguration.getIdentifier())
            .accept(MediaType.ALL_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("document-find-emailconfiguration", preprocessRequest(prettyPrint()),
                    responseFields(
                            fieldWithPath("identifier").description("EmailConfiguration's identifier"),
                            fieldWithPath("host").description("EmailConfiguration's host"),
                            fieldWithPath("port").description("EmailConfiguration's port"),
                            fieldWithPath("protocol").description("EmailConfiguration's protocol"),
                            fieldWithPath("username").description("EmailConfiguration's username"),
                            fieldWithPath("app_password").description("EmailConfiguration's app_password"),
                            fieldWithPath("smtp_auth").description("EmailConfiguration's smtp authentication"),
                            fieldWithPath("start_tls").description("EmailConfiguration's start tls"),
                            fieldWithPath("state").description("EmailConfiguration's state")
                    )
            ));
  }
  
  @Test
  public void documentUpdateEmailConfiguration() throws Exception {
    
    final EmailConfiguration randomEmailConfiguration = DomainObjectGenerator.emailConfiguration();
    final EmailConfiguration newRandomConfiguration = DomainObjectGenerator.emailConfiguration();
    
    this.notificationManager.createEmailConfiguration(randomEmailConfiguration);
    
    super.eventRecorder.wait(NotificationEventConstants.POST_EMAIL_CONFIGURATION, randomEmailConfiguration.getIdentifier());
    
    newRandomConfiguration.setIdentifier(randomEmailConfiguration.getIdentifier());
    newRandomConfiguration.setHost("new.host.com");
    newRandomConfiguration.setApp_password("changePassword");
    newRandomConfiguration.setPort("554");
    newRandomConfiguration.setProtocol("pop3");
    newRandomConfiguration.setUsername("newaddress@example.com");
    newRandomConfiguration.setSmtp_auth("false");
    newRandomConfiguration.setStart_tls("false");
    newRandomConfiguration.setState("ACTIVE");
    
    notificationManager.updateEmailConfiguration(newRandomConfiguration);
    
    super.eventRecorder.wait(NotificationEventConstants.UPDATE_EMAIL_CONFIGURATION, newRandomConfiguration.getIdentifier());
    
    this.mockMvc.perform(put("/configuration/sms")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(gson.toJson(newRandomConfiguration)))
        .andExpect(status().isAccepted())
        .andDo(document("document-update-email-configuration", preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("identifier").description("Configuration Id for Email Server"),
                fieldWithPath("host").description("Email Server host address"),
                fieldWithPath("port").description("Email Server port number"),
                fieldWithPath("protocol").description("Type of protocol in use " +
                    "\nSMTP" +
                    "\nPOP3" +
                    "\nIMAP"),
                fieldWithPath("username").description("Email address"),
                fieldWithPath("app_password").description("Email app password or normal password"),
                fieldWithPath("smtp_auth").description("Enable SMTP"),
                fieldWithPath("start_tls").description("Enable TLS"),
                fieldWithPath("state").description("" +
                    "\n ACTIVE for Gateway to be used" +
                    "\n DEACTIVATED for inactive gateways")
            )));
  }
  
  @Test
  public void documentDeleteEmailConfiguration() throws Exception {
    final EmailConfiguration randomConfiguration = DomainObjectGenerator.emailConfiguration();
    
    notificationManager.createEmailConfiguration(randomConfiguration);
    super.eventRecorder.wait(NotificationEventConstants.DELETE_EMAIL_CONFIGURATION, randomConfiguration.getIdentifier());
    
    this.mockMvc.perform(delete("/configuration/email/" + randomConfiguration.getIdentifier())
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(document("document-delete-email-configuration", preprocessResponse(prettyPrint())));
  }
}
