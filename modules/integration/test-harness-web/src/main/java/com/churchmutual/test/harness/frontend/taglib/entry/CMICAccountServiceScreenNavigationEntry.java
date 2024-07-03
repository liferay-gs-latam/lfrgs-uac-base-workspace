package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;
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
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=10", service = ScreenNavigationEntry.class
)
public class CMICAccountServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_ACCOUNT_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		List<HarnessDescriptor> harnessDescriptors = new ArrayList<>();

		HarnessDescriptor getAccountsByProducerCodesDescriptor = new HarnessDescriptor(
			"Get a list of all accounts that match the specified producer code(s)",
			_GET_ACCOUNTS_BY_PRODUCER_CODES_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter producerCode = new HarnessDescriptor.Parameter(
			"producerCode", "producerCode", true, new String[] {"03254", "03253"}, "String[]");

		getAccountsByProducerCodesDescriptor.addQueryParameters(producerCode);

		harnessDescriptors.add(getAccountsByProducerCodesDescriptor);

		HarnessDescriptor getAccountByAccountNumberDescriptor = new HarnessDescriptor(
			"Get an account with the specified account number", _GET_ACCOUNT_BY_ACCOUNT_NUMBER_ENDPOINT,
			Http.Method.GET);

		HarnessDescriptor.Parameter accountNumber = new HarnessDescriptor.Parameter(
			"accountNumber ", "accountNumber ", true, "00000015", String.class.getName());

		getAccountByAccountNumberDescriptor.addPathParameters(accountNumber);

		harnessDescriptors.add(getAccountByAccountNumberDescriptor);

		HarnessDescriptor getAddressAccountDescriptor = new HarnessDescriptor(
			"Get an active account address for a specified account number", _GET_ADDRESS_ACCOUNT, Http.Method.GET);

		getAddressAccountDescriptor.addQueryParameters(accountNumber);

		harnessDescriptors.add(getAddressAccountDescriptor);

		HarnessDescriptor getMailingAddressAccountDescriptor = new HarnessDescriptor(
			"Get account mailing address by account number", _GET_MAILING_ADDRESS_ACCOUNT, Http.Method.GET);

		getMailingAddressAccountDescriptor.addQueryParameters(accountNumber);

		harnessDescriptors.add(getMailingAddressAccountDescriptor);

		return harnessDescriptors;
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "account-service");
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
			if (_GET_ACCOUNT_BY_ACCOUNT_NUMBER_ENDPOINT.equals(endpoint)) {
				String accountNumber = ParamUtil.getString(portletRequest, "accountNumber");

				CMICAccountDTO account = _accountWebService.getAccounts(accountNumber);

				response.put(account.toJSONObject());
			}
			else if (_GET_ACCOUNTS_BY_PRODUCER_CODES_ENDPOINT.equals(endpoint)) {
				String[] producerCode = ParamUtil.getStringValues(portletRequest, "producerCode");

				List<CMICAccountDTO> accounts = _accountWebService.getAccountsSearchByProducer(producerCode);

				accounts.forEach(account -> response.put(account.toJSONObject()));
			}
			else if (_GET_ADDRESS_ACCOUNT.equals(endpoint)) {
				String accountNumber = ParamUtil.getString(portletRequest, "accountNumber");

				CMICAddressDTO address = _accountWebService.getAddressAccount(accountNumber);

				response.put(address.toJSONObject());
			}
			else if (_GET_MAILING_ADDRESS_ACCOUNT.equals(endpoint)) {
				String accountNumber = ParamUtil.getString(portletRequest, "accountNumber");

				CMICAddressDTO address = _accountWebService.getMailingAddressAccount(accountNumber);

				response.put(address.toJSONObject());
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

	private static final String _GET_ACCOUNT_BY_ACCOUNT_NUMBER_ENDPOINT = "/v1/accounts/{accountNumber}";

	private static final String _GET_ACCOUNTS_BY_PRODUCER_CODES_ENDPOINT = "/v1/accounts/search/by-producer";

	private static final String _GET_ADDRESS_ACCOUNT = "/v1/addresses/account";

	private static final String _GET_MAILING_ADDRESS_ACCOUNT = "/v1/addresses/mailing";

	private static final Log _log = LogFactoryUtil.getLog(CMICAccountServiceScreenNavigationEntry.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private JSPRenderer _jspRenderer;

}