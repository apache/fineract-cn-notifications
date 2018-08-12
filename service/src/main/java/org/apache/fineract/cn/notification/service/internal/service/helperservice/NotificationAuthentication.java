
package org.apache.fineract.cn.notification.service.internal.service.helperservice;

/*
ebenezergraham created on 8/4/18
*/

import org.apache.fineract.cn.anubis.api.v1.domain.AllowedOperation;
import org.apache.fineract.cn.api.context.AutoGuest;
import org.apache.fineract.cn.api.context.AutoUserContext;
import org.apache.fineract.cn.identity.api.v1.client.IdentityManager;
import org.apache.fineract.cn.identity.api.v1.domain.*;
import org.apache.fineract.cn.lang.TenantContextHolder;
import org.apache.fineract.cn.notification.service.ServiceConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.Arrays;
import java.util.Collections;

@Component
public class NotificationAuthentication {
	private final String ADMIN_PASSWORD = "shingi";
	private final String NOTIFICATION_ROLE = "notificationAdmin";
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
//		if(TenantContextHolder.checkedGetIdentifier()
//				.equalsIgnoreCase("Tenant context not set.")){
		UserWithPassword user = createUser();
		TenantContextHolder.setIdentifier(tenant);
//		}
		Authentication auth = identityManager.login(user.getIdentifier(), user.getPassword());
		AutoUserContext userContext = new AutoUserContext(user.getIdentifier(),auth.getAccessToken());
	}

	public UserWithPassword createUser() {
		Authentication adminAuthentication;

		try (final AutoUserContext ignored = new AutoGuest()) {
			adminAuthentication  = identityManager.login(USER_IDENTIFIER, ADMIN_PASSWORD
			);
		}

		try (final AutoUserContext ignored = new AutoUserContext(USER_IDENTIFIER, adminAuthentication.getAccessToken())) {
			final Role notificationRole = defineNotificationRole();
			identityManager.createRole(notificationRole);

			final UserWithPassword notificationUser = new UserWithPassword();
			notificationUser.setIdentifier(USER_IDENTIFIER);
			notificationUser.setPassword(Base64Utils.encodeToString(ADMIN_PASSWORD.getBytes()));
			notificationUser.setRole(notificationRole.getIdentifier());
			identityManager.createUser(notificationUser);

			identityManager.changeUserPassword(notificationUser.getIdentifier(), new Password(notificationUser.getPassword()));

			return notificationUser;
		}
	}

	private Role defineNotificationRole() {
		final Permission customerPermission = new Permission();
		customerPermission.setAllowedOperations(Collections.singleton(AllowedOperation.READ));
		customerPermission.setPermittableEndpointGroupIdentifier(org.apache.fineract.cn.customer.PermittableGroupIds.CUSTOMER);

		final Permission employeeAllPermission = new Permission();
		employeeAllPermission.setAllowedOperations(AllowedOperation.ALL);
		employeeAllPermission.setPermittableEndpointGroupIdentifier(org.apache.fineract.cn.office.api.v1.PermittableGroupIds.EMPLOYEE_MANAGEMENT);

		final Permission ledgerManagementPermission = new Permission();
		ledgerManagementPermission.setAllowedOperations(AllowedOperation.ALL);
		ledgerManagementPermission.setPermittableEndpointGroupIdentifier(org.apache.fineract.cn.accounting.api.v1.PermittableGroupIds.THOTH_LEDGER);

		final Permission accountManagementPermission = new Permission();
		accountManagementPermission.setAllowedOperations(AllowedOperation.ALL);
		accountManagementPermission.setPermittableEndpointGroupIdentifier(org.apache.fineract.cn.accounting.api.v1.PermittableGroupIds.THOTH_ACCOUNT);

		final Permission notificationManagementPermission = new Permission();
		accountManagementPermission.setAllowedOperations(AllowedOperation.ALL);
		accountManagementPermission.setPermittableEndpointGroupIdentifier(org.apache.fineract.cn.notification.api.v1.PermittableGroupIds.SELF_MANAGEMENT);

		final Role role = new Role();
		role.setIdentifier(NOTIFICATION_ROLE);
		role.setPermissions(Arrays.asList(
								employeeAllPermission,
								customerPermission,
								ledgerManagementPermission,
								accountManagementPermission,
								notificationManagementPermission
								)
		);
		return role;
	}

}