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
package org.apache.fineract.cn.notification.api.v1.domain;

import java.util.Objects;
import org.apache.fineract.cn.lang.validation.constraints.ValidIdentifier;
import org.hibernate.validator.constraints.Length;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Sample {
  @ValidIdentifier
  private String identifier;
  @Length(max = 512)
  private String payload;

  public Sample() {
    super();
  }

  public static Sample create(final String identifier, final String payload) {
    final Sample sample = new Sample();
    sample.setIdentifier(identifier);
    sample.setPayload(payload);
    return sample;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sample sample = (Sample) o;
    return Objects.equals(identifier, sample.identifier) &&
            Objects.equals(payload, sample.payload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, payload);
  }
}
