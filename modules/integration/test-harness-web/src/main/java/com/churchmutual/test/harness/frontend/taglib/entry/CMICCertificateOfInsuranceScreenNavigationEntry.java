package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.CertificateOfInsuranceWebService;
import com.churchmutual.rest.model.CMICAdditionalInterestDTO;
import com.churchmutual.rest.model.CMICPolicyDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=70", service = ScreenNavigationEntry.class
)
public class CMICCertificateOfInsuranceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_CERTIFICATE_OF_INSURANCE_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {

		HarnessDescriptor.Parameter certificateHolder = new HarnessDescriptor.Parameter(
			"Certificate Holder", "certificateHolder", true, "{\n" +
			"    \"city\": \"Merrill\",\n" +
			"    \"fullName\": \"John Doe\",\n" +
			"    \"addressLine1\": \"1234 Main Street\",\n" +
			"    \"addressLine2\": \"P.O. Box 135\",\n" +
			"    \"postalCode\": \"54321\",\n" +
			"    \"stateOrProvinceCode\": \"WI\"\n" +
			"  }", "JSONObject");

		HarnessDescriptor.Parameter showingSexualMisconductLimits = new HarnessDescriptor.Parameter(
			"Show SM Limits", "showingSexualMisconductLimits", true, false, Boolean.class.toString());

		HarnessDescriptor.Parameter showHiredAndNonOwnedAutoLimits = new HarnessDescriptor.Parameter(
			"Show Hired And Non Owned Auto Limits", "showHiredAndNonOwnedAutoLimits", true, false,
			Boolean.class.toString());

		HarnessDescriptor.Parameter editable = new HarnessDescriptor.Parameter(
			"Editable", "editable", true, false, Boolean.class.toString());

		HarnessDescriptor.Parameter rentalAutoCertificate = new HarnessDescriptor.Parameter(
			"Rental Auto Certificate", "rentalAutoCertificate", true, false, Boolean.class.toString());

		HarnessDescriptor.Parameter performUpload = new HarnessDescriptor.Parameter(
			"Perform Upload", "performUpload", false, true, Boolean.class.toString());

		HarnessDescriptor.Parameter policyNumbers = new HarnessDescriptor.Parameter(
			"Policy Numbers", "policyNumbers", true,
			new String[] {"0000015 07-106004", "0000015 25-110232", "0000015 85-101509", "0000015 09-001190"},
			"String[]");

		HarnessDescriptor downloadCOIDocumentDescriptor = new HarnessDescriptor(
			"Download COI Document", _DOWNLOAD_COI_DOCUMENT_ENDPOINT, Http.Method.POST);

		downloadCOIDocumentDescriptor.addBodyParameters(
			certificateHolder, policyNumbers, showingSexualMisconductLimits, showHiredAndNonOwnedAutoLimits,
			rentalAutoCertificate, editable, performUpload);

		HarnessDescriptor.Parameter additionalInterest = new HarnessDescriptor.Parameter(
				"Additional Interest", "additionalInterest", true, "{\n" +
				"    \"city\": \"Merrill\",\n" +
				"    \"fullName\": \"John Doe\",\n" +
				"    \"addressLine1\": \"1234 Main Street\",\n" +
				"    \"addressLine2\": \"P.O. Box 135\",\n" +
				"    \"mortgagee\": true,\n" +
				"    \"postalCode\": \"54321\",\n" +
				"    \"stateOrProvinceCode\": \"WI\"\n" +
				"  }", "JSONObject");

		HarnessDescriptor.Parameter policyNumber = new HarnessDescriptor.Parameter(
			"Policy Number", "policyNumber", true, "0404008 25-201889", "String");

		HarnessDescriptor downloadEOPDocumentDescriptor = new HarnessDescriptor(
			"Download EOP Document", _DOWNLOAD_EOP_DOCUMENT_ENDPOINT, Http.Method.POST);

		HarnessDescriptor.Parameter buildingNumber = new HarnessDescriptor.Parameter(
			"Building Number", "buildingNumber", true, "001", "String");

		HarnessDescriptor.Parameter locationPremisesNumber = new HarnessDescriptor.Parameter(
			"Location Premises Number", "locationPremisesNumber", true, "001", "String");

		downloadEOPDocumentDescriptor.addBodyParameters(
			additionalInterest, buildingNumber, editable, performUpload, locationPremisesNumber, policyNumber);

		HarnessDescriptor getCOIEligiblePoliciesDescriptor = new HarnessDescriptor(
			"Get COI Eligible Policies", _GET_COI_ELIGIBLE_POLICIES_ENDPOINT, Http.Method.POST);

		getCOIEligiblePoliciesDescriptor.addBodyParameters(policyNumbers);

		HarnessDescriptor getEOPEligiblePoliciesDescriptor = new HarnessDescriptor(
			"Get EOP Eligible Policies", _GET_EOP_ELIGIBLE_POLICIES_ENDPOINT, Http.Method.POST);

		getEOPEligiblePoliciesDescriptor.addBodyParameters(policyNumbers);

		return ListUtil.fromArray(
			downloadCOIDocumentDescriptor, downloadEOPDocumentDescriptor, getCOIEligiblePoliciesDescriptor,
			getEOPEligiblePoliciesDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "certificate-of-insurance-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		try {
			if (_DOWNLOAD_COI_DOCUMENT_ENDPOINT.equals(endpoint)) {
				String certificateHolder = ParamUtil.getString(portletRequest, "certificateHolder");
				boolean showingSexualMisconductLimits = ParamUtil.getBoolean(portletRequest, "showingSexualMisconductLimits");
				boolean showHiredAndNonOwnedAutoLimits = ParamUtil.getBoolean(
					portletRequest, "showHiredAndNonOwnedAutoLimits");
				boolean editable = ParamUtil.getBoolean(portletRequest, "editable");
				boolean rentalAutoCertificate = ParamUtil.getBoolean(portletRequest, "rentalAutoCertificate");
				boolean performUpload = ParamUtil.getBoolean(portletRequest, "performUpload");
				String[] policyNumbers = ParamUtil.getStringValues(portletRequest, "policyNumbers");

				JSONObject certificateHolderJSONObject = JSONFactoryUtil.createJSONObject(certificateHolder);

				String city = certificateHolderJSONObject.getString("city");
				String fullName = certificateHolderJSONObject.getString("fullName");
				String addressLine1 = certificateHolderJSONObject.getString("addressLine1");
				String addressLine2 = certificateHolderJSONObject.getString("addressLine2");
				String postalCode = certificateHolderJSONObject.getString("postalCode");
				String stateOrProvinceCode = certificateHolderJSONObject.getString("stateOrProvinceCode");

				String COIDocument = _certificateOfInsuranceWebService.downloadCOIDocument(
					city, fullName, addressLine1, addressLine2, postalCode, stateOrProvinceCode,
					showingSexualMisconductLimits, showHiredAndNonOwnedAutoLimits, rentalAutoCertificate, editable,
					performUpload, policyNumbers);

				response.put(COIDocument);
			}
			else if (_DOWNLOAD_EOP_DOCUMENT_ENDPOINT.equals(endpoint)) {
				String additionalInterest = ParamUtil.getString(portletRequest, "additionalInterest");
				String buildingNumber = ParamUtil.getString(portletRequest, "buildingNumber");
				boolean editable = ParamUtil.getBoolean(portletRequest, "editable");
				boolean performUpload = ParamUtil.getBoolean(portletRequest, "performUpload");
				String locationPremisesNumber = ParamUtil.getString(portletRequest, "locationPremisesNumber");
				String policyNumber = ParamUtil.getString(portletRequest, "policyNumber");

				JSONObject additionalInterestJSONObject = JSONFactoryUtil.createJSONObject(additionalInterest);

				CMICAdditionalInterestDTO cmicAdditionalInterestDTO = new CMICAdditionalInterestDTO().fromJSONObject(
					additionalInterestJSONObject);

				String EOPDocument = _certificateOfInsuranceWebService.downloadEOPDocument(
					cmicAdditionalInterestDTO, policyNumber, editable, performUpload, buildingNumber,
					locationPremisesNumber);

				response.put(EOPDocument);
			}
			else if (_GET_COI_ELIGIBLE_POLICIES_ENDPOINT.equals(endpoint)) {
				String[] policyNumbers = ParamUtil.getStringValues(portletRequest, "policyNumbers");

				List<CMICPolicyDTO> cmicPolicyDTOs = _certificateOfInsuranceWebService.getCOIEligiblePolicies(policyNumbers);

				cmicPolicyDTOs.forEach(cmicPolicyDTO -> response.put(cmicPolicyDTO.toJSONObject()));
			}
			else if (_GET_EOP_ELIGIBLE_POLICIES_ENDPOINT.equals(endpoint)) {
				String[] policyNumbers = ParamUtil.getStringValues(portletRequest, "policyNumbers");

				List<CMICPolicyDTO> cmicPolicyDTOs = _certificateOfInsuranceWebService.getEOPEligiblePolicies(policyNumbers);

				cmicPolicyDTOs.forEach(cmicPolicyDTO -> response.put(cmicPolicyDTO.toJSONObject()));
			}
		}
		catch (PortalException pe) {
			response.put(pe.getMessage());

			if (_log.isErrorEnabled()) {
				_log.error("Could not get response for " + endpoint, pe);
			}
		}

		return response.toString();
	}

	private static final String _DOWNLOAD_COI_DOCUMENT_ENDPOINT = "/v1/forms/acord25";

	private static final String _DOWNLOAD_EOP_DOCUMENT_ENDPOINT = "/v1/forms/acord27";

	private static final String _GET_COI_ELIGIBLE_POLICIES_ENDPOINT = "/v1/forms/acord25/eligible-policies";

	private static final String _GET_EOP_ELIGIBLE_POLICIES_ENDPOINT = "/v1/forms/acord27/eligible-policies";

	private static final Log _log = LogFactoryUtil.getLog(CMICCertificateOfInsuranceScreenNavigationEntry.class);

	@Reference
	private CertificateOfInsuranceWebService _certificateOfInsuranceWebService;

	@Reference
	private JSPRenderer _jspRenderer;

}