package org.apache.fineract.cn.notification.api.v1.domain;
/*
ebenezergraham created on 8/19/18
*/

public class Event {
	private final String identifier;
	private boolean enabled;
	
	public Event (String identifier, boolean enabled){
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
}
