/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.churchmutual.content.setup.upgrade.util.common;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Matthew Chan
 */
public abstract class BaseAdminUpgradeProcess extends UpgradeProcess {

	public BaseAdminUpgradeProcess(
		PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
		UserLocalService userLocalService) {

		this.permissionCheckerFactory = permissionCheckerFactory;
		this.portal = portal;
		this.roleLocalService = roleLocalService;
		this.userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		PermissionChecker originalPermissionChecker = PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			long companyId = portal.getDefaultCompanyId();

			Role adminRole = roleLocalService.getRole(companyId, RoleConstants.ADMINISTRATOR);

			User adminUser = userLocalService.getRoleUsers(
				adminRole.getRoleId()
			).get(
				0
			);

			PrincipalThreadLocal.setName(adminUser.getUserId());

			PermissionChecker adminPermissionChecker = permissionCheckerFactory.create(adminUser);

			PermissionThreadLocal.setPermissionChecker(adminPermissionChecker);

			doUpgradeAsAdmin();
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
			PermissionThreadLocal.setPermissionChecker(originalPermissionChecker);
		}
	}

	protected abstract void doUpgradeAsAdmin() throws Exception;

	protected PermissionCheckerFactory permissionCheckerFactory;
	protected Portal portal;
	protected RoleLocalService roleLocalService;
	protected UserLocalService userLocalService;

}