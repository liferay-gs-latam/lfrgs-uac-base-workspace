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
 * Provides a wrapper for {@link CMICLossRunService}.
 *
 * @author Kayleen Lim
 * @see CMICLossRunService
 * @generated
 */
public class CMICLossRunServiceWrapper
	implements CMICLossRunService, ServiceWrapper<CMICLossRunService> {

	public CMICLossRunServiceWrapper(CMICLossRunService cmicLossRunService) {
		_cmicLossRunService = cmicLossRunService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICLossRunServiceUtil} to access the cmic loss run remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICLossRunServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.churchmutual.core.model.CMICPDFDocumentDisplay
			generateLossRunsReport(
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
				String requestReason)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicLossRunService.generateLossRunsReport(
			cmicBusinessKey, requestReason);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicLossRunService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICLossRunService getWrappedService() {
		return _cmicLossRunService;
	}

	@Override
	public void setWrappedService(CMICLossRunService cmicLossRunService) {
		_cmicLossRunService = cmicLossRunService;
	}

	private CMICLossRunService _cmicLossRunService;

}