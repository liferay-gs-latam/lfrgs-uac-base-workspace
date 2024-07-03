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
 * Provides a wrapper for {@link CMICCertificateOfInsuranceLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICCertificateOfInsuranceLocalService
 * @generated
 */
public class CMICCertificateOfInsuranceLocalServiceWrapper
	implements CMICCertificateOfInsuranceLocalService,
			   ServiceWrapper<CMICCertificateOfInsuranceLocalService> {

	public CMICCertificateOfInsuranceLocalServiceWrapper(
		CMICCertificateOfInsuranceLocalService
			cmicCertificateOfInsuranceLocalService) {

		_cmicCertificateOfInsuranceLocalService =
			cmicCertificateOfInsuranceLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICCertificateOfInsuranceLocalServiceUtil} to access the cmic certificate of insurance local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCertificateOfInsuranceLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadCOIDocument(
				String city, String fullName, String address, String address2,
				String postalCode, String state, boolean showSMLimits,
				boolean showHiredAndNonOwnedAutoLimits, String[] policyNumbers)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicCertificateOfInsuranceLocalService.downloadCOIDocument(
			city, fullName, address, address2, postalCode, state, showSMLimits,
			showHiredAndNonOwnedAutoLimits, policyNumbers);
	}

	@Override
	public com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadEOPDocument(
				com.churchmutual.core.model.CMICAdditionalInterestDisplay
					cmicAdditionalInterestDisplay,
				String policyNumber,
				com.churchmutual.core.model.CMICBuildingDisplay
					cmicBuildingDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicCertificateOfInsuranceLocalService.downloadEOPDocument(
			cmicAdditionalInterestDisplay, policyNumber, cmicBuildingDisplay);
	}

	@Override
	public com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadNYWCDocument(
				String city, String fullName, String address, String address2,
				String postalCode, String state, String policyNumber,
				String telephoneNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicCertificateOfInsuranceLocalService.downloadNYWCDocument(
			city, fullName, address, address2, postalCode, state, policyNumber,
			telephoneNumber);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicCertificateOfInsuranceLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CMICCertificateOfInsuranceLocalService getWrappedService() {
		return _cmicCertificateOfInsuranceLocalService;
	}

	@Override
	public void setWrappedService(
		CMICCertificateOfInsuranceLocalService
			cmicCertificateOfInsuranceLocalService) {

		_cmicCertificateOfInsuranceLocalService =
			cmicCertificateOfInsuranceLocalService;
	}

	private CMICCertificateOfInsuranceLocalService
		_cmicCertificateOfInsuranceLocalService;

}