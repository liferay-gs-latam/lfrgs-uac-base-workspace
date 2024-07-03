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

import com.churchmutual.core.service.CMICUserServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CMICUserServiceUtil</code> service
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
 * @see CMICUserServiceSoap
 * @generated
 */
public class CMICUserServiceHttp {

	public static void addRecentlyViewedCMICAccount(
			HttpPrincipal httpPrincipal,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "addRecentlyViewedCMICAccount",
				_addRecentlyViewedCMICAccountParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey);

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

	public static void deletePortraitImage(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "deletePortraitImage",
				_deletePortraitImageParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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
		<com.churchmutual.core.model.CMICBusinessDisplay>
				getBusinessesWithPermission(
					HttpPrincipal httpPrincipal, String actionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessesWithPermission",
				_getBusinessesWithPermissionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, actionId);

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
				<com.churchmutual.core.model.CMICBusinessDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.churchmutual.core.model.CMICUserDisplay>
			getBusinessMembers(
				HttpPrincipal httpPrincipal,
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessMembers",
				_getBusinessMembersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey);

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

			return (java.util.List<com.churchmutual.core.model.CMICUserDisplay>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONArray getBusinessRoles(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessRoles",
				_getBusinessRolesParameterTypes4);

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

			return (com.liferay.portal.kernel.json.JSONArray)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			getBusinessUserDetails(
				HttpPrincipal httpPrincipal,
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessUserDetails",
				_getBusinessUserDetailsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey);

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

			return (com.churchmutual.core.model.CMICUserDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String[] getBusinessUserPermissions(
			HttpPrincipal httpPrincipal,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessUserPermissions",
				_getBusinessUserPermissionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey);

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

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getPortraitImageURL(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getPortraitImageURL",
				_getPortraitImageURLParameterTypes7);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.core.constants.ProducerType[]
			getProducerTypesByUserId(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getProducerTypesByUserId",
				_getProducerTypesByUserIdParameterTypes8);

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

			return (com.churchmutual.core.constants.ProducerType[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.churchmutual.core.model.CMICAccountEntryDisplay>
				getRecentlyViewedCMICAccountEntryDisplays(
					HttpPrincipal httpPrincipal)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class,
				"getRecentlyViewedCMICAccountEntryDisplays",
				_getRecentlyViewedCMICAccountEntryDisplaysParameterTypes9);

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
				<com.churchmutual.core.model.CMICAccountEntryDisplay>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getInsuredThemeSetting(
			HttpPrincipal httpPrincipal, String themeSettingKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getInsuredThemeSetting",
				_getInsuredThemeSettingParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, themeSettingKey);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplay getUserDetails(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getUserDetails",
				_getUserDetailsParameterTypes11);

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

			return (com.churchmutual.core.model.CMICUserDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String inviteBusinessMembers(
			HttpPrincipal httpPrincipal,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String emailAddresses)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "inviteBusinessMembers",
				_inviteBusinessMembersParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey, emailAddresses);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateBusinessMembers(
			HttpPrincipal httpPrincipal,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserRolesJSONString, String removeUsersJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "updateBusinessMembers",
				_updateBusinessMembersParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey, updateUserRolesJSONString,
				removeUsersJSONString);

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

	public static void updateBusinessMemberPermissions(
			HttpPrincipal httpPrincipal,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "updateBusinessMemberPermissions",
				_updateBusinessMemberPermissionsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicBusinessKey, updateUserPermissionsJSONString);

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

	public static String updatePortraitImage(
			HttpPrincipal httpPrincipal, String imageFileString)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "updatePortraitImage",
				_updatePortraitImageParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, imageFileString);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String validateInsuredUserRegistration(
			HttpPrincipal httpPrincipal, long cmicUserId, String accountNumber,
			String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "validateInsuredUserRegistration",
				_validateInsuredUserRegistrationParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicUserId, accountNumber, registrationCode,
				zipCode);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String validateProducerUserRegistration(
			HttpPrincipal httpPrincipal, long cmicUserId, String agentNumber,
			String divisionNumber, String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "validateProducerUserRegistration",
				_validateProducerUserRegistrationParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicUserId, agentNumber, divisionNumber,
				registrationCode, zipCode);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			validateUserRegistrationCode(
				HttpPrincipal httpPrincipal, String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "validateUserRegistrationCode",
				_validateUserRegistrationCodeParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, registrationCode);

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

			return (com.churchmutual.core.model.CMICUserDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CMICUserServiceHttp.class);

	private static final Class<?>[]
		_addRecentlyViewedCMICAccountParameterTypes0 = new Class[] {
			com.churchmutual.core.model.CMICBusinessKey.class
		};
	private static final Class<?>[] _deletePortraitImageParameterTypes1 =
		new Class[] {};
	private static final Class<?>[]
		_getBusinessesWithPermissionParameterTypes2 = new Class[] {
			String.class
		};
	private static final Class<?>[] _getBusinessMembersParameterTypes3 =
		new Class[] {com.churchmutual.core.model.CMICBusinessKey.class};
	private static final Class<?>[] _getBusinessRolesParameterTypes4 =
		new Class[] {};
	private static final Class<?>[] _getBusinessUserDetailsParameterTypes5 =
		new Class[] {com.churchmutual.core.model.CMICBusinessKey.class};
	private static final Class<?>[] _getBusinessUserPermissionsParameterTypes6 =
		new Class[] {com.churchmutual.core.model.CMICBusinessKey.class};
	private static final Class<?>[] _getPortraitImageURLParameterTypes7 =
		new Class[] {};
	private static final Class<?>[] _getProducerTypesByUserIdParameterTypes8 =
		new Class[] {};
	private static final Class<?>[]
		_getRecentlyViewedCMICAccountEntryDisplaysParameterTypes9 =
			new Class[] {};
	private static final Class<?>[] _getInsuredThemeSettingParameterTypes10 =
		new Class[] {String.class};
	private static final Class<?>[] _getUserDetailsParameterTypes11 =
		new Class[] {};
	private static final Class<?>[] _inviteBusinessMembersParameterTypes12 =
		new Class[] {
			com.churchmutual.core.model.CMICBusinessKey.class, String.class
		};
	private static final Class<?>[] _updateBusinessMembersParameterTypes13 =
		new Class[] {
			com.churchmutual.core.model.CMICBusinessKey.class, String.class,
			String.class
		};
	private static final Class<?>[]
		_updateBusinessMemberPermissionsParameterTypes14 = new Class[] {
			com.churchmutual.core.model.CMICBusinessKey.class, String.class
		};
	private static final Class<?>[] _updatePortraitImageParameterTypes15 =
		new Class[] {String.class};
	private static final Class<?>[]
		_validateInsuredUserRegistrationParameterTypes16 = new Class[] {
			long.class, String.class, String.class, String.class
		};
	private static final Class<?>[]
		_validateProducerUserRegistrationParameterTypes17 = new Class[] {
			long.class, String.class, String.class, String.class, String.class
		};
	private static final Class<?>[]
		_validateUserRegistrationCodeParameterTypes18 = new Class[] {
			String.class
		};

}