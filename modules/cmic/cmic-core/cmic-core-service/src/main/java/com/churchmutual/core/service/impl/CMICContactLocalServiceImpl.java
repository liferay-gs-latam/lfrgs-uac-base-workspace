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

import com.churchmutual.core.constants.ProducerType;
import com.churchmutual.core.model.CMICContactDisplay;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.core.service.base.CMICContactLocalServiceBaseImpl;
import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.model.CMICRoleAssignmentDTO;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic contact local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICContactLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICContactLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICContact", service = AopService.class)
public class CMICContactLocalServiceImpl extends CMICContactLocalServiceBaseImpl {

	@Override
	public CMICContactDisplay fetchDirectSalesRepresentative(long userId) throws PortalException {
		ProducerType[] producerTypes = cmicUserLocalService.getProducerTypesByUserId(userId);

		if (!((producerTypes.length == 1) && ProducerType.DIRECT.equals(producerTypes[0]))) {
			return null;
		}

		List<CMICOrganizationDisplay> cmicOrganizationDisplays =
			cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId);

		for (CMICOrganizationDisplay cmicOrganizationDisplay : cmicOrganizationDisplays) {
			List<CMICRoleAssignmentDTO> cmicRoleAssignmentDTOs = producerWebService.getRoleAssignments(
				cmicOrganizationDisplay.getProducerId());

			for (CMICRoleAssignmentDTO cmicRoleAssignmentDTO : cmicRoleAssignmentDTOs) {
				if (_DIRECT_SALES_REPRESENTATIVE.equals(cmicRoleAssignmentDTO.getRole()) &&
					_isValidContact(cmicRoleAssignmentDTO)) {

					return new CMICContactDisplay(cmicRoleAssignmentDTO);
				}
			}
		}

		return null;
	}

	@Override
	public List<CMICContactDisplay> getTerritoryManagers(long userId) throws PortalException {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		List<CMICOrganizationDisplay> cmicOrganizationDisplays =
			cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId);

		Map<String, CMICContactDisplay> cmicContactDisplaysMap = new HashMap<>();

		for (CMICOrganizationDisplay cmicOrganizationDisplay : cmicOrganizationDisplays) {
			String producerCode =
				cmicOrganizationDisplay.getDivisionNumber() + cmicOrganizationDisplay.getAgentNumber();

			List<CMICRoleAssignmentDTO> cmicRoleAssignmentDTOs = producerWebService.getRoleAssignments(
				cmicOrganizationDisplay.getProducerId());

			for (CMICRoleAssignmentDTO cmicRoleAssignmentDTO : cmicRoleAssignmentDTOs) {
				if (Validator.isNotNull(cmicRoleAssignmentDTO.getEmployeeId()) &&
					_TERRITORY_MANAGER.equals(cmicRoleAssignmentDTO.getRole())) {

					CMICContactDisplay existingContact = cmicContactDisplaysMap.get(
						cmicRoleAssignmentDTO.getEmployeeId());

					if (existingContact == null) {
						CMICContactDisplay cmicContactDisplay = new CMICContactDisplay(cmicRoleAssignmentDTO);

						cmicContactDisplay.addProducerCode(producerCode);

						cmicContactDisplaysMap.put(cmicRoleAssignmentDTO.getEmployeeId(), cmicContactDisplay);
					}
					else {
						existingContact.addProducerCode(producerCode);
					}
				}
			}
		}

		Comparator<CMICContactDisplay> compareByNumProducerCodesLastName = Comparator.comparing(
			CMICContactDisplay::producerCodesLength, Comparator.reverseOrder()
		).thenComparing(
			CMICContactDisplay::getLastName
		);

		List<CMICContactDisplay> cmicContactDisplays = cmicContactDisplaysMap.values(
		).stream(
		).sorted(
			compareByNumProducerCodesLastName
		).collect(
			Collectors.toList()
		);

		if (_log.isDebugEnabled()) {
			_log.debug(String.format("getTerritoryManagers for userId %d takes %d ms", userId, stopWatch.getTime()));
		}

		return cmicContactDisplays;
	}

	@Reference
	protected CMICOrganizationLocalService cmicOrganizationLocalService;

	@Reference
	protected CMICUserLocalService cmicUserLocalService;

	@Reference
	protected ProducerWebService producerWebService;

	private boolean _isValidContact(CMICRoleAssignmentDTO cmicRoleAssignmentDTO) {
		if (Validator.isNotNull(cmicRoleAssignmentDTO.getEmployeeId()) &&
			Validator.isNotNull(cmicRoleAssignmentDTO.getName()) &&
			Validator.isNotNull(cmicRoleAssignmentDTO.getJobTitle()) &&
			Validator.isNotNull(cmicRoleAssignmentDTO.getEmail()) &&
			Validator.isNotNull(cmicRoleAssignmentDTO.getPhoneNumber())) {

			return true;
		}

		return false;
	}

	private static final String _DIRECT_SALES_REPRESENTATIVE = "Direct Sales Representative";

	private static final String _TERRITORY_MANAGER = "Territory Manager";

	private static final Log _log = LogFactoryUtil.getLog(CMICContactLocalServiceImpl.class);

}