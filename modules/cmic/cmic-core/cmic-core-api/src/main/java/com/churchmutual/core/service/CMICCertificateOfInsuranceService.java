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

import com.churchmutual.core.model.CMICAdditionalInterestDisplay;
import com.churchmutual.core.model.CMICBuildingDisplay;
import com.churchmutual.core.model.CMICPDFDocumentDisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CMICCertificateOfInsurance. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICCertificateOfInsuranceServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CMICCertificateOfInsuranceService extends BaseService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICCertificateOfInsuranceServiceUtil} to access the cmic certificate of insurance remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCertificateOfInsuranceServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CMICPDFDocumentDisplay downloadCOIDocument(
			String city, String fullName, String address, String address2,
			String postalCode, String state, boolean showSMLimits,
			boolean showHiredAndNonOwnedAutoLimits, String[] policyNumbers)
		throws PortalException;

	public CMICPDFDocumentDisplay downloadEOPDocument(
			CMICAdditionalInterestDisplay cmicAdditionalInterestDisplay,
			String policyNumber, CMICBuildingDisplay cmicBuildingDisplay)
		throws PortalException;

	public CMICPDFDocumentDisplay downloadNYWCDocument(
			String city, String fullName, String address, String address2,
			String postalCode, String state, String policyNumber,
			String telephoneNumber)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}