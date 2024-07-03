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

package com.churchmutual.core.service.persistence.impl;

import com.churchmutual.core.exception.NoSuchCMICUserException;
import com.churchmutual.core.model.CMICUser;
import com.churchmutual.core.model.impl.CMICUserImpl;
import com.churchmutual.core.model.impl.CMICUserModelImpl;
import com.churchmutual.core.service.persistence.CMICUserPersistence;
import com.churchmutual.core.service.persistence.impl.constants.cmicPersistenceConstants;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cmic user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @generated
 */
@Component(service = CMICUserPersistence.class)
public class CMICUserPersistenceImpl
	extends BasePersistenceImpl<CMICUser> implements CMICUserPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CMICUserUtil</code> to access the cmic user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CMICUserImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns the cmic user where userId = &#63; or throws a <code>NoSuchCMICUserException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching cmic user
	 * @throws NoSuchCMICUserException if a matching cmic user could not be found
	 */
	@Override
	public CMICUser findByUserId(long userId) throws NoSuchCMICUserException {
		CMICUser cmicUser = fetchByUserId(userId);

		if (cmicUser == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCMICUserException(msg.toString());
		}

		return cmicUser;
	}

	/**
	 * Returns the cmic user where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching cmic user, or <code>null</code> if a matching cmic user could not be found
	 */
	@Override
	public CMICUser fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the cmic user where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic user, or <code>null</code> if a matching cmic user could not be found
	 */
	@Override
	public CMICUser fetchByUserId(long userId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUserId, finderArgs, this);
		}

		if (result instanceof CMICUser) {
			CMICUser cmicUser = (CMICUser)result;

			if ((userId != cmicUser.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CMICUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<CMICUser> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUserId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {userId};
							}

							_log.warn(
								"CMICUserPersistenceImpl.fetchByUserId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CMICUser cmicUser = list.get(0);

					result = cmicUser;

					cacheResult(cmicUser);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUserId, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CMICUser)result;
		}
	}

	/**
	 * Removes the cmic user where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the cmic user that was removed
	 */
	@Override
	public CMICUser removeByUserId(long userId) throws NoSuchCMICUserException {
		CMICUser cmicUser = findByUserId(userId);

		return remove(cmicUser);
	}

	/**
	 * Returns the number of cmic users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching cmic users
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CMICUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"cmicUser.userId = ?";

	public CMICUserPersistenceImpl() {
		setModelClass(CMICUser.class);

		setModelImplClass(CMICUserImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the cmic user in the entity cache if it is enabled.
	 *
	 * @param cmicUser the cmic user
	 */
	@Override
	public void cacheResult(CMICUser cmicUser) {
		entityCache.putResult(
			entityCacheEnabled, CMICUserImpl.class, cmicUser.getPrimaryKey(),
			cmicUser);

		finderCache.putResult(
			_finderPathFetchByUserId, new Object[] {cmicUser.getUserId()},
			cmicUser);

		cmicUser.resetOriginalValues();
	}

	/**
	 * Caches the cmic users in the entity cache if it is enabled.
	 *
	 * @param cmicUsers the cmic users
	 */
	@Override
	public void cacheResult(List<CMICUser> cmicUsers) {
		for (CMICUser cmicUser : cmicUsers) {
			if (entityCache.getResult(
					entityCacheEnabled, CMICUserImpl.class,
					cmicUser.getPrimaryKey()) == null) {

				cacheResult(cmicUser);
			}
			else {
				cmicUser.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cmic users.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CMICUserImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cmic user.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CMICUser cmicUser) {
		entityCache.removeResult(
			entityCacheEnabled, CMICUserImpl.class, cmicUser.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CMICUserModelImpl)cmicUser, true);
	}

	@Override
	public void clearCache(List<CMICUser> cmicUsers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CMICUser cmicUser : cmicUsers) {
			entityCache.removeResult(
				entityCacheEnabled, CMICUserImpl.class,
				cmicUser.getPrimaryKey());

			clearUniqueFindersCache((CMICUserModelImpl)cmicUser, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CMICUserModelImpl cmicUserModelImpl) {

		Object[] args = new Object[] {cmicUserModelImpl.getUserId()};

		finderCache.putResult(
			_finderPathCountByUserId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUserId, args, cmicUserModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CMICUserModelImpl cmicUserModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {cmicUserModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(_finderPathFetchByUserId, args);
		}

		if ((cmicUserModelImpl.getColumnBitmask() &
			 _finderPathFetchByUserId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				cmicUserModelImpl.getOriginalUserId()
			};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(_finderPathFetchByUserId, args);
		}
	}

	/**
	 * Creates a new cmic user with the primary key. Does not add the cmic user to the database.
	 *
	 * @param cmicUserId the primary key for the new cmic user
	 * @return the new cmic user
	 */
	@Override
	public CMICUser create(long cmicUserId) {
		CMICUser cmicUser = new CMICUserImpl();

		cmicUser.setNew(true);
		cmicUser.setPrimaryKey(cmicUserId);

		return cmicUser;
	}

	/**
	 * Removes the cmic user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user that was removed
	 * @throws NoSuchCMICUserException if a cmic user with the primary key could not be found
	 */
	@Override
	public CMICUser remove(long cmicUserId) throws NoSuchCMICUserException {
		return remove((Serializable)cmicUserId);
	}

	/**
	 * Removes the cmic user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cmic user
	 * @return the cmic user that was removed
	 * @throws NoSuchCMICUserException if a cmic user with the primary key could not be found
	 */
	@Override
	public CMICUser remove(Serializable primaryKey)
		throws NoSuchCMICUserException {

		Session session = null;

		try {
			session = openSession();

			CMICUser cmicUser = (CMICUser)session.get(
				CMICUserImpl.class, primaryKey);

			if (cmicUser == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCMICUserException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cmicUser);
		}
		catch (NoSuchCMICUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CMICUser removeImpl(CMICUser cmicUser) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cmicUser)) {
				cmicUser = (CMICUser)session.get(
					CMICUserImpl.class, cmicUser.getPrimaryKeyObj());
			}

			if (cmicUser != null) {
				session.delete(cmicUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cmicUser != null) {
			clearCache(cmicUser);
		}

		return cmicUser;
	}

	@Override
	public CMICUser updateImpl(CMICUser cmicUser) {
		boolean isNew = cmicUser.isNew();

		if (!(cmicUser instanceof CMICUserModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cmicUser.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cmicUser);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cmicUser proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CMICUser implementation " +
					cmicUser.getClass());
		}

		CMICUserModelImpl cmicUserModelImpl = (CMICUserModelImpl)cmicUser;

		Session session = null;

		try {
			session = openSession();

			if (cmicUser.isNew()) {
				session.save(cmicUser);

				cmicUser.setNew(false);
			}
			else {
				cmicUser = (CMICUser)session.merge(cmicUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, CMICUserImpl.class, cmicUser.getPrimaryKey(),
			cmicUser, false);

		clearUniqueFindersCache(cmicUserModelImpl, false);
		cacheUniqueFindersCache(cmicUserModelImpl);

		cmicUser.resetOriginalValues();

		return cmicUser;
	}

	/**
	 * Returns the cmic user with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cmic user
	 * @return the cmic user
	 * @throws NoSuchCMICUserException if a cmic user with the primary key could not be found
	 */
	@Override
	public CMICUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCMICUserException {

		CMICUser cmicUser = fetchByPrimaryKey(primaryKey);

		if (cmicUser == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCMICUserException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cmicUser;
	}

	/**
	 * Returns the cmic user with the primary key or throws a <code>NoSuchCMICUserException</code> if it could not be found.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user
	 * @throws NoSuchCMICUserException if a cmic user with the primary key could not be found
	 */
	@Override
	public CMICUser findByPrimaryKey(long cmicUserId)
		throws NoSuchCMICUserException {

		return findByPrimaryKey((Serializable)cmicUserId);
	}

	/**
	 * Returns the cmic user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user, or <code>null</code> if a cmic user with the primary key could not be found
	 */
	@Override
	public CMICUser fetchByPrimaryKey(long cmicUserId) {
		return fetchByPrimaryKey((Serializable)cmicUserId);
	}

	/**
	 * Returns all the cmic users.
	 *
	 * @return the cmic users
	 */
	@Override
	public List<CMICUser> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CMICUser> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<CMICUser> findAll(
		int start, int end, OrderByComparator<CMICUser> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<CMICUser> findAll(
		int start, int end, OrderByComparator<CMICUser> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CMICUser> list = null;

		if (useFinderCache) {
			list = (List<CMICUser>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CMICUSER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CMICUSER;

				if (pagination) {
					sql = sql.concat(CMICUserModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CMICUser>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CMICUser>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cmic users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CMICUser cmicUser : findAll()) {
			remove(cmicUser);
		}
	}

	/**
	 * Returns the number of cmic users.
	 *
	 * @return the number of cmic users
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CMICUSER);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "cmicUserId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CMICUSER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CMICUserModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cmic user persistence.
	 */
	@Activate
	public void activate() {
		CMICUserModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		CMICUserModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICUserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] {Long.class.getName()},
			CMICUserModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CMICUserImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = cmicPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.churchmutual.core.model.CMICUser"),
			true);
	}

	@Override
	@Reference(
		target = cmicPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = cmicPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CMICUSER =
		"SELECT cmicUser FROM CMICUser cmicUser";

	private static final String _SQL_SELECT_CMICUSER_WHERE =
		"SELECT cmicUser FROM CMICUser cmicUser WHERE ";

	private static final String _SQL_COUNT_CMICUSER =
		"SELECT COUNT(cmicUser) FROM CMICUser cmicUser";

	private static final String _SQL_COUNT_CMICUSER_WHERE =
		"SELECT COUNT(cmicUser) FROM CMICUser cmicUser WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cmicUser.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CMICUser exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CMICUser exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CMICUserPersistenceImpl.class);

	static {
		try {
			Class.forName(cmicPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}