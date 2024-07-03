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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CMICCertificateOfInsurance. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICCertificateOfInsuranceServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICCertificateOfInsuranceService
 * @generated
 */
public class CMICCertificateOfInsuranceServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCertificateOfInsuranceServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICCertificateOfInsuranceServiceUtil} to access the cmic certificate of insurance remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCertificateOfInsuranceServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadCOIDocument(
				String city, String fullName, String address, String address2,
				String postalCode, String state, boolean showSMLimits,
				boolean showHiredAndNonOwnedAutoLimits, String[] policyNumbers)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().downloadCOIDocument(
			city, fullName, address, address2, postalCode, state, showSMLimits,
			showHiredAndNonOwnedAutoLimits, policyNumbers);
	}

	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadEOPDocument(
				com.churchmutual.core.model.CMICAdditionalInterestDisplay
					cmicAdditionalInterestDisplay,
				String policyNumber,
				com.churchmutual.core.model.CMICBuildingDisplay
					cmicBuildingDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().downloadEOPDocument(
			cmicAdditionalInterestDisplay, policyNumber, cmicBuildingDisplay);
	}

	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadNYWCDocument(
				String city, String fullName, String address, String address2,
				String postalCode, String state, String policyNumber,
				String telephoneNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().downloadNYWCDocument(
			city, fullName, address, address2, postalCode, state, policyNumber,
			telephoneNumber);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CMICCertificateOfInsuranceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICCertificateOfInsuranceService, CMICCertificateOfInsuranceService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICCertificateOfInsuranceService.class);

		ServiceTracker
			<CMICCertificateOfInsuranceService,
			 CMICCertificateOfInsuranceService> serviceTracker =
				new ServiceTracker
					<CMICCertificateOfInsuranceService,
					 CMICCertificateOfInsuranceService>(
						 bundle.getBundleContext(),
						 CMICCertificateOfInsuranceService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}