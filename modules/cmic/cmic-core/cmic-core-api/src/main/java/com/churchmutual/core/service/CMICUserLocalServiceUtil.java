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
 * Provides the local service utility for CMICUser. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICUserLocalService
 * @generated
 */
public class CMICUserLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cmic user to the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUser the cmic user
	 * @return the cmic user that was added
	 */
	public static com.churchmutual.core.model.CMICUser addCMICUser(
		com.churchmutual.core.model.CMICUser cmicUser) {

		return getService().addCMICUser(cmicUser);
	}

	public static void addCMICUser(long cmicUserId, long userId) {
		getService().addCMICUser(cmicUserId, userId);
	}

	public static void addRecentlyViewedCMICAccount(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addRecentlyViewedCMICAccount(userId, cmicBusinessKey);
	}

	/**
	 * Creates a new cmic user with the primary key. Does not add the cmic user to the database.
	 *
	 * @param cmicUserId the primary key for the new cmic user
	 * @return the new cmic user
	 */
	public static com.churchmutual.core.model.CMICUser createCMICUser(
		long cmicUserId) {

		return getService().createCMICUser(cmicUserId);
	}

	/**
	 * Deletes the cmic user from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUser the cmic user
	 * @return the cmic user that was removed
	 */
	public static com.churchmutual.core.model.CMICUser deleteCMICUser(
		com.churchmutual.core.model.CMICUser cmicUser) {

		return getService().deleteCMICUser(cmicUser);
	}

	/**
	 * Deletes the cmic user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user that was removed
	 * @throws PortalException if a cmic user with the primary key could not be found
	 */
	public static com.churchmutual.core.model.CMICUser deleteCMICUser(
			long cmicUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCMICUser(cmicUserId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deletePortraitImage(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePortraitImage(userId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.churchmutual.core.model.CMICUser fetchCMICUser(
		long cmicUserId) {

		return getService().fetchCMICUser(cmicUserId);
	}

	public static com.churchmutual.core.model.CMICUser fetchCMICUserByUserId(
		long userId) {

		return getService().fetchCMICUserByUserId(userId);
	}

	public static com.liferay.portal.kernel.model.User fetchUserByCmicUUID(
			long companyId, String cmicUUID)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchUserByCmicUUID(companyId, cmicUUID);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICBusinessDisplay>
				getBusinessesWithPermission(long userId, String actionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessesWithPermission(userId, actionId);
	}

	public static java.util.List<com.churchmutual.core.model.CMICUserDisplay>
			getBusinessMembers(
				long userId,
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessMembers(userId, cmicBusinessKey);
	}

	public static com.liferay.portal.kernel.json.JSONArray getBusinessRoles(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessRoles(userId);
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			getBusinessUserDetails(
				long userId,
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessUserDetails(userId, cmicBusinessKey);
	}

	public static String[] getBusinessUserPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessUserPermissions(userId, cmicBusinessKey);
	}

	/**
	 * Returns the cmic user with the primary key.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user
	 * @throws PortalException if a cmic user with the primary key could not be found
	 */
	public static com.churchmutual.core.model.CMICUser getCMICUser(
			long cmicUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICUser(cmicUserId);
	}

	/**
	 * Returns a range of all the cmic users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic users
	 * @param end the upper bound of the range of cmic users (not inclusive)
	 * @return the range of cmic users
	 */
	public static java.util.List<com.churchmutual.core.model.CMICUser>
		getCMICUsers(int start, int end) {

		return getService().getCMICUsers(start, end);
	}

	/**
	 * Returns the number of cmic users.
	 *
	 * @return the number of cmic users
	 */
	public static int getCMICUsersCount() {
		return getService().getCMICUsersCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static String getInsuredThemeSetting(
			long userId, String themeSettingKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getInsuredThemeSetting(userId, themeSettingKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static String getPortraitImageURL(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPortraitImageURL(userId);
	}

	public static com.churchmutual.core.constants.ProducerType[]
			getProducerTypesByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProducerTypesByUserId(userId);
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getRecentlyViewedCMICAccountEntryDisplays(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentlyViewedCMICAccountEntryDisplays(userId);
	}

	public static com.churchmutual.core.model.CMICUserDisplay getUserDetails(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserDetails(userId);
	}

	public static String inviteBusinessMembers(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String emailAddresses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().inviteBusinessMembers(
			userId, cmicBusinessKey, emailAddresses);
	}

	public static boolean isDeactivated(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().isDeactivated(userId);
	}

	public static boolean isUserRegistered(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().isUserRegistered(userId);
	}

	public static void updateBusinessMemberPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateBusinessMemberPermissions(
			userId, cmicBusinessKey, updateUserPermissionsJSONString);
	}

	public static void updateBusinessMembers(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserRolesJSONString, String removeUsersJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateBusinessMembers(
			userId, cmicBusinessKey, updateUserRolesJSONString,
			removeUsersJSONString);
	}

	public static void updateBusinessPermissions(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateBusinessPermissions(userId);
	}

	public static void updateBusinessPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateBusinessPermissions(userId, cmicBusinessKey);
	}

	/**
	 * Updates the cmic user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUser the cmic user
	 * @return the cmic user that was updated
	 */
	public static com.churchmutual.core.model.CMICUser updateCMICUser(
		com.churchmutual.core.model.CMICUser cmicUser) {

		return getService().updateCMICUser(cmicUser);
	}

	public static String updatePortraitImage(
			long userId, String imageFileString)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePortraitImage(userId, imageFileString);
	}

	public static String validateInsuredUserRegistration(
			long userId, long cmicUserId, String accountNumber,
			String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateInsuredUserRegistration(
			userId, cmicUserId, accountNumber, registrationCode, zipCode);
	}

	public static String validateProducerUserRegistration(
			long userId, long cmicUserId, String agentNumber,
			String divisionNumber, String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateProducerUserRegistration(
			userId, cmicUserId, agentNumber, divisionNumber, registrationCode,
			zipCode);
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			validateUserRegistrationCode(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateUserRegistrationCode(registrationCode);
	}

	public static CMICUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICUserLocalService, CMICUserLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICUserLocalService.class);

		ServiceTracker<CMICUserLocalService, CMICUserLocalService>
			serviceTracker =
				new ServiceTracker<CMICUserLocalService, CMICUserLocalService>(
					bundle.getBundleContext(), CMICUserLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}