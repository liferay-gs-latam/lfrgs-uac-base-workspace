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
 * Provides a wrapper for {@link CMICQsPolicyService}.
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyService
 * @generated
 */
public class CMICQsPolicyServiceWrapper
	implements CMICQsPolicyService, ServiceWrapper<CMICQsPolicyService> {

	public CMICQsPolicyServiceWrapper(CMICQsPolicyService cmicQsPolicyService) {
		_cmicQsPolicyService = cmicQsPolicyService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>CMICQsPolicyServiceUtil</code> to access the cmic qs policy remote service.
	 */
	@Override
	public CMICPolicyDocumentDisplay downloadPolicyTransaction(
			String accountNumber, String policyNumber, String policyType,
			int sequenceNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.downloadPolicyTransaction(
			accountNumber, policyNumber, policyType, sequenceNumber);
	}

	@Override
	public java.util.List<CMICAdditionalInterestDisplay>
			getAdditionalInterestsByBuildings(
				Long additionalInterestTypeReferenceId, String policyNumber,
				String buildingNumber, String locationPremisesNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getAdditionalInterestsByBuildings(
			additionalInterestTypeReferenceId, policyNumber, buildingNumber,
			locationPremisesNumber);
	}

	@Override
	public java.util.List<CMICBuildingDisplay> getBuildingsByPolicy(
			String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getBuildingsByPolicy(policyNumber);
	}

	@Override
	public java.util.List<CMICEligiblePolicyDisplay> getCOIEligiblePolicies()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getCOIEligiblePolicies();
	}

	@Override
	public java.util.List<CMICEligiblePolicyDisplay> getEOPEligiblePolicies()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getEOPEligiblePolicies();
	}

	@Override
	public java.util.List<String> getNYWCDisplay()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getNYWCDisplay();
	}

	@Override
	public java.util.List<String> getNYWCEligiblePolicies()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getNYWCEligiblePolicies();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicQsPolicyService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICPolicyDisplay getPolicyByPolicyNumber(String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getPolicyByPolicyNumber(policyNumber);
	}

	@Override
	public java.util.List<CMICPolicyDisplay> getPolicyDisplays(
			CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getPolicyDisplays(cmicBusinessKey);
	}

	@Override
	public java.util.List<CMICPolicyDisplay> getPolicyDisplaysByUserId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getPolicyDisplaysByUserId();
	}

	@Override
	public String getPolicyProductName(String policyType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getPolicyProductName(policyType);
	}

	@Override
	public java.util.List<CMICPolicyDisplay> getRecentPolicyDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicQsPolicyService.getRecentPolicyDisplays();
	}

	@Override
	public CMICQsPolicyService getWrappedService() {
		return _cmicQsPolicyService;
	}

	@Override
	public void setWrappedService(CMICQsPolicyService cmicQsPolicyService) {
		_cmicQsPolicyService = cmicQsPolicyService;
	}

	private CMICQsPolicyService _cmicQsPolicyService;

}