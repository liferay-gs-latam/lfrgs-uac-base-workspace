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
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICCommissionDocumentDisplay;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.CMICPermissionService;
import com.churchmutual.core.service.base.CMICCommissionDocumentServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic commission document remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICCommissionDocumentService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICCommissionDocumentServiceBaseImpl
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICCommissionDocumentDisplay"},
	service = AopService.class
)
public class CMICCommissionDocumentServiceImpl extends CMICCommissionDocumentServiceBaseImpl {

	@Override
	public CMICCommissionDocumentDisplay downloadDocument(String id) throws PortalException {
		CMICCommissionDocumentDisplay commissionDocumentDisplay =
			cmicCommissionDocumentLocalService.fetchCMICCommissionDocumentDisplayFromCache(id);

		if (commissionDocumentDisplay == null) {
			throw new PrincipalException("Could not get producer code for document id: " + id);
		}

		CMICOrganizationDisplay cmicOrganizationDisplay =
			cmicOrganizationLocalService.getCMICOrganizationDisplayByAgentNumberDivisionNumber(
				commissionDocumentDisplay.getAgentNumber(), commissionDocumentDisplay.getDivisionNumber());

		cmicPermissionService.checkPermission(
			new CMICBusinessKey(cmicOrganizationDisplay.getProducerId()),
			CMICPermissionAction.VIEW_COMMISSION_STATEMENTS.getActionId());

		return cmicCommissionDocumentLocalService.downloadDocument(id);
	}

	@Override
	public List<CMICCommissionDocumentDisplay> getCommissionDocuments() throws PortalException {
		return cmicCommissionDocumentLocalService.getCommissionDocuments(getUserId());
	}

	@Reference
	protected CMICOrganizationLocalService cmicOrganizationLocalService;

	@Reference
	protected CMICPermissionService cmicPermissionService;

}