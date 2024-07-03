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

package com.churchmutual.core.service.impl;

import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICPermission;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.core.service.base.CMICPermissionServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic permission remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICPermissionService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICPermissionServiceBaseImpl
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICPermission"},
	service = AopService.class
)
public class CMICPermissionServiceImpl extends CMICPermissionServiceBaseImpl {

	public void addDefaultPermissions(CMICBusinessKey cmicBusinessKey) throws PortalException {
		cmicPermissionLocalService.addDefaultPermissions(getUserId(), cmicBusinessKey);
	}

	public void addOwnerPermissions(CMICBusinessKey cmicBusinessKey) throws PortalException {
		cmicPermissionLocalService.addOwnerPermissions(getUserId(), cmicBusinessKey);
	}

	public CMICPermission addPermission(CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		return cmicPermissionLocalService.addPermission(getUserId(), cmicBusinessKey, actionId);
	}

	public void checkPermission(CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		cmicUserLocalService.updateBusinessPermissions(getUserId(), cmicBusinessKey);

		cmicPermissionLocalService.checkPermission(getUserId(), cmicBusinessKey, actionId);
	}

	public void deletePermission(CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		cmicPermissionLocalService.deletePermission(getUserId(), cmicBusinessKey, actionId);
	}

	public boolean hasPermission(CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		cmicUserLocalService.updateBusinessPermissions(getUserId(), cmicBusinessKey);

		return cmicPermissionLocalService.hasPermission(getUserId(), cmicBusinessKey, actionId);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		cmicUserLocalService.updateBusinessPermissions(getUserId());

		return cmicPermissionLocalService.hasPermission(getUserId(), actionId);
	}

	@Reference
	protected CMICUserLocalService cmicUserLocalService;

}