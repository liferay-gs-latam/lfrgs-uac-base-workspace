package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.CommissionDocumentWebService;
import com.churchmutual.rest.configuration.MockCommissionDocumentWebServiceConfiguration;
import com.churchmutual.rest.model.CMICCommissionDocumentDTO;
import com.churchmutual.rest.model.CMICFileDTO;
import com.churchmutual.rest.service.mock.MockCommissionDocumentWebServiceClient;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	configurationPid = "com.churchmutual.rest.configuration.MockCommissionDocumentWebServiceConfiguration",
	immediate = true, service = CommissionDocumentWebService.class
)
public class CommissionDocumentWebServiceImpl implements CommissionDocumentWebService {

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_SEARCH_DOCUMENTS_CACHE_NAME);
	}

	@Override
	public List<CMICFileDTO> downloadDocuments(String[] ids, boolean includeBytes) throws PortalException {
		if (_mockCommissionDocumentWebServiceConfiguration.enableMockDownloadDocuments()) {
			return _mockCommissionDocumentWebServiceClient.downloadDocuments(ids, includeBytes);
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("include-bytes", String.valueOf(includeBytes));

		String response = _webServiceExecutor.executePost(
			_DOWNLOAD_DOCUMENTS_URL, queryParameters, _constructIdBodyParameter(ids));

		JSONDeserializer<CMICFileDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICFileDTO> list = new ArrayList();

		JSONObject jsonObject = _jsonFactory.createJSONObject(response);

		for (String key : jsonObject.keySet()) {
			try {
				CMICFileDTO[] files = jsonDeserializer.deserialize(jsonObject.getString(key), CMICFileDTO[].class);

				Collections.addAll(list, files);
			}
			catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public List<CMICCommissionDocumentDTO> searchDocuments(
			String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
			String minimumStatementDate)
		throws PortalException {

		if (_mockCommissionDocumentWebServiceConfiguration.enableMockSearchDocuments()) {
			return _mockCommissionDocumentWebServiceClient.searchDocuments(
				agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatementDate);
		}

		SearchDocumentsKey key = new SearchDocumentsKey(
			agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatementDate);

		List<CMICCommissionDocumentDTO> cache = _searchDocumentsPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new LinkedHashMap<>();

		queryParameters.put("agentNumber", agentNumber);
		queryParameters.put("divisionNumber", divisionNumber);
		queryParameters.put("documentType", documentType);
		queryParameters.put("maximumStatementDate", maximumStatementDate);
		queryParameters.put("minimumStatementDate", minimumStatementDate);

		String response = _webServiceExecutor.executeGet(_SEARCH_DOCUMENTS_URL, queryParameters);

		JSONDeserializer<CMICCommissionDocumentDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICCommissionDocumentDTO> list = new ArrayList();

		try {
			CMICCommissionDocumentDTO[] results = jsonDeserializer.deserialize(
				response, CMICCommissionDocumentDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_searchDocumentsPortalCache.put(key, list);

		return list;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockCommissionDocumentWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockCommissionDocumentWebServiceConfiguration.class, properties);

		_searchDocumentsPortalCache =
			(PortalCache<SearchDocumentsKey, List<CMICCommissionDocumentDTO>>)_singleVMPool.getPortalCache(
				_SEARCH_DOCUMENTS_CACHE_NAME);
	}

	private String _constructIdBodyParameter(String[] ids) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ \"");

		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			sb.append(id);

			if ((i + 1) < ids.length) {
				sb.append(StringPool.QUOTE);
				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append(StringPool.QUOTE);
			}
		}

		sb.append("\"]");

		return sb.toString();
	}

	private static final String _DOWNLOAD_DOCUMENTS_URL = "/commission-document-service/v1/download/ids";

	private static final String _SEARCH_DOCUMENTS_CACHE_NAME =
		CommissionDocumentWebServiceImpl.class.getName() + "_SEARCH_DOCUMENTS";

	private static final String _SEARCH_DOCUMENTS_URL = "/commission-document-service/v1/search";

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockCommissionDocumentWebServiceClient _mockCommissionDocumentWebServiceClient;

	private MockCommissionDocumentWebServiceConfiguration _mockCommissionDocumentWebServiceConfiguration;
	private PortalCache<SearchDocumentsKey, List<CMICCommissionDocumentDTO>> _searchDocumentsPortalCache;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

	private static class SearchDocumentsKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			SearchDocumentsKey searchDocumentsKey = (SearchDocumentsKey)obj;

			if (Objects.equals(searchDocumentsKey._agentNumber, _agentNumber) &&
				Objects.equals(searchDocumentsKey._divisionNumber, _divisionNumber) &&
				Objects.equals(searchDocumentsKey._documentType, _documentType) &&
				Objects.equals(searchDocumentsKey._maximumStatementDate, _maximumStatementDate) &&
				Objects.equals(searchDocumentsKey._minimumStatementDate, _minimumStatementDate)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _agentNumber);

			hashCode = HashUtil.hash(hashCode, _divisionNumber);
			hashCode = HashUtil.hash(hashCode, _documentType);
			hashCode = HashUtil.hash(hashCode, _maximumStatementDate);

			return HashUtil.hash(hashCode, _minimumStatementDate);
		}

		private SearchDocumentsKey(
			String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
			String minimumStatementDate) {

			_agentNumber = agentNumber;
			_divisionNumber = divisionNumber;
			_documentType = documentType;
			_maximumStatementDate = maximumStatementDate;
			_minimumStatementDate = minimumStatementDate;
		}

		private static final long serialVersionUID = 1L;

		private final String _agentNumber;
		private final String _divisionNumber;
		private final String _documentType;
		private final String _maximumStatementDate;
		private final String _minimumStatementDate;

	}

}