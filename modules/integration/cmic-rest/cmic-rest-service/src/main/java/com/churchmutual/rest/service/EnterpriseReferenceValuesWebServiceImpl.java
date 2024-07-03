package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.EnterpriseReferenceValuesWebService;
import com.churchmutual.rest.model.CMICCompanyDTO;
import com.churchmutual.rest.model.CMICEventTypeDTO;
import com.churchmutual.rest.model.CMICProductDTO;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = EnterpriseReferenceValuesWebService.class)
public class EnterpriseReferenceValuesWebServiceImpl implements EnterpriseReferenceValuesWebService {

	@Activate
	@Modified
	public void activate() {
		_getCompaniesPortalCache = (PortalCache<Boolean, List<CMICCompanyDTO>>)_singleVMPool.getPortalCache(
			_GET_COMPANIES_CACHE_NAME);
		_getEventTypesPortalCache = (PortalCache<Boolean, List<CMICEventTypeDTO>>)_singleVMPool.getPortalCache(
			_GET_EVENT_TYPES_CACHE_NAME);
		_getProductsPortalCache = (PortalCache<Boolean, List<CMICProductDTO>>)_singleVMPool.getPortalCache(
			_GET_PRODUCTS_CACHE_NAME);
	}

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_GET_COMPANIES_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_EVENT_TYPES_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_PRODUCTS_CACHE_NAME);
	}

	@Override
	public List<CMICCompanyDTO> getCompanies(boolean includeInactive) throws PortalException {
		List<CMICCompanyDTO> cache = _getCompaniesPortalCache.get(includeInactive);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = HashMapBuilder.put(
			"includeInactive", String.valueOf(includeInactive)
		).build();

		String response = _webServiceExecutor.executeGet(_GET_COMPANIES_URL, queryParameters);

		JSONDeserializer<CMICCompanyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICCompanyDTO[] results = jsonDeserializer.deserialize(response, CMICCompanyDTO[].class);

			List<CMICCompanyDTO> companies = Arrays.asList(results);

			_getCompaniesPortalCache.put(includeInactive, companies);

			return companies;
		}
		catch (Exception e) {
			throw new PortalException("Companies could not be retrieved", e);
		}
	}

	@Override
	public List<CMICEventTypeDTO> getEventTypes(boolean includeInactive) throws PortalException {
		List<CMICEventTypeDTO> cache = _getEventTypesPortalCache.get(includeInactive);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = HashMapBuilder.put(
			"includeInactive", String.valueOf(includeInactive)
		).build();

		String response = _webServiceExecutor.executeGet(_GET_EVENT_TYPES_URL, queryParameters);

		JSONDeserializer<CMICEventTypeDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICEventTypeDTO[] results = jsonDeserializer.deserialize(response, CMICEventTypeDTO[].class);

			List<CMICEventTypeDTO> eventTypes = Arrays.asList(results);

			_getEventTypesPortalCache.put(includeInactive, eventTypes);

			return eventTypes;
		}
		catch (Exception e) {
			throw new PortalException("Event Types could not be retrieved", e);
		}
	}

	@Override
	public List<CMICProductDTO> getProducts(boolean includeInactive) throws PortalException {
		List<CMICProductDTO> cache = _getProductsPortalCache.get(includeInactive);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = HashMapBuilder.put(
			"includeInactive", String.valueOf(includeInactive)
		).build();

		String response = _webServiceExecutor.executeGet(_GET_PRODUCTS_URL, queryParameters);

		JSONDeserializer<CMICProductDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICProductDTO[] results = jsonDeserializer.deserialize(response, CMICProductDTO[].class);

			List<CMICProductDTO> products = Arrays.asList(results);

			_getProductsPortalCache.put(includeInactive, products);

			return products;
		}
		catch (Exception e) {
			throw new PortalException("Products could not be retrieved", e);
		}
	}

	private static final String _GET_COMPANIES_CACHE_NAME =
		EnterpriseReferenceValuesWebServiceImpl.class.getName() + "_GET_COMPANIES";

	private static final String _GET_COMPANIES_URL = "/enterprise-reference-values-service/companies/v1/";

	private static final String _GET_EVENT_TYPES_CACHE_NAME =
		EnterpriseReferenceValuesWebServiceImpl.class.getName() + "_GET_EVENT_TYPES";

	private static final String _GET_EVENT_TYPES_URL = "/enterprise-reference-values-service/event-types/v1/";

	private static final String _GET_PRODUCTS_CACHE_NAME =
		EnterpriseReferenceValuesWebServiceImpl.class.getName() + "_GET_PRODUCTS";

	private static final String _GET_PRODUCTS_URL = "/enterprise-reference-values-service/products/v1/";

	private PortalCache<Boolean, List<CMICCompanyDTO>> _getCompaniesPortalCache;
	private PortalCache<Boolean, List<CMICEventTypeDTO>> _getEventTypesPortalCache;
	private PortalCache<Boolean, List<CMICProductDTO>> _getProductsPortalCache;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}