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

import com.churchmutual.commons.util.SiteUserUtil;
import com.churchmutual.core.constants.CMICPermissionAction;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.model.CMICPermission;
import com.churchmutual.core.service.CMICAccountEntryLocalService;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICPermissionLocalServiceBaseImpl;
import com.churchmutual.core.service.persistence.CMICPermissionPK;
import com.churchmutual.rest.PortalUserWebService;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic permission local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICPermissionLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICPermissionLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICPermission", service = AopService.class)
public class CMICPermissionLocalServiceImpl extends CMICPermissionLocalServiceBaseImpl {

	public void addDefaultPermissions(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		List<CMICPermissionAction> defaultPermissionActions;

		if (cmicBusinessKey.isProducer()) {
			defaultPermissionActions = CMICPermissionAction.getDefaultProducerPermissionActions();
		}
		else {
			defaultPermissionActions = CMICPermissionAction.getDefaultInsuredPermissionActions();
		}

		for (CMICPermissionAction defaultPermissionAction : defaultPermissionActions) {
			cmicPermissionLocalService.addPermission(userId, cmicBusinessKey, defaultPermissionAction.getActionId());
		}
	}

	public void addOwnerPermissions(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		List<CMICPermissionAction> ownerPermissionActions;

		if (cmicBusinessKey.isProducer()) {
			ownerPermissionActions = CMICPermissionAction.getProducerPermissionActions(true, true);
		}
		else {
			ownerPermissionActions = CMICPermissionAction.getInsuredPermissionActions(true, true);
		}

		for (CMICPermissionAction ownerPermissionAction : ownerPermissionActions) {
			cmicPermissionLocalService.addPermission(userId, cmicBusinessKey, ownerPermissionAction.getActionId());
		}
	}

	public CMICPermission addPermission(long userId, CMICBusinessKey cmicBusinessKey, String actionId) {
		CMICPermission cmicPermission = cmicPermissionPersistence.fetchByU_C_A(
			userId, cmicBusinessKey.getFormattedString(), actionId);

		if (cmicPermission != null) {
			return cmicPermission;
		}

		cmicPermission = cmicPermissionPersistence.create(
		new CMICPermissionPK(userId, cmicBusinessKey.getFormattedString(), actionId));

		cmicPermission.setCmicBusinessKey(cmicBusinessKey.getFormattedString());
		cmicPermission.setActionId(actionId);

		return cmicPermissionPersistence.update(cmicPermission);
	}

	public void checkPermission(long userId, CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		if (!hasPermission(userId, cmicBusinessKey, actionId)) {
			throw new PrincipalException.MustHavePermission(userId, cmicBusinessKey.getFormattedString(), actionId);
		}
	}

	public void deletePermission(long userId, CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		cmicPermissionPersistence.removeByU_C_A(userId, cmicBusinessKey.getFormattedString(), actionId);
	}

	public void deletePermissions(long userId, CMICBusinessKey cmicBusinessKey) {
		cmicPermissionPersistence.removeByU_C(userId, cmicBusinessKey.getFormattedString());
	}

	public boolean hasPermission(long userId, CMICBusinessKey cmicBusinessKey, String actionId) throws PortalException {
		CMICPermission permission = cmicPermissionPersistence.fetchByU_C_A(
			userId, cmicBusinessKey.getFormattedString(), actionId);

		if (permission != null) {
			return true;
		}

		if (!cmicBusinessKey.isProducer() && SiteUserUtil.isProducerSiteUser(userId) &&
			isUserProducerForAccount(userId, cmicBusinessKey)) {

			return true;
		}

		return false;
	}

	public boolean hasPermission(long userId, String actionId) {
		List<CMICPermission> cmicPermissions = cmicPermissionPersistence.findByU_A(userId, actionId);

		return ListUtil.isNotEmpty(cmicPermissions);
	}

	public boolean isUserProducerForAccount(long userId, CMICBusinessKey cmicBusinessKey) throws PortalException {
		List<CMICOrganizationDisplay> cmicOrganizationDisplays =
			cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId);

		String[] producerCodes = cmicOrganizationDisplays.stream(
		).map(
			cmicOrganizationDisplay ->
				cmicOrganizationDisplay.getDivisionNumber() + cmicOrganizationDisplay.getAgentNumber()
		).toArray(
			String[]::new
		);

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays =
			cmicAccountEntryLocalService.getCMICAccountEntryDisplaysByProducerCodes(producerCodes);

		for (CMICAccountEntryDisplay cmicAccountEntryDisplay : cmicAccountEntryDisplays) {
			CMICBusinessKey existingCMICBusinessKey = new CMICBusinessKey(
				cmicAccountEntryDisplay.getAccountNumber(), cmicAccountEntryDisplay.getCompanyNumber());

			if (cmicBusinessKey.equals(existingCMICBusinessKey)) {
				return true;
			}
		}

		return false;
	}

	@Reference
	protected CMICAccountEntryLocalService cmicAccountEntryLocalService;

	@Reference
	protected CMICOrganizationLocalService cmicOrganizationLocalService;

	@Reference
	protected PortalUserWebService portalUserWebService;

}