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

import com.churchmutual.core.constants.ProducerType;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICUserDisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CMICUser. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Kayleen Lim
 * @see CMICUserServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CMICUserService extends BaseService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICUserServiceUtil} to access the cmic user remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addRecentlyViewedCMICAccount(CMICBusinessKey cmicBusinessKey)
		throws PortalException;

	public void deletePortraitImage() throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICBusinessDisplay> getBusinessesWithPermission(
			String actionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICUserDisplay> getBusinessMembers(
			CMICBusinessKey cmicBusinessKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray getBusinessRoles() throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CMICUserDisplay getBusinessUserDetails(
			CMICBusinessKey cmicBusinessKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String[] getBusinessUserPermissions(CMICBusinessKey cmicBusinessKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getInsuredThemeSetting(String themeSettingKey)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getPortraitImageURL() throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProducerType[] getProducerTypesByUserId() throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay>
			getRecentlyViewedCMICAccountEntryDisplays()
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CMICUserDisplay getUserDetails() throws PortalException;

	public String inviteBusinessMembers(
			CMICBusinessKey cmicBusinessKey, String emailAddresses)
		throws PortalException;

	public void updateBusinessMemberPermissions(
			CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws PortalException;

	public void updateBusinessMembers(
			CMICBusinessKey cmicBusinessKey, String updateUserRolesJSONString,
			String removeUsersJSONString)
		throws PortalException;

	public String updatePortraitImage(String imageFileString)
		throws PortalException;

	public String validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String registrationCode,
			String zipCode)
		throws PortalException;

	public String validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber,
			String registrationCode, String zipCode)
		throws PortalException;

	public CMICUserDisplay validateUserRegistrationCode(String registrationCode)
		throws PortalException;

}