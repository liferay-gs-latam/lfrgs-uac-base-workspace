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
 * Provides a wrapper for {@link CMICOrganizationLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICOrganizationLocalService
 * @generated
 */
public class CMICOrganizationLocalServiceWrapper
	implements CMICOrganizationLocalService,
			   ServiceWrapper<CMICOrganizationLocalService> {

	public CMICOrganizationLocalServiceWrapper(
		CMICOrganizationLocalService cmicOrganizationLocalService) {

		_cmicOrganizationLocalService = cmicOrganizationLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICOrganizationLocalServiceUtil} to access the cmic organization local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICOrganizationLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICOrganizationDisplay
			fetchCMICOrganizationDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.fetchCMICOrganizationDisplay(
			cmicBusinessKey);
	}

	@Override
	public com.churchmutual.core.model.CMICOrganizationDisplay
			getCMICOrganizationDisplay(long producerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.getCMICOrganizationDisplay(
			producerId);
	}

	@Override
	public com.churchmutual.core.model.CMICOrganizationDisplay
			getCMICOrganizationDisplayByAgentNumberDivisionNumber(
				String agentNumber, String divisionNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.
			getCMICOrganizationDisplayByAgentNumberDivisionNumber(
				agentNumber, divisionNumber);
	}

	@Override
	public com.churchmutual.core.model.CMICOrganizationDisplay
			getCMICOrganizationDisplayByProducerCode(String producerCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.
			getCMICOrganizationDisplayByProducerCode(producerCode);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICOrganizationDisplay>
			getCMICOrganizationDisplaysByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.
			getCMICOrganizationDisplaysByUserId(userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICOrganizationDisplay>
			getCMICOrganizationDisplaysByUserId(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.
			getCMICOrganizationDisplaysByUserId(userId, start, end);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICOrganizationDisplay>
			getCMICOrganizationDisplaysWithPermission(
				long userId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicOrganizationLocalService.
			getCMICOrganizationDisplaysWithPermission(userId, actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicOrganizationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICOrganizationLocalService getWrappedService() {
		return _cmicOrganizationLocalService;
	}

	@Override
	public void setWrappedService(
		CMICOrganizationLocalService cmicOrganizationLocalService) {

		_cmicOrganizationLocalService = cmicOrganizationLocalService;
	}

	private CMICOrganizationLocalService _cmicOrganizationLocalService;

}