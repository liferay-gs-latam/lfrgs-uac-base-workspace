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

import com.churchmutual.core.service.CMICCertificateOfInsuranceServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CMICCertificateOfInsuranceServiceUtil</code> service
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
 * @see CMICCertificateOfInsuranceServiceSoap
 * @generated
 */
public class CMICCertificateOfInsuranceServiceHttp {

	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadCOIDocument(
				HttpPrincipal httpPrincipal, String city, String fullName,
				String address, String address2, String postalCode,
				String state, boolean showSMLimits,
				boolean showHiredAndNonOwnedAutoLimits, String[] policyNumbers)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICCertificateOfInsuranceServiceUtil.class,
				"downloadCOIDocument", _downloadCOIDocumentParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, city, fullName, address, address2, postalCode, state,
				showSMLimits, showHiredAndNonOwnedAutoLimits, policyNumbers);

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

			return (com.churchmutual.core.model.CMICPDFDocumentDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadNYWCDocument(
				HttpPrincipal httpPrincipal, String city, String fullName,
				String address, String address2, String postalCode,
				String state, String policyNumber, String telephoneNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICCertificateOfInsuranceServiceUtil.class,
				"downloadNYWCDocument", _downloadNYWCDocumentParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, city, fullName, address, address2, postalCode, state,
				policyNumber, telephoneNumber);

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

			return (com.churchmutual.core.model.CMICPDFDocumentDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			downloadEOPDocument(
				HttpPrincipal httpPrincipal,
				com.churchmutual.core.model.CMICAdditionalInterestDisplay
					cmicAdditionalInterestDisplay,
				String policyNumber,
				com.churchmutual.core.model.CMICBuildingDisplay
					cmicBuildingDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICCertificateOfInsuranceServiceUtil.class,
				"downloadEOPDocument", _downloadEOPDocumentParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicAdditionalInterestDisplay, policyNumber,
				cmicBuildingDisplay);

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

			return (com.churchmutual.core.model.CMICPDFDocumentDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CMICCertificateOfInsuranceServiceHttp.class);

	private static final Class<?>[] _downloadCOIDocumentParameterTypes0 =
		new Class[] {
			String.class, String.class, String.class, String.class,
			String.class, String.class, boolean.class, boolean.class,
			String[].class
		};
	private static final Class<?>[] _downloadNYWCDocumentParameterTypes1 =
		new Class[] {
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _downloadEOPDocumentParameterTypes2 =
		new Class[] {
			com.churchmutual.core.model.CMICAdditionalInterestDisplay.class,
			String.class, com.churchmutual.core.model.CMICBuildingDisplay.class
		};

}