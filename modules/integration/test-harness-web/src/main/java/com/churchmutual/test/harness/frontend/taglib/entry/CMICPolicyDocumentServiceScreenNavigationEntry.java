package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.PolicyDocumentWebService;
import com.churchmutual.rest.model.CMICPolicyDocumentDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=50", service = ScreenNavigationEntry.class
)
public class CMICPolicyDocumentServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_POLICY_DOCUMENT_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor.Parameter accountNum = new HarnessDescriptor.Parameter(
			"accountNumber", "accountNumber", true, "0000015", String.class.getName());
		HarnessDescriptor.Parameter includeBytes = new HarnessDescriptor.Parameter(
			"includeBytes", "includeBytes", false, true, Boolean.class.getName());
		HarnessDescriptor.Parameter policyNum = new HarnessDescriptor.Parameter(
			"policyNumber", "policyNumber", true, "001217", String.class.getName());
		HarnessDescriptor.Parameter policyType = new HarnessDescriptor.Parameter(
			"policyType", "policyType", true, "07", String.class.getName());
		HarnessDescriptor.Parameter sequenceNum = new HarnessDescriptor.Parameter(
			"sequenceNumber", "sequenceNumber", true, "1", String.class.getName());

		HarnessDescriptor getTransactionsDescriptor = new HarnessDescriptor(
			"Download transaction document", _GET_TRANSACTIONS_ENDPOINT, Http.Method.GET);

		getTransactionsDescriptor.addQueryParameters(accountNum, includeBytes, policyNum, policyType, sequenceNum);

		return ListUtil.fromArray(getTransactionsDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "policy-document-service");
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
			if (_GET_TRANSACTIONS_ENDPOINT.equals(endpoint)) {
				String accountNumber = ParamUtil.getString(portletRequest, "accountNumber");
				boolean includeBytes = ParamUtil.getBoolean(portletRequest, "includeBytes");
				String policyNumber = ParamUtil.getString(portletRequest, "policyNumber");
				String policyType = ParamUtil.getString(portletRequest, "policyType");
				String sequenceNumber = ParamUtil.getString(portletRequest, "sequenceNumber");

				CMICPolicyDocumentDTO policyDocument = _policyDocumentWebService.downloadTransaction(
					accountNumber, includeBytes, policyNumber, policyType, sequenceNumber);

				response.put(policyDocument.toJSONObject());
			}
		}
		catch (Exception pe) {
			response.put(pe.getMessage());

			if (_log.isErrorEnabled()) {
				_log.error("Could not get response for " + endpoint, pe);
			}
		}

		return response.toString();
	}

	private static final String _GET_TRANSACTIONS_ENDPOINT = "/v1/download/transactions";

	private static final Log _log = LogFactoryUtil.getLog(CMICPolicyDocumentServiceScreenNavigationEntry.class);

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PolicyDocumentWebService _policyDocumentWebService;

}