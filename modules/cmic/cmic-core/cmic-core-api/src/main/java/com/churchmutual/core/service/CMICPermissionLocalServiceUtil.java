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
 * Provides the local service utility for CMICPermission. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICPermissionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICPermissionLocalService
 * @generated
 */
public class CMICPermissionLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICPermissionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cmic permission to the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicPermission the cmic permission
	 * @return the cmic permission that was added
	 */
	public static com.churchmutual.core.model.CMICPermission addCMICPermission(
		com.churchmutual.core.model.CMICPermission cmicPermission) {

		return getService().addCMICPermission(cmicPermission);
	}

	public static void addDefaultPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addDefaultPermissions(userId, cmicBusinessKey);
	}

	public static void addOwnerPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addOwnerPermissions(userId, cmicBusinessKey);
	}

	public static com.churchmutual.core.model.CMICPermission addPermission(
		long userId,
		com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
		String actionId) {

		return getService().addPermission(userId, cmicBusinessKey, actionId);
	}

	public static void checkPermission(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkPermission(userId, cmicBusinessKey, actionId);
	}

	/**
	 * Creates a new cmic permission with the primary key. Does not add the cmic permission to the database.
	 *
	 * @param cmicPermissionPK the primary key for the new cmic permission
	 * @return the new cmic permission
	 */
	public static com.churchmutual.core.model.CMICPermission
		createCMICPermission(
			com.churchmutual.core.service.persistence.CMICPermissionPK
				cmicPermissionPK) {

		return getService().createCMICPermission(cmicPermissionPK);
	}

	/**
	 * Deletes the cmic permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicPermission the cmic permission
	 * @return the cmic permission that was removed
	 */
	public static com.churchmutual.core.model.CMICPermission
		deleteCMICPermission(
			com.churchmutual.core.model.CMICPermission cmicPermission) {

		return getService().deleteCMICPermission(cmicPermission);
	}

	/**
	 * Deletes the cmic permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission that was removed
	 * @throws PortalException if a cmic permission with the primary key could not be found
	 */
	public static com.churchmutual.core.model.CMICPermission
			deleteCMICPermission(
				com.churchmutual.core.service.persistence.CMICPermissionPK
					cmicPermissionPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCMICPermission(cmicPermissionPK);
	}

	public static void deletePermission(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePermission(userId, cmicBusinessKey, actionId);
	}

	public static void deletePermissions(
		long userId,
		com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey) {

		getService().deletePermissions(userId, cmicBusinessKey);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.churchmutual.core.model.CMICPermission
		fetchCMICPermission(
			com.churchmutual.core.service.persistence.CMICPermissionPK
				cmicPermissionPK) {

		return getService().fetchCMICPermission(cmicPermissionPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cmic permission with the primary key.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission
	 * @throws PortalException if a cmic permission with the primary key could not be found
	 */
	public static com.churchmutual.core.model.CMICPermission getCMICPermission(
			com.churchmutual.core.service.persistence.CMICPermissionPK
				cmicPermissionPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICPermission(cmicPermissionPK);
	}

	/**
	 * Returns a range of all the cmic permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @return the range of cmic permissions
	 */
	public static java.util.List<com.churchmutual.core.model.CMICPermission>
		getCMICPermissions(int start, int end) {

		return getService().getCMICPermissions(start, end);
	}

	/**
	 * Returns the number of cmic permissions.
	 *
	 * @return the number of cmic permissions
	 */
	public static int getCMICPermissionsCount() {
		return getService().getCMICPermissionsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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

	public static boolean hasPermission(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasPermission(userId, cmicBusinessKey, actionId);
	}

	public static boolean hasPermission(long userId, String actionId) {
		return getService().hasPermission(userId, actionId);
	}

	public static boolean isUserProducerForAccount(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().isUserProducerForAccount(userId, cmicBusinessKey);
	}

	/**
	 * Updates the cmic permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param cmicPermission the cmic permission
	 * @return the cmic permission that was updated
	 */
	public static com.churchmutual.core.model.CMICPermission
		updateCMICPermission(
			com.churchmutual.core.model.CMICPermission cmicPermission) {

		return getService().updateCMICPermission(cmicPermission);
	}

	public static CMICPermissionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICPermissionLocalService, CMICPermissionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICPermissionLocalService.class);

		ServiceTracker<CMICPermissionLocalService, CMICPermissionLocalService>
			serviceTracker =
				new ServiceTracker
					<CMICPermissionLocalService, CMICPermissionLocalService>(
						bundle.getBundleContext(),
						CMICPermissionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}