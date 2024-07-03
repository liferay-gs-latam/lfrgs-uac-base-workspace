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
 * Provides a wrapper for {@link CMICAccountEntryService}.
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryService
 * @generated
 */
public class CMICAccountEntryServiceWrapper
	implements CMICAccountEntryService,
			   ServiceWrapper<CMICAccountEntryService> {

	public CMICAccountEntryServiceWrapper(
		CMICAccountEntryService cmicAccountEntryService) {

		_cmicAccountEntryService = cmicAccountEntryService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAccountEntryServiceUtil} to access the cmic account entry remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICAccountEntryDisplay
			getCMICAccountEntryDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryService.getCMICAccountEntryDisplay(
			cmicBusinessKey);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryService.getCMICAccountEntryDisplays();
	}

	@Override
	public String getCMICAccountEntryDisplaysCacheStatus()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryService.
			getCMICAccountEntryDisplaysCacheStatus();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysWithAddresses()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryService.
			getCMICAccountEntryDisplaysWithAddresses();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysWithPermission(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryService.
			getCMICAccountEntryDisplaysWithPermission(actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicAccountEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public void refreshCMICAccountEntryDisplaysCache()
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAccountEntryService.refreshCMICAccountEntryDisplaysCache();
	}

	@Override
	public CMICAccountEntryService getWrappedService() {
		return _cmicAccountEntryService;
	}

	@Override
	public void setWrappedService(
		CMICAccountEntryService cmicAccountEntryService) {

		_cmicAccountEntryService = cmicAccountEntryService;
	}

	private CMICAccountEntryService _cmicAccountEntryService;

}