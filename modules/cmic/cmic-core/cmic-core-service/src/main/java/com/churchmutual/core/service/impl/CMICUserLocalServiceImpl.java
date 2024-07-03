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

import com.churchmutual.commons.constants.ExpandoConstants;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.commons.util.SiteUserUtil;
import com.churchmutual.core.constants.CMICCoreConstants;
import com.churchmutual.core.constants.CMICPermissionAction;
import com.churchmutual.core.constants.ProducerType;
import com.churchmutual.core.constants.RegistrationConstants;
import com.churchmutual.core.constants.SelfProvisioningConstants;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.model.CMICPermission;
import com.churchmutual.core.model.CMICUser;
import com.churchmutual.core.model.CMICUserDisplay;
import com.churchmutual.core.service.CMICAccountEntryLocalService;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.CMICPermissionLocalService;
import com.churchmutual.core.service.base.CMICUserLocalServiceBaseImpl;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.exception.SelfProvisioningException;
import com.churchmutual.rest.exception.UserRegistrationException;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.model.CMICUserRelationDTO;
import com.churchmutual.notification.NotificationEventSender;
import com.churchmutual.notification.constants.NotificationConstants;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICUser", service = AopService.class)
public class CMICUserLocalServiceImpl extends CMICUserLocalServiceBaseImpl {

	@Override
	public void addCMICUser(long cmicUserId, long userId) {
		CMICUser cmicUser = cmicUserLocalService.createCMICUser(cmicUserId);

		cmicUser.setUserId(userId);

		cmicUserLocalService.updateCMICUser(cmicUser);
	}

	@Override
	public void addRecentlyViewedCMICAccount(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		if (cmicBusinessKey.isNotValid()) {
			return;
		}

		User user = userLocalService.getUser(userId);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		String recentlyViewedCmicAccountNumbers = (String)expandoBridge.getAttribute(
			ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNTS, false);

		String formattedString = cmicBusinessKey.getFormattedString();

		if (recentlyViewedCmicAccountNumbers != null) {
			List<String> recentAccountNumbers = new ArrayList<>();

			Collections.addAll(recentAccountNumbers, StringUtil.split(recentlyViewedCmicAccountNumbers));

			recentAccountNumbers.remove(formattedString);

			recentAccountNumbers.add(0, formattedString);

			if (recentAccountNumbers.size() > _RECENT_ACCOUNT_ENTRIES_DISPLAY_COUNT) {
				recentAccountNumbers = recentAccountNumbers.subList(0, _RECENT_ACCOUNT_ENTRIES_DISPLAY_COUNT);
			}

			expandoBridge.setAttribute(
				ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNTS,
				StringUtil.merge(recentAccountNumbers, StringPool.COMMA), false);
		}
		else {
			expandoBridge.setAttribute(ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNTS, formattedString, false);
		}
	}

	@Override
	public void deletePortraitImage(long userId) throws PortalException {
		userLocalService.deletePortrait(userId);

		User user = userLocalService.getUser(userId);

		user.setPortraitId(0);

		userLocalService.updateUser(user);
	}

	@Override
	public CMICUser fetchCMICUserByUserId(long userId) {
		return cmicUserPersistence.fetchByUserId(userId);
	}

	@Override
	public User fetchUserByCmicUUID(long companyId, String cmicUUID) throws PortalException {
		return userLocalService.fetchUserByReferenceCode(companyId, cmicUUID);
	}

	@Override
	public List<CMICBusinessDisplay> getBusinessesWithPermission(long userId, String actionId) throws PortalException {
		updateBusinessPermissions(userId);

		CMICUserDisplay cmicUserDisplay = getUserDetails(userId);

		List<CMICBusinessDisplay> businesses = new ArrayList<>();

		if (Validator.isBlank(actionId)) {
			if (cmicUserDisplay.isProducer()) {
				businesses.addAll(cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId));
			}
			else {
				businesses.addAll(cmicAccountEntryLocalService.getCMICAccountEntryDisplaysByUserId(userId));
			}
		}
		else {
			if (cmicUserDisplay.isProducer()) {
				businesses.addAll(cmicOrganizationLocalService.getCMICOrganizationDisplaysWithPermission(
					userId, actionId));
			}
			else {
				businesses.addAll(cmicAccountEntryLocalService.getCMICAccountEntryDisplaysWithPermission(
					userId, actionId));
			}
		}

		return businesses;
	}

	@Override
	public List<CMICUserDisplay> getBusinessMembers(long userId, CMICBusinessKey cmicBusinessKey)
		throws PortalException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		List<CMICUserDTO> cmicUserDTOs;
		if (cmicBusinessKey.isProducer()) {
			cmicUserDTOs = portalUserWebService.getProducerEntityUsers(cmicBusinessKey.getProducerId());
		}
		else {
			cmicUserDTOs = portalUserWebService.getAccountEntityUsers(cmicBusinessKey.getAccountNumber());
		}

		List<CMICUserDisplay> cmicUserDisplays = new ArrayList<>();

		for (CMICUserDTO cmicUserDTO : cmicUserDTOs) {
			try {
				CMICUser cmicUser = cmicUserPersistence.fetchByPrimaryKey(cmicUserDTO.getId());

				if (cmicUser != null) {
					CMICUserDisplay cmicUserDisplay = getBusinessUserDetails(cmicUser.getUserId(), cmicBusinessKey);

					cmicUserDisplays.add(cmicUserDisplay);
				}
			}
			catch (PortalException pe) {
				_log.error(pe);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"getBusinessMembers for {userId %d, accountNumber %s, companyNumber %s, producerId %d} takes %d ms",
					userId, cmicBusinessKey.getAccountNumber(), cmicBusinessKey.getCompanyNumber(),
					cmicBusinessKey.getProducerId(), stopWatch.getTime()));
		}

		return cmicUserDisplays;
	}

	@Override
	public JSONArray getBusinessRoles(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		if (cmicUserDTO.isProducer()) {
			return _getRoles(BusinessRole.getBusinessRoles(BusinessPortalType.PRODUCER));
		}

		return _getRoles(BusinessRole.getBusinessRoles(BusinessPortalType.INSURED));
	}

	@Override
	public CMICUserDisplay getBusinessUserDetails(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		List<CMICUserRelationDTO> userRelationDTOs;

		CMICUserRelationDTO cmicUserRelationDTO = null;

		boolean isProducer = cmicBusinessKey.isProducer();

		BusinessRole businessRole = isProducer ? BusinessRole.ORGANIZATION_MEMBER : BusinessRole.ACCOUNT_MEMBER;

		if (isProducer) {
			userRelationDTOs = cmicUserDTO.getOrganizations();

			long producerId = cmicBusinessKey.getProducerId();

			cmicUserRelationDTO = userRelationDTOs.stream(
			).filter(
				rel -> producerId == rel.getProducerId()
			).findFirst(
			).orElse(null);

			if (cmicUserRelationDTO == null) {
				throw new PortalException(
					String.format("Could not find producer id %d for user %d", producerId, userId));
			}

			String shortenedNameKey = cmicUserRelationDTO.getRole();

			if (Validator.isNotNull(shortenedNameKey)) {
				businessRole = BusinessRole.fromShortenedNameKey(shortenedNameKey, BusinessPortalType.PRODUCER);
			}
		}
		else {
			userRelationDTOs = cmicUserDTO.getAccounts();

			String accountNumber = cmicBusinessKey.getAccountNumber();
			String companyNumber = cmicBusinessKey.getCompanyNumber();

			if (cmicUserDTO.isProducer()) {
				if (!cmicPermissionLocalService.isUserProducerForAccount(userId, cmicBusinessKey)) {
					throw new PortalException(
						String.format(
							"Could not find accountNumber %s and companyNumber %s for user %d", accountNumber,
							companyNumber, userId));
				}
			}
			else {
				cmicUserRelationDTO = userRelationDTOs.stream(
				).filter(
					rel -> accountNumber.equals(rel.getAccountNumber()) && companyNumber.equals(rel.getCompanyNumber())
				).findFirst(
				).orElse(null);

				if (cmicUserRelationDTO == null) {
					throw new PortalException(
						String.format(
							"Could not find accountNumber %s and companyNumber %s for user %d", accountNumber,
							companyNumber, userId));
				}
			}

			if (cmicUserRelationDTO != null) {
				String shortenedNameKey = cmicUserRelationDTO.getRole();

				if (Validator.isNotNull(shortenedNameKey)) {
					businessRole = BusinessRole.fromShortenedNameKey(shortenedNameKey, BusinessPortalType.INSURED);
				}
			}
		}

		CMICUserDisplay cmicUserDisplay = new CMICUserDisplay(
			userLocalService.getUser(userId), getPortraitImageURL(userId), cmicUserDTO);

		cmicUserDisplay.setBusinessRole(businessRole);

		BusinessUserStatus businessUserStatus = _getUserRegistrationStatus(cmicUserDTO);

		cmicUserDisplay.setBusinessUserStatus(businessUserStatus);

		List<CMICPermission> permissions = cmicPermissionPersistence.findByU_C(
			userId, cmicBusinessKey.getFormattedString());

		String permissionString = permissions.stream(
		).map(
			permission -> permission.getActionId()
		).collect(
			Collectors.joining(StringPool.COMMA)
		);

		cmicUserDisplay.setPermissions(permissionString);

		return cmicUserDisplay;
	}

	public String[] getBusinessUserPermissions(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		CMICUserDisplay cmicUserDisplay = getBusinessUserDetails(userId, cmicBusinessKey);

		String role = cmicUserDisplay.getRole();

		boolean includeOwnerOnlyViewPermissions = false;

		if (cmicBusinessKey.isProducer()) {

			if (!Validator.isBlank(cmicUserDisplay.getRole())) {
				BusinessRole businessRole = BusinessRole.fromShortenedNameKey(role, BusinessPortalType.PRODUCER);

				includeOwnerOnlyViewPermissions = BusinessRole.ORGANIZATION_OWNER.equals(businessRole);
			}

			return CMICPermissionAction.getProducerPermissionActions(
				false, includeOwnerOnlyViewPermissions
			).stream(
			).map(
				cmicPermissionAction -> cmicPermissionAction.getActionId()
			).toArray(
				String[]::new
			);
		}
		else {
			if (!Validator.isBlank(role)) {
				BusinessRole businessRole = BusinessRole.fromShortenedNameKey(role, BusinessPortalType.INSURED);

				includeOwnerOnlyViewPermissions = BusinessRole.ACCOUNT_OWNER.equals(businessRole);
			}

			return CMICPermissionAction.getInsuredPermissionActions(
				false, includeOwnerOnlyViewPermissions
			).stream(
			).map(
				cmicPermissionAction -> cmicPermissionAction.getActionId()
			).toArray(
				String[]::new
			);
		}
	}

	@Override
	public String getPortraitImageURL(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		if (user.getPortraitId() == 0) {
			return StringPool.BLANK;
		}

		String portraitURL = UserConstants.getPortraitURL(
			portal.getPathImage(), user.isMale(), user.getPortraitId(), user.getUserUuid());

		return portraitURL + "&timestamp=" + new Date().getTime();
	}

	@Override
	public ProducerType[] getProducerTypesByUserId(long userId) throws PortalException {
		List<CMICOrganizationDisplay> organizations = cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(
			userId);

		Set<ProducerType> producerTypes = new HashSet<>();

		for (CMICOrganizationDisplay organization : organizations) {
			producerTypes.add(organization.getProducerType());
		}

		return producerTypes.toArray(new ProducerType[0]);
	}

	@Override
	public List<CMICAccountEntryDisplay> getRecentlyViewedCMICAccountEntryDisplays(long userId) throws PortalException {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays;

		try {
			User user = userLocalService.getUser(userId);

			ExpandoBridge expandoBridge = user.getExpandoBridge();

			String recentlyViewedAccounts = (String)expandoBridge.getAttribute(
				ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNTS, false);

			if (_log.isDebugEnabled()) {
				_log.debug("Gettings recent viewed accounts for userId: " + userId + ", found recentlyViewedAccounts: " + recentlyViewedAccounts);
			}

			List<String> recentAccounts = new ArrayList<>();

			if (Validator.isNotNull(recentlyViewedAccounts)) {
				recentAccounts = Arrays.asList(StringUtil.split(recentlyViewedAccounts));
			}

			if (recentAccounts.size() == _RECENT_ACCOUNT_ENTRIES_DISPLAY_COUNT) {
				return cmicAccountEntryLocalService.getCMICAccountEntryDisplays(recentAccounts);
			}

			// There are not enough recent accounts, we will need to grab additional accounts to fill in the list.

			if (_log.isDebugEnabled()) {
				_log.debug("There are not enough recent viewed accounts for userId: " + userId + ", grabbing additional accounts to fill in the list");
			}

			int index = 0;

			List<CMICAccountEntryDisplay> existingCMICAccountEntryDisplays =
				cmicAccountEntryLocalService.getCMICAccountEntryDisplaysByUserIdUnsorted(
					userId, _RECENT_ACCOUNT_ENTRIES_DISPLAY_COUNT);

			while ((index < existingCMICAccountEntryDisplays.size()) &&
				   (recentAccounts.size() < _RECENT_ACCOUNT_ENTRIES_DISPLAY_COUNT)) {

				CMICAccountEntryDisplay cmicAccountEntryDisplay = existingCMICAccountEntryDisplays.get(index++);

				CMICBusinessKey cmicBusinessKey = new CMICBusinessKey(
					cmicAccountEntryDisplay.getAccountNumber(), cmicAccountEntryDisplay.getCompanyNumber());

				String formattedString = cmicBusinessKey.getFormattedString();

				if (!recentAccounts.contains(formattedString)) {
					recentAccounts.add(formattedString);
				}
			}

			expandoBridge.setAttribute(
				ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNTS,
				StringUtil.merge(recentAccounts, StringPool.COMMA), false);

			cmicAccountEntryDisplays =
				cmicAccountEntryLocalService.getCMICAccountEntryDisplays(recentAccounts);

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"getRecentlyViewedCMICAccountEntryDisplays for userId %d takes %d ms", userId,
						stopWatch.getTime()));
			}
		}
		catch (Exception e) {
			_log.error(e);

			throw new PortalException(e);
		}

		return cmicAccountEntryDisplays;
	}

	@Override
	public String getInsuredThemeSetting(long userId, String themeSettingKey) throws PortalException {
		User user = userLocalService.getUser(userId);

		Group group = groupLocalService.getGroup(user.getCompanyId(), CMICSite.INSURED.getName());

		Layout layout = layoutLocalService.getLayout(group.getDefaultPrivatePlid());

		return layout.getThemeSetting(themeSettingKey, "regular");
	}

	@Override
	public CMICUserDisplay getUserDetails(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new CMICUserDisplay(user, StringPool.BLANK, null);
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		return new CMICUserDisplay(user, getPortraitImageURL(userId), cmicUserDTO);
	}

	@Override
	public String inviteBusinessMembers(long userId, CMICBusinessKey cmicBusinessKey, String emailAddresses)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		List<String> newEmails = new ArrayList<>();

		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		long accountProducerId = 0L;

		if (SiteUserUtil.isProducerSiteUser(userId) && cmicBusinessKey.isValidAccountNumber()) {
			CMICAccountEntryDisplay cmicAccountEntryDisplay = cmicAccountEntryLocalService.getCMICAccountEntryDisplay(cmicBusinessKey);

			if (Validator.isNotNull(cmicAccountEntryDisplay.getProducerCode())) {
				CMICOrganizationDisplay cmicOrganizationDisplay =
					cmicOrganizationLocalService.getCMICOrganizationDisplayByProducerCode(
						cmicAccountEntryDisplay.getProducerCode());

				accountProducerId = cmicOrganizationDisplay.getProducerId();
			}
		}

		long cmicUserId = cmicUser.getCmicUserId();

		for (String email : _getEmailAddressesArray(emailAddresses)) {
			User curUser = userLocalService.fetchUserByEmailAddress(user.getCompanyId(), email);

			if (curUser == null) {
				newEmails.add(email);

				continue;
			}

			long curUserId = curUser.getUserId();

			CMICUser curCMICUser = cmicUserPersistence.fetchByUserId(curUserId);

			if (curCMICUser == null) {
				newEmails.add(email);
			}
			else {
				if (cmicBusinessKey.isProducer()) {
					if (SiteUserUtil.isInsuredSiteUser(curUserId)) {
						return SelfProvisioningConstants.PROVISIONING_USER_TYPE_ERROR;
					}

					try {
						portalUserWebService.provisionExistingProducerUser(
							cmicUserId, curCMICUser.getCmicUserId(), cmicBusinessKey.getProducerId(),
							curUser.getEmailAddress());
					}
					catch (SelfProvisioningException spe) {
						if (spe.getType() == SelfProvisioningException.DUPLICATE_USER) {
							return SelfProvisioningConstants.PROVISIONING_DUPLICATE_USER_ERROR;
						}
					}

					CMICOrganizationDisplay cmicOrganizationDisplay =
						cmicOrganizationLocalService.getCMICOrganizationDisplay(cmicBusinessKey.getProducerId());

					notificationEventSender.sendNotification(
						NotificationConstants.TYPE_INVITE_TO_PRODUCER, curUserId, userId, cmicOrganizationDisplay.getName());

				}
				else {
					if (SiteUserUtil.isProducerSiteUser(curUserId)) {
						return SelfProvisioningConstants.PROVISIONING_USER_TYPE_ERROR;
					}

					try {
						portalUserWebService.provisionExistingInsuredUser(
							cmicUserId, curCMICUser.getCmicUserId(), cmicBusinessKey.getAccountNumber(),
							cmicBusinessKey.getCompanyNumber(), accountProducerId, curUser.getEmailAddress());
					}
					catch (SelfProvisioningException spe) {
						if (spe.getType() == SelfProvisioningException.DUPLICATE_USER) {
							return SelfProvisioningConstants.PROVISIONING_DUPLICATE_USER_ERROR;
						}
					}

					CMICAccountEntryDisplay cmicAccountEntryDisplay =
						cmicAccountEntryLocalService.getCMICAccountEntryDisplay(cmicBusinessKey);

					notificationEventSender.sendNotification(
						NotificationConstants.TYPE_INVITE_TO_ACCOUNT, curUserId, userId, cmicAccountEntryDisplay.getName());
				}
			}
		}

		List<CMICUserDTO> cmicUserDTOs;

		if (cmicBusinessKey.isProducer()) {
			cmicUserDTOs = portalUserWebService.provisionNewProducerUsers(
				cmicUserId, cmicBusinessKey.getProducerId(), newEmails);
		}
		else {
			cmicUserDTOs = portalUserWebService.provisionNewInsuredUsers(
				cmicUserId, cmicBusinessKey.getAccountNumber(), cmicBusinessKey.getCompanyNumber(),
				accountProducerId, newEmails);
		}

		for (CMICUserDTO cmicUserDTO : cmicUserDTOs) {
			User invitedUser = _inviteBusinessMember(userId, cmicUserDTO.getId(), cmicUserDTO.getEmailId(), cmicBusinessKey.isProducer());

			cmicPermissionLocalService.addDefaultPermissions(invitedUser.getUserId(), cmicBusinessKey);
		}

		return SelfProvisioningConstants.PROVISIONING_SUCCESS;
	}

	@Override
	public boolean isDeactivated(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return false;
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		return _REGISTRATION_STATUS_DEACTIVATED.equalsIgnoreCase(cmicUserDTO.getRegistrationStatus());
	}

	@Override
	public boolean isUserRegistered(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return false;
		}

		return portalUserWebService.isUserRegistered(cmicUser.getCmicUserId());
	}

	@Override
	public void updateBusinessMembers(
			long userId, CMICBusinessKey cmicBusinessKey, String updateUserRolesJSONString,
			String removeUsersJSONString)
		throws PortalException {

		Map<Long, String> userRolesToUpdate = _deserializeUsers(updateUserRolesJSONString);

		Map<Long, String> userRolesToRemove = _deserializeUsers(removeUsersJSONString);

		boolean isProducer = cmicBusinessKey.isProducer();

		boolean ownerRoleUpdated = _isOwnerRoleChanged(userRolesToUpdate, isProducer);

		boolean ownerRoleRemoved = _isOwnerRoleChanged(userRolesToRemove, isProducer);

		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		long callerCMICUserId = cmicUser.getCmicUserId();

		boolean currentUserBusinessOwner = _isCurrentUserBusinessOwner(userId, cmicBusinessKey);

		if ((ownerRoleUpdated || ownerRoleRemoved) && !currentUserBusinessOwner) {
			throw new PrincipalException(
				"Error while updating members: User " + userId +
					" cannot update owners because they do not have the OWNER role");
		}

		_updateBusinessUserRoles(userId, callerCMICUserId, cmicBusinessKey, userRolesToUpdate);

		_removeBusinessEntityUsers(callerCMICUserId, cmicBusinessKey, userRolesToRemove);
	}

	@Override
	public void updateBusinessPermissions(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		boolean isProducer = cmicUserDTO.isProducer();

		List<CMICUserRelationDTO> cmicUserRelationDTOs =
			isProducer ? cmicUserDTO.getOrganizations() : cmicUserDTO.getAccounts();

		BusinessPortalType businessPortalType = isProducer ? BusinessPortalType.PRODUCER : BusinessPortalType.INSURED;

		String ownerRoleString = BusinessRole.getBusinessOwnerRole(businessPortalType).getShortenedNameKey();

		String memberRoleString = BusinessRole.getBusinessMemberRole(businessPortalType).getShortenedNameKey();

		List<CMICPermission> oldPermissionList = cmicPermissionPersistence.findByUserId(userId);

		Map<String, CMICBusinessKey> cmicBusinessKeys = new HashMap<>();

		for (CMICUserRelationDTO userRelation : cmicUserRelationDTOs) {
			String shortenedNameKey = userRelation.getRole();

			CMICBusinessKey cmicBusinessKey;

			if (isProducer) {
				cmicBusinessKey = new CMICBusinessKey(userRelation.getProducerId());
			}
			else {
				cmicBusinessKey = new CMICBusinessKey(userRelation.getAccountNumber(), userRelation.getCompanyNumber());
			}

			cmicBusinessKeys.put(cmicBusinessKey.getFormattedString(), cmicBusinessKey);

			List<CMICPermission> userPermissions = cmicPermissionPersistence.findByU_C(
				userId, cmicBusinessKey.getFormattedString());

			if (ownerRoleString.equals(shortenedNameKey)) {
				cmicPermissionLocalService.addOwnerPermissions(userId, cmicBusinessKey);
			}
			else if (userPermissions.isEmpty() && memberRoleString.equals(shortenedNameKey)) {
				cmicPermissionLocalService.addDefaultPermissions(userId, cmicBusinessKey);
			}
		}

		for (CMICPermission cmicPermission : oldPermissionList) {
			if (cmicBusinessKeys.get(cmicPermission.getCmicBusinessKey()) == null) {
				cmicPermissionLocalService.deleteCMICPermission(cmicPermission);
			}
		}
	}

	@Override
	public void updateBusinessPermissions(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		BusinessPortalType businessPortalType =
			cmicBusinessKey.isProducer() ? BusinessPortalType.PRODUCER : BusinessPortalType.INSURED;

		String ownerRoleString = BusinessRole.getBusinessOwnerRole(businessPortalType).getShortenedNameKey();

		String memberRoleString = BusinessRole.getBusinessMemberRole(businessPortalType).getShortenedNameKey();

		List<CMICPermission> userPermissions = cmicPermissionPersistence.findByU_C(
			userId, cmicBusinessKey.getFormattedString());

		CMICUserDisplay cmicUserDisplay = getBusinessUserDetails(userId, cmicBusinessKey);

		if (ownerRoleString.equals(cmicUserDisplay.getRole())) {
			cmicPermissionLocalService.addOwnerPermissions(userId, cmicBusinessKey);
		}
		else if (userPermissions.isEmpty() && memberRoleString.equals(cmicUserDisplay.getRole())) {
			cmicPermissionLocalService.addDefaultPermissions(userId, cmicBusinessKey);
		}
	}

	@Override
	public void updateBusinessMemberPermissions(
			long userId, CMICBusinessKey cmicBusinessKey, String updateUserPermissionsJSONString)
		throws PortalException {

		JSONArray userPermissionsJSONArray = jsonFactory.createJSONArray(updateUserPermissionsJSONString);

		for (int i = 0; i < userPermissionsJSONArray.length(); i++) {
			JSONObject userPermissionJSONObject = userPermissionsJSONArray.getJSONObject(i);

			long cmicUserId = Long.parseLong(userPermissionJSONObject.getString(_CMIC_USER_ID));

			CMICUser cmicUser = getCMICUser(cmicUserId);

			String permissionsString = userPermissionJSONObject.getString(_PERMISSIONS);

			List<String> actiondIds = ListUtil.fromArray(permissionsString.split(StringPool.COMMA));

			long curUserId = cmicUser.getUserId();

			List<CMICPermission> existingPermissions = cmicPermissionPersistence.findByU_C(
				curUserId, cmicBusinessKey.getFormattedString());

			for (CMICPermission existingPermission : existingPermissions) {
				String curActionId = existingPermission.getActionId();

				int index = actiondIds.indexOf(curActionId);
				if (index < 0) {
					cmicPermissionLocalService.deleteCMICPermission(existingPermission);
				}
				else {
					actiondIds.remove(curActionId);
				}
			}

			actiondIds = actiondIds.stream().filter(
				actionId -> !Validator.isBlank(actionId)
			).collect(
				Collectors.toList()
			);

			if (existingPermissions.isEmpty() && !actiondIds.isEmpty()) {
				cmicPermissionLocalService.addPermission(
					curUserId, cmicBusinessKey, CMICPermissionAction.VIEW.getActionId());
			}

			for (String actionId : actiondIds) {
				cmicPermissionLocalService.addPermission(curUserId, cmicBusinessKey, actionId);

				if (curUserId != userId) {
					_sendPermissionNotification(userId, curUserId, cmicBusinessKey, actionId);
				}
			}
		}
	}

	@Override
	public String updatePortraitImage(long userId, String imageFileString) throws PortalException {

		// remove file type information "data:image/png;base64," from the string

		String imageString = imageFileString.substring(imageFileString.indexOf(StringPool.COMMA) + 1);

		byte[] decodedImageFile = Base64.getDecoder(
		).decode(
			imageString
		);

		userLocalService.updatePortrait(userId, decodedImageFile);

		return getPortraitImageURL(userId);
	}

	@Override
	public String validateInsuredUserRegistration(
			long userId, long cmicUserId, String accountNumber, String registrationCode, String zipCode)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		try {
			portalUserWebService.validateInsuredUserRegistration(
				cmicUserId, accountNumber, CMICCoreConstants.DEFAULT_COMPANY_NUMBER, registrationCode, cmicUUID,
				zipCode);
		}
		catch (UserRegistrationException ure) {
			if (ure.getType() == UserRegistrationException.ZIP_CODE) {
				return RegistrationConstants.ZIP_CODE_ERROR;
			}

			return RegistrationConstants.ACCOUNT_NUMBER_ERROR;
		}

		_addUserToInsuredGroup(user);

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		// If CMICUser is not null AND matches the userId of the logged in user, then do nothing

		if ((cmicUser != null) && (cmicUser.getUserId() == userId)) {
			return RegistrationConstants.VALID;
		}

		if (cmicUser == null) {
			addCMICUser(cmicUserId, userId);
		}
		else if (cmicUser.getUserId() != userId) {
			long oldUserId = cmicUser.getUserId();

			userLocalService.deleteUser(oldUserId);

			cmicUser.setUserId(user.getUserId());

			updateCMICUser(cmicUser);
		}

		return RegistrationConstants.VALID;
	}

	@Override
	public String validateProducerUserRegistration(
			long userId, long cmicUserId, String agentNumber, String divisionNumber, String registrationCode,
			String zipCode)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		try {
			portalUserWebService.validateProducerUserRegistration(
				cmicUserId, agentNumber, divisionNumber, registrationCode, cmicUUID, zipCode);
		}
		catch (UserRegistrationException ure) {
			if (ure.getType() == UserRegistrationException.ZIP_CODE) {
				return RegistrationConstants.ZIP_CODE_ERROR;
			}

			return RegistrationConstants.PRODUCER_CODE_ERROR;
		}

		_addUserToProducerGroup(user);

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		// If CMICUser is not null AND matches the userId of the logged in user, then do nothing

		if ((cmicUser != null) && (cmicUser.getUserId() == userId)) {
			return RegistrationConstants.VALID;
		}

		if (cmicUser == null) {
			addCMICUser(cmicUserId, userId);
		}
		else if (cmicUser.getUserId() != userId) {
			long oldUserId = cmicUser.getUserId();

			userLocalService.deleteUser(oldUserId);

			cmicUser.setUserId(user.getUserId());

			updateCMICUser(cmicUser);
		}

		return RegistrationConstants.VALID;
	}

	@Override
	public CMICUserDisplay validateUserRegistrationCode(String registrationCode) throws PortalException {
		CMICUserDTO cmicUserDTO = portalUserWebService.validateUserRegistrationCode(registrationCode);

		return new CMICUserDisplay(null, StringPool.BLANK, cmicUserDTO);
	}

	@Reference
	protected CMICAccountEntryLocalService cmicAccountEntryLocalService;

	@Reference
	protected CMICOrganizationLocalService cmicOrganizationLocalService;

	@Reference
	protected CMICPermissionLocalService cmicPermissionLocalService;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected NotificationEventSender notificationEventSender;

	@Reference
	protected Portal portal;

	@Reference
	protected PortalUserWebService portalUserWebService;

	private void _addUserToInsuredGroup(User user) throws PortalException {
		Group insuredSiteGroup = groupLocalService.getFriendlyURLGroup(
			user.getCompanyId(), BusinessPortalType.INSURED.getFriendlyURL());

		userLocalService.addGroupUser(insuredSiteGroup.getGroupId(), user);
	}

	private void _addUserToProducerGroup(User user) throws PortalException {
		Group producerSiteGroup = groupLocalService.getFriendlyURLGroup(
			user.getCompanyId(), BusinessPortalType.PRODUCER.getFriendlyURL());

		userLocalService.addGroupUser(producerSiteGroup.getGroupId(), user);
	}

	private User _createOrFetchUser(long creatorUserId, long cmicUserId, String email) throws PortalException {
		User creatorUser = userLocalService.fetchUser(creatorUserId);

		User invitedUser = userLocalService.fetchUserByEmailAddress(creatorUser.getCompanyId(), email);

		if (invitedUser == null) {
			invitedUser = userLocalService.addUser(
				creatorUserId, creatorUser.getCompanyId(), SelfProvisioningConstants.AUTO_PASSWORD, StringPool.BLANK,
				StringPool.BLANK, SelfProvisioningConstants.AUTO_SCREEN_NAME, StringPool.BLANK, email, 0,
				StringPool.BLANK, LocaleUtil.getDefault(), SelfProvisioningConstants.NAME, StringPool.BLANK,
				SelfProvisioningConstants.NAME, 0, 0, SelfProvisioningConstants.IS_MALE,
				SelfProvisioningConstants.BIRTHDAY_MONTH, SelfProvisioningConstants.BIRTHDAY_DAY,
				SelfProvisioningConstants.BIRTHDAY_YEAR, StringPool.BLANK, null, null, null, null,
				SelfProvisioningConstants.SEND_EMAIL_NOTIFICATIONS, null);
		}

		CMICUser cmicUser = fetchCMICUser(cmicUserId);

		if (cmicUser == null) {
			addCMICUser(cmicUserId, invitedUser.getUserId());
		}

		return invitedUser;
	}

	private Map<Long, String> _deserializeUsers(String userRolesJSONString) throws PortalException {
		JSONArray userRolesJSONArray = jsonFactory.createJSONArray(userRolesJSONString);

		Map<Long, String> userRoles = new HashMap<>();

		for (int i = 0; i < userRolesJSONArray.length(); i++) {
			JSONObject userRoleJSONObject = userRolesJSONArray.getJSONObject(i);

			String userIdString = userRoleJSONObject.getString("userId");

			String roleName = userRoleJSONObject.getString(
				"role"
			).toLowerCase();

			userRoles.put(Long.valueOf(userIdString), roleName);
		}

		return userRoles;
	}

	private String[] _getEmailAddressesArray(String emailAddresses) {
		emailAddresses = emailAddresses.replaceAll(StringPool.SPACE, StringPool.BLANK);

		String[] emails = emailAddresses.split(StringPool.COMMA);

		return _getUniqueEntries(emails);
	}

	private JSONArray _getRoles(BusinessRole[] businessRoles) {
		JSONArray response = jsonFactory.createJSONArray();

		for (BusinessRole businessRole : businessRoles) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("label", businessRole.getShortenedNameKey());
			jsonObject.put("value", businessRole.getMessageKey());

			response.put(jsonObject);
		}

		return response;
	}

	private String[] _getUniqueEntries(String[] array) {
		Set<String> set = new HashSet<>(Arrays.asList(array));

		return set.toArray(new String[0]);
	}

	private BusinessUserStatus _getUserRegistrationStatus(CMICUserDTO cmicUserDTO) {
		BusinessUserStatus businessUserStatus = BusinessUserStatus.INVITED;

		if (_REGISTRATION_STATUS_REGISTERED.equalsIgnoreCase(cmicUserDTO.getRegistrationStatus())) {
			businessUserStatus = BusinessUserStatus.ACTIVE;
		}

		return businessUserStatus;
	}

	private User _inviteBusinessMember(long userId, long cmicUserId, String email, boolean isProducer)
		throws PortalException {

		User invitedUser = _createOrFetchUser(userId, cmicUserId, email);

		if (isProducer) {
			_addUserToProducerGroup(invitedUser);
		}
		else {
			_addUserToInsuredGroup(invitedUser);
		}

		return invitedUser;
	}

	private boolean _isCurrentUserBusinessOwner(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		if (cmicBusinessKey.isProducer()) {
			List<CMICUserRelationDTO> organizations = cmicUserDTO.getOrganizations();

			for (CMICUserRelationDTO organization : organizations) {
				if (organization.getProducerId() == cmicBusinessKey.getProducerId()) {
					BusinessRole businessRole = BusinessRole.fromShortenedNameKey(
						organization.getRole(), BusinessPortalType.PRODUCER);

					if (BusinessRole.ORGANIZATION_OWNER.equals(businessRole)) {
						return true;
					}
				}
			}
		}
		else {
			List<CMICUserRelationDTO> accounts = cmicUserDTO.getAccounts();

			for (CMICUserRelationDTO account : accounts) {
				String accountNumber = account.getAccountNumber();
				String companyNumber = account.getCompanyNumber();

				if (accountNumber.equals(cmicBusinessKey.getAccountNumber()) &&
					companyNumber.equals(cmicBusinessKey.getCompanyNumber())) {

					BusinessRole businessRole = BusinessRole.fromShortenedNameKey(
						account.getRole(), BusinessPortalType.INSURED);

					if (BusinessRole.ACCOUNT_OWNER.equals(businessRole)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean _isOwnerRoleChanged(Map<Long, String> userRolesToUpdate, boolean isProducer)
		throws PortalException {

		for (Map.Entry<Long, String> userRoleToUpdate : userRolesToUpdate.entrySet()) {
			BusinessPortalType businessPortalType =
				isProducer ? BusinessPortalType.PRODUCER : BusinessPortalType.INSURED;

			BusinessRole businessRole = BusinessRole.fromShortenedNameKey(
				userRoleToUpdate.getValue(), businessPortalType);

			if (isProducer && BusinessRole.ORGANIZATION_OWNER.equals(businessRole)) {
				return true;
			}
			else if (!isProducer && BusinessRole.ACCOUNT_OWNER.equals(businessRole)) {
				return true;
			}
		}

		return false;
	}

	private void _removeBusinessEntityUsers(
			long callerCMICUserId, CMICBusinessKey cmicBusinessKey, Map<Long, String> userRolesToRemove)
		throws PortalException {

		for (Long userId : userRolesToRemove.keySet()) {
			CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

			if (cmicBusinessKey.isProducer()) {
				portalUserWebService.deleteProducerEntityUser(
					callerCMICUserId, cmicUser.getCmicUserId(), cmicBusinessKey.getProducerId());
			}
			else {
				portalUserWebService.deleteInsuredEntityUser(
					callerCMICUserId, cmicUser.getCmicUserId(), cmicBusinessKey.getAccountNumber(),
					cmicBusinessKey.getCompanyNumber());
			}

			cmicPermissionLocalService.deletePermissions(userId, cmicBusinessKey);
		}
	}

	private void _sendPermissionNotification(
			long userId, long recipientUserId, CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {

		CMICPermissionAction permissionAction = CMICPermissionAction.getPermissionActionFromActionId(actionId);

		int notificationType = -1;

		if (permissionAction == null) {
			return;
		}
		else if (CMICPermissionAction.MANAGE_PERMISSIONS.equals(permissionAction)) {
			notificationType = NotificationConstants.TYPE_GRANT_MANAGE_PERMISSIONS_PERMISSION;
		}
		else if (CMICPermissionAction.MANAGE_USERS.equals(permissionAction)) {
			notificationType = NotificationConstants.TYPE_GRANT_MANAGE_USERS_PERMISSION;
		}
		else if (CMICPermissionAction.REQUEST_COI_EOP.equals(permissionAction)) {
			notificationType = NotificationConstants.TYPE_GRANT_DOWNLOAD_COI_EOP_PERMISSION;
		}
		else if (CMICPermissionAction.REQUEST_LOSS_RUNS.equals(permissionAction)) {
			notificationType = NotificationConstants.TYPE_GRANT_DOWNLOAD_LOSS_RUNS_PERMISSION;
		}
		else if (CMICPermissionAction.VIEW_COMMISSION_STATEMENTS.equals(permissionAction)) {
			notificationType = NotificationConstants.TYPE_GRANT_VIEW_DOWNLOAD_COMMISSION_STATEMENTS_PERMISSION;
		}
		else if (CMICPermissionAction.VIEW_POLICY_DOCUMENTS.equals(permissionAction)) {
			notificationType = NotificationConstants.TYPE_GRANT_DOWNLOAD_POLICY_TRANSACTION_DOCUMENTS_PERMISSION;
		}

		if (notificationType > -1) {
			if (cmicBusinessKey.isProducer()) {
				CMICOrganizationDisplay cmicOrganizationDisplay =
					cmicOrganizationLocalService.getCMICOrganizationDisplay(cmicBusinessKey.getProducerId());

				notificationEventSender.sendNotification(
					notificationType, recipientUserId, userId, cmicOrganizationDisplay.getName());
			}
			else {
				CMICAccountEntryDisplay cmicAccountEntryDisplay =
					cmicAccountEntryLocalService.getCMICAccountEntryDisplay(cmicBusinessKey);

				notificationEventSender.sendNotification(
					notificationType, recipientUserId, userId, cmicAccountEntryDisplay.getName());
			}
		}
	}

	private void _updateBusinessUserRoles(
			long userId, long callerCMICUserId, CMICBusinessKey cmicBusinessKey, Map<Long, String> userRolesToUpdate)
		throws PortalException {

		boolean isProducer = cmicBusinessKey.isProducer();

		for (Map.Entry<Long, String> userRoleToUpdate : userRolesToUpdate.entrySet()) {
			Long curUserId = userRoleToUpdate.getKey();

			CMICUser curCMICUser = cmicUserPersistence.findByUserId(curUserId);

			String roleName = userRoleToUpdate.getValue();

			if (isProducer) {
				BusinessRole businessRole = BusinessRole.fromShortenedNameKey(roleName, BusinessPortalType.PRODUCER);

				portalUserWebService.updateProducerUserRole(
					callerCMICUserId, curCMICUser.getCmicUserId(), cmicBusinessKey.getProducerId(),
					businessRole.getShortenedNameKey());

				cmicPermissionLocalService.deletePermissions(curUserId, cmicBusinessKey);

				if (businessRole.equals(BusinessRole.ORGANIZATION_MEMBER)) {
					cmicPermissionLocalService.addDefaultPermissions(curUserId, cmicBusinessKey);
				}
				else if (businessRole.equals(BusinessRole.ORGANIZATION_OWNER)) {
					List<CMICPermissionAction> cmicPermissionActions = CMICPermissionAction.getProducerPermissionActions(true, true);

					for (CMICPermissionAction cmicPermissionAction : cmicPermissionActions) {
						cmicPermissionLocalService.addPermission(curUserId, cmicBusinessKey, cmicPermissionAction.getActionId());
					}
				}

				if (callerCMICUserId != curCMICUser.getCmicUserId()) {
					if (BusinessRole.ORGANIZATION_OWNER.equals(businessRole)) {
						CMICOrganizationDisplay cmicOrganizationDisplay =
							cmicOrganizationLocalService.getCMICOrganizationDisplay(cmicBusinessKey.getProducerId());

						notificationEventSender.sendNotification(
							NotificationConstants.TYPE_UPDATE_ROLE_TO_OWNER, curUserId, userId,
							cmicOrganizationDisplay.getName());
					}
				}
			}
			else {
				BusinessRole businessRole = BusinessRole.fromShortenedNameKey(roleName, BusinessPortalType.INSURED);

				portalUserWebService.updateInsuredUserRole(
					callerCMICUserId, curCMICUser.getCmicUserId(), cmicBusinessKey.getAccountNumber(),
					cmicBusinessKey.getCompanyNumber(), businessRole.getShortenedNameKey());

				cmicPermissionLocalService.deletePermissions(curUserId, cmicBusinessKey);

				if (businessRole.equals(BusinessRole.ACCOUNT_MEMBER)) {
					cmicPermissionLocalService.addDefaultPermissions(curUserId, cmicBusinessKey);
				}
				else if (businessRole.equals(BusinessRole.ACCOUNT_OWNER)) {
					List<CMICPermissionAction> cmicPermissionActions = CMICPermissionAction.getInsuredPermissionActions(true, true);

					for (CMICPermissionAction cmicPermissionAction : cmicPermissionActions) {
						cmicPermissionLocalService.addPermission(curUserId, cmicBusinessKey, cmicPermissionAction.getActionId());
					}
				}

				if (callerCMICUserId != curCMICUser.getCmicUserId()) {
					if (BusinessRole.ACCOUNT_OWNER.equals(businessRole)) {
						CMICAccountEntryDisplay cmicAccountEntryDisplay =
							cmicAccountEntryLocalService.getCMICAccountEntryDisplay(cmicBusinessKey);

						notificationEventSender.sendNotification(
							NotificationConstants.TYPE_UPDATE_ROLE_TO_OWNER, curUserId, userId,
							cmicAccountEntryDisplay.getName());
					}
				}
			}
		}
	}

	private static final String _CMIC_USER_ID = "CMICUserId";

	private static final int _RECENT_ACCOUNT_ENTRIES_DISPLAY_COUNT = 5;

	private static final String _REGISTRATION_STATUS_DEACTIVATED = "Deactivated";

	private static final String _REGISTRATION_STATUS_REGISTERED = "Registered";

	private static final String _PERMISSIONS = "permissions";

	private static final Log _log = LogFactoryUtil.getLog(CMICUserLocalServiceImpl.class);

}