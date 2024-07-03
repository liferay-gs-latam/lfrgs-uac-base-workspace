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
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CMICQsPolicy. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICQsPolicyServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyService
 * @generated
 */
public class CMICQsPolicyServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICQsPolicyServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>CMICQsPolicyServiceUtil</code> to access the cmic qs policy remote service.
	 */
	public static CMICPolicyDocumentDisplay downloadPolicyTransaction(
			String accountNumber, String policyNumber, String policyType,
			int sequenceNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().downloadPolicyTransaction(
			accountNumber, policyNumber, policyType, sequenceNumber);
	}

	public static java.util.List<CMICAdditionalInterestDisplay>
			getAdditionalInterestsByBuildings(
				Long additionalInterestTypeReferenceId, String policyNumber,
				String buildingNumber, String locationPremisesNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAdditionalInterestsByBuildings(
			additionalInterestTypeReferenceId, policyNumber, buildingNumber,
			locationPremisesNumber);
	}

	public static java.util.List<CMICBuildingDisplay> getBuildingsByPolicy(
			String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBuildingsByPolicy(policyNumber);
	}

	public static java.util.List<CMICEligiblePolicyDisplay>
			getCOIEligiblePolicies()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCOIEligiblePolicies();
	}

	public static java.util.List<CMICEligiblePolicyDisplay>
			getEOPEligiblePolicies()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEOPEligiblePolicies();
	}

	public static java.util.List<String> getNYWCDisplay()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNYWCDisplay();
	}

	public static java.util.List<String> getNYWCEligiblePolicies()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNYWCEligiblePolicies();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CMICPolicyDisplay getPolicyByPolicyNumber(String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPolicyByPolicyNumber(policyNumber);
	}

	public static java.util.List<CMICPolicyDisplay> getPolicyDisplays(
			CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPolicyDisplays(cmicBusinessKey);
	}

	public static java.util.List<CMICPolicyDisplay> getPolicyDisplaysByUserId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPolicyDisplaysByUserId();
	}

	public static String getPolicyProductName(String policyType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPolicyProductName(policyType);
	}

	public static java.util.List<CMICPolicyDisplay> getRecentPolicyDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentPolicyDisplays();
	}

	public static CMICQsPolicyService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICQsPolicyService, CMICQsPolicyService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICQsPolicyService.class);

		ServiceTracker<CMICQsPolicyService, CMICQsPolicyService>
			serviceTracker =
				new ServiceTracker<CMICQsPolicyService, CMICQsPolicyService>(
					bundle.getBundleContext(), CMICQsPolicyService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}