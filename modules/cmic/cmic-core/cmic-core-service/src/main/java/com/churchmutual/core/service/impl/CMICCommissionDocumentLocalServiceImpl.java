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
import com.churchmutual.core.constants.CommissionDocumentType;
import com.churchmutual.core.exception.NoSuchCMICCommissionDocumentException;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICCommissionDocumentDisplay;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.CMICPermissionLocalService;
import com.churchmutual.core.service.base.CMICCommissionDocumentLocalServiceBaseImpl;
import com.churchmutual.rest.CommissionDocumentWebService;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICCommissionDocumentDTO;
import com.churchmutual.rest.model.CMICFileDTO;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic commission document local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICCommissionDocumentLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICCommissionDocumentLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.churchmutual.core.model.CMICCommissionDocumentDisplay", service = AopService.class
)
public class CMICCommissionDocumentLocalServiceImpl extends CMICCommissionDocumentLocalServiceBaseImpl {

	public static final String DOCUMENT_ID_TO_PRODUCER_CODE_CACHE_NAME =
		CMICCommissionDocumentLocalServiceImpl.class.getName() + "_DOCUMENT_ID_TO_PRODUCER_CODE";

	@Override
	public CMICCommissionDocumentDisplay downloadDocument(String id) throws PortalException {
		List<CMICFileDTO> cmicFileDTOS = commissionDocumentWebService.downloadDocuments(new String[] {id}, true);

		if (ListUtil.isEmpty(cmicFileDTOS)) {
			throw new NoSuchCMICCommissionDocumentException(id);
		}

		return new CMICCommissionDocumentDisplay(cmicFileDTOS.get(0));
	}

	public CMICCommissionDocumentDisplay fetchCMICCommissionDocumentDisplayFromCache(String id) {
		return _documentIdToProducerCodeCache.get(id);
	}

	@Override
	public List<CMICCommissionDocumentDisplay> getCommissionDocuments(long userId) throws PortalException {
		List<CMICCommissionDocumentDisplay> cmicCommissionDocumentDisplays = new ArrayList<>();

		List<CMICOrganizationDisplay> cmicOrganizationDisplays =
			cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId);

		LocalDate now = LocalDate.now();

		LocalDate thirteenMonthsPrior = now.minusMonths(13);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String maximumStatementDate = now.format(formatter);
		String minimumStatementDate = thirteenMonthsPrior.format(formatter);

		String actionId = CMICPermissionAction.VIEW_COMMISSION_STATEMENTS.getActionId();

		for (CMICOrganizationDisplay cmicOrganizationDisplay : cmicOrganizationDisplays) {
			CMICBusinessKey cmicBusinessKey = new CMICBusinessKey(cmicOrganizationDisplay.getProducerId());

			if (cmicPermissionLocalService.hasPermission(userId, cmicBusinessKey, actionId)) {
				for (CommissionDocumentType documentType : CommissionDocumentType.values()) {
					List<CMICCommissionDocumentDTO> commissionDocumentDTOs = commissionDocumentWebService.searchDocuments(
						cmicOrganizationDisplay.getAgentNumber(), cmicOrganizationDisplay.getDivisionNumber(),
						documentType.toString(), maximumStatementDate, minimumStatementDate);

					commissionDocumentDTOs.stream(
					).forEach(
						commissionDocumentDTO -> cmicCommissionDocumentDisplays.add(
							new CMICCommissionDocumentDisplay(commissionDocumentDTO))
					);
				}
			}
		}

		cmicCommissionDocumentDisplays.stream(
		).forEach(
			commissionDocumentDisplay -> _documentIdToProducerCodeCache.put(
				commissionDocumentDisplay.getDocumentId(), commissionDocumentDisplay)
		);

		return cmicCommissionDocumentDisplays;
	}

	@Activate
	@Modified
	protected void activate() {
		_documentIdToProducerCodeCache =
			(PortalCache<String, CMICCommissionDocumentDisplay>)_singleVMPool.getPortalCache(
				DOCUMENT_ID_TO_PRODUCER_CODE_CACHE_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_singleVMPool.removePortalCache(CMICCommissionDocumentLocalServiceImpl.DOCUMENT_ID_TO_PRODUCER_CODE_CACHE_NAME);
	}

	@Reference
	protected CMICOrganizationLocalService cmicOrganizationLocalService;

	@Reference
	protected CMICPermissionLocalService cmicPermissionLocalService;

	@Reference
	protected CommissionDocumentWebService commissionDocumentWebService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected PortalUserWebService portalUserWebService;

	private PortalCache<String, CMICCommissionDocumentDisplay> _documentIdToProducerCodeCache;

	@Reference
	private SingleVMPool _singleVMPool;

}