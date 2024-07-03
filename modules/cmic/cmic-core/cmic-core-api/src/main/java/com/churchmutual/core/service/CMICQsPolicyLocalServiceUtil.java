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
 * Provides the local service utility for CMICQsPolicy. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICQsPolicyLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyLocalService
 * @generated
 */
public class CMICQsPolicyLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICQsPolicyLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>CMICQsPolicyLocalService</code> via injection or a <code>ServiceTracker</code> or use <code>CMICQsPolicyLocalServiceUtil</code>.
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
			getCOIEligiblePolicies(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCOIEligiblePolicies(userId);
	}

	public static java.util.List<CMICEligiblePolicyDisplay>
			getEOPEligiblePolicies(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEOPEligiblePolicies(userId);
	}

	public static java.util.List<String> getNYWCDisplay(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNYWCDisplay(userId);
	}

	public static java.util.List<String> getNYWCEligiblePolicies(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNYWCEligiblePolicies(userId);
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

	public static java.util.List<CMICPolicyDisplay> getPolicyDisplaysByUserId(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPolicyDisplaysByUserId(userId);
	}

	public static String getPolicyProductName(String policyType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPolicyProductName(policyType);
	}

	public static java.util.List<CMICPolicyDisplay> getRecentPolicyDisplays(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentPolicyDisplays(userId);
	}

	public static CMICQsPolicyLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICQsPolicyLocalService, CMICQsPolicyLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICQsPolicyLocalService.class);

		ServiceTracker<CMICQsPolicyLocalService, CMICQsPolicyLocalService>
			serviceTracker =
				new ServiceTracker
					<CMICQsPolicyLocalService, CMICQsPolicyLocalService>(
						bundle.getBundleContext(),
						CMICQsPolicyLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}