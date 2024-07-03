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
 * Provides a wrapper for {@link CMICTransactionService}.
 *
 * @author Kayleen Lim
 * @see CMICTransactionService
 * @generated
 */
public class CMICTransactionServiceWrapper
	implements CMICTransactionService, ServiceWrapper<CMICTransactionService> {

	public CMICTransactionServiceWrapper(
		CMICTransactionService cmicTransactionService) {

		_cmicTransactionService = cmicTransactionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicTransactionService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICTransactionDisplay>
			getTransactionDisplays(String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicTransactionService.getTransactionDisplays(policyNumber);
	}

	@Override
	public CMICTransactionService getWrappedService() {
		return _cmicTransactionService;
	}

	@Override
	public void setWrappedService(
		CMICTransactionService cmicTransactionService) {

		_cmicTransactionService = cmicTransactionService;
	}

	private CMICTransactionService _cmicTransactionService;

}