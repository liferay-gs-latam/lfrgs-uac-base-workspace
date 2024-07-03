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

package com.churchmutual.core.service.persistence;

import com.churchmutual.core.model.CMICPermission;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cmic permission service. This utility wraps <code>com.churchmutual.core.service.persistence.impl.CMICPermissionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICPermissionPersistence
 * @generated
 */
public class CMICPermissionUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CMICPermission cmicPermission) {
		getPersistence().clearCache(cmicPermission);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CMICPermission> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CMICPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CMICPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CMICPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CMICPermission update(CMICPermission cmicPermission) {
		return getPersistence().update(cmicPermission);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CMICPermission update(
		CMICPermission cmicPermission, ServiceContext serviceContext) {

		return getPersistence().update(cmicPermission, serviceContext);
	}

	/**
	 * Returns the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; or throws a <code>NoSuchCMICPermissionException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByU_C_A(
			long userId, String cmicBusinessKey, String actionId)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_C_A(userId, cmicBusinessKey, actionId);
	}

	/**
	 * Returns the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByU_C_A(
		long userId, String cmicBusinessKey, String actionId) {

		return getPersistence().fetchByU_C_A(userId, cmicBusinessKey, actionId);
	}

	/**
	 * Returns the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByU_C_A(
		long userId, String cmicBusinessKey, String actionId,
		boolean useFinderCache) {

		return getPersistence().fetchByU_C_A(
			userId, cmicBusinessKey, actionId, useFinderCache);
	}

	/**
	 * Removes the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the cmic permission that was removed
	 */
	public static CMICPermission removeByU_C_A(
			long userId, String cmicBusinessKey, String actionId)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().removeByU_C_A(
			userId, cmicBusinessKey, actionId);
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the number of matching cmic permissions
	 */
	public static int countByU_C_A(
		long userId, String cmicBusinessKey, String actionId) {

		return getPersistence().countByU_C_A(userId, cmicBusinessKey, actionId);
	}

	/**
	 * Returns all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @return the matching cmic permissions
	 */
	public static List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey) {

		return getPersistence().findByU_C(userId, cmicBusinessKey);
	}

	/**
	 * Returns a range of all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @return the range of matching cmic permissions
	 */
	public static List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey, int start, int end) {

		return getPersistence().findByU_C(userId, cmicBusinessKey, start, end);
	}

	/**
	 * Returns an ordered range of all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cmic permissions
	 */
	public static List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().findByU_C(
			userId, cmicBusinessKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cmic permissions
	 */
	public static List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_C(
			userId, cmicBusinessKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByU_C_First(
			long userId, String cmicBusinessKey,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_C_First(
			userId, cmicBusinessKey, orderByComparator);
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByU_C_First(
		long userId, String cmicBusinessKey,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().fetchByU_C_First(
			userId, cmicBusinessKey, orderByComparator);
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByU_C_Last(
			long userId, String cmicBusinessKey,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_C_Last(
			userId, cmicBusinessKey, orderByComparator);
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByU_C_Last(
		long userId, String cmicBusinessKey,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().fetchByU_C_Last(
			userId, cmicBusinessKey, orderByComparator);
	}

	/**
	 * Returns the cmic permissions before and after the current cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param cmicPermissionPK the primary key of the current cmic permission
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cmic permission
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	public static CMICPermission[] findByU_C_PrevAndNext(
			CMICPermissionPK cmicPermissionPK, long userId,
			String cmicBusinessKey,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_C_PrevAndNext(
			cmicPermissionPK, userId, cmicBusinessKey, orderByComparator);
	}

	/**
	 * Removes all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 */
	public static void removeByU_C(long userId, String cmicBusinessKey) {
		getPersistence().removeByU_C(userId, cmicBusinessKey);
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @return the number of matching cmic permissions
	 */
	public static int countByU_C(long userId, String cmicBusinessKey) {
		return getPersistence().countByU_C(userId, cmicBusinessKey);
	}

	/**
	 * Returns all the cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @return the matching cmic permissions
	 */
	public static List<CMICPermission> findByU_A(long userId, String actionId) {
		return getPersistence().findByU_A(userId, actionId);
	}

	/**
	 * Returns a range of all the cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @return the range of matching cmic permissions
	 */
	public static List<CMICPermission> findByU_A(
		long userId, String actionId, int start, int end) {

		return getPersistence().findByU_A(userId, actionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cmic permissions
	 */
	public static List<CMICPermission> findByU_A(
		long userId, String actionId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().findByU_A(
			userId, actionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cmic permissions
	 */
	public static List<CMICPermission> findByU_A(
		long userId, String actionId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_A(
			userId, actionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByU_A_First(
			long userId, String actionId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_A_First(
			userId, actionId, orderByComparator);
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByU_A_First(
		long userId, String actionId,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().fetchByU_A_First(
			userId, actionId, orderByComparator);
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByU_A_Last(
			long userId, String actionId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_A_Last(
			userId, actionId, orderByComparator);
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByU_A_Last(
		long userId, String actionId,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().fetchByU_A_Last(
			userId, actionId, orderByComparator);
	}

	/**
	 * Returns the cmic permissions before and after the current cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param cmicPermissionPK the primary key of the current cmic permission
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cmic permission
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	public static CMICPermission[] findByU_A_PrevAndNext(
			CMICPermissionPK cmicPermissionPK, long userId, String actionId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByU_A_PrevAndNext(
			cmicPermissionPK, userId, actionId, orderByComparator);
	}

	/**
	 * Removes all the cmic permissions where userId = &#63; and actionId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 */
	public static void removeByU_A(long userId, String actionId) {
		getPersistence().removeByU_A(userId, actionId);
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @return the number of matching cmic permissions
	 */
	public static int countByU_A(long userId, String actionId) {
		return getPersistence().countByU_A(userId, actionId);
	}

	/**
	 * Returns all the cmic permissions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching cmic permissions
	 */
	public static List<CMICPermission> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the cmic permissions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @return the range of matching cmic permissions
	 */
	public static List<CMICPermission> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the cmic permissions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cmic permissions
	 */
	public static List<CMICPermission> findByUserId(
		long userId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cmic permissions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cmic permissions
	 */
	public static List<CMICPermission> findByUserId(
		long userId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByUserId_First(
			long userId, OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByUserId_First(
		long userId, OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	public static CMICPermission findByUserId_Last(
			long userId, OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	public static CMICPermission fetchByUserId_Last(
		long userId, OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the cmic permissions before and after the current cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param cmicPermissionPK the primary key of the current cmic permission
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cmic permission
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	public static CMICPermission[] findByUserId_PrevAndNext(
			CMICPermissionPK cmicPermissionPK, long userId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByUserId_PrevAndNext(
			cmicPermissionPK, userId, orderByComparator);
	}

	/**
	 * Removes all the cmic permissions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching cmic permissions
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Caches the cmic permission in the entity cache if it is enabled.
	 *
	 * @param cmicPermission the cmic permission
	 */
	public static void cacheResult(CMICPermission cmicPermission) {
		getPersistence().cacheResult(cmicPermission);
	}

	/**
	 * Caches the cmic permissions in the entity cache if it is enabled.
	 *
	 * @param cmicPermissions the cmic permissions
	 */
	public static void cacheResult(List<CMICPermission> cmicPermissions) {
		getPersistence().cacheResult(cmicPermissions);
	}

	/**
	 * Creates a new cmic permission with the primary key. Does not add the cmic permission to the database.
	 *
	 * @param cmicPermissionPK the primary key for the new cmic permission
	 * @return the new cmic permission
	 */
	public static CMICPermission create(CMICPermissionPK cmicPermissionPK) {
		return getPersistence().create(cmicPermissionPK);
	}

	/**
	 * Removes the cmic permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission that was removed
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	public static CMICPermission remove(CMICPermissionPK cmicPermissionPK)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().remove(cmicPermissionPK);
	}

	public static CMICPermission updateImpl(CMICPermission cmicPermission) {
		return getPersistence().updateImpl(cmicPermission);
	}

	/**
	 * Returns the cmic permission with the primary key or throws a <code>NoSuchCMICPermissionException</code> if it could not be found.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	public static CMICPermission findByPrimaryKey(
			CMICPermissionPK cmicPermissionPK)
		throws com.churchmutual.core.exception.NoSuchCMICPermissionException {

		return getPersistence().findByPrimaryKey(cmicPermissionPK);
	}

	/**
	 * Returns the cmic permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission, or <code>null</code> if a cmic permission with the primary key could not be found
	 */
	public static CMICPermission fetchByPrimaryKey(
		CMICPermissionPK cmicPermissionPK) {

		return getPersistence().fetchByPrimaryKey(cmicPermissionPK);
	}

	/**
	 * Returns all the cmic permissions.
	 *
	 * @return the cmic permissions
	 */
	public static List<CMICPermission> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cmic permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @return the range of cmic permissions
	 */
	public static List<CMICPermission> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cmic permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cmic permissions
	 */
	public static List<CMICPermission> findAll(
		int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cmic permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic permissions
	 * @param end the upper bound of the range of cmic permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cmic permissions
	 */
	public static List<CMICPermission> findAll(
		int start, int end, OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cmic permissions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cmic permissions.
	 *
	 * @return the number of cmic permissions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getCompoundPKColumnNames() {
		return getPersistence().getCompoundPKColumnNames();
	}

	public static CMICPermissionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICPermissionPersistence, CMICPermissionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICPermissionPersistence.class);

		ServiceTracker<CMICPermissionPersistence, CMICPermissionPersistence>
			serviceTracker =
				new ServiceTracker
					<CMICPermissionPersistence, CMICPermissionPersistence>(
						bundle.getBundleContext(),
						CMICPermissionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}