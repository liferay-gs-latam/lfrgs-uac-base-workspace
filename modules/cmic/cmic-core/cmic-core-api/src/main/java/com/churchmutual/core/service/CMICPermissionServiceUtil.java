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
 * Provides the remote service utility for CMICPermission. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICPermissionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICPermissionService
 * @generated
 */
public class CMICPermissionServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICPermissionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICPermissionServiceUtil} to access the cmic permission remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICPermissionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static void addDefaultPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addDefaultPermissions(cmicBusinessKey);
	}

	public static void addOwnerPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addOwnerPermissions(cmicBusinessKey);
	}

	public static com.churchmutual.core.model.CMICPermission addPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPermission(cmicBusinessKey, actionId);
	}

	public static void checkPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkPermission(cmicBusinessKey, actionId);
	}

	public static void deletePermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePermission(cmicBusinessKey, actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean hasPermission(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasPermission(cmicBusinessKey, actionId);
	}

	public static boolean hasPermission(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasPermission(actionId);
	}

	public static CMICPermissionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICPermissionService, CMICPermissionService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICPermissionService.class);

		ServiceTracker<CMICPermissionService, CMICPermissionService>
			serviceTracker =
				new ServiceTracker
					<CMICPermissionService, CMICPermissionService>(
						bundle.getBundleContext(), CMICPermissionService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}