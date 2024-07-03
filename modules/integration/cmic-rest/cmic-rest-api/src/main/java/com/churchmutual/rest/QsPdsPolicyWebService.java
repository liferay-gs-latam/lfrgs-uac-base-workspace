package com.churchmutual.rest;

import com.churchmutual.rest.model.*;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public interface QsPdsPolicyWebService {
    public CMICPolicyAccountSummaryDTO fetchPolicyAccountSummaryByAccount(String accountNumber) throws PortalException;

    public List<CMICAdditionalInterestDTO> getAdditionalInterestsOnBuilding(
            Long additionalInterestTypeReferenceId, String policyNumber, String buildingNumber, String locationPremisesNumber) throws PortalException;

    public List<CMICBuildingDTO> getBuildingsOnPolicy(String policyNumber) throws PortalException;

    public List<CMICPolicyDTO> getPoliciesByPolicyNumbers(String[] policyNumbers) throws PortalException;

    public List<CMICPolicyDTO> getPoliciesOnAccount(String accountNumber) throws PortalException;

    public List<CMICPolicyAccountSummaryDTO> getPolicyAccountSummariesByAccounts(String[] accountNumber)
            throws PortalException;

    public String getPolicyAccountSummariesByAccountsCacheStatus(String[] accountNumber) throws PortalException;

    public CMICTransactionDTO getTransaction(String policyNumber, int sequenceNumber) throws PortalException;

    public List<CMICTransactionDTO> getTransactionsOnPolicy(String policyNumber) throws PortalException;

    public List<CMICPolicyAccountSummaryDTO> refreshPolicyAccountSummariesByAccountsCache(String[] accountNumbers)
            throws PortalException;
}
