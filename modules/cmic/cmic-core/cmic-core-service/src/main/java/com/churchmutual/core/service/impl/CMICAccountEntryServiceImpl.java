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

import com.churchmutual.core.constants.CMICPermissionAction;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.service.CMICPermissionService;
import com.churchmutual.core.service.base.CMICAccountEntryServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic account entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICAccountEntryService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryServiceBaseImpl
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICAccountEntry"},
	service = AopService.class
)
public class CMICAccountEntryServiceImpl extends CMICAccountEntryServiceBaseImpl {

	@Override
	public CMICAccountEntryDisplay getCMICAccountEntryDisplay(CMICBusinessKey cmicBusinessKey) throws PortalException {
		if (cmicBusinessKey.isNotValid()) {
			return null;
		}

		cmicPermissionService.checkPermission(
			new CMICBusinessKey(cmicBusinessKey.getAccountNumber(), cmicBusinessKey.getCompanyNumber()),
			CMICPermissionAction.VIEW.getActionId());

		return cmicAccountEntryLocalService.getCMICAccountEntryDisplay(cmicBusinessKey);
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplays() throws PortalException {
		return cmicAccountEntryLocalService.getCMICAccountEntryDisplaysByUserId(getUserId());
	}

	@Override
	public String getCMICAccountEntryDisplaysCacheStatus() throws PortalException {
		return cmicAccountEntryLocalService.getCMICAccountEntryDisplaysCacheStatusByUserId(getUserId());
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysWithAddresses() throws PortalException {
		return cmicAccountEntryLocalService.getCMICAccountEntryDisplaysWithAddresses(getUserId());
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysWithPermission(String actionId)
		throws PortalException {

		return cmicAccountEntryLocalService.getCMICAccountEntryDisplaysWithPermission(getUserId(), actionId);
	}

	@Override
	public void refreshCMICAccountEntryDisplaysCache() throws PortalException {
		cmicAccountEntryLocalService.refreshCMICAccountEntryDisplaysByUserIdCache(getUserId());
	}

	@Reference
	protected CMICPermissionService cmicPermissionService;

}