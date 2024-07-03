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
 * Provides the remote service utility for CMICAccountEntry. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICAccountEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryService
 * @generated
 */
public class CMICAccountEntryServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAccountEntryServiceUtil} to access the cmic account entry remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICAccountEntryDisplay
			getCMICAccountEntryDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplay(cmicBusinessKey);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplays()
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplays();
	}

	public static String getCMICAccountEntryDisplaysCacheStatus()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysCacheStatus();
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysWithAddresses()
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysWithAddresses();
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysWithPermission(String actionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysWithPermission(actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void refreshCMICAccountEntryDisplaysCache()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().refreshCMICAccountEntryDisplaysCache();
	}

	public static CMICAccountEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICAccountEntryService, CMICAccountEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICAccountEntryService.class);

		ServiceTracker<CMICAccountEntryService, CMICAccountEntryService>
			serviceTracker =
				new ServiceTracker
					<CMICAccountEntryService, CMICAccountEntryService>(
						bundle.getBundleContext(),
						CMICAccountEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}