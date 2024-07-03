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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CMICQsPolicyServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyServiceSoap
 * @generated
 */
public class CMICQsPolicyServiceHttp {

	public static CMICPolicyDocumentDisplay downloadPolicyTransaction(
			HttpPrincipal httpPrincipal, String accountNumber,
			String policyNumber, String policyType, int sequenceNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "downloadPolicyTransaction",
				_downloadPolicyTransactionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountNumber, policyNumber, policyType,
				sequenceNumber);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (CMICPolicyDocumentDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICAdditionalInterestDisplay>
			getAdditionalInterestsByBuildings(
				HttpPrincipal httpPrincipal,
				Long additionalInterestTypeReferenceId, String policyNumber,
				String buildingNumber, String locationPremisesNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class,
				"getAdditionalInterestsByBuildings",
				_getAdditionalInterestsByBuildingsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, additionalInterestTypeReferenceId, policyNumber,
				buildingNumber, locationPremisesNumber);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICAdditionalInterestDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICBuildingDisplay> getBuildingsByPolicy(
			HttpPrincipal httpPrincipal, String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getBuildingsByPolicy",
				_getBuildingsByPolicyParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, policyNumber);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICBuildingDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICEligiblePolicyDisplay>
			getCOIEligiblePolicies(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getCOIEligiblePolicies",
				_getCOIEligiblePoliciesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICEligiblePolicyDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<String> getNYWCEligiblePolicies(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getNYWCEligiblePolicies",
				_getNYWCEligiblePoliciesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<String>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<String> getNYWCDisplay(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getNYWCDisplay",
				_getNYWCDisplayParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<String>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICEligiblePolicyDisplay>
			getEOPEligiblePolicies(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getEOPEligiblePolicies",
				_getEOPEligiblePoliciesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICEligiblePolicyDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static CMICPolicyDisplay getPolicyByPolicyNumber(
			HttpPrincipal httpPrincipal, String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getPolicyByPolicyNumber",
				_getPolicyByPolicyNumberParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, policyNumber);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (CMICPolicyDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICPolicyDisplay> getPolicyDisplays(
			HttpPrincipal httpPrincipal, CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getPolicyDisplays",
				_getPolicyDisplaysParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICPolicyDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICPolicyDisplay> getPolicyDisplaysByUserId(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getPolicyDisplaysByUserId",
				_getPolicyDisplaysByUserIdParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICPolicyDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getPolicyProductName(
			HttpPrincipal httpPrincipal, String policyType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getPolicyProductName",
				_getPolicyProductNameParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, policyType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<CMICPolicyDisplay> getRecentPolicyDisplays(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICQsPolicyServiceUtil.class, "getRecentPolicyDisplays",
				_getRecentPolicyDisplaysParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<CMICPolicyDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CMICQsPolicyServiceHttp.class);

	private static final Class<?>[] _downloadPolicyTransactionParameterTypes0 =
		new Class[] {String.class, String.class, String.class, int.class};
	private static final Class<?>[]
		_getAdditionalInterestsByBuildingsParameterTypes1 = new Class[] {
			Long.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _getBuildingsByPolicyParameterTypes2 =
		new Class[] {String.class};
	private static final Class<?>[] _getCOIEligiblePoliciesParameterTypes3 =
		new Class[] {};
	private static final Class<?>[] _getNYWCEligiblePoliciesParameterTypes4 =
		new Class[] {};
	private static final Class<?>[] _getNYWCDisplayParameterTypes5 =
		new Class[] {};
	private static final Class<?>[] _getEOPEligiblePoliciesParameterTypes6 =
		new Class[] {};
	private static final Class<?>[] _getPolicyByPolicyNumberParameterTypes7 =
		new Class[] {String.class};
	private static final Class<?>[] _getPolicyDisplaysParameterTypes8 =
		new Class[] {CMICBusinessKey.class};
	private static final Class<?>[] _getPolicyDisplaysByUserIdParameterTypes9 =
		new Class[] {};
	private static final Class<?>[] _getPolicyProductNameParameterTypes10 =
		new Class[] {String.class};
	private static final Class<?>[] _getRecentPolicyDisplaysParameterTypes11 =
		new Class[] {};

}