package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.exception.WebServiceException;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration;
import com.churchmutual.rest.exception.SelfProvisioningException;
import com.churchmutual.rest.exception.UserRegistrationException;
import com.churchmutual.rest.model.CMICInsuredUserDTO;
import com.churchmutual.rest.model.CMICProducerUserDTO;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.service.mock.MockPortalUserWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration", immediate = true,
	service = PortalUserWebService.class
)
public class PortalUserWebServiceImpl implements PortalUserWebService {

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_GET_ACCOUNT_ENTITY_USERS_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_PRODUCER_ENTITY_USERS_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_USER_DETAILS_CACHE_NAME);
		_singleVMPool.removePortalCache(_IS_USER_REGISTERED_CACHE_NAME);
	}

	@Override
	public CMICUserDTO deleteInsuredEntityUser(
			long callerCMICUserId, long cmicUserId, String accountNumber, String companyNumber)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockDeleteInsuredEntityUser()) {
			return _mockPortalUserWebServiceClient.deleteInsuredEntityUser();
		}

		List<String> pathParameters = new ArrayList<>();

		pathParameters.add(String.valueOf(cmicUserId));

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNumber", accountNumber);
		queryParameters.put("callerUserId", String.valueOf(callerCMICUserId));
		queryParameters.put("companyNumber", companyNumber);

		String response = _webServiceExecutor.executePut(
			_DELETE_INSURED_ENTITY_USER_URL, pathParameters, queryParameters);

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getAccountEntityUsersPortalCache.remove(accountNumber);
		_getUserDetailsPortalCache.remove(cmicUserId);

		return cmicUserDTO;
	}

	@Override
	public CMICUserDTO deleteProducerEntityUser(long callerCMICUserId, long cmicUserId, long producerId)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockDeleteProducerEntityUser()) {
			return _mockPortalUserWebServiceClient.deleteProducerEntityUser();
		}

		List<String> pathParameters = new ArrayList<>();

		pathParameters.add(String.valueOf(cmicUserId));

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("callerUserId", String.valueOf(callerCMICUserId));
		queryParameters.put("producerId", String.valueOf(producerId));

		String response = _webServiceExecutor.executePut(
			_DELETE_PRODUCER_ENTITY_USER_URL, pathParameters, queryParameters);

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getProducerEntityUsersPortalCache.remove(producerId);
		_getUserDetailsPortalCache.remove(cmicUserId);

		return cmicUserDTO;
	}

	@Override
	public String generateLossRunReport(
			String accountNumber, long cmicUserId, String requesterFirstName, String requesterLastName,
			String claimHistoryEndDate, String claimHistoryStartDate, String requestReason,
			boolean showClaimantInformation, boolean showReserveInformation)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockGenerateLossRunReport()) {
			return _mockPortalUserWebServiceClient.generateLossRunReport();
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNumber", accountNumber);
		queryParameters.put("claimHistoryEndDate", claimHistoryEndDate);
		queryParameters.put("claimHistoryStartDate", claimHistoryStartDate);
		queryParameters.put("requestReason", requestReason);
		queryParameters.put("showClaimantInformation", String.valueOf(showClaimantInformation));
		queryParameters.put("showReserveInformation", String.valueOf(showReserveInformation));
		queryParameters.put("userId", String.valueOf(cmicUserId));
		queryParameters.put("requesterFirstName", requesterFirstName);
		queryParameters.put("requesterLastName", requesterLastName);

		return _webServiceExecutor.executeGet(_GENERATE_LOSS_RUN_REPORT_URL, queryParameters);
	}

	@Override
	public List<CMICUserDTO> getAccountEntityUsers(String accountNumber) throws PortalException {
		if (_mockPortalUserWebServiceConfiguration.enableMockGetAccountEntityUsers()) {
			return _mockPortalUserWebServiceClient.getAccountEntityUsers();
		}

		List<CMICUserDTO> cache = _getAccountEntityUsersPortalCache.get(accountNumber);

		if (cache != null) {
			return cache;
		}

		String url = StringUtil.replace(
			_GET_ACCOUNT_ENTITY_USERS_URL, "{{ACCOUNT_NUMBER}}", String.valueOf(accountNumber));

		String response = _webServiceExecutor.executeGet(url, new ArrayList<>());

		JSONDeserializer<CMICUserDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICUserDTO> list = new ArrayList();

		try {
			CMICUserDTO[] results = jsonDeserializer.deserialize(response, CMICUserDTO[].class);

			list = Arrays.asList(results);
		}
		catch (Exception e) {
			_log.error(
				String.format("Could not get insured users for accountNumber, %s: %s", accountNumber, e.getMessage()));

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		_getAccountEntityUsersPortalCache.put(accountNumber, list);

		return list;
	}

	@Override
	public List<CMICUserDTO> getProducerEntityUsers(long producerId) throws PortalException {
		if (_mockPortalUserWebServiceConfiguration.enableMockGetProducerEntityUsers()) {
			return _mockPortalUserWebServiceClient.getProducerEntityUsers();
		}

		List<CMICUserDTO> cache = _getProducerEntityUsersPortalCache.get(producerId);

		if (cache != null) {
			return cache;
		}

		String url = StringUtil.replace(_GET_PRODUCER_ENTITY_USERS_URL, "{{PRODUCER_ID}}", String.valueOf(producerId));

		String response = _webServiceExecutor.executeGet(url, new ArrayList<>());

		JSONDeserializer<CMICUserDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICUserDTO> list = new ArrayList();

		try {
			CMICUserDTO[] results = jsonDeserializer.deserialize(response, CMICUserDTO[].class);

			list = Arrays.asList(results);
		}
		catch (Exception e) {
			_log.error(
				String.format("Could not get producer users for producerId, %s: %s", producerId, e.getMessage()));

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		_getProducerEntityUsersPortalCache.put(producerId, list);

		return list;
	}

	@Override
	public CMICUserDTO getUserDetails(long cmicUserId) throws PortalException {
		if (_mockPortalUserWebServiceConfiguration.enableMockGetUserDetails()) {
			return _mockPortalUserWebServiceClient.getUserDetails();
		}

		CMICUserDTO cache = _getUserDetailsPortalCache.get(cmicUserId);

		if (cache != null) {
			return cache;
		}

		String url = StringUtil.replace(_GET_USER_DETAILS_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		String response = _webServiceExecutor.executeGet(url, new ArrayList<>());

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getUserDetailsPortalCache.put(cmicUserId, cmicUserDTO);

		return cmicUserDTO;
	}

	@Override
	public boolean isUserRegistered(long cmicUserId) throws PortalException {
		if (_mockPortalUserWebServiceConfiguration.enableMockIsUserRegistered()) {
			return _mockPortalUserWebServiceClient.isUserRegistered();
		}

		Boolean cache = _isUserRegisteredCache.get(cmicUserId);

		if (cache != null) {
			return cache;
		}

		String url = StringUtil.replace(_IS_USER_REGISTERED_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		String response = _webServiceExecutor.executeGet(url, new ArrayList<>());

		JSONDeserializer<Boolean> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		Boolean isUserRegistered = jsonDeserializer.deserialize(response, Boolean.class);

		if (Validator.isNull(isUserRegistered)) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId));
		}

		_isUserRegisteredCache.put(cmicUserId, isUserRegistered);

		return isUserRegistered;
	}

	@Override
	public boolean isServiceHealthy() {
		boolean status = false;

		try {
			String response = _webServiceExecutor.executeGet(_IS_SERVICE_HEALTHY_URL, new ArrayList<>());

			String[] splitResponse = response.replace("\"", "").split(":");
			String[] statusUp = splitResponse[1].split("}");

			if (statusUp[0].toUpperCase().equals("UP")) {
				status = true;
			}
		} catch (Exception e){

		}

		return status;
	}
	
	@Override
	public CMICInsuredUserDTO provisionExistingInsuredUser(
			long callerCMICUserId, long cmicUserId, String accountNumber, String companyNumber, long producerId,
			String email)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockProvisionExistingInsuredUser()) {
			return _mockPortalUserWebServiceClient.provisionExistingInsuredUser();
		}

		String url = StringUtil.replace(
			_PROVISION_EXISTING_INSURED_USER_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		JSONObject body = _jsonFactory.createJSONObject();

		JSONArray emails = _jsonFactory.createJSONArray();

		emails.put(email);

		body.put("accountNumber", accountNumber);
		body.put("companyNumber", companyNumber);
		body.put("emails", emails);
		body.put("inviterUserId", String.valueOf(callerCMICUserId));

		if (producerId > 0) {
			body.put("producerId", String.valueOf(producerId));
		}

		String response = null;

		try {
			response = _webServiceExecutor.executePut(url, body.toString());
		}
		catch (WebServiceException wse) {
			if (wse.getResponseCode() == _UNPROCESSABLE_ENTITY_STATUS_CODE) {
				JSONObject payload = JSONFactoryUtil.createJSONObject(wse.getPayload());

				JSONArray errorsArray = payload.getJSONArray("errors");

				for (int i = 0; i < errorsArray.length(); i++) {
					String error = errorsArray.getString(i);

					if (error.contains(_CMIC_DUPLICATE_PROVISIONING_ERROR_RESPONSE)) {
						throw new SelfProvisioningException(SelfProvisioningException.DUPLICATE_USER);
					}
				}
			}

			throw wse;
		}

		JSONDeserializer<CMICInsuredUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICInsuredUserDTO cmicInsuredUserDTO = null;

		try {
			cmicInsuredUserDTO = jsonDeserializer.deserialize(response, CMICInsuredUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("Error occurred when provisioning user with id %d", cmicUserId), e);
		}

		_getAccountEntityUsersPortalCache.remove(accountNumber);
		_getUserDetailsPortalCache.remove(cmicUserId);

		return cmicInsuredUserDTO;
	}

	@Override
	public CMICProducerUserDTO provisionExistingProducerUser(
			long callerCMICUserId, long cmicUserId, long producerId, String email)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockProvisionExistingProducerUser()) {
			return _mockPortalUserWebServiceClient.provisionExistingProducerUser();
		}

		String url = StringUtil.replace(
			_PROVISION_EXISTING_PRODUCER_USER_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		JSONObject body = _jsonFactory.createJSONObject();

		JSONArray emails = _jsonFactory.createJSONArray();

		emails.put(email);

		body.put("emails", emails);
		body.put("inviterUserId", String.valueOf(callerCMICUserId));
		body.put("producerId", String.valueOf(producerId));

		String response = null;

		try {
			response = _webServiceExecutor.executePut(url, body.toString());
		}
		catch (WebServiceException wse) {
			if (wse.getResponseCode() == _UNPROCESSABLE_ENTITY_STATUS_CODE) {
				JSONObject payload = JSONFactoryUtil.createJSONObject(wse.getPayload());

				JSONArray errorsArray = payload.getJSONArray("errors");

				for (int i = 0; i < errorsArray.length(); i++) {
					String error = errorsArray.getString(i);

					if (error.contains(_CMIC_DUPLICATE_PROVISIONING_ERROR_RESPONSE)) {
						throw new SelfProvisioningException(SelfProvisioningException.DUPLICATE_USER);
					}
				}
			}

			throw wse;
		}

		JSONDeserializer<CMICProducerUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICProducerUserDTO cmicProducerUserDTO = null;

		try {
			cmicProducerUserDTO = jsonDeserializer.deserialize(response, CMICProducerUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("Error occurred when provisioning user with id %d", cmicUserId), e);
		}

		_getProducerEntityUsersPortalCache.remove(producerId);
		_getUserDetailsPortalCache.remove(cmicUserId);

		return cmicProducerUserDTO;
	}

	@Override
	public List<CMICUserDTO> provisionNewInsuredUsers(
			long callerCMICUserId, String accountNumber, String companyNumber, long producerId, List<String> emails)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockProvisionNewInsuredUsers()) {
			return _mockPortalUserWebServiceClient.provisionNewInsuredUsers();
		}

		List<CMICUserDTO> list = new ArrayList();

		if (emails.isEmpty()) {
			return list;
		}

		JSONObject body = _jsonFactory.createJSONObject();

		body.put("accountNumber", accountNumber);
		body.put("companyNumber", companyNumber);
		body.put("emails", _jsonFactory.createJSONArray(emails));
		body.put("inviterUserId", String.valueOf(callerCMICUserId));

		if (producerId > 0) {
			body.put("producerId", String.valueOf(producerId));
		}

		String response = _webServiceExecutor.executePost(
			_PROVISION_NEW_INSURED_USER_URL, new HashMap<>(), body.toString());

		JSONDeserializer<CMICUserDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICUserDTO[] results = jsonDeserializer.deserialize(response, CMICUserDTO[].class);

			list = Arrays.asList(results);
		}
		catch (Exception e) {
			_log.error(
				String.format(
					"Error provisioning new users for accountNumber, %s, and companyNumber, %s: %s", accountNumber,
					companyNumber, e.getMessage()));

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		_getAccountEntityUsersPortalCache.remove(accountNumber);

		return list;
	}

	@Override
	public List<CMICUserDTO> provisionNewProducerUsers(long callerCMICUserId, long producerId, List<String> emails)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockProvisionNewProducerUsers()) {
			return _mockPortalUserWebServiceClient.provisionNewProducerUsers();
		}

		List<CMICUserDTO> list = new ArrayList();

		if (emails.isEmpty()) {
			return list;
		}

		JSONObject body = _jsonFactory.createJSONObject();

		body.put("emails", _jsonFactory.createJSONArray(emails));
		body.put("inviterUserId", String.valueOf(callerCMICUserId));
		body.put("producerId", String.valueOf(producerId));

		String response = _webServiceExecutor.executePost(
			_PROVISION_NEW_PRODUCER_USER_URL, new HashMap<>(), body.toString());

		JSONDeserializer<CMICUserDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICUserDTO[] results = jsonDeserializer.deserialize(response, CMICUserDTO[].class);

			list = Arrays.asList(results);
		}
		catch (Exception e) {
			_log.error(
				String.format("Error provisioning new users for producerId, %s: %s", producerId, e.getMessage()));

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		_getProducerEntityUsersPortalCache.remove(producerId);

		return list;
	}

	@Override
	public CMICUserDTO updateInsuredUserRole(
			long callerCMICUserId, long cmicUserId, String accountNumber, String companyNumber, String role)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockUpdateInsuredUserRole()) {
			return _mockPortalUserWebServiceClient.updateInsuredUserRole();
		}

		String url = StringUtil.replace(_UPDATE_INSURED_USER_ROLE_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		JSONObject body = _jsonFactory.createJSONObject();

		body.put("accountNumber", accountNumber);
		body.put("callerUserId", String.valueOf(callerCMICUserId));
		body.put("companyNumber", companyNumber);
		body.put("role", role);

		String response = _webServiceExecutor.executePut(url, body.toString());

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getUserDetailsPortalCache.remove(cmicUserId);

		return cmicUserDTO;
	}

	@Override
	public CMICUserDTO updateProducerUserRole(long callerCMICUserId, long cmicUserId, long producerId, String role)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockUpdateProducerUserRole()) {
			return _mockPortalUserWebServiceClient.updateProducerUserRole();
		}

		String url = StringUtil.replace(_UPDATE_PRODUCER_USER_ROLE_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		JSONObject body = _jsonFactory.createJSONObject();

		body.put("callerUserId", String.valueOf(callerCMICUserId));
		body.put("producerId", String.valueOf(producerId));
		body.put("role", role);

		String response = _webServiceExecutor.executePut(url, body.toString());

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getUserDetailsPortalCache.remove(cmicUserId);

		return cmicUserDTO;
	}

	@Override
	public CMICUserDTO validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String companyNumber, String registrationCode, String cmicUUID,
			String zipCode)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockValidateInsuredUser()) {
			return _mockPortalUserWebServiceClient.validateInsuredUserRegistration();
		}

		String url = StringUtil.replace(
			_VALIDATE_INSURED_USER_REGISTRATION_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		JSONObject body = _jsonFactory.createJSONObject();

		body.put("accountNumber", accountNumber);
		body.put("companyNumber", companyNumber);
		body.put("registrationCode", registrationCode);
		body.put("uuid", cmicUUID);
		body.put("zipCode", zipCode);

		String response = null;

		try {
			response = _webServiceExecutor.executePut(url, body.toString());
		}
		catch (WebServiceException wse) {
			if (wse.getResponseCode() == _UNPROCESSABLE_ENTITY_STATUS_CODE) {
				JSONObject payload = JSONFactoryUtil.createJSONObject(wse.getPayload());

				JSONArray errorsArray = payload.getJSONArray("errors");

				for (int i = 0; i < errorsArray.length(); i++) {
					String error = errorsArray.getString(i);

					if (error.contains(_CMIC_ZIP_CODE_ERROR_RESPONSE)) {
						throw new UserRegistrationException(UserRegistrationException.ZIP_CODE);
					}
				}

				throw new UserRegistrationException(UserRegistrationException.ACCOUNT_NUMBER);
			}

			throw wse;
		}

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getUserDetailsPortalCache.remove(cmicUserId);
		_isUserRegisteredCache.remove(cmicUserId);

		return cmicUserDTO;
	}

	@Override
	public CMICUserDTO validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber, String registrationCode, String cmicUUID,
			String zipCode)
		throws PortalException {

		if (_mockPortalUserWebServiceConfiguration.enableMockValidateProducerUser()) {
			return _mockPortalUserWebServiceClient.validateProducerUserRegistration();
		}

		String url = StringUtil.replace(
			_VALIDATE_PRODUCER_USER_REGISTRATION_URL, "{{USER_ID}}", String.valueOf(cmicUserId));

		JSONObject body = _jsonFactory.createJSONObject();

		body.put("agentNumber", agentNumber);
		body.put("divisionNumber", divisionNumber);
		body.put("registrationCode", registrationCode);
		body.put("uuid", cmicUUID);
		body.put("zipCode", zipCode);

		String response = null;

		try {
			response = _webServiceExecutor.executePut(url, body.toString());
		}
		catch (WebServiceException wse) {
			if (wse.getResponseCode() == _UNPROCESSABLE_ENTITY_STATUS_CODE) {
				JSONObject payload = JSONFactoryUtil.createJSONObject(wse.getPayload());

				JSONArray errorsArray = payload.getJSONArray("errors");

				for (int i = 0; i < errorsArray.length(); i++) {
					String error = errorsArray.getString(i);

					if (error.contains(_CMIC_ZIP_CODE_ERROR_RESPONSE)) {
						throw new UserRegistrationException(UserRegistrationException.ZIP_CODE);
					}
				}

				throw new UserRegistrationException(UserRegistrationException.PRODUCER_CODE);
			}

			throw wse;
		}

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with id %d could not be found", cmicUserId), e);
		}

		_getUserDetailsPortalCache.remove(cmicUserId);
		_isUserRegisteredCache.remove(cmicUserId);

		return cmicUserDTO;
	}

	@Override
	public CMICUserDTO validateUserRegistrationCode(String registrationCode) throws PortalException {
		if (_mockPortalUserWebServiceConfiguration.enableMockValidateUser()) {
			return _mockPortalUserWebServiceClient.validateUser(registrationCode);
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("registrationCode", registrationCode);

		String response = _webServiceExecutor.executeGet(_VALIDATE_USER_URL, queryParameters);

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("Registration code %s could not be found", registrationCode), e);
		}

		if (cmicUserDTO.getRegistrationExpired()) {
			throw new UserRegistrationException(UserRegistrationException.REGISTRATION_CODE);
		}

		return cmicUserDTO;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockPortalUserWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockPortalUserWebServiceConfiguration.class, properties);

		_getAccountEntityUsersPortalCache = (PortalCache<String, List<CMICUserDTO>>)_singleVMPool.getPortalCache(
			_GET_ACCOUNT_ENTITY_USERS_CACHE_NAME);
		_getProducerEntityUsersPortalCache = (PortalCache<Long, List<CMICUserDTO>>)_singleVMPool.getPortalCache(
			_GET_PRODUCER_ENTITY_USERS_CACHE_NAME);
		_getUserDetailsPortalCache = (PortalCache<Long, CMICUserDTO>)_singleVMPool.getPortalCache(
			_GET_USER_DETAILS_CACHE_NAME);
		_isUserRegisteredCache = (PortalCache<Long, Boolean>)_singleVMPool.getPortalCache(
			_IS_USER_REGISTERED_CACHE_NAME);
	}

	private static final String _CMIC_DUPLICATE_PROVISIONING_ERROR_RESPONSE = "is already self-provisioned for";

	private static final String _CMIC_ZIP_CODE_ERROR_RESPONSE = "Zip code didn't match";

	private static final String _DELETE_INSURED_ENTITY_USER_URL = "/portal-user-service/v2/insured-users";

	private static final String _DELETE_PRODUCER_ENTITY_USER_URL = "/portal-user-service/v2/producer-users";

	private static final String _GENERATE_LOSS_RUN_REPORT_URL = "/portal-user-service/v1/loss-run/for-account/pdf";

	private static final String _GET_ACCOUNT_ENTITY_USERS_CACHE_NAME =
		PortalUserWebServiceImpl.class.getName() + "_GET_ACCOUNT_ENTITY_USERS";

	private static final String _GET_ACCOUNT_ENTITY_USERS_URL =
		"/portal-user-service/v2/insureds/{{ACCOUNT_NUMBER}}/users";

	private static final String _GET_PRODUCER_ENTITY_USERS_CACHE_NAME =
		PortalUserWebServiceImpl.class.getName() + "_GET_PRODUCER_ENTITY_USERS";

	private static final String _GET_PRODUCER_ENTITY_USERS_URL =
		"/portal-user-service/v2/producers/{{PRODUCER_ID}}/users";

	private static final String _GET_USER_DETAILS_CACHE_NAME =
		PortalUserWebServiceImpl.class.getName() + "_GET_USER_DETAILS";

	private static final String _GET_USER_DETAILS_URL = "/portal-user-service/v2/users/{{USER_ID}}";

	private static final String _IS_USER_REGISTERED_CACHE_NAME =
		PortalUserWebServiceImpl.class.getName() + "_IS_USER_REGISTERED";

	private static final String _IS_USER_REGISTERED_URL =
		"/portal-user-service/v2/users/{{USER_ID}}/is-user-registered";
	
	private static final String _IS_SERVICE_HEALTHY_URL = 
			"/portal-user-service/actuator/health";

	private static final String _PROVISION_EXISTING_INSURED_USER_URL =
		"/portal-user-service/v2/insured-users/{{USER_ID}}/self-provision-existing";

	private static final String _PROVISION_EXISTING_PRODUCER_USER_URL =
		"/portal-user-service/v2/producer-users/{{USER_ID}}/self-provision-existing";

	private static final String _PROVISION_NEW_INSURED_USER_URL =
		"/portal-user-service/v2/insured-users/self-provision-new";

	private static final String _PROVISION_NEW_PRODUCER_USER_URL =
		"/portal-user-service/v2/producer-users/self-provision-new";

	private static final int _UNPROCESSABLE_ENTITY_STATUS_CODE = 422;

	private static final String _UPDATE_INSURED_USER_ROLE_URL =
		"/portal-user-service/v2/insured-users/{{USER_ID}}/role";

	private static final String _UPDATE_PRODUCER_USER_ROLE_URL =
		"/portal-user-service/v2/producer-users/{{USER_ID}}/role";

	private static final String _VALIDATE_INSURED_USER_REGISTRATION_URL =
		"/portal-user-service/v2/insured-users/{{USER_ID}}/registration";

	private static final String _VALIDATE_PRODUCER_USER_REGISTRATION_URL =
		"/portal-user-service/v2/producer-users/{{USER_ID}}/registration";

	private static final String _VALIDATE_USER_URL = "/portal-user-service/v2/users/registration";

	private static final Log _log = LogFactoryUtil.getLog(PortalUserWebServiceImpl.class);

	private PortalCache<String, List<CMICUserDTO>> _getAccountEntityUsersPortalCache;
	private PortalCache<Long, List<CMICUserDTO>> _getProducerEntityUsersPortalCache;
	private PortalCache<Long, CMICUserDTO> _getUserDetailsPortalCache;
	private PortalCache<Long, Boolean> _isUserRegisteredCache;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockPortalUserWebServiceClient _mockPortalUserWebServiceClient;

	private MockPortalUserWebServiceConfiguration _mockPortalUserWebServiceConfiguration;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}