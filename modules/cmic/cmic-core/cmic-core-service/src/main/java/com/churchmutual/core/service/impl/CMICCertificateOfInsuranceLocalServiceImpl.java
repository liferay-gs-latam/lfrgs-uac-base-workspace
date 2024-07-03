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

import com.churchmutual.core.model.CMICAdditionalInterestDisplay;
import com.churchmutual.core.model.CMICBuildingDisplay;
import com.churchmutual.core.model.CMICPDFDocumentDisplay;
import com.churchmutual.core.service.base.CMICCertificateOfInsuranceLocalServiceBaseImpl;
import com.churchmutual.rest.CertificateOfInsuranceWebService;
import com.churchmutual.rest.model.CMICAdditionalInterestDTO;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic certificate of insurance local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICCertificateOfInsuranceLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthew Chan
 * @see CMICCertificateOfInsuranceLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.churchmutual.core.model.CMICCertificateOfInsurance", service = AopService.class
)
public class CMICCertificateOfInsuranceLocalServiceImpl extends CMICCertificateOfInsuranceLocalServiceBaseImpl {

	@Override
	public CMICPDFDocumentDisplay downloadCOIDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			boolean showSMLimits, boolean showHiredAndNonOwnedAutoLimits, String[] policyNumbers)
		throws PortalException {

		boolean editable = false;
		boolean rentalAutoCertificate = false;
		boolean performUpload = true;

		String COIDocument = certificateOfInsuranceWebService.downloadCOIDocument(
			city, fullName, address, address2, postalCode, state, showSMLimits, showHiredAndNonOwnedAutoLimits,
			rentalAutoCertificate, editable, performUpload, policyNumbers);

		return new CMICPDFDocumentDisplay(COIDocument);
	}

	public CMICPDFDocumentDisplay downloadNYWCDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			String policyNumber, String telephoneNumber)
		throws PortalException {

		boolean performUpload = true;

		String NYWCDocument = certificateOfInsuranceWebService.downloadNYWCDocument(
			city, fullName, address, address2, postalCode, state,
			 performUpload, policyNumber, telephoneNumber);

		return new CMICPDFDocumentDisplay(NYWCDocument);
	}
	
	@Override
	public CMICPDFDocumentDisplay downloadEOPDocument(
			CMICAdditionalInterestDisplay cmicAdditionalInterestDisplay, String policyNumber,
			CMICBuildingDisplay cmicBuildingDisplay)
		throws PortalException {

		boolean editable = false;
		boolean performUpload = true;

		CMICAdditionalInterestDTO cmicAdditionalInterestDTO = null;

		if (cmicAdditionalInterestDisplay != null) {
			cmicAdditionalInterestDTO = new CMICAdditionalInterestDTO().fromJSONObject(cmicAdditionalInterestDisplay.toJSONObject());
		}

		String EOPDocument = certificateOfInsuranceWebService.downloadEOPDocument(
			cmicAdditionalInterestDTO, policyNumber, editable, performUpload, cmicBuildingDisplay.getBuildingNumber(),
			cmicBuildingDisplay.getLocationPremisesNumber());

		return new CMICPDFDocumentDisplay(EOPDocument);
	}

	@Reference
	protected CertificateOfInsuranceWebService certificateOfInsuranceWebService;

	@Reference
	protected JSONFactory jsonFactory;

}