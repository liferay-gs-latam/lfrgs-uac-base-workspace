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
 * Provides the remote service utility for CMICUser. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICUserServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICUserService
 * @generated
 */
public class CMICUserServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICUserServiceUtil} to access the cmic user remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static void addRecentlyViewedCMICAccount(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addRecentlyViewedCMICAccount(cmicBusinessKey);
	}

	public static void deletePortraitImage()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePortraitImage();
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICBusinessDisplay>
				getBusinessesWithPermission(String actionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessesWithPermission(actionId);
	}

	public static java.util.List<com.churchmutual.core.model.CMICUserDisplay>
			getBusinessMembers(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessMembers(cmicBusinessKey);
	}

	public static com.liferay.portal.kernel.json.JSONArray getBusinessRoles()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessRoles();
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			getBusinessUserDetails(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessUserDetails(cmicBusinessKey);
	}

	public static String[] getBusinessUserPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessUserPermissions(cmicBusinessKey);
	}

	public static String getInsuredThemeSetting(String themeSettingKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getInsuredThemeSetting(themeSettingKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static String getPortraitImageURL()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPortraitImageURL();
	}

	public static com.churchmutual.core.constants.ProducerType[]
			getProducerTypesByUserId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProducerTypesByUserId();
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getRecentlyViewedCMICAccountEntryDisplays()
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentlyViewedCMICAccountEntryDisplays();
	}

	public static com.churchmutual.core.model.CMICUserDisplay getUserDetails()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserDetails();
	}

	public static String inviteBusinessMembers(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String emailAddresses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().inviteBusinessMembers(
			cmicBusinessKey, emailAddresses);
	}

	public static void updateBusinessMemberPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateBusinessMemberPermissions(
			cmicBusinessKey, updateUserPermissionsJSONString);
	}

	public static void updateBusinessMembers(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserRolesJSONString, String removeUsersJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateBusinessMembers(
			cmicBusinessKey, updateUserRolesJSONString, removeUsersJSONString);
	}

	public static String updatePortraitImage(String imageFileString)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePortraitImage(imageFileString);
	}

	public static String validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String registrationCode,
			String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateInsuredUserRegistration(
			cmicUserId, accountNumber, registrationCode, zipCode);
	}

	public static String validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber,
			String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateProducerUserRegistration(
			cmicUserId, agentNumber, divisionNumber, registrationCode, zipCode);
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			validateUserRegistrationCode(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateUserRegistrationCode(registrationCode);
	}

	public static CMICUserService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICUserService, CMICUserService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICUserService.class);

		ServiceTracker<CMICUserService, CMICUserService> serviceTracker =
			new ServiceTracker<CMICUserService, CMICUserService>(
				bundle.getBundleContext(), CMICUserService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}