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
/*
ebenezergraham created on 8/19/18
*/

import java.util.Objects;

public class Event {
	private final String identifier;
	private boolean enabled;
	
	public Event(String identifier, boolean enabled) {
		super();
		this.identifier = identifier;
		this.enabled = enabled;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean state) {
		this.enabled = state;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event event = (Event) o;
		return enabled == event.enabled &&
				Objects.equals(identifier, event.identifier);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(identifier, enabled);
	}
	
	@Override
	public String toString() {
		return "Event{" +
				"identifier='" + identifier + '\'' +
				", enabled=" + enabled +
				'}';
	}
}
