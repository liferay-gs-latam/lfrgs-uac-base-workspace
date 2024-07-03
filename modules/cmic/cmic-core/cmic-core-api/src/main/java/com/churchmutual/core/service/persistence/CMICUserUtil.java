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

import com.churchmutual.core.model.CMICUser;

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
 * The persistence utility for the cmic user service. This utility wraps <code>com.churchmutual.core.service.persistence.impl.CMICUserPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICUserPersistence
 * @generated
 */
public class CMICUserUtil {

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
	public static void clearCache(CMICUser cmicUser) {
		getPersistence().clearCache(cmicUser);
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
	public static Map<Serializable, CMICUser> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CMICUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CMICUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CMICUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CMICUser> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CMICUser update(CMICUser cmicUser) {
		return getPersistence().update(cmicUser);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CMICUser update(
		CMICUser cmicUser, ServiceContext serviceContext) {

		return getPersistence().update(cmicUser, serviceContext);
	}

	/**
	 * Returns the cmic user where userId = &#63; or throws a <code>NoSuchCMICUserException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching cmic user
	 * @throws NoSuchCMICUserException if a matching cmic user could not be found
	 */
	public static CMICUser findByUserId(long userId)
		throws com.churchmutual.core.exception.NoSuchCMICUserException {

		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns the cmic user where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching cmic user, or <code>null</code> if a matching cmic user could not be found
	 */
	public static CMICUser fetchByUserId(long userId) {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	 * Returns the cmic user where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic user, or <code>null</code> if a matching cmic user could not be found
	 */
	public static CMICUser fetchByUserId(long userId, boolean useFinderCache) {
		return getPersistence().fetchByUserId(userId, useFinderCache);
	}

	/**
	 * Removes the cmic user where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the cmic user that was removed
	 */
	public static CMICUser removeByUserId(long userId)
		throws com.churchmutual.core.exception.NoSuchCMICUserException {

		return getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of cmic users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching cmic users
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Caches the cmic user in the entity cache if it is enabled.
	 *
	 * @param cmicUser the cmic user
	 */
	public static void cacheResult(CMICUser cmicUser) {
		getPersistence().cacheResult(cmicUser);
	}

	/**
	 * Caches the cmic users in the entity cache if it is enabled.
	 *
	 * @param cmicUsers the cmic users
	 */
	public static void cacheResult(List<CMICUser> cmicUsers) {
		getPersistence().cacheResult(cmicUsers);
	}

	/**
	 * Creates a new cmic user with the primary key. Does not add the cmic user to the database.
	 *
	 * @param cmicUserId the primary key for the new cmic user
	 * @return the new cmic user
	 */
	public static CMICUser create(long cmicUserId) {
		return getPersistence().create(cmicUserId);
	}

	/**
	 * Removes the cmic user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user that was removed
	 * @throws NoSuchCMICUserException if a cmic user with the primary key could not be found
	 */
	public static CMICUser remove(long cmicUserId)
		throws com.churchmutual.core.exception.NoSuchCMICUserException {

		return getPersistence().remove(cmicUserId);
	}

	public static CMICUser updateImpl(CMICUser cmicUser) {
		return getPersistence().updateImpl(cmicUser);
	}

	/**
	 * Returns the cmic user with the primary key or throws a <code>NoSuchCMICUserException</code> if it could not be found.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user
	 * @throws NoSuchCMICUserException if a cmic user with the primary key could not be found
	 */
	public static CMICUser findByPrimaryKey(long cmicUserId)
		throws com.churchmutual.core.exception.NoSuchCMICUserException {

		return getPersistence().findByPrimaryKey(cmicUserId);
	}

	/**
	 * Returns the cmic user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user, or <code>null</code> if a cmic user with the primary key could not be found
	 */
	public static CMICUser fetchByPrimaryKey(long cmicUserId) {
		return getPersistence().fetchByPrimaryKey(cmicUserId);
	}

	/**
	 * Returns all the cmic users.
	 *
	 * @return the cmic users
	 */
	public static List<CMICUser> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cmic users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic users
	 * @param end the upper bound of the range of cmic users (not inclusive)
	 * @return the range of cmic users
	 */
	public static List<CMICUser> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cmic users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic users
	 * @param end the upper bound of the range of cmic users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cmic users
	 */
	public static List<CMICUser> findAll(
		int start, int end, OrderByComparator<CMICUser> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cmic users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic users
	 * @param end the upper bound of the range of cmic users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cmic users
	 */
	public static List<CMICUser> findAll(
		int start, int end, OrderByComparator<CMICUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cmic users from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cmic users.
	 *
	 * @return the number of cmic users
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CMICUserPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICUserPersistence, CMICUserPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICUserPersistence.class);

		ServiceTracker<CMICUserPersistence, CMICUserPersistence>
			serviceTracker =
				new ServiceTracker<CMICUserPersistence, CMICUserPersistence>(
					bundle.getBundleContext(), CMICUserPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}