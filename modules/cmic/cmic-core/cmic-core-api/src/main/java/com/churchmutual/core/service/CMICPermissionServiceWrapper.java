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

package com.churchmutual.core.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CMICPermissionService}.
 *
 * @author Kayleen Lim
 * @see CMICPermissionService
 * @generated
 */
public class CMICPermissionServiceWrapper
	implements CMICPermissionService, ServiceWrapper<CMICPermissionService> {

	public CMICPermissionServiceWrapper(
		CMICPermissionService cmicPermissionService) {

		_cmicPermissionService = cmicPermissionService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICPermissionServiceUtil} to access the cmic permission remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICPermissionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void addDefaultPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicPermissionService.addDefaultPermissions(cmicBusinessKey);
	}

	@Override
	public void addOwnerPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicPermissionService.addOwnerPermissions(cmicBusinessKey);
	}

	@Override
	public com.churchmutual.core.model.CMICPermission addPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicPermissionService.addPermission(cmicBusinessKey, actionId);
	}

	@Override
	public void checkPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicPermissionService.checkPermission(cmicBusinessKey, actionId);
	}

	@Override
	public void deletePermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicPermissionService.deletePermission(cmicBusinessKey, actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicPermissionService.getOSGiServiceIdentifier();
	}

	@Override
	public boolean hasPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicPermissionService.hasPermission(cmicBusinessKey, actionId);
	}

	@Override
	public boolean hasPermission(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicPermissionService.hasPermission(actionId);
	}

	@Override
	public CMICPermissionService getWrappedService() {
		return _cmicPermissionService;
	}

	@Override
	public void setWrappedService(CMICPermissionService cmicPermissionService) {
		_cmicPermissionService = cmicPermissionService;
	}

	private CMICPermissionService _cmicPermissionService;

}