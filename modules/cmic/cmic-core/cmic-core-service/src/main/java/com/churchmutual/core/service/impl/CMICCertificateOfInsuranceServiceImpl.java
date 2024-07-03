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
import com.churchmutual.core.service.CMICQsPolicyLocalService;
import com.churchmutual.core.service.base.CMICCertificateOfInsuranceServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic certificate of insurance remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICCertificateOfInsuranceService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Matthew Chan
 * @see CMICCertificateOfInsuranceServiceBaseImpl
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICCertificateOfInsurance"},
	service = AopService.class
)
public class CMICCertificateOfInsuranceServiceImpl extends CMICCertificateOfInsuranceServiceBaseImpl {

	@Override
	public CMICPDFDocumentDisplay downloadCOIDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			boolean showSMLimits, boolean showHiredAndNonOwnedAutoLimits, String[] policyNumbers)
		throws PortalException {

		for (String policyNumber : policyNumbers) {
			CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

			cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
				CMICPermissionAction.REQUEST_COI_EOP.getActionId());
		}

		return cmicCertificateOfInsuranceLocalService.downloadCOIDocument(
			city, fullName, address, address2, postalCode, state, showSMLimits, showHiredAndNonOwnedAutoLimits,
			policyNumbers);
	}
	
	@Override
	public CMICPDFDocumentDisplay downloadNYWCDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			String policyNumber, String telephoneNumber)
		throws PortalException {

		
			CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

			cmicPermissionService.checkPermission(
				new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
				CMICPermissionAction.REQUEST_COI_EOP.getActionId());
		

		return cmicCertificateOfInsuranceLocalService.downloadNYWCDocument(
			city, fullName, address, address2, postalCode, state,
			policyNumber,telephoneNumber);
	}

	@Override
	public CMICPDFDocumentDisplay downloadEOPDocument(
			CMICAdditionalInterestDisplay cmicAdditionalInterestDisplay, String policyNumber,
			CMICBuildingDisplay cmicBuildingDisplay)
		throws PortalException {

		CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

		cmicPermissionService.checkPermission(
			new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
			CMICPermissionAction.REQUEST_COI_EOP.getActionId());

		return cmicCertificateOfInsuranceLocalService.downloadEOPDocument(
			cmicAdditionalInterestDisplay, policyNumber, cmicBuildingDisplay);
	}

	@Reference
	protected CMICPermissionService cmicPermissionService;

//	@Reference
//	protected CMICPolicyLocalService cmicPolicyLocalService;

	@Reference
	protected CMICQsPolicyLocalService cmicQsPolicyLocalService;

}