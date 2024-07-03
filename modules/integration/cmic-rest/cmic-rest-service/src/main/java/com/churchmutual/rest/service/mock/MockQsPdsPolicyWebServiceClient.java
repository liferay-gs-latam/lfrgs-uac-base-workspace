package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.*;
import com.churchmutual.rest.service.MockResponseReaderUtil;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(immediate = true, service = MockQsPdsPolicyWebServiceClient.class)
public class MockQsPdsPolicyWebServiceClient {
    public List<CMICAdditionalInterestDTO> getAdditionalInterestsOnBuilding() {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getAdditionalInterestsOnBuilding.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICAdditionalInterestDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICAdditionalInterestDTO[] additionalInterests = jsonDeserializer.deserialize(fileContent, CMICAdditionalInterestDTO[].class);

        return ListUtil.fromArray(additionalInterests);
    }

    public List<CMICBuildingDTO> getBuildingsOnPolicy() {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getBuildingsOnPolicy.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICBuildingDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICBuildingDTO[] buildings = jsonDeserializer.deserialize(fileContent, CMICBuildingDTO[].class);

        return ListUtil.fromArray(buildings);
    }

    public List<CMICPolicyDTO> getPoliciesByPolicyNumbers(String[] policyNumbers) {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getPoliciesByPolicyNumbers.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICPolicyDTO[] policies = jsonDeserializer.deserialize(fileContent, CMICPolicyDTO[].class);

        return ListUtil.fromArray(policies);
    }

    public List<CMICPolicyDTO> getPoliciesOnAccount(String accountNumber) {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getPoliciesOnAccount.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICPolicyDTO[] policies = jsonDeserializer.deserialize(fileContent, CMICPolicyDTO[].class);

        return ListUtil.fromArray(policies);
    }

    public List<CMICPolicyAccountSummaryDTO> getPolicyAccountSummariesByAccounts(String[] accountNumber) {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getPolicyAccountSummariesByAccounts.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICPolicyAccountSummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICPolicyAccountSummaryDTO[] policyAccountSummaries = jsonDeserializer.deserialize(
                fileContent, CMICPolicyAccountSummaryDTO[].class);

        return ListUtil.fromArray(policyAccountSummaries);
    }

    public CMICTransactionDTO getTransaction(String policyNumber, int sequenceNumber) {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getTransaction.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICTransactionDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        return jsonDeserializer.deserialize(fileContent, CMICTransactionDTO.class);
    }

    public List<CMICTransactionDTO> getTransactionsOnPolicy(String policyNumber) {
        String fileName = _QS_PDS_POLICY_WEB_SERVICE_DIR + "getTransactionsOnPolicy.json";

        String fileContent = MockResponseReaderUtil.readFile(fileName);

        JSONDeserializer<CMICTransactionDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICTransactionDTO[] transactions = jsonDeserializer.deserialize(fileContent, CMICTransactionDTO[].class);

        return ListUtil.fromArray(transactions);
    }

    private static final String _QS_PDS_POLICY_WEB_SERVICE_DIR = "qs-pds-policy-web-service/";

    @Reference
    private JSONFactory _jsonFactory;
}
