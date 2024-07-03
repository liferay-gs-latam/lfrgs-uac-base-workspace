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
 * Provides a wrapper for {@link CMICTransactionLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICTransactionLocalService
 * @generated
 */
public class CMICTransactionLocalServiceWrapper
	implements CMICTransactionLocalService,
			   ServiceWrapper<CMICTransactionLocalService> {

	public CMICTransactionLocalServiceWrapper(
		CMICTransactionLocalService cmicTransactionLocalService) {

		_cmicTransactionLocalService = cmicTransactionLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicTransactionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICTransactionDisplay>
			getTransactionDisplays(String policyNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicTransactionLocalService.getTransactionDisplays(
			policyNumber);
	}

	@Override
	public CMICTransactionLocalService getWrappedService() {
		return _cmicTransactionLocalService;
	}

	@Override
	public void setWrappedService(
		CMICTransactionLocalService cmicTransactionLocalService) {

		_cmicTransactionLocalService = cmicTransactionLocalService;
	}

	private CMICTransactionLocalService _cmicTransactionLocalService;

}