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
import com.churchmutual.core.model.CMICPolicyDisplay;
import com.churchmutual.core.model.CMICTransactionDisplay;
import com.churchmutual.core.service.CMICPermissionService;
import com.churchmutual.core.service.CMICQsPolicyLocalService;
import com.churchmutual.core.service.base.CMICTransactionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the cmic transaction remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICTransactionService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICTransactionServiceBaseImpl
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICTransaction"},
	service = AopService.class
)
public class CMICTransactionServiceImpl extends CMICTransactionServiceBaseImpl {

	@Override
	public List<CMICTransactionDisplay> getTransactionDisplays(String policyNumber) throws PortalException {
		if (Validator.isBlank(policyNumber)) {
			return new ArrayList<>();
		}

		CMICPolicyDisplay cmicPolicyDisplay = cmicQsPolicyLocalService.getPolicyByPolicyNumber(policyNumber);

		cmicPermissionService.checkPermission(
			new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber()),
			CMICPermissionAction.VIEW.getActionId());

		return cmicTransactionLocalService.getTransactionDisplays(policyNumber);
	}

	@Reference
	protected CMICPermissionService cmicPermissionService;

	@Reference
	protected CMICQsPolicyLocalService cmicQsPolicyLocalService;

}