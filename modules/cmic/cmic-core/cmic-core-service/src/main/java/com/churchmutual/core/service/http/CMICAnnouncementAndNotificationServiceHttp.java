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

package com.churchmutual.core.service.http;

import com.churchmutual.core.service.CMICAnnouncementAndNotificationServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CMICAnnouncementAndNotificationServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAnnouncementAndNotificationServiceSoap
 * @generated
 */
public class CMICAnnouncementAndNotificationServiceHttp {

	public static void deleteNotification(
			HttpPrincipal httpPrincipal, long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICAnnouncementAndNotificationServiceUtil.class,
				"deleteNotification", _deleteNotificationParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userNotificationEventId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void dismissAnnouncement(
			HttpPrincipal httpPrincipal, long announcementEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICAnnouncementAndNotificationServiceUtil.class,
				"dismissAnnouncement", _dismissAnnouncementParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, announcementEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAnnouncementDisplay>
				getAnnouncementDisplays(HttpPrincipal httpPrincipal)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICAnnouncementAndNotificationServiceUtil.class,
				"getAnnouncementDisplays",
				_getAnnouncementDisplaysParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.churchmutual.core.model.CMICAnnouncementDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICNotificationDisplay>
				getNotificationDisplays(HttpPrincipal httpPrincipal)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICAnnouncementAndNotificationServiceUtil.class,
				"getNotificationDisplays",
				_getNotificationDisplaysParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.churchmutual.core.model.CMICNotificationDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void markAsReadAnnouncement(
			HttpPrincipal httpPrincipal, long announcementEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICAnnouncementAndNotificationServiceUtil.class,
				"markAsReadAnnouncement",
				_markAsReadAnnouncementParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, announcementEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void markAsReadNotification(
			HttpPrincipal httpPrincipal, long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICAnnouncementAndNotificationServiceUtil.class,
				"markAsReadNotification",
				_markAsReadNotificationParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userNotificationEventId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CMICAnnouncementAndNotificationServiceHttp.class);

	private static final Class<?>[] _deleteNotificationParameterTypes0 =
		new Class[] {long.class};
	private static final Class<?>[] _dismissAnnouncementParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getAnnouncementDisplaysParameterTypes2 =
		new Class[] {};
	private static final Class<?>[] _getNotificationDisplaysParameterTypes3 =
		new Class[] {};
	private static final Class<?>[] _markAsReadAnnouncementParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _markAsReadNotificationParameterTypes5 =
		new Class[] {long.class};

}