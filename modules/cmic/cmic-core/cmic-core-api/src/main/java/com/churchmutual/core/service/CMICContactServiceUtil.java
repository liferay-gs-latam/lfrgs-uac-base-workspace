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
 * Provides the remote service utility for CMICContact. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICContactServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICContactService
 * @generated
 */
public class CMICContactServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICContactServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICContactServiceUtil} to access the cmic contact remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICContactServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICContactDisplay
			fetchDirectSalesRepresentative()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchDirectSalesRepresentative();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.churchmutual.core.model.CMICContactDisplay>
			getTerritoryManagers()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTerritoryManagers();
	}

	public static CMICContactService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICContactService, CMICContactService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICContactService.class);

		ServiceTracker<CMICContactService, CMICContactService> serviceTracker =
			new ServiceTracker<CMICContactService, CMICContactService>(
				bundle.getBundleContext(), CMICContactService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}