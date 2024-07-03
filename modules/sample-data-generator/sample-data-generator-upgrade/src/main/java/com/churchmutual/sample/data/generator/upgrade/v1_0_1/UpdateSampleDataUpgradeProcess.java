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

package com.churchmutual.sample.data.generator.upgrade.v1_0_1;

import com.churchmutual.content.setup.upgrade.util.common.BaseAdminUpgradeProcess;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.sample.data.generator.upgrade.util.UpgradeUsersUtil;

import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;

/**
 * @author Matthew Chan
 */
public class UpdateSampleDataUpgradeProcess extends BaseAdminUpgradeProcess {

	public UpdateSampleDataUpgradeProcess(
		CMICUserLocalService cmicUserLocalService, GroupLocalService groupLocalService,
		PermissionCheckerFactory permissionCheckerFactory, Portal portal,
		Props props, RoleLocalService roleLocalService, UserLocalService userLocalService,
		UserGroupLocalService userGroupLocalService) {

		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.cmicUserLocalService = cmicUserLocalService;
		this.groupLocalService = groupLocalService;
		this.props = props;
		this.userGroupLocalService = userGroupLocalService;
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		UpgradeUsersUtil upgradeUsersUtil = new UpgradeUsersUtil(
			cmicUserLocalService, groupLocalService, props, userGroupLocalService, userLocalService);

		upgradeUsersUtil.setDefaultAdminAsCMICEmployee(companyId);

		upgradeUsersUtil.addSampleContentManagerUser(companyId, defaultUserId);

		upgradeUsersUtil.addTestUsers(companyId, defaultUserId);
	}

	protected CMICUserLocalService cmicUserLocalService;
	protected GroupLocalService groupLocalService;
	protected Props props;
	protected UserGroupLocalService userGroupLocalService;

}