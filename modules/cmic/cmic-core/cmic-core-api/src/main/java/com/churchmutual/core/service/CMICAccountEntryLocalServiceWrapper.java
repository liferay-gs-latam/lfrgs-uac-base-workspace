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
 * Provides a wrapper for {@link CMICAccountEntryLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryLocalService
 * @generated
 */
public class CMICAccountEntryLocalServiceWrapper
	implements CMICAccountEntryLocalService,
			   ServiceWrapper<CMICAccountEntryLocalService> {

	public CMICAccountEntryLocalServiceWrapper(
		CMICAccountEntryLocalService cmicAccountEntryLocalService) {

		_cmicAccountEntryLocalService = cmicAccountEntryLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAccountEntryLocalServiceUtil} to access the cmic account entry local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICAccountEntryDisplay
			getCMICAccountEntryDisplay(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.getCMICAccountEntryDisplay(
			cmicBusinessKey);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplays(java.util.List<String> recentAccounts)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.getCMICAccountEntryDisplays(
			recentAccounts);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByProducerCodes(String[] producerCodes)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysByProducerCodes(producerCodes);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysByUserId(userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByUserId(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysByUserId(userId, start, end);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByUserIdOrderedByName(
				long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysByUserIdOrderedByName(
				userId, start, end);
	}

	/**
	 * Use this method for quickly getting very few accounts.  This method will be fast for getting few accounts since
	 * it grabs them one by one, however, this will be slow when needed many accounts.
	 */
	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByUserIdUnsorted(long userId, int count)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysByUserIdUnsorted(userId, count);
	}

	@Override
	public String getCMICAccountEntryDisplaysCacheStatusByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysCacheStatusByUserId(userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysWithAddresses(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysWithAddresses(userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysWithPermission(
				long userId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.
			getCMICAccountEntryDisplaysWithPermission(userId, actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicAccountEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public String getProducerName(String producerCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAccountEntryLocalService.getProducerName(producerCode);
	}

	/**
	 * Refresh the policy account summary for producer users
	 */
	@Override
	public void refreshCMICAccountEntryDisplaysByUserIdCache(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAccountEntryLocalService.
			refreshCMICAccountEntryDisplaysByUserIdCache(userId);
	}

	@Override
	public CMICAccountEntryLocalService getWrappedService() {
		return _cmicAccountEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CMICAccountEntryLocalService cmicAccountEntryLocalService) {

		_cmicAccountEntryLocalService = cmicAccountEntryLocalService;
	}

	private CMICAccountEntryLocalService _cmicAccountEntryLocalService;

}