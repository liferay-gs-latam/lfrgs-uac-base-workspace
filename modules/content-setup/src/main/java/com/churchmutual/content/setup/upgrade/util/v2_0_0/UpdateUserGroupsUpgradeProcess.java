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

package com.churchmutual.content.setup.upgrade.util.v2_0_0;

import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.commons.enums.CMICUserGroup;
import com.churchmutual.content.setup.upgrade.util.common.BaseAdminUpgradeProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Matthew Chan
 */
public class UpdateUserGroupsUpgradeProcess extends BaseAdminUpgradeProcess {

	public UpdateUserGroupsUpgradeProcess(
		GroupLocalService groupLocalService, PermissionCheckerFactory permissionCheckerFactory, Portal portal,
		RoleLocalService roleLocalService, UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.groupLocalService = groupLocalService;
		this.userGroupLocalService = userGroupLocalService;
	}

	/**
	 * Add User groups, associate user groups with Insured site
	 */
	protected void updateUserGroups() throws PortalException {
		long userId = PrincipalThreadLocal.getUserId();

		long companyId = portal.getDefaultCompanyId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		for (CMICUserGroup portalUserGroups : CMICUserGroup.values()) {
			String name = portalUserGroups.getName();

			UserGroup userGroup = userGroupLocalService.getUserGroup(companyId, name);

			if (_log.isInfoEnabled()) {
				_log.info("Added User Group with name: " + name);
			}

			Group insuredGroup = groupLocalService.getFriendlyURLGroup(
				companyId, CMICSite.INSURED.getFriendlyURL());

			userGroupLocalService.addGroupUserGroup(insuredGroup.getGroupId(), userGroup);
		}
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		updateUserGroups();
	}

	protected GroupLocalService groupLocalService;
	protected UserGroupLocalService userGroupLocalService;

	private static final Log _log = LogFactoryUtil.getLog(UpdateUserGroupsUpgradeProcess.class);

}