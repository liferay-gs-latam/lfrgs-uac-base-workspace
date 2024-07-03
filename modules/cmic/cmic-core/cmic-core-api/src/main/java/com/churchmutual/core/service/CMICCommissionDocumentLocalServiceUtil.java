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
 * Provides the local service utility for CMICCommissionDocument. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICCommissionDocumentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICCommissionDocumentLocalService
 * @generated
 */
public class CMICCommissionDocumentLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCommissionDocumentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICCommissionDocumentLocalServiceUtil} to access the cmic commission document local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCommissionDocumentLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICCommissionDocumentDisplay
			downloadDocument(String id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().downloadDocument(id);
	}

	public static com.churchmutual.core.model.CMICCommissionDocumentDisplay
		fetchCMICCommissionDocumentDisplayFromCache(String id) {

		return getService().fetchCMICCommissionDocumentDisplayFromCache(id);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICCommissionDocumentDisplay>
				getCommissionDocuments(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommissionDocuments(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CMICCommissionDocumentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICCommissionDocumentLocalService, CMICCommissionDocumentLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICCommissionDocumentLocalService.class);

		ServiceTracker
			<CMICCommissionDocumentLocalService,
			 CMICCommissionDocumentLocalService> serviceTracker =
				new ServiceTracker
					<CMICCommissionDocumentLocalService,
					 CMICCommissionDocumentLocalService>(
						 bundle.getBundleContext(),
						 CMICCommissionDocumentLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}