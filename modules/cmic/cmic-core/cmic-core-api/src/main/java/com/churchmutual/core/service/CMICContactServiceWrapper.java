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
 * Provides a wrapper for {@link CMICContactService}.
 *
 * @author Kayleen Lim
 * @see CMICContactService
 * @generated
 */
public class CMICContactServiceWrapper
	implements CMICContactService, ServiceWrapper<CMICContactService> {

	public CMICContactServiceWrapper(CMICContactService cmicContactService) {
		_cmicContactService = cmicContactService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICContactServiceUtil} to access the cmic contact remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICContactServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICContactDisplay
			fetchDirectSalesRepresentative()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicContactService.fetchDirectSalesRepresentative();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicContactService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICContactDisplay>
			getTerritoryManagers()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicContactService.getTerritoryManagers();
	}

	@Override
	public CMICContactService getWrappedService() {
		return _cmicContactService;
	}

	@Override
	public void setWrappedService(CMICContactService cmicContactService) {
		_cmicContactService = cmicContactService;
	}

	private CMICContactService _cmicContactService;

}