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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CMICUserService}.
 *
 * @author Kayleen Lim
 * @see CMICUserService
 * @generated
 */
public class CMICUserServiceWrapper
	implements CMICUserService, ServiceWrapper<CMICUserService> {

	public CMICUserServiceWrapper(CMICUserService cmicUserService) {
		_cmicUserService = cmicUserService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICUserServiceUtil} to access the cmic user remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void addRecentlyViewedCMICAccount(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserService.addRecentlyViewedCMICAccount(cmicBusinessKey);
	}

	@Override
	public void deletePortraitImage()
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserService.deletePortraitImage();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICBusinessDisplay>
			getBusinessesWithPermission(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getBusinessesWithPermission(actionId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICUserDisplay>
			getBusinessMembers(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getBusinessMembers(cmicBusinessKey);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getBusinessRoles()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getBusinessRoles();
	}

	@Override
	public com.churchmutual.core.model.CMICUserDisplay getBusinessUserDetails(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getBusinessUserDetails(cmicBusinessKey);
	}

	@Override
	public String[] getBusinessUserPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getBusinessUserPermissions(cmicBusinessKey);
	}

	@Override
	public String getInsuredThemeSetting(String themeSettingKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getInsuredThemeSetting(themeSettingKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicUserService.getOSGiServiceIdentifier();
	}

	@Override
	public String getPortraitImageURL()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getPortraitImageURL();
	}

	@Override
	public com.churchmutual.core.constants.ProducerType[]
			getProducerTypesByUserId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getProducerTypesByUserId();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getRecentlyViewedCMICAccountEntryDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getRecentlyViewedCMICAccountEntryDisplays();
	}

	@Override
	public com.churchmutual.core.model.CMICUserDisplay getUserDetails()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.getUserDetails();
	}

	@Override
	public String inviteBusinessMembers(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String emailAddresses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.inviteBusinessMembers(
			cmicBusinessKey, emailAddresses);
	}

	@Override
	public void updateBusinessMemberPermissions(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserService.updateBusinessMemberPermissions(
			cmicBusinessKey, updateUserPermissionsJSONString);
	}

	@Override
	public void updateBusinessMembers(
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserRolesJSONString, String removeUsersJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserService.updateBusinessMembers(
			cmicBusinessKey, updateUserRolesJSONString, removeUsersJSONString);
	}

	@Override
	public String updatePortraitImage(String imageFileString)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.updatePortraitImage(imageFileString);
	}

	@Override
	public String validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String registrationCode,
			String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.validateInsuredUserRegistration(
			cmicUserId, accountNumber, registrationCode, zipCode);
	}

	@Override
	public String validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber,
			String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.validateProducerUserRegistration(
			cmicUserId, agentNumber, divisionNumber, registrationCode, zipCode);
	}

	@Override
	public com.churchmutual.core.model.CMICUserDisplay
			validateUserRegistrationCode(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserService.validateUserRegistrationCode(registrationCode);
	}

	@Override
	public CMICUserService getWrappedService() {
		return _cmicUserService;
	}

	@Override
	public void setWrappedService(CMICUserService cmicUserService) {
		_cmicUserService = cmicUserService;
	}

	private CMICUserService _cmicUserService;

}