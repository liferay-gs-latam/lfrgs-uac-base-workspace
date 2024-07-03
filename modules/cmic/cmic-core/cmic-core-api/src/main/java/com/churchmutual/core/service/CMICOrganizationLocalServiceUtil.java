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
 * Provides the local service utility for CMICOrganization. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICOrganizationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICOrganizationLocalService
 * @generated
 */
public class CMICOrganizationLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICOrganizationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICOrganizationLocalServiceUtil} to access the cmic organization local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICOrganizationLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICOrganizationDisplay
			fetchCMICOrganizationDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCMICOrganizationDisplay(cmicBusinessKey);
	}

	public static com.churchmutual.core.model.CMICOrganizationDisplay
			getCMICOrganizationDisplay(long producerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICOrganizationDisplay(producerId);
	}

	public static com.churchmutual.core.model.CMICOrganizationDisplay
			getCMICOrganizationDisplayByAgentNumberDivisionNumber(
				String agentNumber, String divisionNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().
			getCMICOrganizationDisplayByAgentNumberDivisionNumber(
				agentNumber, divisionNumber);
	}

	public static com.churchmutual.core.model.CMICOrganizationDisplay
			getCMICOrganizationDisplayByProducerCode(String producerCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICOrganizationDisplayByProducerCode(
			producerCode);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICOrganizationDisplay>
				getCMICOrganizationDisplaysByUserId(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICOrganizationDisplaysByUserId(userId);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICOrganizationDisplay>
				getCMICOrganizationDisplaysByUserId(
					long userId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICOrganizationDisplaysByUserId(
			userId, start, end);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICOrganizationDisplay>
				getCMICOrganizationDisplaysWithPermission(
					long userId, String actionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICOrganizationDisplaysWithPermission(
			userId, actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CMICOrganizationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICOrganizationLocalService, CMICOrganizationLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICOrganizationLocalService.class);

		ServiceTracker
			<CMICOrganizationLocalService, CMICOrganizationLocalService>
				serviceTracker =
					new ServiceTracker
						<CMICOrganizationLocalService,
						 CMICOrganizationLocalService>(
							 bundle.getBundleContext(),
							 CMICOrganizationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}