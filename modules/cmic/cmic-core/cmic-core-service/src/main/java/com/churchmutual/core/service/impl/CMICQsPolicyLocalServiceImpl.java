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

import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.commons.util.NameFormatterUtil;
import com.churchmutual.core.constants.CMICPermissionAction;
import com.churchmutual.core.exception.NoSuchCMICPolicyException;
import com.churchmutual.core.model.*;
import com.churchmutual.core.service.CMICPermissionLocalService;
import com.churchmutual.core.service.base.CMICQsPolicyLocalServiceBaseImpl;

import com.churchmutual.rest.*;
import com.churchmutual.rest.model.*;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The implementation of the cmic qs policy local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICQsPolicyLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICQsPolicyLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.churchmutual.core.model.CMICQsPolicy",
	service = AopService.class
)
public class CMICQsPolicyLocalServiceImpl
	extends CMICQsPolicyLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.churchmutual.core.service.CMICQsPolicyLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.churchmutual.core.service.CMICQsPolicyLocalServiceUtil</code>.
	 */

	@Override
	public CMICPolicyDocumentDisplay downloadPolicyTransaction(String accountNumber, String policyNumber, String policyType, int sequenceNumber)
			throws PortalException {

		String shortenedPolicyNumber = policyNumber.split(StringPool.DASH)[1];

		CMICPolicyDocumentDTO cmicPolicyDocumentDTO = policyDocumentWebService.downloadTransaction(
				accountNumber, true, shortenedPolicyNumber, policyType, String.valueOf(sequenceNumber));

		return new CMICPolicyDocumentDisplay(cmicPolicyDocumentDTO);
	}

	@Override
	public List<CMICAdditionalInterestDisplay> getAdditionalInterestsByBuildings(
			Long additionalInterestTypeReferenceId, String policyNumber, String buildingNumber, String locationPremisesNumber)
			throws PortalException {

		List<CMICAdditionalInterestDTO> additionalInterestDTOs = qsPdsPolicyWebService.getAdditionalInterestsOnBuilding(
				additionalInterestTypeReferenceId, policyNumber, buildingNumber, locationPremisesNumber);

		List<CMICAdditionalInterestDisplay> additionalInterests = new ArrayList<>();

		for (CMICAdditionalInterestDTO additionalInterestDTO : additionalInterestDTOs) {
			additionalInterests.add(new CMICAdditionalInterestDisplay(additionalInterestDTO));
		}

		return additionalInterests;
	}

	@Override
	public List<CMICBuildingDisplay> getBuildingsByPolicy(String policyNumber) throws PortalException {
		List<CMICBuildingDTO> cmicBuildingDTOs = qsPdsPolicyWebService.getBuildingsOnPolicy(policyNumber);

		List<CMICBuildingDisplay> buildings = new ArrayList<>();

		for (CMICBuildingDTO cmicBuildingDTO : cmicBuildingDTOs) {
			buildings.add(new CMICBuildingDisplay(cmicBuildingDTO));
		}

		return buildings;
	}

	@Override
	public List<CMICEligiblePolicyDisplay> getCOIEligiblePolicies(long userId) throws PortalException {
		String[] policyNumbers = _getPolicyNumbersWithPermission(
				userId, CMICPermissionAction.REQUEST_COI_EOP.getActionId());

		if (policyNumbers.length == 0) {
			return new ArrayList<>();
		}

		List<CMICPolicyDTO> cmicPolicyDTOs = certificateOfInsuranceWebService.getCOIEligiblePolicies(policyNumbers);

		Map<String, String> policyProductIdNameMap = _getPolicyProductIdNameMap();

		List<CMICEligiblePolicyDisplay> cmicEligiblePolicyDisplays = new ArrayList<>();

		for (CMICPolicyDTO cmicPolicyDTO : cmicPolicyDTOs) {
			String policyNumber = cmicPolicyDTO.getPolicyNumber();

			String[] splitStrings = policyNumber.split("[- ]");

			if (splitStrings.length == 3) {
				cmicEligiblePolicyDisplays.add(
						new CMICEligiblePolicyDisplay(cmicPolicyDTO, policyProductIdNameMap.get(splitStrings[1])));
			}
		}

		return cmicEligiblePolicyDisplays;
	}

	@Override
	public List<String> getNYWCEligiblePolicies(long userId) throws PortalException {
		String accountNumber = _getAccountWithPermission(
				userId, CMICPermissionAction.REQUEST_COI_EOP.getActionId());

		List<String> accountPolicies = certificateOfInsuranceWebService.getNYWCEligiblePolicies(accountNumber);

		return accountPolicies;
	}

	@Override
	public List<String> getNYWCDisplay(long userId)throws PortalException {
		String accountNumber = _getAccountWithPermission(
				userId, CMICPermissionAction.REQUEST_COI_EOP.getActionId());

		List<String> accountDisplay = certificateOfInsuranceWebService.getNYWCDisplay(accountNumber);

		return accountDisplay;
	}

	@Override
	public List<CMICEligiblePolicyDisplay> getEOPEligiblePolicies(long userId) throws PortalException {
		String[] policyNumbers = _getPolicyNumbersWithPermission(
				userId, CMICPermissionAction.REQUEST_COI_EOP.getActionId());

		if (policyNumbers.length == 0) {
			return new ArrayList<>();
		}

		List<CMICPolicyDTO> cmicPolicyDTOs = certificateOfInsuranceWebService.getEOPEligiblePolicies(
				policyNumbers);

		Map<String, String> policyProductIdNameMap = _getPolicyProductIdNameMap();

		List<CMICEligiblePolicyDisplay> cmicEligiblePolicyDisplays = new ArrayList<>();

		for (CMICPolicyDTO cmicPolicyDTO : cmicPolicyDTOs) {
			String policyNumber = cmicPolicyDTO.getPolicyNumber();

			String[] splitStrings = policyNumber.split("[- ]");

			if (splitStrings.length == 3) {
				cmicEligiblePolicyDisplays.add(
						new CMICEligiblePolicyDisplay(cmicPolicyDTO, policyProductIdNameMap.get(splitStrings[1])));
			}
		}

		return cmicEligiblePolicyDisplays;
	}

	@Override
	public CMICPolicyDisplay getPolicyByPolicyNumber(String policyNumber) throws PortalException {
		List<CMICPolicyDTO> cmicPolicyDTOs = qsPdsPolicyWebService.getPoliciesByPolicyNumbers(
				new String[] {policyNumber});

		CMICPolicyDTO cmicPolicyDTO = CollectionsUtil.getFirst(cmicPolicyDTOs);

		if (cmicPolicyDTO == null) {
			throw new NoSuchCMICPolicyException(
					"Could not find policy with policyNumber: " + policyNumber);
		}

		return new CMICPolicyDisplay(cmicPolicyDTO, null, getPolicyProductName(cmicPolicyDTO.getProduct()));
	}

	@Override
	public List<CMICPolicyDisplay> getPolicyDisplays(CMICBusinessKey cmicBusinessKey) throws PortalException {
		List<CMICPolicyDTO> cmicPolicyDTOs = qsPdsPolicyWebService.getPoliciesOnAccount(cmicBusinessKey.getAccountNumber());

		List<CMICPolicyDisplay> cmicPolicyDisplays = new ArrayList<>();

		Map<String, String> policyProductIdNameMap = _getPolicyProductIdNameMap();

		for (CMICPolicyDTO cmicPolicyDTO : cmicPolicyDTOs) {
			CMICPolicyDisplay cmicPolicyDisplay =
					new CMICPolicyDisplay(cmicPolicyDTO, null, policyProductIdNameMap.get(cmicPolicyDTO.getProduct()));

			cmicPolicyDisplay.setHasViewPolicyDocument(true);

			cmicPolicyDisplays.add(cmicPolicyDisplay);
		}

		cmicPolicyDisplays.sort(
				Comparator.comparing(
						CMICPolicyDisplay::getEffectiveDateSortable, Comparator.reverseOrder()
				).thenComparing(
						CMICPolicyDisplay::getProductName
				));

		return cmicPolicyDisplays;
	}

	@Override
	public List<CMICPolicyDisplay> getPolicyDisplaysByUserId(long userId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new ArrayList<>();
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		List<CMICUserRelationDTO> cmicUserRelationDTOs = cmicUserDTO.getAccounts();

		Map<String, String> policyProductIdNameMap = _getPolicyProductIdNameMap();

		List<CMICPolicyDisplay> cmicPolicyDisplays = new ArrayList<>();

		if (!cmicUserDTO.isProducer()) {
			for (CMICUserRelationDTO cmicUserRelationDTO : cmicUserRelationDTOs) {
				String accountNumber = cmicUserRelationDTO.getAccountNumber();

				CMICAccountDTO cmicAccountDTO = accountWebService.getAccounts(accountNumber);

				List<CMICPolicyDTO> cmicPolicyDTOs = qsPdsPolicyWebService.getPoliciesOnAccount(accountNumber);

				for (CMICPolicyDTO cmicPolicyDTO : cmicPolicyDTOs) {
					CMICPolicyDisplay cmicPolicyDisplay = new CMICPolicyDisplay(
							cmicPolicyDTO, cmicAccountDTO, policyProductIdNameMap.get(cmicPolicyDTO.getProduct()));

					CMICBusinessKey cmicBusinessKey =
							new CMICBusinessKey(cmicPolicyDisplay.getAccountNumber(), cmicPolicyDisplay.getCompanyNumber());

					if (cmicPermissionLocalService.hasPermission(
							userId, cmicBusinessKey, CMICPermissionAction.VIEW_POLICY_DOCUMENTS.getActionId())) {

						cmicPolicyDisplay.setHasViewPolicyDocument(true);
					}

					cmicPolicyDisplays.add(cmicPolicyDisplay);
				}
			}

			cmicPolicyDisplays.sort(
					Comparator.comparing(
							CMICPolicyDisplay::getEffectiveDateSortable, Comparator.reverseOrder()
					).thenComparing(
							CMICPolicyDisplay::getProductName
					));
		}

		return cmicPolicyDisplays;
	}

	@Override
	public String getPolicyProductName(String policyType) throws PortalException {
		Map<String, String> policyProductIdNameMap = _getPolicyProductIdNameMap();

		String productName = policyProductIdNameMap.get(policyType);

		return NameFormatterUtil.format(productName);
	}

	@Override
	public List<CMICPolicyDisplay> getRecentPolicyDisplays(long userId) throws PortalException {
		List<CMICPolicyDisplay> cmicPolicyDisplays = getPolicyDisplaysByUserId(userId);

		cmicPolicyDisplays.sort(
				Comparator.comparing(
						CMICPolicyDisplay::getEffectiveDateSortable, Comparator.reverseOrder()
				).thenComparing(
						CMICPolicyDisplay::getProductName
				));

		if (cmicPolicyDisplays.size() > _RECENT_POLICIES_DISPLAY_COUNT) {
			return cmicPolicyDisplays.subList(0, _RECENT_POLICIES_DISPLAY_COUNT);
		}

		return cmicPolicyDisplays;
	}

	@Reference
	protected AccountWebService accountWebService;

	@Reference
	protected CertificateOfInsuranceWebService certificateOfInsuranceWebService;

	@Reference
	protected CMICPermissionLocalService cmicPermissionLocalService;

	@Reference
	protected EnterpriseReferenceValuesWebService enterpriseReferenceValuesWebService;

	@Reference
	protected PolicyDocumentWebService policyDocumentWebService;

	@Reference
	protected QsPdsPolicyWebService qsPdsPolicyWebService;

	@Reference
	protected PortalUserWebService portalUserWebService;

	private String[] _getPolicyNumbersWithPermission(long userId, String actionId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return new String[0];
		}

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		List<CMICUserRelationDTO> cmicUserRelationDTOs = cmicUserDTO.getAccounts();

		List<String> policyNumbers = new ArrayList<>();

		if (!cmicUserDTO.isProducer()) {
			for (CMICUserRelationDTO cmicUserRelationDTO : cmicUserRelationDTOs) {
				String accountNumber = cmicUserRelationDTO.getAccountNumber();

				CMICBusinessKey cmicBusinessKey =
						new CMICBusinessKey(accountNumber, cmicUserRelationDTO.getCompanyNumber());

				if (cmicPermissionLocalService.hasPermission(userId, cmicBusinessKey, actionId)) {
					List<CMICPolicyDTO> cmicPolicyDTOs = qsPdsPolicyWebService.getPoliciesOnAccount(accountNumber);

					for (CMICPolicyDTO cmicPolicyDTO : cmicPolicyDTOs) {
						policyNumbers.add(cmicPolicyDTO.getPolicyNumber());
					}
				}
			}
		}

		return policyNumbers.toArray(new String[0]);
	}

	private String _getAccountWithPermission(long userId, String actionId) throws PortalException {
		CMICUser cmicUser = cmicUserPersistence.fetchByUserId(userId);

		if (cmicUser == null) {
			return "";
		}
		String accountNumber="";

		CMICUserDTO cmicUserDTO = portalUserWebService.getUserDetails(cmicUser.getCmicUserId());

		List<CMICUserRelationDTO> cmicUserRelationDTOs = cmicUserDTO.getAccounts();

		if (!cmicUserDTO.isProducer()) {
			for (CMICUserRelationDTO cmicUserRelationDTO : cmicUserRelationDTOs) {
				accountNumber = cmicUserRelationDTO.getAccountNumber();
			}
		}

		return accountNumber;
	}

	private Map<String, String> _getPolicyProductIdNameMap() throws PortalException {
		List<CMICProductDTO> cmicProductDTOs = enterpriseReferenceValuesWebService.getProducts(false);

		return cmicProductDTOs.stream(
		).collect(
				Collectors.toMap(CMICProductDTO::getRefAbbreviation, CMICProductDTO::getRefDescription)
		);
	}

	private static final int _RECENT_POLICIES_DISPLAY_COUNT = 4;
}