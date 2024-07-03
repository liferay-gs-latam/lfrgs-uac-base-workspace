package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.PolicyDocumentWebService;
import com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration;
import com.churchmutual.rest.model.CMICPolicyDocumentDTO;
import com.churchmutual.rest.service.mock.MockPolicyDocumentWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration",
	immediate = true, service = PolicyDocumentWebService.class
)
public class PolicyDocumentWebServiceImpl implements PolicyDocumentWebService {

	@Override
	public CMICPolicyDocumentDTO downloadTransaction(
			String accountNumber, boolean includeBytes, String policyNumber, String policyType, String sequenceNumber)
		throws PortalException {

		if (_mockPolicyDocumentWebServiceConfiguration.enableMockDownloadTransaction()) {
			return _mockPolicyDocumentWebServiceClient.downloadTransaction(
				accountNumber, includeBytes, policyNumber, policyType, sequenceNumber);
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNumber", accountNumber);
		queryParameters.put("includeBytes", String.valueOf(includeBytes));
		queryParameters.put("policyNumber", policyNumber);
		queryParameters.put("policyType", policyType);
		queryParameters.put("sequenceNumber", sequenceNumber);

		String response = _webServiceExecutor.executeGet(_DOWNLOAD_TRANSACTION_URL, queryParameters);

		JSONDeserializer<CMICPolicyDocumentDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			return jsonDeserializer.deserialize(response, CMICPolicyDocumentDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(
				String.format("Transaction with sequence number %d could not be found", sequenceNumber), e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockPolicyDocumentWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockPolicyDocumentWebServiceConfiguration.class, properties);
	}

	private static final String _DOWNLOAD_TRANSACTION_URL = "/policy-document-service/v1/download/transactions";

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockPolicyDocumentWebServiceClient _mockPolicyDocumentWebServiceClient;

	private MockPolicyDocumentWebServiceConfiguration _mockPolicyDocumentWebServiceConfiguration;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}