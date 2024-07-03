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

import com.churchmutual.core.service.CMICPermissionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CMICPermissionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.churchmutual.core.model.CMICPermissionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.churchmutual.core.model.CMICPermission</code>, that is translated to a
 * <code>com.churchmutual.core.model.CMICPermissionSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
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
 * @see CMICPermissionServiceHttp
 * @generated
 */
public class CMICPermissionServiceSoap {

	public static void addDefaultPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			CMICPermissionServiceUtil.addDefaultPermissions(cmicBusinessKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addOwnerPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			CMICPermissionServiceUtil.addOwnerPermissions(cmicBusinessKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICPermissionSoap addPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws RemoteException {

		try {
			com.churchmutual.core.model.CMICPermission returnValue =
				CMICPermissionServiceUtil.addPermission(
					cmicBusinessKey, actionId);

			return com.churchmutual.core.model.CMICPermissionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws RemoteException {

		try {
			CMICPermissionServiceUtil.checkPermission(
				cmicBusinessKey, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws RemoteException {

		try {
			CMICPermissionServiceUtil.deletePermission(
				cmicBusinessKey, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws RemoteException {

		try {
			boolean returnValue = CMICPermissionServiceUtil.hasPermission(
				cmicBusinessKey, actionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasPermission(String actionId)
		throws RemoteException {

		try {
			boolean returnValue = CMICPermissionServiceUtil.hasPermission(
				actionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CMICPermissionServiceSoap.class);

}