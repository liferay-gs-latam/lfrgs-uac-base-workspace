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
 * Provides a wrapper for {@link CMICCommissionDocumentService}.
 *
 * @author Kayleen Lim
 * @see CMICCommissionDocumentService
 * @generated
 */
public class CMICCommissionDocumentServiceWrapper
	implements CMICCommissionDocumentService,
			   ServiceWrapper<CMICCommissionDocumentService> {

	public CMICCommissionDocumentServiceWrapper(
		CMICCommissionDocumentService cmicCommissionDocumentService) {

		_cmicCommissionDocumentService = cmicCommissionDocumentService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICCommissionDocumentServiceUtil} to access the cmic commission document remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICCommissionDocumentServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICCommissionDocumentDisplay
			downloadDocument(String id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicCommissionDocumentService.downloadDocument(id);
	}

	@Override
	public java.util.List
		<com.churchmutual.core.model.CMICCommissionDocumentDisplay>
				getCommissionDocuments()
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicCommissionDocumentService.getCommissionDocuments();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicCommissionDocumentService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICCommissionDocumentService getWrappedService() {
		return _cmicCommissionDocumentService;
	}

	@Override
	public void setWrappedService(
		CMICCommissionDocumentService cmicCommissionDocumentService) {

		_cmicCommissionDocumentService = cmicCommissionDocumentService;
	}

	private CMICCommissionDocumentService _cmicCommissionDocumentService;

}