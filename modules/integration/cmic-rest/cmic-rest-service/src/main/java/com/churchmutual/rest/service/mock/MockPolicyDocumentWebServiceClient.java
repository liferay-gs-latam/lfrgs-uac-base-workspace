package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICPolicyDocumentDTO;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockPolicyDocumentWebServiceClient.class)
public class MockPolicyDocumentWebServiceClient {

	public List<CMICPolicyDocumentDTO> getRecentTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType) {

		String fileName = _POLICY_DOCUMENT_WEB_SERVICE_DIR + "getRecentTransactions.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICPolicyDocumentDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICPolicyDocumentDTO[] policyDocuments = jsonDeserializer.deserialize(
			fileContent, CMICPolicyDocumentDTO[].class);

		return ListUtil.fromArray(policyDocuments);
	}

	public CMICPolicyDocumentDTO downloadTransaction(
		String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum) {

		String fileName = _POLICY_DOCUMENT_WEB_SERVICE_DIR + "getTransactions.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICPolicyDocumentDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICPolicyDocumentDTO.class);
	}

	private static final String _POLICY_DOCUMENT_WEB_SERVICE_DIR = "policy-document-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}