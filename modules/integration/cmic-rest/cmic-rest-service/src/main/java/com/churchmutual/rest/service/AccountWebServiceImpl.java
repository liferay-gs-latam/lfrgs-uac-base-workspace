package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.configuration.MockAccountWebServiceConfiguration;
import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;
import com.churchmutual.rest.service.mock.MockAccountWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockAccountWebServiceConfiguration", immediate = true,
	service = AccountWebService.class
)
public class AccountWebServiceImpl implements AccountWebService {

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_GET_ACCOUNTS_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_ACCOUNTS_SEARCH_BY_PRODUCER_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_ADDRESS_ACCOUNT_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_MAILING_ADDRESS_ACCOUNT_CACHE_NAME);
	}

	@Override
	public CMICAccountDTO getAccounts(String accountNumber) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetAccounts()) {
			return _mockAccountWebServiceClient.getAccounts(accountNumber);
		}

		CMICAccountDTO cache = _getAccountsPortalCache.get(accountNumber);

		if (cache != null) {
			return cache;
		}

		String response = _webServiceExecutor.executeGet(_GET_ACCOUNTS_URL, ListUtil.toList(accountNumber));

		if (Validator.isBlank(response)) {
			_log.error("Unable to retrieve information for Account with accountNumber " + accountNumber);

			return null;
		}

		JSONDeserializer<CMICAccountDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICAccountDTO cmicAccountDTO = jsonDeserializer.deserialize(response, CMICAccountDTO.class);

			_getAccountsPortalCache.put(accountNumber, cmicAccountDTO);

			return cmicAccountDTO;
		}
		catch (Exception e) {
			throw new PortalException(
				String.format("Account with accountNumber %s could not be found", accountNumber), e);
		}
	}

	@Override
	public List<CMICAccountDTO> getAccountsSearchByProducer(String[] producerCode) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetAccountsSearchByProducer()) {
			return _mockAccountWebServiceClient.getAccountsSearchByProducer(producerCode);
		}

		String producerCodes = StringUtil.merge(producerCode);

		List<CMICAccountDTO> cmicAccountDTOs = _getAccountsSearchByProducerPortalCache.get(producerCodes);

		if (cmicAccountDTOs != null) {
			return cmicAccountDTOs;
		}

		Map<String, String[]> repeatedQueryParameters = new HashMap<>();

		repeatedQueryParameters.put("producerCode", producerCode);

		String response = _webServiceExecutor.executeGetWithRepeatedQueryParameters(
			_GET_ACCOUNTS_SEARCH_BY_PRODUCER_URL, repeatedQueryParameters);

		JSONDeserializer<CMICAccountDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICAccountDTO[] results = jsonDeserializer.deserialize(response, CMICAccountDTO[].class);

			cmicAccountDTOs = ListUtil.fromArray(results);

			_getAccountsSearchByProducerPortalCache.put(producerCodes, cmicAccountDTOs);

			return cmicAccountDTOs;
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public CMICAddressDTO getAddressAccount(String accountNumber) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetAddressAccount()) {
			return _mockAccountWebServiceClient.getAddressAccount(accountNumber);
		}

		CMICAddressDTO cache = _getAddressAccountPortalCache.get(accountNumber);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNumber", accountNumber);

		String response = _webServiceExecutor.executeGet(_GET_ADDRESS_ACCOUNT_URL, queryParameters);

		JSONDeserializer<CMICAddressDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICAddressDTO cmicAddressDTO = jsonDeserializer.deserialize(response, CMICAddressDTO.class);

			_getAddressAccountPortalCache.put(accountNumber, cmicAddressDTO);

			return cmicAddressDTO;
		}
		catch (Exception e) {
			throw new PortalException(
				String.format("Address for accountNumber %s could not be found", accountNumber), e);
		}
	}

	@Override
	public CMICAddressDTO getMailingAddressAccount(String accountNumber) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetMailingAddressAccount()) {
			return _mockAccountWebServiceClient.getMailingAddressAccount();
		}

		CMICAddressDTO cache = _getMailingAddressAccountPortalCache.get(accountNumber);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNumber", accountNumber);

		String response = _webServiceExecutor.executeGet(_GET_MAILING_ADDRESS_ACCOUNT_URL, queryParameters);

		JSONDeserializer<CMICAddressDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICAddressDTO cmicAddressDTO = jsonDeserializer.deserialize(response, CMICAddressDTO.class);

			_getMailingAddressAccountPortalCache.put(accountNumber, cmicAddressDTO);

			return cmicAddressDTO;
		}
		catch (Exception e) {
			throw new PortalException(
				String.format("Mailing Address for accountNumber %s could not be found", accountNumber), e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockAccountWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockAccountWebServiceConfiguration.class, properties);

		_getAccountsPortalCache = (PortalCache<String, CMICAccountDTO>)_singleVMPool.getPortalCache(
			_GET_ACCOUNTS_CACHE_NAME);
		_getAccountsSearchByProducerPortalCache =
			(PortalCache<String, List<CMICAccountDTO>>)_singleVMPool.getPortalCache(
				_GET_ACCOUNTS_SEARCH_BY_PRODUCER_CACHE_NAME);
		_getAddressAccountPortalCache = (PortalCache<String, CMICAddressDTO>)_singleVMPool.getPortalCache(
			_GET_ADDRESS_ACCOUNT_CACHE_NAME);
		_getMailingAddressAccountPortalCache = (PortalCache<String, CMICAddressDTO>)_singleVMPool.getPortalCache(
			_GET_MAILING_ADDRESS_ACCOUNT_CACHE_NAME);
	}

	private static final Log _log = LogFactoryUtil.getLog(AccountWebServiceImpl.class);

	private static final String _GET_ACCOUNTS_CACHE_NAME = AccountWebServiceImpl.class.getName() + "_GET_ACCOUNTS";

	private static final String _GET_ACCOUNTS_SEARCH_BY_PRODUCER_CACHE_NAME =
		AccountWebServiceImpl.class.getName() + "_GET_ACCOUNTS_SEARCH_BY_PRODUCER";

	private static final String _GET_ACCOUNTS_SEARCH_BY_PRODUCER_URL =
		"/account-service/v1/accounts/search/by-producer";

	private static final String _GET_ACCOUNTS_URL = "/account-service/v1/accounts";

	private static final String _GET_ADDRESS_ACCOUNT_CACHE_NAME =
		AccountWebServiceImpl.class.getName() + "_GET_ADDRESS_ACCOUNT";

	private static final String _GET_ADDRESS_ACCOUNT_URL = "/account-service/v1/addresses/account";

	private static final String _GET_MAILING_ADDRESS_ACCOUNT_CACHE_NAME =
		AccountWebServiceImpl.class.getName() + "_GET_MAILING_ADDRESS_ACCOUNT";

	private static final String _GET_MAILING_ADDRESS_ACCOUNT_URL = "/account-service/v1/addresses/mailing";

	private PortalCache<String, CMICAccountDTO> _getAccountsPortalCache;
	private PortalCache<String, List<CMICAccountDTO>> _getAccountsSearchByProducerPortalCache;
	private PortalCache<String, CMICAddressDTO> _getAddressAccountPortalCache;
	private PortalCache<String, CMICAddressDTO> _getMailingAddressAccountPortalCache;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockAccountWebServiceClient _mockAccountWebServiceClient;

	private MockAccountWebServiceConfiguration _mockAccountWebServiceConfiguration;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}