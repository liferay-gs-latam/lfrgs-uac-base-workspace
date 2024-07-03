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

import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICPDFDocumentDisplay;
import com.churchmutual.core.model.CMICUser;
import com.churchmutual.core.service.base.CMICLossRunLocalServiceBaseImpl;
import com.churchmutual.rest.PortalUserWebService;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import com.liferay.portal.kernel.model.User;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic loss run local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICLossRunLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthew Chan
 * @see CMICLossRunLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICLossRun", service = AopService.class)
public class CMICLossRunLocalServiceImpl extends CMICLossRunLocalServiceBaseImpl {

	@Override
	public CMICPDFDocumentDisplay generateLossRunsReport(long userId, CMICBusinessKey cmicBusinessKey, String requestReason)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CMICUser cmicUser = cmicUserPersistence.findByUserId(userId);

		long cmicUserId = cmicUser.getCmicUserId();

		Date claimHistoryEndDate = new Date();

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.YEAR, -1 * _CLAIM_HISTORY_RANGE_YEAR_SPAN);

		Date claimHistoryStartDate = cal.getTime();

		SimpleDateFormat dateFormat = new SimpleDateFormat(_CLAIM_HISTORY_DATE_FORMAT);

		boolean showClaimantInformation = false;
		boolean showReserveInformation = false;

		String lossRunsReportPDF = portalUserWebService.generateLossRunReport(
			cmicBusinessKey.getAccountNumber(), cmicUserId, user.getFirstName(), user.getLastName(), dateFormat.format(claimHistoryEndDate),
			dateFormat.format(claimHistoryStartDate), requestReason, showClaimantInformation, showReserveInformation);

		return new CMICPDFDocumentDisplay(lossRunsReportPDF);
	}

	@Reference
	protected PortalUserWebService portalUserWebService;

	private static final String _CLAIM_HISTORY_DATE_FORMAT = "yyyy-MM-dd";

	private static final int _CLAIM_HISTORY_RANGE_YEAR_SPAN = 6;

}