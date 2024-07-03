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

package com.churchmutual.core.service.http;

import com.churchmutual.core.model.*;
import com.churchmutual.core.service.CMICQsPolicyServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CMICQsPolicyServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyServiceHttp
 * @generated
 */
public class CMICQsPolicyServiceSoap {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>CMICQsPolicyServiceUtil</code> to access the cmic qs policy remote service.
	 */
	public static CMICPolicyDocumentDisplay downloadPolicyTransaction(
			String accountNumber, String policyNumber, String policyType,
			int sequenceNumber)
		throws RemoteException {

		try {
			CMICPolicyDocumentDisplay returnValue =
				CMICQsPolicyServiceUtil.downloadPolicyTransaction(
					accountNumber, policyNumber, policyType, sequenceNumber);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICAdditionalInterestDisplay[]
			getAdditionalInterestsByBuildings(
				Long additionalInterestTypeReferenceId, String policyNumber,
				String buildingNumber, String locationPremisesNumber)
		throws RemoteException {

		try {
			java.util.List<CMICAdditionalInterestDisplay> returnValue =
				CMICQsPolicyServiceUtil.getAdditionalInterestsByBuildings(
					additionalInterestTypeReferenceId, policyNumber,
					buildingNumber, locationPremisesNumber);

			return returnValue.toArray(
				new CMICAdditionalInterestDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICBuildingDisplay[] getBuildingsByPolicy(
			String policyNumber)
		throws RemoteException {

		try {
			java.util.List<CMICBuildingDisplay> returnValue =
				CMICQsPolicyServiceUtil.getBuildingsByPolicy(policyNumber);

			return returnValue.toArray(
				new CMICBuildingDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICEligiblePolicyDisplay[] getCOIEligiblePolicies()
		throws RemoteException {

		try {
			java.util.List<CMICEligiblePolicyDisplay> returnValue =
				CMICQsPolicyServiceUtil.getCOIEligiblePolicies();

			return returnValue.toArray(
				new CMICEligiblePolicyDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String[] getNYWCEligiblePolicies() throws RemoteException {
		try {
			java.util.List<String> returnValue =
				CMICQsPolicyServiceUtil.getNYWCEligiblePolicies();

			return returnValue.toArray(new String[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String[] getNYWCDisplay() throws RemoteException {
		try {
			java.util.List<String> returnValue =
				CMICQsPolicyServiceUtil.getNYWCDisplay();

			return returnValue.toArray(new String[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICEligiblePolicyDisplay[] getEOPEligiblePolicies()
		throws RemoteException {

		try {
			java.util.List<CMICEligiblePolicyDisplay> returnValue =
				CMICQsPolicyServiceUtil.getEOPEligiblePolicies();

			return returnValue.toArray(
				new CMICEligiblePolicyDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICPolicyDisplay getPolicyByPolicyNumber(String policyNumber)
		throws RemoteException {

		try {
			CMICPolicyDisplay returnValue =
				CMICQsPolicyServiceUtil.getPolicyByPolicyNumber(policyNumber);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICPolicyDisplay[] getPolicyDisplays(
			CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			java.util.List<CMICPolicyDisplay> returnValue =
				CMICQsPolicyServiceUtil.getPolicyDisplays(cmicBusinessKey);

			return returnValue.toArray(
				new CMICPolicyDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICPolicyDisplay[] getPolicyDisplaysByUserId()
		throws RemoteException {

		try {
			java.util.List<CMICPolicyDisplay> returnValue =
				CMICQsPolicyServiceUtil.getPolicyDisplaysByUserId();

			return returnValue.toArray(
				new CMICPolicyDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String getPolicyProductName(String policyType)
		throws RemoteException {

		try {
			String returnValue = CMICQsPolicyServiceUtil.getPolicyProductName(
				policyType);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static CMICPolicyDisplay[] getRecentPolicyDisplays()
		throws RemoteException {

		try {
			java.util.List<CMICPolicyDisplay> returnValue =
				CMICQsPolicyServiceUtil.getRecentPolicyDisplays();

			return returnValue.toArray(
				new CMICPolicyDisplay[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CMICQsPolicyServiceSoap.class);

}