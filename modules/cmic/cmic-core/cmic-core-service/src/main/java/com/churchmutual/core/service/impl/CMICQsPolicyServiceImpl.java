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

import com.churchmutual.core.constants.CMICPermissionAction;
import com.churchmutual.core.model.*;
import com.churchmutual.core.service.CMICPermissionService;
import com.churchmutual.core.service.base.CMICQsPolicyServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * The implementation of the cmic qs policy remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICQsPolicyService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=cmic",
		"json.web.service.context.path=CMICQsPolicy"
	},
	service = AopService.class
)
public class CMICQsPolicyServiceImpl extends CMICQsPolicyServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.churchmutual.core.service.CMICQsPolicyServiceUtil</code> to access the cmic qs policy remote service.
	 */
	@Override
	public CMICPolicyDocumentDisplay downloadPolicyTransaction(
			String accountNumber, String policyNumber, String policyType, int sequenceNumber)
			throws PortalException {

		CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

		cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
				CMICPermissionAction.VIEW_POLICY_DOCUMENTS.getActionId());

		return cmicQsPolicyLocalService.downloadPolicyTransaction(
				accountNumber, policyNumber, policyType, sequenceNumber);
	}

	@Override
	public List<CMICAdditionalInterestDisplay> getAdditionalInterestsByBuildings(
			Long additionalInterestTypeReferenceId, String policyNumber, String buildingNumber, String locationPremisesNumber)
			throws PortalException {

		if (Validator.isBlank(policyNumber)) {
			return null;
		}

		CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

		cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
				CMICPermissionAction.VIEW.getActionId());

		return cmicQsPolicyLocalService.getAdditionalInterestsByBuildings(
				additionalInterestTypeReferenceId, policyNumber, buildingNumber, locationPremisesNumber);
	}

	@Override
	public List<CMICBuildingDisplay> getBuildingsByPolicy(String policyNumber) throws PortalException {
		if (Validator.isBlank(policyNumber)) {
			return null;
		}

		CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

		cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
				CMICPermissionAction.VIEW.getActionId());

		return cmicQsPolicyLocalService.getBuildingsByPolicy(policyNumber);
	}

	@Override
	public List<CMICEligiblePolicyDisplay> getCOIEligiblePolicies() throws PortalException {
		return cmicQsPolicyLocalService.getCOIEligiblePolicies(getUserId());
	}

	@Override
	public List<String> getNYWCEligiblePolicies() throws PortalException {
		return cmicQsPolicyLocalService.getNYWCEligiblePolicies(getUserId());
	}

	@Override
	public List<String> getNYWCDisplay() throws PortalException{
		return cmicQsPolicyLocalService.getNYWCDisplay(getUserId());
	}

	@Override
	public List<CMICEligiblePolicyDisplay> getEOPEligiblePolicies() throws PortalException {
		return cmicQsPolicyLocalService.getEOPEligiblePolicies(getUserId());
	}

	@Override
	public CMICPolicyDisplay getPolicyByPolicyNumber(String policyNumber) throws PortalException {
		if (Validator.isBlank(policyNumber)) {
			return null;
		}

		CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

		cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
				CMICPermissionAction.VIEW.getActionId());

		return cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);
	}

	@Override
	public List<CMICPolicyDisplay> getPolicyDisplays(CMICBusinessKey cmicBusinessKey) throws PortalException {
		cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicBusinessKey.getAccountNumber(), cmicBusinessKey.getCompanyNumber()),
				CMICPermissionAction.VIEW.getActionId());

		return cmicQsPolicyLocalService.getPolicyDisplays(cmicBusinessKey);
	}

	@Override
	public List<CMICPolicyDisplay> getPolicyDisplaysByUserId() throws PortalException {
		return cmicQsPolicyLocalService.getPolicyDisplaysByUserId(getUserId());
	}

	@Override
	public String getPolicyProductName(String policyType) throws PortalException {
		return cmicQsPolicyLocalService.getPolicyProductName(policyType);
	}

	@Override
	public List<CMICPolicyDisplay> getRecentPolicyDisplays() throws PortalException {
		return cmicQsPolicyLocalService.getRecentPolicyDisplays(getUserId());
	}
	@Reference
	protected CMICPermissionService cmicPermissionService;
}