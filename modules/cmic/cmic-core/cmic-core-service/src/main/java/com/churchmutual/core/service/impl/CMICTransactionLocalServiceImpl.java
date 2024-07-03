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

import com.churchmutual.core.model.CMICTransactionDisplay;
import com.churchmutual.core.service.base.CMICTransactionLocalServiceBaseImpl;
import com.churchmutual.rest.EnterpriseReferenceValuesWebService;
//import com.churchmutual.rest.PolicyWebService;
import com.churchmutual.rest.QsPdsPolicyWebService;
import com.churchmutual.rest.model.CMICEventTypeDTO;
import com.churchmutual.rest.model.CMICTransactionDTO;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HtmlUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The implementation of the cmic transaction local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICTransactionLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICTransactionLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICTransaction", service = AopService.class)
public class CMICTransactionLocalServiceImpl extends CMICTransactionLocalServiceBaseImpl {

	@Override
	public List<CMICTransactionDisplay> getTransactionDisplays(String policyNumber) throws PortalException {
		List<CMICTransactionDTO> cmicTransactionDTOs = qsPdsPolicyWebService.getTransactionsOnPolicy(
//				policyWebService.getTransactionsOnPolicy(
			HtmlUtil.unescape(policyNumber));

		Map<String, String> policyEventTypeIdNameMap = _getPolicyEventTypeIdNameMap();

		List<CMICTransactionDisplay> cmicTransactionDisplays = new ArrayList<>();

		for (CMICTransactionDTO cmicTransactionDTO : cmicTransactionDTOs) {
			CMICTransactionDisplay cmicTransactionDisplay = new CMICTransactionDisplay(cmicTransactionDTO);

			cmicTransactionDisplay.setEventType(policyEventTypeIdNameMap.get(cmicTransactionDTO.getEventType()));

			cmicTransactionDisplays.add(cmicTransactionDisplay);
		}

		cmicTransactionDisplays.sort(Comparator.comparing(CMICTransactionDisplay::getSequenceNumber, Comparator.reverseOrder()));

		return cmicTransactionDisplays;
	}

	@Reference
	protected EnterpriseReferenceValuesWebService enterpriseReferenceValuesWebService;

//	@Reference
//	protected PolicyWebService policyWebService;

	@Reference
	protected QsPdsPolicyWebService qsPdsPolicyWebService;

	private Map<String, String> _getPolicyEventTypeIdNameMap() throws PortalException {
		List<CMICEventTypeDTO> cmicEventTypeDTOs = enterpriseReferenceValuesWebService.getEventTypes(false);

		return cmicEventTypeDTOs.stream(
		).collect(
			Collectors.toMap(CMICEventTypeDTO::getRefAbbreviation, CMICEventTypeDTO::getRefDescription)
		);
	}

}