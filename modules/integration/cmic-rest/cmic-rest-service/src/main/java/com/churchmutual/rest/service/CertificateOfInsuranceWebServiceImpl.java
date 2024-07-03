package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.CertificateOfInsuranceWebService;
import com.churchmutual.rest.configuration.MockCertificateOfInsuranceWebServiceConfiguration;
import com.churchmutual.rest.model.CMICAdditionalInterestDTO;
import com.churchmutual.rest.model.CMICPolicyDTO;
import com.churchmutual.rest.service.mock.MockCertificateOfInsuranceWebServiceClient;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import org.osgi.service.component.annotations.*;

import java.util.*;

/**
 * @author Matthew Chan
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockCertificateOfInsuranceWebServiceConfiguration",
	immediate = true, service = CertificateOfInsuranceWebService.class
)
public class CertificateOfInsuranceWebServiceImpl implements CertificateOfInsuranceWebService {

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_GET_COI_ELIGIBLE_POLICIES_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_EOP_ELIGIBLE_POLICIES_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_NYWC_ELIGIBLE_POLICIES_CACHE_NAME);
	}

	@Override
	public String downloadCOIDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			boolean showSMLimits, boolean showHiredAndNonOwnedAutoLimits, boolean rentalAutoCertificate,
			boolean editable, boolean performUpload, String[] policyNumbers)
		throws PortalException {

		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockDownloadCOIDocument()) {
			return _mockCertificateOfInsuranceWebServiceClient.downloadCOIDocument();
		}

		JSONObject bodyParameters = _jsonFactory.createJSONObject();

		JSONObject certificateHolder = _jsonFactory.createJSONObject();

		certificateHolder.put("city", city);
		certificateHolder.put("fullName", fullName);
		certificateHolder.put("addressLine1", address);
		certificateHolder.put("addressLine2", address2);
		certificateHolder.put("postalCode", postalCode);
		certificateHolder.put("stateOrProvinceCode", state);

		bodyParameters.put("certificateHolder", certificateHolder);

		bodyParameters.put("policyNumbers", _jsonFactory.createJSONArray(policyNumbers));
		bodyParameters.put("showingSexualMisconductLimits", showSMLimits);
		bodyParameters.put("showHiredAndNonOwnedAutoLimits", showHiredAndNonOwnedAutoLimits);
		bodyParameters.put("rentalAutoCertificate", rentalAutoCertificate);
		bodyParameters.put("editable", editable);
		bodyParameters.put("performUpload", performUpload);

		return _webServiceExecutor.executePost(_DOWNLOAD_COI_DOCUMENT_URL, new HashMap<>(), bodyParameters.toString());
	}

	@Override
	public String downloadNYWCDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			boolean performUpload, String policyNumber, String telephoneNumber)
		throws PortalException {

		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockDownloadNYWCDocument()) {
			return _mockCertificateOfInsuranceWebServiceClient.downloadNYWCDocument();
		}

		JSONObject bodyParameters = _jsonFactory.createJSONObject();

		JSONObject certificateHolder = _jsonFactory.createJSONObject();

		certificateHolder.put("city", city);
		certificateHolder.put("fullName", fullName);
		certificateHolder.put("addressLine1", address);
		certificateHolder.put("addressLine2", address2);
		certificateHolder.put("postalCode", postalCode);
		certificateHolder.put("stateOrProvinceCode", state);

		bodyParameters.put("certificateHolder", certificateHolder);

		bodyParameters.put("policyNumber", policyNumber);
		bodyParameters.put("performUpload", performUpload);
		bodyParameters.put("telephoneNumber", telephoneNumber);

		return _webServiceExecutor.executePost(_DOWNLOAD_NYWC_DOCUMENT_URL, new HashMap<>(), bodyParameters.toString());
	}
	
	@Override
	public String downloadEOPDocument(
			CMICAdditionalInterestDTO cmicAdditionalInterestDTO, String policyNumber, boolean editable,
			boolean performUpload, String buildingNumber, String locationPremisesNumber)
		throws PortalException {

		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockDownloadEOPDocument()) {
			return _mockCertificateOfInsuranceWebServiceClient.downloadEOPDocument();
		}

		JSONObject bodyParameters = _jsonFactory.createJSONObject();

		JSONObject additionalInterest = _jsonFactory.createJSONObject();

		if (cmicAdditionalInterestDTO != null) {
			additionalInterest.put("city", cmicAdditionalInterestDTO.getCity());
			additionalInterest.put("fullName", cmicAdditionalInterestDTO.getName());
			additionalInterest.put("addressLine1", cmicAdditionalInterestDTO.getAddressLine1());
			additionalInterest.put("addressLine2", cmicAdditionalInterestDTO.getAddressLine2());
			additionalInterest.put("mortgagee", cmicAdditionalInterestDTO.isMortgagee());
			additionalInterest.put("postalCode", cmicAdditionalInterestDTO.getPostalCode());
			additionalInterest.put("stateOrProvinceCode", cmicAdditionalInterestDTO.getState());
		}

		bodyParameters.put("additionalInterest", additionalInterest);
		bodyParameters.put("policyNumber", policyNumber);
		bodyParameters.put("editable", editable);
		bodyParameters.put("performUpload", performUpload);
		bodyParameters.put("buildingNumber", buildingNumber);
		bodyParameters.put("locationPremisesNumber", locationPremisesNumber);

		return _webServiceExecutor.executePost(_DOWNLOAD_EOP_DOCUMENT_URL, new HashMap<>(), bodyParameters.toString());
	}

	@Override
	public List<CMICPolicyDTO> getCOIEligiblePolicies(String[] policyNumbers) throws PortalException {
		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockGetCOIEligiblePolicies()) {
			return _mockCertificateOfInsuranceWebServiceClient.getCOIEligiblePolicies();
		}

		String key = StringUtil.merge(policyNumbers);

		List<CMICPolicyDTO> cache = _getCOIEligiblePoliciesPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		JSONArray bodyParameters = _jsonFactory.createJSONArray(policyNumbers);

		String response = _webServiceExecutor.executePost(
			_GET_COI_ELIGIBLE_POLICIES, new HashMap<>(), bodyParameters.toString());

		JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICPolicyDTO> list = new ArrayList<>();

		try {
			CMICPolicyDTO[] results = jsonDeserializer.deserialize(response, CMICPolicyDTO[].class);

			Collections.addAll(list, results);
		}
		catch(Exception e) {
		}

		_getCOIEligiblePoliciesPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<String> getNYWCEligiblePolicies(String accountNumber) throws PortalException {
		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockGetNYWCEligiblePolicies()) {
			return _mockCertificateOfInsuranceWebServiceClient.getNYWCEligiblePolicies();
		}
		
		Map<String, String> accountNumbers =  new HashMap<>();
		accountNumbers.put("accountNumber", accountNumber);
		
		
		String response = _webServiceExecutor.executeGet(
				_GET_NYWC_ELIGIBLE_POLICIES, accountNumbers);

		JSONDeserializer<String[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<String> list = new ArrayList<>();
		
		try {
			String[] results = jsonDeserializer.deserialize(response, String[].class);

			Collections.addAll(list, results);
		}
		catch(Exception e) {
		}

		return list;
	}

	@Override
	public List<String> getNYWCDisplay(String accountNumber)throws PortalException {
		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockGetNYWCDisplay()) {
			return _mockCertificateOfInsuranceWebServiceClient.getNYWCDisplay();
		}
		Map<String, String> accountNumbers =  new HashMap<>();
		accountNumbers.put("accountNumber", accountNumber);
		
		
		String response = _webServiceExecutor.executeGet(
				_GET_NYWC_ELIGIBLE_POLICIES, accountNumbers);
		
		JSONDeserializer<String[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<String> list = new ArrayList<>();
	
		try {
			String[] results = jsonDeserializer.deserialize(response, String[].class);

			Collections.addAll(list, results);
		}
		catch(Exception e) {
		}

		return list;
	}

	
	@Override
	public List<CMICPolicyDTO> getEOPEligiblePolicies(String[] policyNumbers) throws PortalException {
		if (_mockCertificateOfInsuranceWebServiceConfiguration.enableMockGetEOPEligiblePolicies()) {
			return _mockCertificateOfInsuranceWebServiceClient.getEOPEligiblePolicies();
		}

		String key = StringUtil.merge(policyNumbers);

		List<CMICPolicyDTO> cache = _getEOPEligiblePoliciesPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		JSONArray bodyParameters = _jsonFactory.createJSONArray(policyNumbers);

		String response = _webServiceExecutor.executePost(
			_GET_EOP_ELIGIBLE_POLICIES, new HashMap<>(), bodyParameters.toString());

		JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICPolicyDTO> list = new ArrayList<>();

		try {
			CMICPolicyDTO[] results = jsonDeserializer.deserialize(response, CMICPolicyDTO[].class);

			Collections.addAll(list, results);
		}
		catch(Exception e) {
		}

		_getEOPEligiblePoliciesPortalCache.put(key, list);

		return list;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockCertificateOfInsuranceWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockCertificateOfInsuranceWebServiceConfiguration.class, properties);

		_getCOIEligiblePoliciesPortalCache =
			(PortalCache<String, List<CMICPolicyDTO>>)_singleVMPool.getPortalCache(
				_GET_COI_ELIGIBLE_POLICIES_CACHE_NAME);
		_getNYWCEligiblePoliciesPortalCache =
				(PortalCache<String, List<CMICPolicyDTO>>)_singleVMPool.getPortalCache(
					_GET_NYWC_ELIGIBLE_POLICIES_CACHE_NAME);
		_getEOPEligiblePoliciesPortalCache =
			(PortalCache<String, List<CMICPolicyDTO>>)_singleVMPool.getPortalCache(
				_GET_EOP_ELIGIBLE_POLICIES_CACHE_NAME);
	}

	private static final String _DOWNLOAD_COI_DOCUMENT_URL = "/certificate-of-insurance-service/v1/forms/acord25";
	
	private static final String _DOWNLOAD_NYWC_DOCUMENT_URL = "/certificate-of-insurance-service/v1/forms/c1052";

	private static final String _DOWNLOAD_EOP_DOCUMENT_URL = "/certificate-of-insurance-service/v1/forms/acord27";

	private static final String _GET_COI_ELIGIBLE_POLICIES_CACHE_NAME =
		CertificateOfInsuranceWebServiceImpl.class.getName() + "_GET_COI_ELIGIBLE_POLICIES";

	private static final String _GET_COI_ELIGIBLE_POLICIES = "/certificate-of-insurance-service/v1/forms/acord25/eligible-policies";
	
	private static final String _GET_NYWC_ELIGIBLE_POLICIES_CACHE_NAME =
			CertificateOfInsuranceWebServiceImpl.class.getName() + "_GET_NYWC_ELIGIBLE_POLICIES";

	private static final String _GET_EOP_ELIGIBLE_POLICIES_CACHE_NAME =
		CertificateOfInsuranceWebServiceImpl.class.getName() + "_GET_EOP_ELIGIBLE_POLICIES";
		
	private static final String _GET_NYWC_ELIGIBLE_POLICIES = "/qs-pds-policy-service/v1/new-york-workers-compensation/policy-numbers-on-account";

	private static final String _GET_EOP_ELIGIBLE_POLICIES = "/certificate-of-insurance-service/v1/forms/acord27/eligible-policies";

	private PortalCache<String, List<CMICPolicyDTO>> _getCOIEligiblePoliciesPortalCache;
	private PortalCache<String, List<CMICPolicyDTO>> _getNYWCEligiblePoliciesPortalCache;
	private PortalCache<String, List<CMICPolicyDTO>> _getEOPEligiblePoliciesPortalCache;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockCertificateOfInsuranceWebServiceClient _mockCertificateOfInsuranceWebServiceClient;

	private MockCertificateOfInsuranceWebServiceConfiguration _mockCertificateOfInsuranceWebServiceConfiguration;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}