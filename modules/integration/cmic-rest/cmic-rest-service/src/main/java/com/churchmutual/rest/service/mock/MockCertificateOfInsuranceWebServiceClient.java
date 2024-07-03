package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICPolicyDTO;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Matthew Chan
 */
@Component(immediate = true, service = MockCertificateOfInsuranceWebServiceClient.class)
public class MockCertificateOfInsuranceWebServiceClient {

	public String downloadCOIDocument() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "downloadCOIDocument.pdf";

		return MockResponseReaderUtil.readFile(fileName);
	}
	
	public String downloadNYWCDocument() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "downloadNYWCDocument.pdf";

		return MockResponseReaderUtil.readFile(fileName);
	}

	public String downloadEOPDocument() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "downloadEOPDocument.pdf";

		return MockResponseReaderUtil.readFile(fileName);
	}

	public List<CMICPolicyDTO> getCOIEligiblePolicies() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "getCOIEligiblePolicies.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICPolicyDTO[] cmicPolicyDTOs = jsonDeserializer.deserialize(fileContent, CMICPolicyDTO[].class);

		return ListUtil.fromArray(cmicPolicyDTOs);
	}
	
	public List<String> getNYWCEligiblePolicies() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "getNYWCEligiblePolicies.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<String[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		String[] cmicPolicyDTOs = jsonDeserializer.deserialize(fileContent, String[].class);

		return ListUtil.fromArray(cmicPolicyDTOs);
	}
	
	public List<String> getNYWCDisplay() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "getNYWCDisplay.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<String> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		String cmicPolicyDTOs = jsonDeserializer.deserialize(fileContent, String.class);

		return ListUtil.fromArray(cmicPolicyDTOs);
	}

	public List<CMICPolicyDTO> getEOPEligiblePolicies() {
		String fileName = _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR + "getEOPEligiblePolicies.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICPolicyDTO[] cmicPolicyDTOs = jsonDeserializer.deserialize(fileContent, CMICPolicyDTO[].class);

		return ListUtil.fromArray(cmicPolicyDTOs);
	}

	private static final String _CERTIFICATE_OF_INSURANCE_WEB_SERVICE_DIR = "certificate-of-insurance-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}