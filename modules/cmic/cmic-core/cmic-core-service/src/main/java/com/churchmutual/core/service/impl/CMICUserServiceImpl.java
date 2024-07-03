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

package com.churchmutual.core.service.impl;

import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.core.constants.CMICPermissionAction;
import com.churchmutual.core.constants.ProducerType;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICUserDisplay;
import com.churchmutual.core.service.CMICPermissionService;
import com.churchmutual.core.service.base.CMICUserServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.service.permission.UserPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICUser"},
	service = AopService.class
)
public class CMICUserServiceImpl extends CMICUserServiceBaseImpl {

	@Override
	public void addRecentlyViewedCMICAccount(CMICBusinessKey cmicBusinessKey) throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.UPDATE);

		cmicPermissionService.checkPermission(
			new CMICBusinessKey(cmicBusinessKey.getAccountNumber(), cmicBusinessKey.getCompanyNumber()),
			CMICPermissionAction.VIEW.getActionId());

		cmicUserLocalService.addRecentlyViewedCMICAccount(getUserId(), cmicBusinessKey);
	}

	@Override
	public void deletePortraitImage() throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.UPDATE);

		cmicUserLocalService.deletePortraitImage(getUserId());
	}

	@Override
	public List<CMICBusinessDisplay> getBusinessesWithPermission(String actionId) throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.VIEW);

		return cmicUserLocalService.getBusinessesWithPermission(getUserId(), actionId);
	}

	@Override
	public List<CMICUserDisplay> getBusinessMembers(CMICBusinessKey cmicBusinessKey) throws PortalException {
		cmicPermissionService.checkPermission(cmicBusinessKey, CMICPermissionAction.VIEW.getActionId());

		return cmicUserLocalService.getBusinessMembers(getUserId(), cmicBusinessKey);
	}

	@Override
	public JSONArray getBusinessRoles() throws PortalException {
		return cmicUserLocalService.getBusinessRoles(getUserId());
	}

	@Override
	public CMICUserDisplay getBusinessUserDetails(CMICBusinessKey cmicBusinessKey) throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.VIEW);

		return cmicUserLocalService.getBusinessUserDetails(getUserId(), cmicBusinessKey);
	}

	@Override
	public String[] getBusinessUserPermissions(CMICBusinessKey cmicBusinessKey) throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.VIEW);

		return cmicUserLocalService.getBusinessUserPermissions(getUserId(), cmicBusinessKey);
	}

	@Override
	public String getPortraitImageURL() throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.VIEW);

		return cmicUserLocalService.getPortraitImageURL(getUserId());
	}

	@Override
	public ProducerType[] getProducerTypesByUserId() throws PortalException {
		return cmicUserLocalService.getProducerTypesByUserId(getUserId());
	}

	@Override
	public List<CMICAccountEntryDisplay> getRecentlyViewedCMICAccountEntryDisplays() throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.VIEW);

		return cmicUserLocalService.getRecentlyViewedCMICAccountEntryDisplays(getUserId());
	}

	@Override
	public String getInsuredThemeSetting(String themeSettingKey) throws PortalException {
		Group group = groupService.getGroup(getUser().getCompanyId(), CMICSite.INSURED.getName());

		groupPermission.check(getPermissionChecker(), group, ActionKeys.VIEW);

		return cmicUserLocalService.getInsuredThemeSetting(getUserId(), themeSettingKey);
	}

	@Override
	public CMICUserDisplay getUserDetails() throws PortalException {
		return cmicUserLocalService.getUserDetails(getUserId());
	}

	@Override
	public String inviteBusinessMembers(CMICBusinessKey cmicBusinessKey, String emailAddresses) throws PortalException {
		cmicPermissionService.checkPermission(cmicBusinessKey, CMICPermissionAction.MANAGE_USERS.getActionId());

		return cmicUserLocalService.inviteBusinessMembers(getUserId(), cmicBusinessKey, emailAddresses);
	}

	@Override
	public void updateBusinessMembers(
			CMICBusinessKey cmicBusinessKey, String updateUserRolesJSONString, String removeUsersJSONString)
		throws PortalException {
		cmicPermissionService.checkPermission(cmicBusinessKey, CMICPermissionAction.MANAGE_USERS.getActionId());

		cmicUserLocalService.updateBusinessMembers(
			getUserId(), cmicBusinessKey, updateUserRolesJSONString, removeUsersJSONString);
	}

	@Override
	public void updateBusinessMemberPermissions(
			CMICBusinessKey cmicBusinessKey, String updateUserPermissionsJSONString)
		throws PortalException {
		cmicPermissionService.checkPermission(cmicBusinessKey, CMICPermissionAction.MANAGE_PERMISSIONS.getActionId());

		cmicUserLocalService.updateBusinessMemberPermissions(
			getUserId(), cmicBusinessKey, updateUserPermissionsJSONString);
	}

	@Override
	public String updatePortraitImage(String imageFileString) throws PortalException {
		userPermission.check(getPermissionChecker(), getUserId(), ActionKeys.UPDATE);

		return cmicUserLocalService.updatePortraitImage(getUserId(), imageFileString);
	}

	@Override
	public String validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String registrationCode, String zipCode)
		throws PortalException {

		return cmicUserLocalService.validateInsuredUserRegistration(
			getUserId(), cmicUserId, accountNumber, registrationCode, zipCode);
	}

	@Override
	public String validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber, String registrationCode, String zipCode)
		throws PortalException {

		return cmicUserLocalService.validateProducerUserRegistration(
			getUserId(), cmicUserId, agentNumber, divisionNumber, registrationCode, zipCode);
	}

	@Override
	public CMICUserDisplay validateUserRegistrationCode(String registrationCode) throws PortalException {
		return cmicUserLocalService.validateUserRegistrationCode(registrationCode);
	}

	@Reference
	protected CMICPermissionService cmicPermissionService;

	@Reference
	protected GroupPermission groupPermission;

	@Reference
	protected UserPermission userPermission;

}