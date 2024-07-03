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
 * Provides the local service utility for CMICLossRun. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICLossRunLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICLossRunLocalService
 * @generated
 */
public class CMICLossRunLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICLossRunLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICLossRunLocalServiceUtil} to access the cmic loss run local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICLossRunLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICPDFDocumentDisplay
			generateLossRunsReport(
				long userId,
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
				String requestReason)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().generateLossRunsReport(
			userId, cmicBusinessKey, requestReason);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CMICLossRunLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICLossRunLocalService, CMICLossRunLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICLossRunLocalService.class);

		ServiceTracker<CMICLossRunLocalService, CMICLossRunLocalService>
			serviceTracker =
				new ServiceTracker
					<CMICLossRunLocalService, CMICLossRunLocalService>(
						bundle.getBundleContext(),
						CMICLossRunLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}