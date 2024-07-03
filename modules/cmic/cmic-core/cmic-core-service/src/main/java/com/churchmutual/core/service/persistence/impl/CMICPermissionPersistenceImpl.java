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

import com.churchmutual.core.exception.NoSuchCMICPermissionException;
import com.churchmutual.core.model.CMICPermission;
import com.churchmutual.core.model.impl.CMICPermissionImpl;
import com.churchmutual.core.model.impl.CMICPermissionModelImpl;
import com.churchmutual.core.service.persistence.CMICPermissionPK;
import com.churchmutual.core.service.persistence.CMICPermissionPersistence;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cmic permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @generated
 */
@Component(service = CMICPermissionPersistence.class)
public class CMICPermissionPersistenceImpl
	extends BasePersistenceImpl<CMICPermission>
	implements CMICPermissionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CMICPermissionUtil</code> to access the cmic permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CMICPermissionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByU_C_A;
	private FinderPath _finderPathCountByU_C_A;

	/**
	 * Returns the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; or throws a <code>NoSuchCMICPermissionException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission findByU_C_A(
			long userId, String cmicBusinessKey, String actionId)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByU_C_A(
			userId, cmicBusinessKey, actionId);

		if (cmicPermission == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", cmicBusinessKey=");
			msg.append(cmicBusinessKey);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCMICPermissionException(msg.toString());
		}

		return cmicPermission;
	}

	/**
	 * Returns the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByU_C_A(
		long userId, String cmicBusinessKey, String actionId) {

		return fetchByU_C_A(userId, cmicBusinessKey, actionId, true);
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
	@Override
	public CMICPermission fetchByU_C_A(
		long userId, String cmicBusinessKey, String actionId,
		boolean useFinderCache) {

		cmicBusinessKey = Objects.toString(cmicBusinessKey, "");
		actionId = Objects.toString(actionId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, cmicBusinessKey, actionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_C_A, finderArgs, this);
		}

		if (result instanceof CMICPermission) {
			CMICPermission cmicPermission = (CMICPermission)result;

			if ((userId != cmicPermission.getUserId()) ||
				!Objects.equals(
					cmicBusinessKey, cmicPermission.getCmicBusinessKey()) ||
				!Objects.equals(actionId, cmicPermission.getActionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_U_C_A_USERID_2);

			boolean bindCmicBusinessKey = false;

			if (cmicBusinessKey.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_A_CMICBUSINESSKEY_3);
			}
			else {
				bindCmicBusinessKey = true;

				query.append(_FINDER_COLUMN_U_C_A_CMICBUSINESSKEY_2);
			}

			boolean bindActionId = false;

			if (actionId.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				query.append(_FINDER_COLUMN_U_C_A_ACTIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindCmicBusinessKey) {
					qPos.add(cmicBusinessKey);
				}

				if (bindActionId) {
					qPos.add(actionId);
				}

				List<CMICPermission> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_C_A, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									userId, cmicBusinessKey, actionId
								};
							}

							_log.warn(
								"CMICPermissionPersistenceImpl.fetchByU_C_A(long, String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CMICPermission cmicPermission = list.get(0);

					result = cmicPermission;

					cacheResult(cmicPermission);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByU_C_A, finderArgs);
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
			return (CMICPermission)result;
		}
	}

	/**
	 * Removes the cmic permission where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the cmic permission that was removed
	 */
	@Override
	public CMICPermission removeByU_C_A(
			long userId, String cmicBusinessKey, String actionId)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = findByU_C_A(
			userId, cmicBusinessKey, actionId);

		return remove(cmicPermission);
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63; and cmicBusinessKey = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param actionId the action ID
	 * @return the number of matching cmic permissions
	 */
	@Override
	public int countByU_C_A(
		long userId, String cmicBusinessKey, String actionId) {

		cmicBusinessKey = Objects.toString(cmicBusinessKey, "");
		actionId = Objects.toString(actionId, "");

		FinderPath finderPath = _finderPathCountByU_C_A;

		Object[] finderArgs = new Object[] {userId, cmicBusinessKey, actionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_U_C_A_USERID_2);

			boolean bindCmicBusinessKey = false;

			if (cmicBusinessKey.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_A_CMICBUSINESSKEY_3);
			}
			else {
				bindCmicBusinessKey = true;

				query.append(_FINDER_COLUMN_U_C_A_CMICBUSINESSKEY_2);
			}

			boolean bindActionId = false;

			if (actionId.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				query.append(_FINDER_COLUMN_U_C_A_ACTIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindCmicBusinessKey) {
					qPos.add(cmicBusinessKey);
				}

				if (bindActionId) {
					qPos.add(actionId);
				}

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

	private static final String _FINDER_COLUMN_U_C_A_USERID_2 =
		"cmicPermission.id.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_A_CMICBUSINESSKEY_2 =
		"cmicPermission.id.cmicBusinessKey = ? AND ";

	private static final String _FINDER_COLUMN_U_C_A_CMICBUSINESSKEY_3 =
		"(cmicPermission.id.cmicBusinessKey IS NULL OR cmicPermission.id.cmicBusinessKey = '') AND ";

	private static final String _FINDER_COLUMN_U_C_A_ACTIONID_2 =
		"cmicPermission.id.actionId = ?";

	private static final String _FINDER_COLUMN_U_C_A_ACTIONID_3 =
		"(cmicPermission.id.actionId IS NULL OR cmicPermission.id.actionId = '')";

	private FinderPath _finderPathWithPaginationFindByU_C;
	private FinderPath _finderPathWithoutPaginationFindByU_C;
	private FinderPath _finderPathCountByU_C;

	/**
	 * Returns all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @return the matching cmic permissions
	 */
	@Override
	public List<CMICPermission> findByU_C(long userId, String cmicBusinessKey) {
		return findByU_C(
			userId, cmicBusinessKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
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
	@Override
	public List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey, int start, int end) {

		return findByU_C(userId, cmicBusinessKey, start, end, null);
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
	@Override
	public List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return findByU_C(
			userId, cmicBusinessKey, start, end, orderByComparator, true);
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
	@Override
	public List<CMICPermission> findByU_C(
		long userId, String cmicBusinessKey, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		cmicBusinessKey = Objects.toString(cmicBusinessKey, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_C;
				finderArgs = new Object[] {userId, cmicBusinessKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_C;
			finderArgs = new Object[] {
				userId, cmicBusinessKey, start, end, orderByComparator
			};
		}

		List<CMICPermission> list = null;

		if (useFinderCache) {
			list = (List<CMICPermission>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CMICPermission cmicPermission : list) {
					if ((userId != cmicPermission.getUserId()) ||
						!cmicBusinessKey.equals(
							cmicPermission.getCmicBusinessKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindCmicBusinessKey = false;

			if (cmicBusinessKey.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_CMICBUSINESSKEY_3);
			}
			else {
				bindCmicBusinessKey = true;

				query.append(_FINDER_COLUMN_U_C_CMICBUSINESSKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CMICPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindCmicBusinessKey) {
					qPos.add(cmicBusinessKey);
				}

				if (!pagination) {
					list = (List<CMICPermission>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CMICPermission>)QueryUtil.list(
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
	 * Returns the first cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission findByU_C_First(
			long userId, String cmicBusinessKey,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByU_C_First(
			userId, cmicBusinessKey, orderByComparator);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", cmicBusinessKey=");
		msg.append(cmicBusinessKey);

		msg.append("}");

		throw new NoSuchCMICPermissionException(msg.toString());
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByU_C_First(
		long userId, String cmicBusinessKey,
		OrderByComparator<CMICPermission> orderByComparator) {

		List<CMICPermission> list = findByU_C(
			userId, cmicBusinessKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CMICPermission findByU_C_Last(
			long userId, String cmicBusinessKey,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByU_C_Last(
			userId, cmicBusinessKey, orderByComparator);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", cmicBusinessKey=");
		msg.append(cmicBusinessKey);

		msg.append("}");

		throw new NoSuchCMICPermissionException(msg.toString());
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByU_C_Last(
		long userId, String cmicBusinessKey,
		OrderByComparator<CMICPermission> orderByComparator) {

		int count = countByU_C(userId, cmicBusinessKey);

		if (count == 0) {
			return null;
		}

		List<CMICPermission> list = findByU_C(
			userId, cmicBusinessKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CMICPermission[] findByU_C_PrevAndNext(
			CMICPermissionPK cmicPermissionPK, long userId,
			String cmicBusinessKey,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		cmicBusinessKey = Objects.toString(cmicBusinessKey, "");

		CMICPermission cmicPermission = findByPrimaryKey(cmicPermissionPK);

		Session session = null;

		try {
			session = openSession();

			CMICPermission[] array = new CMICPermissionImpl[3];

			array[0] = getByU_C_PrevAndNext(
				session, cmicPermission, userId, cmicBusinessKey,
				orderByComparator, true);

			array[1] = cmicPermission;

			array[2] = getByU_C_PrevAndNext(
				session, cmicPermission, userId, cmicBusinessKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CMICPermission getByU_C_PrevAndNext(
		Session session, CMICPermission cmicPermission, long userId,
		String cmicBusinessKey,
		OrderByComparator<CMICPermission> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_U_C_USERID_2);

		boolean bindCmicBusinessKey = false;

		if (cmicBusinessKey.isEmpty()) {
			query.append(_FINDER_COLUMN_U_C_CMICBUSINESSKEY_3);
		}
		else {
			bindCmicBusinessKey = true;

			query.append(_FINDER_COLUMN_U_C_CMICBUSINESSKEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CMICPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (bindCmicBusinessKey) {
			qPos.add(cmicBusinessKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cmicPermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CMICPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cmic permissions where userId = &#63; and cmicBusinessKey = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 */
	@Override
	public void removeByU_C(long userId, String cmicBusinessKey) {
		for (CMICPermission cmicPermission :
				findByU_C(
					userId, cmicBusinessKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cmicPermission);
		}
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63; and cmicBusinessKey = &#63;.
	 *
	 * @param userId the user ID
	 * @param cmicBusinessKey the cmic business key
	 * @return the number of matching cmic permissions
	 */
	@Override
	public int countByU_C(long userId, String cmicBusinessKey) {
		cmicBusinessKey = Objects.toString(cmicBusinessKey, "");

		FinderPath finderPath = _finderPathCountByU_C;

		Object[] finderArgs = new Object[] {userId, cmicBusinessKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindCmicBusinessKey = false;

			if (cmicBusinessKey.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_CMICBUSINESSKEY_3);
			}
			else {
				bindCmicBusinessKey = true;

				query.append(_FINDER_COLUMN_U_C_CMICBUSINESSKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindCmicBusinessKey) {
					qPos.add(cmicBusinessKey);
				}

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

	private static final String _FINDER_COLUMN_U_C_USERID_2 =
		"cmicPermission.id.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_CMICBUSINESSKEY_2 =
		"cmicPermission.id.cmicBusinessKey = ?";

	private static final String _FINDER_COLUMN_U_C_CMICBUSINESSKEY_3 =
		"(cmicPermission.id.cmicBusinessKey IS NULL OR cmicPermission.id.cmicBusinessKey = '')";

	private FinderPath _finderPathWithPaginationFindByU_A;
	private FinderPath _finderPathWithoutPaginationFindByU_A;
	private FinderPath _finderPathCountByU_A;

	/**
	 * Returns all the cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @return the matching cmic permissions
	 */
	@Override
	public List<CMICPermission> findByU_A(long userId, String actionId) {
		return findByU_A(
			userId, actionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CMICPermission> findByU_A(
		long userId, String actionId, int start, int end) {

		return findByU_A(userId, actionId, start, end, null);
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
	@Override
	public List<CMICPermission> findByU_A(
		long userId, String actionId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return findByU_A(userId, actionId, start, end, orderByComparator, true);
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
	@Override
	public List<CMICPermission> findByU_A(
		long userId, String actionId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		actionId = Objects.toString(actionId, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_A;
				finderArgs = new Object[] {userId, actionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_A;
			finderArgs = new Object[] {
				userId, actionId, start, end, orderByComparator
			};
		}

		List<CMICPermission> list = null;

		if (useFinderCache) {
			list = (List<CMICPermission>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CMICPermission cmicPermission : list) {
					if ((userId != cmicPermission.getUserId()) ||
						!actionId.equals(cmicPermission.getActionId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			boolean bindActionId = false;

			if (actionId.isEmpty()) {
				query.append(_FINDER_COLUMN_U_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				query.append(_FINDER_COLUMN_U_A_ACTIONID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CMICPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindActionId) {
					qPos.add(actionId);
				}

				if (!pagination) {
					list = (List<CMICPermission>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CMICPermission>)QueryUtil.list(
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
	 * Returns the first cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission findByU_A_First(
			long userId, String actionId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByU_A_First(
			userId, actionId, orderByComparator);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", actionId=");
		msg.append(actionId);

		msg.append("}");

		throw new NoSuchCMICPermissionException(msg.toString());
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByU_A_First(
		long userId, String actionId,
		OrderByComparator<CMICPermission> orderByComparator) {

		List<CMICPermission> list = findByU_A(
			userId, actionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CMICPermission findByU_A_Last(
			long userId, String actionId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByU_A_Last(
			userId, actionId, orderByComparator);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", actionId=");
		msg.append(actionId);

		msg.append("}");

		throw new NoSuchCMICPermissionException(msg.toString());
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByU_A_Last(
		long userId, String actionId,
		OrderByComparator<CMICPermission> orderByComparator) {

		int count = countByU_A(userId, actionId);

		if (count == 0) {
			return null;
		}

		List<CMICPermission> list = findByU_A(
			userId, actionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CMICPermission[] findByU_A_PrevAndNext(
			CMICPermissionPK cmicPermissionPK, long userId, String actionId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		actionId = Objects.toString(actionId, "");

		CMICPermission cmicPermission = findByPrimaryKey(cmicPermissionPK);

		Session session = null;

		try {
			session = openSession();

			CMICPermission[] array = new CMICPermissionImpl[3];

			array[0] = getByU_A_PrevAndNext(
				session, cmicPermission, userId, actionId, orderByComparator,
				true);

			array[1] = cmicPermission;

			array[2] = getByU_A_PrevAndNext(
				session, cmicPermission, userId, actionId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CMICPermission getByU_A_PrevAndNext(
		Session session, CMICPermission cmicPermission, long userId,
		String actionId, OrderByComparator<CMICPermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_U_A_USERID_2);

		boolean bindActionId = false;

		if (actionId.isEmpty()) {
			query.append(_FINDER_COLUMN_U_A_ACTIONID_3);
		}
		else {
			bindActionId = true;

			query.append(_FINDER_COLUMN_U_A_ACTIONID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CMICPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (bindActionId) {
			qPos.add(actionId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cmicPermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CMICPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cmic permissions where userId = &#63; and actionId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 */
	@Override
	public void removeByU_A(long userId, String actionId) {
		for (CMICPermission cmicPermission :
				findByU_A(
					userId, actionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cmicPermission);
		}
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63; and actionId = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @return the number of matching cmic permissions
	 */
	@Override
	public int countByU_A(long userId, String actionId) {
		actionId = Objects.toString(actionId, "");

		FinderPath finderPath = _finderPathCountByU_A;

		Object[] finderArgs = new Object[] {userId, actionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			boolean bindActionId = false;

			if (actionId.isEmpty()) {
				query.append(_FINDER_COLUMN_U_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				query.append(_FINDER_COLUMN_U_A_ACTIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindActionId) {
					qPos.add(actionId);
				}

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

	private static final String _FINDER_COLUMN_U_A_USERID_2 =
		"cmicPermission.id.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_A_ACTIONID_2 =
		"cmicPermission.id.actionId = ?";

	private static final String _FINDER_COLUMN_U_A_ACTIONID_3 =
		"(cmicPermission.id.actionId IS NULL OR cmicPermission.id.actionId = '')";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the cmic permissions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching cmic permissions
	 */
	@Override
	public List<CMICPermission> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CMICPermission> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
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
	@Override
	public List<CMICPermission> findByUserId(
		long userId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
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
	@Override
	public List<CMICPermission> findByUserId(
		long userId, int start, int end,
		OrderByComparator<CMICPermission> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<CMICPermission> list = null;

		if (useFinderCache) {
			list = (List<CMICPermission>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CMICPermission cmicPermission : list) {
					if ((userId != cmicPermission.getUserId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CMICPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<CMICPermission>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CMICPermission>)QueryUtil.list(
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
	 * Returns the first cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission findByUserId_First(
			long userId, OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByUserId_First(
			userId, orderByComparator);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchCMICPermissionException(msg.toString());
	}

	/**
	 * Returns the first cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByUserId_First(
		long userId, OrderByComparator<CMICPermission> orderByComparator) {

		List<CMICPermission> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission
	 * @throws NoSuchCMICPermissionException if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission findByUserId_Last(
			long userId, OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByUserId_Last(
			userId, orderByComparator);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchCMICPermissionException(msg.toString());
	}

	/**
	 * Returns the last cmic permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cmic permission, or <code>null</code> if a matching cmic permission could not be found
	 */
	@Override
	public CMICPermission fetchByUserId_Last(
		long userId, OrderByComparator<CMICPermission> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<CMICPermission> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CMICPermission[] findByUserId_PrevAndNext(
			CMICPermissionPK cmicPermissionPK, long userId,
			OrderByComparator<CMICPermission> orderByComparator)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = findByPrimaryKey(cmicPermissionPK);

		Session session = null;

		try {
			session = openSession();

			CMICPermission[] array = new CMICPermissionImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, cmicPermission, userId, orderByComparator, true);

			array[1] = cmicPermission;

			array[2] = getByUserId_PrevAndNext(
				session, cmicPermission, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CMICPermission getByUserId_PrevAndNext(
		Session session, CMICPermission cmicPermission, long userId,
		OrderByComparator<CMICPermission> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CMICPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CMICPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cmicPermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CMICPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cmic permissions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (CMICPermission cmicPermission :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cmicPermission);
		}
	}

	/**
	 * Returns the number of cmic permissions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching cmic permissions
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CMICPERMISSION_WHERE);

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
		"cmicPermission.id.userId = ?";

	public CMICPermissionPersistenceImpl() {
		setModelClass(CMICPermission.class);

		setModelImplClass(CMICPermissionImpl.class);
		setModelPKClass(CMICPermissionPK.class);
	}

	/**
	 * Caches the cmic permission in the entity cache if it is enabled.
	 *
	 * @param cmicPermission the cmic permission
	 */
	@Override
	public void cacheResult(CMICPermission cmicPermission) {
		entityCache.putResult(
			entityCacheEnabled, CMICPermissionImpl.class,
			cmicPermission.getPrimaryKey(), cmicPermission);

		finderCache.putResult(
			_finderPathFetchByU_C_A,
			new Object[] {
				cmicPermission.getUserId(), cmicPermission.getCmicBusinessKey(),
				cmicPermission.getActionId()
			},
			cmicPermission);

		cmicPermission.resetOriginalValues();
	}

	/**
	 * Caches the cmic permissions in the entity cache if it is enabled.
	 *
	 * @param cmicPermissions the cmic permissions
	 */
	@Override
	public void cacheResult(List<CMICPermission> cmicPermissions) {
		for (CMICPermission cmicPermission : cmicPermissions) {
			if (entityCache.getResult(
					entityCacheEnabled, CMICPermissionImpl.class,
					cmicPermission.getPrimaryKey()) == null) {

				cacheResult(cmicPermission);
			}
			else {
				cmicPermission.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cmic permissions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CMICPermissionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cmic permission.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CMICPermission cmicPermission) {
		entityCache.removeResult(
			entityCacheEnabled, CMICPermissionImpl.class,
			cmicPermission.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CMICPermissionModelImpl)cmicPermission, true);
	}

	@Override
	public void clearCache(List<CMICPermission> cmicPermissions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CMICPermission cmicPermission : cmicPermissions) {
			entityCache.removeResult(
				entityCacheEnabled, CMICPermissionImpl.class,
				cmicPermission.getPrimaryKey());

			clearUniqueFindersCache(
				(CMICPermissionModelImpl)cmicPermission, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CMICPermissionModelImpl cmicPermissionModelImpl) {

		Object[] args = new Object[] {
			cmicPermissionModelImpl.getUserId(),
			cmicPermissionModelImpl.getCmicBusinessKey(),
			cmicPermissionModelImpl.getActionId()
		};

		finderCache.putResult(
			_finderPathCountByU_C_A, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByU_C_A, args, cmicPermissionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CMICPermissionModelImpl cmicPermissionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				cmicPermissionModelImpl.getUserId(),
				cmicPermissionModelImpl.getCmicBusinessKey(),
				cmicPermissionModelImpl.getActionId()
			};

			finderCache.removeResult(_finderPathCountByU_C_A, args);
			finderCache.removeResult(_finderPathFetchByU_C_A, args);
		}

		if ((cmicPermissionModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_C_A.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				cmicPermissionModelImpl.getOriginalUserId(),
				cmicPermissionModelImpl.getOriginalCmicBusinessKey(),
				cmicPermissionModelImpl.getOriginalActionId()
			};

			finderCache.removeResult(_finderPathCountByU_C_A, args);
			finderCache.removeResult(_finderPathFetchByU_C_A, args);
		}
	}

	/**
	 * Creates a new cmic permission with the primary key. Does not add the cmic permission to the database.
	 *
	 * @param cmicPermissionPK the primary key for the new cmic permission
	 * @return the new cmic permission
	 */
	@Override
	public CMICPermission create(CMICPermissionPK cmicPermissionPK) {
		CMICPermission cmicPermission = new CMICPermissionImpl();

		cmicPermission.setNew(true);
		cmicPermission.setPrimaryKey(cmicPermissionPK);

		return cmicPermission;
	}

	/**
	 * Removes the cmic permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission that was removed
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	@Override
	public CMICPermission remove(CMICPermissionPK cmicPermissionPK)
		throws NoSuchCMICPermissionException {

		return remove((Serializable)cmicPermissionPK);
	}

	/**
	 * Removes the cmic permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cmic permission
	 * @return the cmic permission that was removed
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	@Override
	public CMICPermission remove(Serializable primaryKey)
		throws NoSuchCMICPermissionException {

		Session session = null;

		try {
			session = openSession();

			CMICPermission cmicPermission = (CMICPermission)session.get(
				CMICPermissionImpl.class, primaryKey);

			if (cmicPermission == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCMICPermissionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cmicPermission);
		}
		catch (NoSuchCMICPermissionException nsee) {
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
	protected CMICPermission removeImpl(CMICPermission cmicPermission) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cmicPermission)) {
				cmicPermission = (CMICPermission)session.get(
					CMICPermissionImpl.class,
					cmicPermission.getPrimaryKeyObj());
			}

			if (cmicPermission != null) {
				session.delete(cmicPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cmicPermission != null) {
			clearCache(cmicPermission);
		}

		return cmicPermission;
	}

	@Override
	public CMICPermission updateImpl(CMICPermission cmicPermission) {
		boolean isNew = cmicPermission.isNew();

		if (!(cmicPermission instanceof CMICPermissionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cmicPermission.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cmicPermission);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cmicPermission proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CMICPermission implementation " +
					cmicPermission.getClass());
		}

		CMICPermissionModelImpl cmicPermissionModelImpl =
			(CMICPermissionModelImpl)cmicPermission;

		Session session = null;

		try {
			session = openSession();

			if (cmicPermission.isNew()) {
				session.save(cmicPermission);

				cmicPermission.setNew(false);
			}
			else {
				cmicPermission = (CMICPermission)session.merge(cmicPermission);
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
			Object[] args = new Object[] {
				cmicPermissionModelImpl.getUserId(),
				cmicPermissionModelImpl.getCmicBusinessKey()
			};

			finderCache.removeResult(_finderPathCountByU_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_C, args);

			args = new Object[] {
				cmicPermissionModelImpl.getUserId(),
				cmicPermissionModelImpl.getActionId()
			};

			finderCache.removeResult(_finderPathCountByU_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_A, args);

			args = new Object[] {cmicPermissionModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((cmicPermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					cmicPermissionModelImpl.getOriginalUserId(),
					cmicPermissionModelImpl.getOriginalCmicBusinessKey()
				};

				finderCache.removeResult(_finderPathCountByU_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_C, args);

				args = new Object[] {
					cmicPermissionModelImpl.getUserId(),
					cmicPermissionModelImpl.getCmicBusinessKey()
				};

				finderCache.removeResult(_finderPathCountByU_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_C, args);
			}

			if ((cmicPermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					cmicPermissionModelImpl.getOriginalUserId(),
					cmicPermissionModelImpl.getOriginalActionId()
				};

				finderCache.removeResult(_finderPathCountByU_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_A, args);

				args = new Object[] {
					cmicPermissionModelImpl.getUserId(),
					cmicPermissionModelImpl.getActionId()
				};

				finderCache.removeResult(_finderPathCountByU_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_A, args);
			}

			if ((cmicPermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					cmicPermissionModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {cmicPermissionModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, CMICPermissionImpl.class,
			cmicPermission.getPrimaryKey(), cmicPermission, false);

		clearUniqueFindersCache(cmicPermissionModelImpl, false);
		cacheUniqueFindersCache(cmicPermissionModelImpl);

		cmicPermission.resetOriginalValues();

		return cmicPermission;
	}

	/**
	 * Returns the cmic permission with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cmic permission
	 * @return the cmic permission
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	@Override
	public CMICPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCMICPermissionException {

		CMICPermission cmicPermission = fetchByPrimaryKey(primaryKey);

		if (cmicPermission == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCMICPermissionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cmicPermission;
	}

	/**
	 * Returns the cmic permission with the primary key or throws a <code>NoSuchCMICPermissionException</code> if it could not be found.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission
	 * @throws NoSuchCMICPermissionException if a cmic permission with the primary key could not be found
	 */
	@Override
	public CMICPermission findByPrimaryKey(CMICPermissionPK cmicPermissionPK)
		throws NoSuchCMICPermissionException {

		return findByPrimaryKey((Serializable)cmicPermissionPK);
	}

	/**
	 * Returns the cmic permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicPermissionPK the primary key of the cmic permission
	 * @return the cmic permission, or <code>null</code> if a cmic permission with the primary key could not be found
	 */
	@Override
	public CMICPermission fetchByPrimaryKey(CMICPermissionPK cmicPermissionPK) {
		return fetchByPrimaryKey((Serializable)cmicPermissionPK);
	}

	/**
	 * Returns all the cmic permissions.
	 *
	 * @return the cmic permissions
	 */
	@Override
	public List<CMICPermission> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CMICPermission> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<CMICPermission> findAll(
		int start, int end,
		OrderByComparator<CMICPermission> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<CMICPermission> findAll(
		int start, int end, OrderByComparator<CMICPermission> orderByComparator,
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

		List<CMICPermission> list = null;

		if (useFinderCache) {
			list = (List<CMICPermission>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CMICPERMISSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CMICPERMISSION;

				if (pagination) {
					sql = sql.concat(CMICPermissionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CMICPermission>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CMICPermission>)QueryUtil.list(
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
	 * Removes all the cmic permissions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CMICPermission cmicPermission : findAll()) {
			remove(cmicPermission);
		}
	}

	/**
	 * Returns the number of cmic permissions.
	 *
	 * @return the number of cmic permissions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CMICPERMISSION);

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
	public Set<String> getCompoundPKColumnNames() {
		return _compoundPKColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "cmicPermissionPK";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CMICPERMISSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CMICPermissionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cmic permission persistence.
	 */
	@Activate
	public void activate() {
		CMICPermissionModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		CMICPermissionModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByU_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			CMICPermissionModelImpl.USERID_COLUMN_BITMASK |
			CMICPermissionModelImpl.CMICBUSINESSKEY_COLUMN_BITMASK |
			CMICPermissionModelImpl.ACTIONID_COLUMN_BITMASK);

		_finderPathCountByU_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByU_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C",
			new String[] {Long.class.getName(), String.class.getName()},
			CMICPermissionModelImpl.USERID_COLUMN_BITMASK |
			CMICPermissionModelImpl.CMICBUSINESSKEY_COLUMN_BITMASK);

		_finderPathCountByU_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByU_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_A",
			new String[] {Long.class.getName(), String.class.getName()},
			CMICPermissionModelImpl.USERID_COLUMN_BITMASK |
			CMICPermissionModelImpl.ACTIONID_COLUMN_BITMASK);

		_finderPathCountByU_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_A",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			CMICPermissionModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CMICPermissionImpl.class.getName());
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
				"value.object.column.bitmask.enabled.com.churchmutual.core.model.CMICPermission"),
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

	private static final String _SQL_SELECT_CMICPERMISSION =
		"SELECT cmicPermission FROM CMICPermission cmicPermission";

	private static final String _SQL_SELECT_CMICPERMISSION_WHERE =
		"SELECT cmicPermission FROM CMICPermission cmicPermission WHERE ";

	private static final String _SQL_COUNT_CMICPERMISSION =
		"SELECT COUNT(cmicPermission) FROM CMICPermission cmicPermission";

	private static final String _SQL_COUNT_CMICPERMISSION_WHERE =
		"SELECT COUNT(cmicPermission) FROM CMICPermission cmicPermission WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cmicPermission.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CMICPermission exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CMICPermission exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CMICPermissionPersistenceImpl.class);

	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {"userId", "cmicBusinessKey", "actionId"});

	static {
		try {
			Class.forName(cmicPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}