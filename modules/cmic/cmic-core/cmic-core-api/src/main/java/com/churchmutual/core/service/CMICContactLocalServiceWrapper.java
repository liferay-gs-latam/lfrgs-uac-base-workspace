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
 * Provides a wrapper for {@link CMICContactLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICContactLocalService
 * @generated
 */
public class CMICContactLocalServiceWrapper
	implements CMICContactLocalService,
			   ServiceWrapper<CMICContactLocalService> {

	public CMICContactLocalServiceWrapper(
		CMICContactLocalService cmicContactLocalService) {

		_cmicContactLocalService = cmicContactLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICContactLocalServiceUtil} to access the cmic contact local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICContactLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICContactDisplay
			fetchDirectSalesRepresentative(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicContactLocalService.fetchDirectSalesRepresentative(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicContactLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICContactDisplay>
			getTerritoryManagers(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicContactLocalService.getTerritoryManagers(userId);
	}

	@Override
	public CMICContactLocalService getWrappedService() {
		return _cmicContactLocalService;
	}

	@Override
	public void setWrappedService(
		CMICContactLocalService cmicContactLocalService) {

		_cmicContactLocalService = cmicContactLocalService;
	}

	private CMICContactLocalService _cmicContactLocalService;

}