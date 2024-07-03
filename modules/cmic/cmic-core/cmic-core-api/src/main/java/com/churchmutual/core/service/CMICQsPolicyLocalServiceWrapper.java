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

import com.churchmutual.core.model.*;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CMICQsPolicyLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyLocalService
 * @generated
 */
public class CMICQsPolicyLocalServiceWrapper
	implements CMICQsPolicyLocalService,
			   ServiceWrapper<CMICQsPolicyLocalService> {

	public CMICQsPolicyLocalServiceWrapper(
		CMICQsPolicyLocalService cmicQsPolicyLocalService) {

		_cmicQsPolicyLocalService = cmicQsPolicyLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>CMICQsPolicyLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CMICQsPolicyLocalServiceUtil</code>.
	 */
	@Override
	public CMICPolicyDocumentDisplay downloadPolicyTransaction(
			String accountNumber, String policyNumber, String policyType,
			int sequenceNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.downloadPolicyTransaction(
			accountNumber, policyNumber, policyType, sequenceNumber);
	}

	@Override
	public java.util.List<CMICAdditionalInterestDisplay>
			getAdditionalInterestsByBuildings(
				Long additionalInterestTypeReferenceId, String policyNumber,
				String buildingNumber, String locationPremisesNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getAdditionalInterestsByBuildings(
			additionalInterestTypeReferenceId, policyNumber, buildingNumber,
			locationPremisesNumber);
	}

	@Override
	public java.util.List<CMICBuildingDisplay> getBuildingsByPolicy(
			String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getBuildingsByPolicy(policyNumber);
	}

	@Override
	public java.util.List<CMICEligiblePolicyDisplay> getCOIEligiblePolicies(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getCOIEligiblePolicies(userId);
	}

	@Override
	public java.util.List<CMICEligiblePolicyDisplay> getEOPEligiblePolicies(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getEOPEligiblePolicies(userId);
	}

	@Override
	public java.util.List<String> getNYWCDisplay(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getNYWCDisplay(userId);
	}

	@Override
	public java.util.List<String> getNYWCEligiblePolicies(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getNYWCEligiblePolicies(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicQsPolicyLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICPolicyDisplay getPolicyByPolicyNumber(String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);
	}

	@Override
	public java.util.List<CMICPolicyDisplay> getPolicyDisplays(
			CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getPolicyDisplays(cmicBusinessKey);
	}

	@Override
	public java.util.List<CMICPolicyDisplay> getPolicyDisplaysByUserId(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getPolicyDisplaysByUserId(userId);
	}

	@Override
	public String getPolicyProductName(String policyType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getPolicyProductName(policyType);
	}

	@Override
	public java.util.List<CMICPolicyDisplay> getRecentPolicyDisplays(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyLocalService.getRecentPolicyDisplays(userId);
	}

	@Override
	public CMICQsPolicyLocalService getWrappedService() {
		return _cmicQsPolicyLocalService;
	}

	@Override
	public void setWrappedService(
		CMICQsPolicyLocalService cmicQsPolicyLocalService) {

		_cmicQsPolicyLocalService = cmicQsPolicyLocalService;
	}

	private CMICQsPolicyLocalService _cmicQsPolicyLocalService;

}