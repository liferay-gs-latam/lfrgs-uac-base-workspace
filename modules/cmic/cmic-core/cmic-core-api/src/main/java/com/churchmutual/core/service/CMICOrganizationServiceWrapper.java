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
 * Provides a wrapper for {@link CMICOrganizationService}.
 *
 * @author Kayleen Lim
 * @see CMICOrganizationService
 * @generated
 */
public class CMICOrganizationServiceWrapper
	implements CMICOrganizationService,
			   ServiceWrapper<CMICOrganizationService> {

	public CMICOrganizationServiceWrapper(
		CMICOrganizationService cmicOrganizationService) {

		_cmicOrganizationService = cmicOrganizationService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICOrganizationServiceUtil} to access the cmic organization remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICOrganizationServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICOrganizationDisplay
			fetchCMICOrganizationDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationService.fetchCMICOrganizationDisplay(
			cmicBusinessKey);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICOrganizationDisplay>
			getCMICOrganizationDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationService.getCMICOrganizationDisplays();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicOrganizationService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICOrganizationService getWrappedService() {
		return _cmicOrganizationService;
	}

	@Override
	public void setWrappedService(
		CMICOrganizationService cmicOrganizationService) {

		_cmicOrganizationService = cmicOrganizationService;
	}

	private CMICOrganizationService _cmicOrganizationService;

}