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

package com.churchmutual.portal.security.sso.openid.connect.internal;

import com.churchmutual.commons.constants.ExpandoConstants;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.id.Subject;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Copy of com.liferay.portal.security.sso.openid.connect.internal.OpenIdConnectUserInfoProcessorImpl
 * For customizing user info processing
 *
 * @author Michael C. Han
 */
@Component(immediate = true, service = CMICOpenIdConnectUserInfoProcessor.class)
public class CMICOpenIdConnectUserInfoProcessorImpl implements CMICOpenIdConnectUserInfoProcessor {

	@Override
	public long processUserInfo(UserInfo userInfo, long companyId) throws PortalException {
		String emailAddress = userInfo.getEmailAddress();

		// CMIC uuid is passed in the "sub" claim

		Subject subject = userInfo.getSubject();

		String uuid = subject.getValue();

		User user = _userLocalService.fetchUserByReferenceCode(companyId, uuid);

		String firstName = userInfo.getGivenName();
		String lastName = userInfo.getFamilyName();

		if (user == null) {
			user = _userLocalService.fetchUserByEmailAddress(companyId, emailAddress);
		}

		if (user != null) {

			// CUSTOM START- update user info

			user.setFirstName(firstName);
			user.setLastName(lastName);

			if (Validator.isBlank(user.getExternalReferenceCode()) || user.getExternalReferenceCode().equals(user.getUuid())) {
				user.setExternalReferenceCode(uuid);
			}

			user = _userLocalService.updateUser(user);

			if (!user.getEmailAddress().equals(emailAddress)) {
				user = _userLocalService.updateEmailAddress(
					user.getUserId(), user.getPassword(), emailAddress, emailAddress);
			}

			setCMICEmployeeUserGroups(userInfo, user);

			// CUSTOM END

			return user.getUserId();
		}

		checkAddUser(companyId, emailAddress, userInfo.getIssuer());

		if (Validator.isNull(firstName) || Validator.isNull(lastName) || Validator.isNull(emailAddress)) {
			StringBundler sb = new StringBundler(8);

			sb.append("Unable to map OpenId Connect user to the portal, missing or invalid profile information: ");
			sb.append("{emailAddresss=");
			sb.append(emailAddress);
			sb.append(", firstName=");
			sb.append(firstName);
			sb.append(", lastName=");
			sb.append(lastName);
			sb.append("}");

			throw new OpenIdConnectServiceException.UserMappingException(sb.toString());
		}

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;

		Company company = _companyLocalService.getCompany(companyId);

		Locale locale = company.getLocale();

		String middleName = userInfo.getMiddleName();
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2, autoScreenName, screenName, emailAddress,
			facebookId, null, locale, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		// CUSTOM START- save uuid

		user.setExternalReferenceCode(uuid);

		setCMICEmployeeUserGroups(userInfo, user);

		user = _userLocalService.updateUser(user);

		// CUSTOM END

		return user.getUserId();
	}

	protected void checkAddUser(long companyId, String emailAddress, Issuer issuer) throws PortalException {

		// CUSTOM START- If request is from Church Mutual Azure AD, then allow strangers

		String issuerValue = issuer.getValue();

		if (issuerValue.contains(PropsUtil.get(CMICOpenIdPropsKeys.AZURE_AD_ISSUER_URL))) {
			return;
		}

		// CUSTOM END

		Company company = _companyLocalService.getCompany(companyId);

		if (!company.isStrangers()) {
			throw new PortalException(String.format("Company %s does not allow strangers", companyId));
		}

		if (!company.isStrangersWithMx() && company.hasCompanyMx(emailAddress)) {
			throw new UserEmailAddressException.MustNotUseCompanyMx(emailAddress);
		}
	}

	protected void setCMICEmployeeUserGroups(UserInfo userInfo, User user) throws PortalException {
		if (userInfo.getClaim(PropsUtil.get(CMICOpenIdPropsKeys.GROUPS_CLAIM_NAME)) == null) {
			return;
		}

		String groupsClaimJSONArrayString = userInfo.getClaim(
			PropsUtil.get(CMICOpenIdPropsKeys.GROUPS_CLAIM_NAME)).toString();

		JSONArray groupsClaim = _jsonFactory.createJSONArray(groupsClaimJSONArrayString);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		boolean cmicEmployee = false;

		long companyId = user.getCompanyId();

		long creatorUserId = _userLocalService.getDefaultUserId(companyId);

		long userId = user.getUserId();

		List<UserGroup> userGroups = _userGroupLocalService.getUserUserGroups(userId);

		String cmicEmployeeGroupsPrefix = PropsUtil.get(CMICOpenIdPropsKeys.EMPLOYEE_GROUPS_PREFIX);

		List<UserGroup> liferayUserGroups = userGroups.stream(
		).filter(
			userGroup -> userGroup.getName().startsWith(cmicEmployeeGroupsPrefix)
		).collect(
			Collectors.toList()
		);

		_userGroupLocalService.deleteUserUserGroups(userId, liferayUserGroups);

		for (int i = 0; i < groupsClaim.length(); i++) {
			String groupName = groupsClaim.getString(i);

			if (groupName.startsWith(cmicEmployeeGroupsPrefix)) {
				cmicEmployee = true;

				UserGroup userGroup = _userGroupLocalService.fetchUserGroup(companyId, groupName);

				if (userGroup == null) {
					userGroup = _userGroupLocalService.addUserGroup(creatorUserId, companyId, groupName, null, null);
				}

				_userGroupLocalService.addUserUserGroup(userId, userGroup.getUserGroupId());
			}
		}

		expandoBridge.setAttribute(ExpandoConstants.CMIC_EMPLOYEE, cmicEmployee, false);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}