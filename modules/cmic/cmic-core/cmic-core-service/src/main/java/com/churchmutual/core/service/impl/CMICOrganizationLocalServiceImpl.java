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

import com.churchmutual.core.exception.NoSuchCMICOrganizationException;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.model.CMICUser;
import com.churchmutual.core.service.base.CMICOrganizationLocalServiceBaseImpl;
import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.model.CMICUserRelationDTO;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic organization local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICOrganizationLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICOrganizationLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICOrganization", service = AopService.class)
public class CMICOrganizationLocalServiceImpl extends CMICOrganizationLocalServiceBaseImpl {

	@Override
	public CMICOrganizationDisplay fetchCMICOrganizationDisplay(CMICBusinessKey cmicBusinessKey) throws PortalException {
		try {
			return getCMICOrganizationDisplay(cmicBusinessKey.getProducerId());
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Could not find producer with producerId " + cmicBusinessKey.getProducerId(), pe);
			}

			return null;
		}
	}

	@Override
	public CMICOrganizationDisplay getCMICOrganizationDisplay(long producerId) throws PortalException {
		CMICProducerDTO cmicProducerDTO = producerWebService.getProducerById(producerId);

		CMICContactDTO cmicContactDTO = producerWebService.getPrimaryContact(producerId);

		return new CMICOrganizationDisplay(cmicProducerDTO, cmicContactDTO);
	}

	@Override
	public CMICOrganizationDisplay getCMICOrganizationDisplayByAgentNumberDivisionNumber(
			String agentNumber, String divisionNumber)
		throws PortalException {

		List<CMICProducerDTO> cmicProducerDTOS = producerWebService.getProducers(
			agentNumber, divisionNumber, null, null);

		if (cmicProducerDTOS.isEmpty()) {
			throw new NoSuchCMICOrganizationException(
				String.format(
					"No producer with agentNumber, %s, and divisionNumber, %s.", agentNumber, divisionNumber));
		}

		CMICProducerDTO cmicProducerDTO = cmicProducerDTOS.get(0);

		CMICContactDTO cmicContactDTO = producerWebService.getPrimaryContact(cmicProducerDTO.getId());

		return new CMICOrganizationDisplay(cmicProducerDTO, cmicContactDTO);
	}

	@Override
	public CMICOrganizationDisplay getCMICOrganizationDisplayByProducerCode(String producerCode)
		throws PortalException {

		String divisionNumber = producerCode.substring(0, 2);
		String agentNumber = producerCode.substring(2);

		return getCMICOrganizationDisplayByAgentNumberDivisionNumber(agentNumber, divisionNumber);
	}

	@Override
	public List<CMICOrganizationDisplay> getCMICOrganizationDisplaysByUserId(long userId) throws PortalException {
		List<CMICOrganizationDisplay> cmicOrganizationDisplays = getCMICOrganizationDisplaysByUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return cmicOrganizationDisplays;
	}

	@Override
	public List<CMICOrganizationDisplay> getCMICOrganizationDisplaysByUserId(long userId, int start, int end)
		throws PortalException {

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new ArrayList<>();
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		List<CMICOrganizationDisplay> cmicOrganizationDisplayList = new ArrayList<>();

		if (cmicUserDTO.isProducer()) {
			List<CMICUserRelationDTO> organizations = cmicUserDTO.getOrganizations();

			organizations = ListUtil.subList(organizations, start, end);

			for (CMICUserRelationDTO organization : organizations) {
				cmicOrganizationDisplayList.add(getCMICOrganizationDisplay(organization.getProducerId()));
			}
		}
		else {
			List<CMICUserRelationDTO> accounts = cmicUserDTO.getAccounts();

			for (CMICUserRelationDTO account : accounts) {
				CMICAccountDTO cmicAccountDTO = accountWebService.getAccounts(account.getAccountNumber());

				if (cmicAccountDTO != null) {
					cmicOrganizationDisplayList.add(
						getCMICOrganizationDisplayByProducerCode(cmicAccountDTO.getProducerCode()));
				}
			}

			cmicOrganizationDisplayList = ListUtil.subList(cmicOrganizationDisplayList, start, end);
		}

		cmicOrganizationDisplayList.sort(Comparator.comparing(CMICOrganizationDisplay::getName));

		return cmicOrganizationDisplayList;
	}

	public List<CMICOrganizationDisplay> getCMICOrganizationDisplaysWithPermission(long userId, String actionId)
		throws PortalException {
		List<CMICOrganizationDisplay> cmicOrganizationDisplays = getCMICOrganizationDisplaysByUserId(userId);

		return cmicOrganizationDisplays.stream(
		).filter(
			cmicOrganizationDisplay ->
				cmicPermissionPersistence.fetchByU_C_A(
					userId,
					new CMICBusinessKey(
						cmicOrganizationDisplay.getProducerId()
					).getFormattedString(),
					actionId) != null
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	protected AccountWebService accountWebService;

	@Reference
	protected PortalUserWebService portalUserWebService;

	@Reference
	protected ProducerWebService producerWebService;

	private static final Log _log = LogFactoryUtil.getLog(CMICOrganizationLocalServiceImpl.class);

}