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
 * Provides the local service utility for CMICAccountEntry. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICAccountEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryLocalService
 * @generated
 */
public class CMICAccountEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAccountEntryLocalServiceUtil} to access the cmic account entry local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.churchmutual.core.model.CMICAccountEntryDisplay
			getCMICAccountEntryDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplay(cmicBusinessKey);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplays(
					java.util.List<String> recentAccounts)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplays(recentAccounts);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysByProducerCodes(
					String[] producerCodes)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysByProducerCodes(
			producerCodes);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysByUserId(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysByUserId(userId);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysByUserId(
					long userId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysByUserId(
			userId, start, end);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysByUserIdOrderedByName(
					long userId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysByUserIdOrderedByName(
			userId, start, end);
	}

	/**
	 * Use this method for quickly getting very few accounts.  This method will be fast for getting few accounts since
	 * it grabs them one by one, however, this will be slow when needed many accounts.
	 */
	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysByUserIdUnsorted(
					long userId, int count)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysByUserIdUnsorted(
			userId, count);
	}

	public static String getCMICAccountEntryDisplaysCacheStatusByUserId(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysCacheStatusByUserId(
			userId);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysWithAddresses(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysWithAddresses(userId);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getCMICAccountEntryDisplaysWithPermission(
					long userId, String actionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICAccountEntryDisplaysWithPermission(
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

	public static String getProducerName(String producerCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProducerName(producerCode);
	}

	/**
	 * Refresh the policy account summary for producer users
	 */
	public static void refreshCMICAccountEntryDisplaysByUserIdCache(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().refreshCMICAccountEntryDisplaysByUserIdCache(userId);
	}

	public static CMICAccountEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICAccountEntryLocalService, CMICAccountEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICAccountEntryLocalService.class);

		ServiceTracker
			<CMICAccountEntryLocalService, CMICAccountEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<CMICAccountEntryLocalService,
						 CMICAccountEntryLocalService>(
							 bundle.getBundleContext(),
							 CMICAccountEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}