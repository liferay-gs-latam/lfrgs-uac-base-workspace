package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.configuration.MockProducerWebServiceConfiguration;
import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICRoleAssignmentDTO;
import com.churchmutual.rest.service.mock.MockProducerWebServiceClient;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockProducerWebServiceConfiguration", immediate = true,
	service = ProducerWebService.class
)
public class ProducerWebServiceImpl implements ProducerWebService {

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_GET_CONTACTS_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_PRIMARY_CONTACT_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_PRODUCER_BY_ID_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_PRODUCERS_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_ROLE_ASSIGNMENTS_CACHE_NAME);
	}

	@Override
	public List<CMICContactDTO> getContacts(long producerId) throws PortalException {
		if (_mockProducerWebServiceConfiguration.enableMockGetContacts()) {
			return _mockProducerWebServiceClient.getContacts(producerId);
		}

		ProducerIdKey key = new ProducerIdKey(producerId);

		List<CMICContactDTO> cache = _getContactsPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("producerId", String.valueOf(producerId));

		String response = _webServiceExecutor.executeGet(_GET_CONTACTS, queryParameters);

		JSONDeserializer<CMICContactDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICContactDTO> list = new ArrayList();

		try {
			CMICContactDTO[] results = jsonDeserializer.deserialize(response, CMICContactDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_getContactsPortalCache.put(key, list);

		return list;
	}

	@Override
	public CMICContactDTO getPrimaryContact(long producerId) throws PortalException {
		if (_mockProducerWebServiceConfiguration.enableMockGetPrimaryContact()) {
			return _mockProducerWebServiceClient.getPrimaryContact(producerId);
		}

		ProducerIdKey key = new ProducerIdKey(producerId);

		CMICContactDTO cache = _getPrimaryContactPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("producerId", String.valueOf(producerId));

		String response = _webServiceExecutor.executeGet(_GET_PRIMARY_CONTACT, queryParameters);

		JSONDeserializer<CMICContactDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICContactDTO result = null;

		try {
			result = jsonDeserializer.deserialize(response, CMICContactDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(
				String.format("Primary contact for producer %s could not be found", producerId), e);
		}

		_getPrimaryContactPortalCache.put(key, result);

		return result;
	}

	@Override
	public CMICProducerDTO getProducerById(long id) throws PortalException {
		if (_mockProducerWebServiceConfiguration.enableMockGetProducerById()) {
			return _mockProducerWebServiceClient.getProducerById(id);
		}

		ProducerIdKey key = new ProducerIdKey(id);

		CMICProducerDTO cache = _getProducerByIdPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		String response = _webServiceExecutor.executeGet(_GET_PRODUCER_BY_ID, ListUtil.toList(String.valueOf(id)));

		JSONDeserializer<CMICProducerDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICProducerDTO producer = null;

		try {
			producer = jsonDeserializer.deserialize(response, CMICProducerDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("Producer with id %s could not be found", id), e);
		}

		_getProducerByIdPortalCache.put(key, producer);

		return producer;
	}

	@Override
	public List<CMICProducerDTO> getProducers(String agent, String division, String name, Boolean payOutOfCdms)
		throws PortalException {

		if (_mockProducerWebServiceConfiguration.enableMockGetProducers()) {
			return _mockProducerWebServiceClient.getProducers(agent, division, name, payOutOfCdms);
		}

		GetProducersKey key = new GetProducersKey(agent, division, name, payOutOfCdms);

		List<CMICProducerDTO> cache = _getProducersPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("agent", agent);
		queryParameters.put("division", division);

		if (Validator.isNotNull(name)) {
			queryParameters.put("name", name);
		}

		if (payOutOfCdms != null) {
			queryParameters.put("payOutOfCdms", String.valueOf(payOutOfCdms));
		}

		String response = _webServiceExecutor.executeGet(_GET_PRODUCERS, queryParameters);

		JSONDeserializer<CMICProducerDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICProducerDTO> list = new ArrayList();

		try {
			CMICProducerDTO[] results = jsonDeserializer.deserialize(response, CMICProducerDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_getProducersPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICRoleAssignmentDTO> getRoleAssignments(long producerId) throws PortalException {
		if (_mockProducerWebServiceConfiguration.enableMockGetRoleAssignments()) {
			return _mockProducerWebServiceClient.getRoleAssignments(producerId);
		}

		ProducerIdKey key = new ProducerIdKey(producerId);

		List<CMICRoleAssignmentDTO> cache = _getRoleAssignmentsCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("producerId", String.valueOf(producerId));

		String response = _webServiceExecutor.executeGet(_GET_ROLE_ASSIGNMENTS, queryParameters);

		JSONDeserializer<CMICRoleAssignmentDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICRoleAssignmentDTO> list = new ArrayList();

		try {
			CMICRoleAssignmentDTO[] results = jsonDeserializer.deserialize(response, CMICRoleAssignmentDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
			_log.error(
				String.format("Could not get role assignments for producerId, %s: %s", producerId, e.getMessage()));

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		_getRoleAssignmentsCache.put(key, list);

		return list;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockProducerWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockProducerWebServiceConfiguration.class, properties);

		_getContactsPortalCache = (PortalCache<ProducerIdKey, List<CMICContactDTO>>)_singleVMPool.getPortalCache(
			_GET_CONTACTS_CACHE_NAME);
		_getPrimaryContactPortalCache = (PortalCache<ProducerIdKey, CMICContactDTO>)_singleVMPool.getPortalCache(
			_GET_PRIMARY_CONTACT_CACHE_NAME);
		_getProducerByIdPortalCache = (PortalCache<ProducerIdKey, CMICProducerDTO>)_singleVMPool.getPortalCache(
			_GET_PRODUCER_BY_ID_CACHE_NAME);
		_getProducersPortalCache = (PortalCache<GetProducersKey, List<CMICProducerDTO>>)_singleVMPool.getPortalCache(
			_GET_PRODUCERS_CACHE_NAME);
		_getRoleAssignmentsCache = (PortalCache<ProducerIdKey, List<CMICRoleAssignmentDTO>>)_singleVMPool.getPortalCache(
			_GET_PRODUCERS_CACHE_NAME);
	}

	private static final String _GET_CONTACTS = "/producer-api/v1/contacts";

	private static final String _GET_CONTACTS_CACHE_NAME = ProducerWebServiceImpl.class.getName() + "_GET_CONTACTS";

	private static final String _GET_PRIMARY_CONTACT = "/producer-api/v1/contacts/with-assignment";

	private static final String _GET_PRIMARY_CONTACT_CACHE_NAME =
		ProducerWebServiceImpl.class.getName() + "_GET_PRIMARY_CONTACT";

	private static final String _GET_PRODUCER_BY_ID = "/producer-api/v1/producers";

	private static final String _GET_PRODUCER_BY_ID_CACHE_NAME =
		ProducerWebServiceImpl.class.getName() + "_GET_PRODUCER_BY_ID";

	private static final String _GET_PRODUCERS = "/producer-api/v1/producers";

	private static final String _GET_PRODUCERS_CACHE_NAME = ProducerWebServiceImpl.class.getName() + "_GET_PRODUCERS";

	private static final String _GET_ROLE_ASSIGNMENTS = "/producer-api/v1/role-assignments";

	private static final String _GET_ROLE_ASSIGNMENTS_CACHE_NAME = ProducerWebServiceImpl.class.getName() + "_GET_ROLE_ASSIGNMENTS";

	private static final Log _log = LogFactoryUtil.getLog(ProducerWebServiceImpl.class);

	private PortalCache<ProducerIdKey, List<CMICContactDTO>> _getContactsPortalCache;
	private PortalCache<ProducerIdKey, CMICContactDTO> _getPrimaryContactPortalCache;
	private PortalCache<ProducerIdKey, CMICProducerDTO> _getProducerByIdPortalCache;
	private PortalCache<GetProducersKey, List<CMICProducerDTO>> _getProducersPortalCache;
	private PortalCache<ProducerIdKey, List<CMICRoleAssignmentDTO>> _getRoleAssignmentsCache;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockProducerWebServiceClient _mockProducerWebServiceClient;

	private MockProducerWebServiceConfiguration _mockProducerWebServiceConfiguration;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

	private static class GetProducersKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			GetProducersKey key = (GetProducersKey)obj;

			if (Objects.equals(key._agent, _agent) && Objects.equals(key._division, _division) &&
				Objects.equals(key._name, _name) && (key._payOutOfCdms == _payOutOfCdms)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _agent);

			hashCode = HashUtil.hash(hashCode, _division);
			hashCode = HashUtil.hash(hashCode, _name);

			return HashUtil.hash(hashCode, _payOutOfCdms);
		}

		private GetProducersKey(String agent, String division, String name, Boolean payOutOfCdms) {
			_agent = agent;
			_division = division;
			_name = name;
			_payOutOfCdms = payOutOfCdms;
		}

		private static final long serialVersionUID = 1L;

		private final String _agent;
		private final String _division;
		private final String _name;
		private final Boolean _payOutOfCdms;

	}

	private static class ProducerIdKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			ProducerIdKey key = (ProducerIdKey)obj;

			if (key._producerId == _producerId) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return HashUtil.hash(0, _producerId);
		}

		private ProducerIdKey(long producerId) {
			_producerId = producerId;
		}

		private static final long serialVersionUID = 1L;

		private final long _producerId;

	}

}