package org.apache.fineract.cn.notification.service.internal.service.helperservice;

/*
 ebenezergraham created on 8/4/18
*/

import org.apache.fineract.cn.api.util.UserContextHolder;
import org.apache.fineract.cn.identity.api.v1.client.IdentityManager;
import org.apache.fineract.cn.identity.api.v1.domain.Authentication;
import org.apache.fineract.cn.lang.TenantContextHolder;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class NotificationAuthentication {
	private final String USER_PASSWORD = "shingi";
	private final String USER_IDENTIFIER = "wadaadmin";
	
	private IdentityManager identityManager;
	private Logger logger;
	
	@Autowired
	public NotificationAuthentication(IdentityManager identityManager,
	                                  @Qualifier(ServiceConstants.LOGGER_NAME) final Logger logger) {
		this.identityManager = identityManager;
		this.logger = logger;
	}
	
	public void authenticate(String tenant) {
		TenantContextHolder.clear();
		TenantContextHolder.setIdentifier(tenant);
		
		final Authentication authentication =
				this.identityManager.login(USER_IDENTIFIER, Base64Utils.encodeToString(USER_PASSWORD.getBytes()));
		UserContextHolder.clear();
		UserContextHolder.setAccessToken(USER_IDENTIFIER, authentication.getAccessToken());
		this.logger.debug("{} Authenticated Successfully", UserContextHolder.getUserContext().get().getUser());
	}
}