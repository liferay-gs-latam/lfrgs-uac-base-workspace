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

package com.churchmutual.sample.data.generator.upgrade;

import com.churchmutual.core.service.CMICPermissionLocalService;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.sample.data.generator.upgrade.v1_0_0.AddSampleDataUpgradeProcess;

import com.churchmutual.sample.data.generator.upgrade.v1_0_1.UpdateAdminUserPermissionsUpgradeProcess;
import com.churchmutual.sample.data.generator.upgrade.v1_0_1.UpdateSampleDataUpgradeProcess;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SampleDataUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new AddSampleDataUpgradeProcess(
				cmicUserLocalService, groupLocalService, permissionCheckerFactory, portal, props,
				roleLocalService, userLocalService, userGroupLocalService));

		registry.register(
			"1.0.0", "1.0.1",
			new UpdateSampleDataUpgradeProcess(
				cmicUserLocalService, groupLocalService, permissionCheckerFactory, portal, props,
				roleLocalService, userLocalService, userGroupLocalService),
			new UpdateAdminUserPermissionsUpgradeProcess(
				cmicPermissionLocalService, cmicUserLocalService, groupLocalService, permissionCheckerFactory, portal,
				roleLocalService, userLocalService));

		registry.register(
			"1.0.1", "1.0.2",
			new com.churchmutual.sample.data.generator.upgrade.v1_0_2.UpdateSampleDataUpgradeProcess(
				cmicUserLocalService, groupLocalService, permissionCheckerFactory, portal, props,
				roleLocalService, userLocalService, userGroupLocalService));
	}

	@Reference
	protected CMICPermissionLocalService cmicPermissionLocalService;

	@Reference
	protected CMICUserLocalService cmicUserLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected PermissionCheckerFactory permissionCheckerFactory;

	@Reference
	protected Portal portal;

	@Reference
	protected Props props;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.churchmutual.content.setup)(release.schema.version>=2.0.0))",
		unbind = "-"
	)
	protected Release release;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserGroupLocalService userGroupLocalService;

	@Reference
	protected UserLocalService userLocalService;

}