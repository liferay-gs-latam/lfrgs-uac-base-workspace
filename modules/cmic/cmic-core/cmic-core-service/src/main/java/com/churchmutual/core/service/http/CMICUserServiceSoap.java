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

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CMICUserServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.churchmutual.core.model.CMICUserSoap</code>. If the method in the
 * service utility returns a
 * <code>com.churchmutual.core.model.CMICUser</code>, that is translated to a
 * <code>com.churchmutual.core.model.CMICUserSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICUserServiceHttp
 * @generated
 */
public class CMICUserServiceSoap {

	public static void addRecentlyViewedCMICAccount(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			CMICUserServiceUtil.addRecentlyViewedCMICAccount(cmicBusinessKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePortraitImage() throws RemoteException {
		try {
			CMICUserServiceUtil.deletePortraitImage();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICBusinessDisplaySoap[]
			getBusinessesWithPermission(String actionId)
		throws RemoteException {

		try {
			java.util.List<com.churchmutual.core.model.CMICBusinessDisplay>
				returnValue = CMICUserServiceUtil.getBusinessesWithPermission(
					actionId);

			return com.churchmutual.core.model.CMICBusinessDisplaySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplaySoap[]
			getBusinessMembers(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			java.util.List<com.churchmutual.core.model.CMICUserDisplay>
				returnValue = CMICUserServiceUtil.getBusinessMembers(
					cmicBusinessKey);

			return com.churchmutual.core.model.CMICUserDisplaySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String getBusinessRoles() throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONArray returnValue =
				CMICUserServiceUtil.getBusinessRoles();

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			getBusinessUserDetails(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			com.churchmutual.core.model.CMICUserDisplay returnValue =
				CMICUserServiceUtil.getBusinessUserDetails(cmicBusinessKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String[] getBusinessUserPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws RemoteException {

		try {
			String[] returnValue =
				CMICUserServiceUtil.getBusinessUserPermissions(cmicBusinessKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String getPortraitImageURL() throws RemoteException {
		try {
			String returnValue = CMICUserServiceUtil.getPortraitImageURL();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.constants.ProducerType[]
			getProducerTypesByUserId()
		throws RemoteException {

		try {
			com.churchmutual.core.constants.ProducerType[] returnValue =
				CMICUserServiceUtil.getProducerTypesByUserId();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICAccountEntryDisplaySoap[]
			getRecentlyViewedCMICAccountEntryDisplays()
		throws RemoteException {

		try {
			java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
				returnValue =
					CMICUserServiceUtil.
						getRecentlyViewedCMICAccountEntryDisplays();

			return com.churchmutual.core.model.CMICAccountEntryDisplaySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String getInsuredThemeSetting(String themeSettingKey)
		throws RemoteException {

		try {
			String returnValue = CMICUserServiceUtil.getInsuredThemeSetting(
				themeSettingKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplay getUserDetails()
		throws RemoteException {

		try {
			com.churchmutual.core.model.CMICUserDisplay returnValue =
				CMICUserServiceUtil.getUserDetails();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String inviteBusinessMembers(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String emailAddresses)
		throws RemoteException {

		try {
			String returnValue = CMICUserServiceUtil.inviteBusinessMembers(
				cmicBusinessKey, emailAddresses);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateBusinessMembers(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserRolesJSONString, String removeUsersJSONString)
		throws RemoteException {

		try {
			CMICUserServiceUtil.updateBusinessMembers(
				cmicBusinessKey, updateUserRolesJSONString,
				removeUsersJSONString);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateBusinessMemberPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws RemoteException {

		try {
			CMICUserServiceUtil.updateBusinessMemberPermissions(
				cmicBusinessKey, updateUserPermissionsJSONString);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String updatePortraitImage(String imageFileString)
		throws RemoteException {

		try {
			String returnValue = CMICUserServiceUtil.updatePortraitImage(
				imageFileString);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String registrationCode,
			String zipCode)
		throws RemoteException {

		try {
			String returnValue =
				CMICUserServiceUtil.validateInsuredUserRegistration(
					cmicUserId, accountNumber, registrationCode, zipCode);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber,
			String registrationCode, String zipCode)
		throws RemoteException {

		try {
			String returnValue =
				CMICUserServiceUtil.validateProducerUserRegistration(
					cmicUserId, agentNumber, divisionNumber, registrationCode,
					zipCode);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.churchmutual.core.model.CMICUserDisplay
			validateUserRegistrationCode(String registrationCode)
		throws RemoteException {

		try {
			com.churchmutual.core.model.CMICUserDisplay returnValue =
				CMICUserServiceUtil.validateUserRegistrationCode(
					registrationCode);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CMICUserServiceSoap.class);

}