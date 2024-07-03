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

import com.churchmutual.commons.constants.CacheConstants;
import com.churchmutual.commons.util.SiteUserUtil;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.model.CMICUser;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICAccountEntryLocalServiceBaseImpl;
import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.QsPdsPolicyWebService;
import com.churchmutual.rest.model.*;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The implementation of the cmic account entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICAccountEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICAccountEntry", service = AopService.class)
public class CMICAccountEntryLocalServiceImpl extends CMICAccountEntryLocalServiceBaseImpl {

	@Override
	public CMICAccountEntryDisplay getCMICAccountEntryDisplay(CMICBusinessKey cmicBusinessKey) throws PortalException {
		CMICAccountDTO cmicAccountDTO = accountWebService.getAccounts(cmicBusinessKey.getAccountNumber());

		CMICPolicyAccountSummaryDTO cmicPolicyAccountSummaryDTO = qsPdsPolicyWebService.fetchPolicyAccountSummaryByAccount(
				cmicBusinessKey.getAccountNumber());
//		CMICPolicyAccountSummaryDTO cmicPolicyAccountSummaryDTO = policyWebService.fetchPolicyAccountSummaryByAccount(
//			cmicBusinessKey.getAccountNumber());

		return new CMICAccountEntryDisplay(cmicAccountDTO, null, cmicPolicyAccountSummaryDTO, cmicBusinessKey.getAccountNumber());
	}

	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplays(List<String> recentAccounts)
		throws PortalException {

		List<CMICAccountDTO> cmicAccountDTOS = new ArrayList<>();

		for (String recentAccount : recentAccounts) {
			String[] splitStrings = recentAccount.split(StringPool.DASH);

			String accountNumber = splitStrings[1];

			CMICAccountDTO cmicAccountDTO = accountWebService.getAccounts(accountNumber);

			if (cmicAccountDTO != null) {
				cmicAccountDTOS.add(cmicAccountDTO);
			}
			else {
				CMICAccountDTO account = new CMICAccountDTO();

				account.setAccountNumber(accountNumber);

				cmicAccountDTOS.add(account);
			}
		}

		return getCMICAccountEntryDisplaysByAccounts(cmicAccountDTOS);
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByProducerCodes(String[] producerCodes)
		throws PortalException {

		List<CMICAccountDTO> cmicAccountDTOS = accountWebService.getAccountsSearchByProducer(producerCodes);

		return getCMICAccountEntryDisplaysByAccounts(cmicAccountDTOS);
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByUserId(long userId, int start, int end)
		throws PortalException {

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new ArrayList<>();
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		if (cmicUserDTO.isProducer()) {
			String[] producerCodes = getProducerCodes(userId);

			return ListUtil.subList(getCMICAccountEntryDisplaysByProducerCodes(producerCodes), start, end);
		}

		List<CMICUserRelationDTO> cmicUserRelationDTOs = ListUtil.subList(cmicUserDTO.getAccounts(), start, end);

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<>();

		for (CMICUserRelationDTO cmicUserRelationDTO : cmicUserRelationDTOs) {
			cmicAccountEntryDisplays.add(getCMICAccountEntryDisplay(new CMICBusinessKey(cmicUserRelationDTO.getAccountNumber(), cmicUserRelationDTO.getCompanyNumber())));
		}

		return cmicAccountEntryDisplays;
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByUserId(long userId) throws PortalException {
		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = null;

		try {
			cmicAccountEntryDisplays = getCMICAccountEntryDisplaysByUserId(
				userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			cmicAccountEntryDisplays.sort(Comparator.comparing(CMICAccountEntryDisplay::getAccountName));
		}
		catch (Exception e) {
			_log.error(e);

			throw new PortalException(e);
		}

		return cmicAccountEntryDisplays;
	}

	/**
	 * Use this method for quickly getting very few accounts.  This method will be fast for getting few accounts since
	 * it grabs them one by one, however, this will be slow when needed many accounts.
	 */
	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByUserIdUnsorted(long userId, int count)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Getting " + count + " unsorted accounts for userId: " + userId + "");
		}

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new ArrayList<>();
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		if (cmicUserDTO.isProducer()) {
			List<CMICOrganizationDisplay> cmicOrganizationDisplays =
				cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId);

			String[] producerCodes = cmicOrganizationDisplays.stream(
			).map(
				cmicOrganizationDisplay ->
					cmicOrganizationDisplay.getDivisionNumber() + cmicOrganizationDisplay.getAgentNumber()
			).toArray(
				String[]::new
			);

			// Only iterate to the next producerCode if we still don't have enough accounts

			List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<CMICAccountEntryDisplay>();

			for (String producerCode : producerCodes) {
				List<CMICAccountDTO> cmicAccountDTOs = accountWebService.getAccountsSearchByProducer(
					new String[] { producerCode });

				if (cmicAccountDTOs != null) {
					for (CMICAccountDTO cmicAccountDTO : cmicAccountDTOs) {
						if (_log.isDebugEnabled()) {
							_log.debug("looking through ProducerCode: " + producerCode + ", found AccountNumber: " + cmicAccountDTO.getAccountNumber());
						}

						CMICAccountEntryDisplay cmicAccountEntryDisplay = getCMICAccountEntryDisplayByAccount(
							cmicAccountDTO);

						if (cmicAccountEntryDisplay != null) {
							cmicAccountEntryDisplays.add(cmicAccountEntryDisplay);
						}

						if (cmicAccountEntryDisplays.size() == count) {
							if (_log.isDebugEnabled()) {
								_log.debug("found " + count + " unsorted accounts, returning list.");
							}

							return cmicAccountEntryDisplays;
						}
					}
				}
			}

			// Return partial list if the user just doesn't have enough accounts.

			if (_log.isDebugEnabled()) {
				_log.debug("Was only able to find " + cmicAccountEntryDisplays.size() + " unsorted accounts, looks like user doesn't have " + count + " accounts.");
			}

			return cmicAccountEntryDisplays;
		}

		List<CMICUserRelationDTO> cmicUserRelationDTOs = ListUtil.subList(cmicUserDTO.getAccounts(), 0, count);

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<>();

		for (CMICUserRelationDTO cmicUserRelationDTO : cmicUserRelationDTOs) {
			cmicAccountEntryDisplays.add(getCMICAccountEntryDisplay(
				new CMICBusinessKey(cmicUserRelationDTO.getAccountNumber(), cmicUserRelationDTO.getCompanyNumber())));
		}

		return cmicAccountEntryDisplays;
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByUserIdOrderedByName(
			long userId, int start, int end)
		throws PortalException {

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = getCMICAccountEntryDisplaysByUserId(
			userId, start, end);

		cmicAccountEntryDisplays.sort(Comparator.comparing(CMICAccountEntryDisplay::getAccountName));

		return cmicAccountEntryDisplays;
	}

	@Override
	public String getCMICAccountEntryDisplaysCacheStatusByUserId(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return CacheConstants.NOT_IN_CACHE;
		}

		try {
			CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

			if (cmicUserDTO.isProducer()) {
				String[] producerCodes = getProducerCodes(userId);

				List<CMICAccountDTO> cmicAccountDTOs = accountWebService.getAccountsSearchByProducer(producerCodes);

				String[] accountNumbers = getAccountNumbers(cmicAccountDTOs);

//				return policyWebService.getPolicyAccountSummariesByAccountsCacheStatus(accountNumbers);
				return qsPdsPolicyWebService.getPolicyAccountSummariesByAccountsCacheStatus(accountNumbers);
			}
		}
		catch (Exception e) {
			_log.error(e);

			throw new PortalException(e);
		}

		return CacheConstants.NOT_IN_CACHE;
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysWithAddresses(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new ArrayList<>();
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		List<CMICUserRelationDTO> cmicUserRelationDTOs = cmicUserDTO.getAccounts();

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<>();

		if (!cmicUserDTO.isProducer()) {
			for (CMICUserRelationDTO cmicUserRelationDTO : cmicUserRelationDTOs) {
				String accountNumber = cmicUserRelationDTO.getAccountNumber();

				CMICAccountDTO cmicAccountDTO = accountWebService.getAccounts(accountNumber);

				CMICAddressDTO cmicAddressDTO = accountWebService.getMailingAddressAccount(accountNumber);

				cmicAccountEntryDisplays.add(new CMICAccountEntryDisplay(cmicAccountDTO, cmicAddressDTO, null, accountNumber));
			}
		}

		return cmicAccountEntryDisplays;
	}

	/*
		This method returns a filtered list of accounts entries where the user can perform a specified action (e.g. get
		all account entries where the user can request loss runs).
	 */
	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysWithPermission(long userId, String actionId)
		throws PortalException {

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = getCMICAccountEntryDisplaysByUserId(userId);

		if (SiteUserUtil.isProducerSiteUser(userId)) {
			return cmicAccountEntryDisplays;
		}

		return cmicAccountEntryDisplays.stream(
		).filter(
			a ->
				cmicPermissionPersistence.fetchByU_C_A(
					userId,
					new CMICBusinessKey(
						a.getAccountNumber(), a.getCompanyNumber()
					).getFormattedString(),
					actionId) != null
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public String getProducerName(String producerCode) throws PortalException {
		if (Validator.isBlank(producerCode)) {
			return "No Producer Name";
		}

		CMICOrganizationDisplay cmicOrganizationDisplay =
			cmicOrganizationLocalService.getCMICOrganizationDisplayByProducerCode(producerCode);

		return cmicOrganizationDisplay.getName();
	}

	/**
	 * Refresh the policy account summary for producer users
	 */
	@Override
	public void refreshCMICAccountEntryDisplaysByUserIdCache(long userId)
		throws PortalException {

		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return;
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		if (cmicUserDTO.isProducer()) {
			String[] producerCodes = getProducerCodes(userId);

			List<CMICAccountDTO> cmicAccountDTOs = accountWebService.getAccountsSearchByProducer(producerCodes);

			String[] accountNumbers = getAccountNumbers(cmicAccountDTOs);

//			policyWebService.refreshPolicyAccountSummariesByAccountsCache(accountNumbers);
			qsPdsPolicyWebService.refreshPolicyAccountSummariesByAccountsCache(accountNumbers);
		}
	}

	protected CMICAccountEntryDisplay getCMICAccountEntryDisplayByAccount(CMICAccountDTO cmicAccountDTO)
		throws PortalException {

		List<CMICAccountDTO> cmicAccountDTOs = new ArrayList<CMICAccountDTO>();

		cmicAccountDTOs.add(cmicAccountDTO);

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplay = getCMICAccountEntryDisplaysByAccounts(cmicAccountDTOs);

		if (!cmicAccountEntryDisplay.isEmpty()) {
			return cmicAccountEntryDisplay.get(0);
		}

		return null;
	}

	protected List<CMICAccountEntryDisplay> getCMICAccountEntryDisplaysByAccounts(List<CMICAccountDTO> cmicAccountDTOs)
		throws PortalException {

		String[] accountNumbers = getAccountNumbers(cmicAccountDTOs);

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<>();

		if (accountNumbers.length == 0) {
			return cmicAccountEntryDisplays;
		}

		List<CMICPolicyAccountSummaryDTO> cmicPolicyAccountSummaryDTOs =
				qsPdsPolicyWebService.getPolicyAccountSummariesByAccounts(accountNumbers);
//			policyWebService.getPolicyAccountSummariesByAccounts(accountNumbers);

		Map<String, CMICPolicyAccountSummaryDTO> cmicPolicyAccountSummaryDTOMap = cmicPolicyAccountSummaryDTOs.stream(
		).collect(
			Collectors.toMap(
				CMICPolicyAccountSummaryDTO::getAccountNumber,
				cmicPolicyAccountSummaryDTO -> cmicPolicyAccountSummaryDTO)
		);

		for (CMICAccountDTO cmicAccountDTO : cmicAccountDTOs) {
			if (cmicAccountDTO != null) {
				CMICPolicyAccountSummaryDTO cmicPolicyAccountSummaryDTO = cmicPolicyAccountSummaryDTOMap.get(
					cmicAccountDTO.getAccountNumber());

				cmicAccountEntryDisplays.add(
					new CMICAccountEntryDisplay(cmicAccountDTO, null, cmicPolicyAccountSummaryDTO));
			}
		}

		return cmicAccountEntryDisplays;
	}

	@Reference
	protected AccountWebService accountWebService;

	@Reference
	protected CMICOrganizationLocalService cmicOrganizationLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

//	@Reference
//	protected PolicyWebService policyWebService;

	@Reference
	protected QsPdsPolicyWebService qsPdsPolicyWebService;

	@Reference
	protected PortalUserWebService portalUserWebService;

	private String[] getAccountNumbers(List<CMICAccountDTO> cmicAccountDTOs) {
		if (cmicAccountDTOs == null) {
			return new String[0];
		}

		String[] accountNumbers = cmicAccountDTOs.stream(
		).map(
			CMICAccountDTO::getAccountNumber
		).toArray(
			String[]::new
		);

		return accountNumbers;
	}

	private String[] getProducerCodes(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new String[0];
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		if (cmicUserDTO.isProducer()) {
			List<CMICOrganizationDisplay> cmicOrganizationDisplays =
				cmicOrganizationLocalService.getCMICOrganizationDisplaysByUserId(userId);

			String[] producerCodes = cmicOrganizationDisplays.stream(
			).map(
				cmicOrganizationDisplay ->
					cmicOrganizationDisplay.getDivisionNumber() + cmicOrganizationDisplay.getAgentNumber()
			).toArray(
				String[]::new
			);

			return producerCodes;
		}

		return new String[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICAccountEntryLocalServiceImpl.class);

}