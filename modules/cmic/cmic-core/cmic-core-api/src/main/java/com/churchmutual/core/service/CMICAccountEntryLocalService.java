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

import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.rest.model.*;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CMICAccountEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CMICAccountEntryLocalService extends BaseLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAccountEntryLocalServiceUtil} to access the cmic account entry local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAccountEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CMICAccountEntryDisplay getCMICAccountEntryDisplay(
			CMICBusinessKey cmicBusinessKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplays(
			List<String> recentAccounts)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByProducerCodes(String[] producerCodes)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByUserId(
			long userId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByUserId(
			long userId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByUserIdOrderedByName(
				long userId, int start, int end)
		throws PortalException;

	/**
	 * Use this method for quickly getting very few accounts.  This method will be fast for getting few accounts since
	 * it grabs them one by one, however, this will be slow when needed many accounts.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysByUserIdUnsorted(long userId, int count)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getCMICAccountEntryDisplaysCacheStatusByUserId(long userId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysWithAddresses(long userId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CMICAccountEntryDisplay>
			getCMICAccountEntryDisplaysWithPermission(
				long userId, String actionId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getProducerName(String producerCode) throws PortalException;

	/**
	 * Refresh the policy account summary for producer users
	 */
	public void refreshCMICAccountEntryDisplaysByUserIdCache(long userId)
		throws PortalException;

}