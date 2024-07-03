package com.churchmutual.portal.ws.commons.client.internal.executor;

import com.churchmutual.portal.ws.commons.client.exception.WebServiceException;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequest;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutor;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutorFactory;
import com.churchmutual.rest.configuration.CMICWebServiceAuthenticationConfiguration;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.CMICWebServiceAuthenticationConfiguration",
	immediate = true, service = WebServiceExecutor.class
)
public class DefaultWebServiceExecutor implements WebServiceExecutor {

	@Override
	public String executeDelete(String baseURL, List<String> pathParameters) throws PortalException {
		return executeDelete(baseURL, pathParameters, new HashMap<>());
	}

	@Override
	public String executeDelete(String baseURL, List<String> pathParameters, Map<String, String> queryParameters)
		throws PortalException {

		URI fullURI = getFullURI(baseURL, pathParameters, queryParameters, new HashMap<>());

		HttpDelete httpDelete = new HttpDelete(fullURI);

		WebServiceRequest webServiceRequest = new WebServiceRequest(httpDelete, _basicAuthUsername, _basicAuthPassword);

		return execute(webServiceRequest);
	}

	@Override
	public String executeGet(String baseURL, List<String> pathParameters) throws PortalException {
		return executeGet(baseURL, pathParameters, new HashMap<>(), new HashMap<>());
	}

	@Override
	public String executeGet(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters,
			Map<String, String[]> repeatedQueryParameters)
		throws PortalException {

		URI fullURI = getFullURI(baseURL, pathParameters, queryParameters, repeatedQueryParameters);

		HttpGet httpGet = new HttpGet(fullURI);

		// TODO: dev-portal is currently facing issues. Please remove the code and comments below when dev-portal is running
		//  without any issues. Code below will skip checking if portal-user-service is down
		if (fullURI.toString().equals("https://devcontact.churchmutual.com/portal-user-service/actuator/health")) {
			return "{\"status\":\"UP\"}";
		}
		// TODO: dev-portal is currently facing issues. Please remove the code and comment above when dev-portal is running
		//  without any issues. Code above will skip checking if portal-user-service is down

		WebServiceRequest webServiceRequest = new WebServiceRequest(httpGet, _basicAuthUsername, _basicAuthPassword);

		return execute(webServiceRequest);
	}

	@Override
	public String executeGet(String baseURL, Map<String, String> queryParameters) throws PortalException {
		return executeGet(baseURL, new ArrayList<>(), queryParameters, new HashMap<>());
	}

	@Override
	public String executeGetWithRepeatedQueryParameters(String baseURL, Map<String, String[]> repeatedQueryParameters)
		throws PortalException {

		return executeGet(baseURL, new ArrayList<>(), new HashMap<>(), repeatedQueryParameters);
	}

	@Override
	public String executePost(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters, String bodyParameters)
		throws PortalException {

		URI fullURI = getFullURI(baseURL, pathParameters, queryParameters, new HashMap<>());

		HttpPost httpPost = new HttpPost(fullURI);

		try {
			if (Validator.isNotNull(bodyParameters)) {
				StringEntity entity = new StringEntity(bodyParameters);

				httpPost.setEntity(entity);

				httpPost.setHeader(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
				httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			}
		}
		catch (IOException ioe) {
			throw new WebServiceException(ioe);
		}

		WebServiceRequest webServiceRequest = new WebServiceRequest(httpPost, _basicAuthUsername, _basicAuthPassword);

		return execute(webServiceRequest);
	}

	public String executePost(String baseURL, Map<String, String> queryParameters) throws PortalException {
		return executePost(baseURL, new ArrayList<>(), queryParameters, StringPool.BLANK);
	}

	@Override
	public String executePost(String baseURL, Map<String, String> queryParameters, String bodyParameters)
		throws PortalException {

		return executePost(baseURL, new ArrayList<>(), queryParameters, bodyParameters);
	}

	public String executePut(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters, String bodyParameters)
		throws PortalException {

		URI fullURI = getFullURI(baseURL, pathParameters, queryParameters, new HashMap<>());

		HttpPut httpPut = new HttpPut(fullURI);

		try {
			if (Validator.isNotNull(bodyParameters)) {
				StringEntity entity = new StringEntity(bodyParameters);

				httpPut.setEntity(entity);

				httpPut.setHeader(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
				httpPut.setHeader(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			}
		}
		catch (IOException ioe) {
			throw new WebServiceException(ioe);
		}

		WebServiceRequest webServiceRequest = new WebServiceRequest(httpPut, _basicAuthUsername, _basicAuthPassword);

		return execute(webServiceRequest);
	}

	public String executePut(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters)
		throws PortalException {
		return executePut(baseURL, pathParameters, queryParameters, StringPool.BLANK);
	}

	public String executePut(String baseURL, Map<String, String> queryParameters) throws PortalException {
		return executePut(baseURL, new ArrayList<>(), queryParameters, StringPool.BLANK);
	}

	public String executePut(String baseURL, String bodyParameters) throws PortalException {
		return executePut(baseURL, new ArrayList<>(), new HashMap<>(), bodyParameters);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CMICWebServiceAuthenticationConfiguration configuration = ConfigurableUtil.createConfigurable(
			CMICWebServiceAuthenticationConfiguration.class, properties);

		_baseURL = configuration.baseURL();
		_basicAuthUsername = configuration.basicAuthUsername();
		_basicAuthPassword = configuration.basicAuthPassword();
	}

	protected String execute(WebServiceRequest webServiceRequest) throws PortalException {
		WebServiceRequestExecutor<String> webServiceRequestExecutor =
			webServiceRequestExecutorFactory.createWebServiceRequestExecutor(webServiceRequest);

		return webServiceRequestExecutor.execute();
	}

	protected URI getFullURI(
			String url, List<String> pathParameters, Map<String, String> queryParameters,
			Map<String, String[]> repeatedQueryParameters)
		throws PortalException {

		String fullURL = url;

		if (StringUtil.startsWith(url, CharPool.SLASH)) {
			if (Validator.isNull(_baseURL)) {
				throw new WebServiceException(HttpServletResponse.SC_NOT_FOUND, "Unable to set location");
			}

			fullURL = _baseURL + url;
		}

		if (Validator.isNull(fullURL)) {
			throw new WebServiceException(HttpServletResponse.SC_NOT_FOUND, "Unable to set location");
		}

		for (String pathParameter : pathParameters) {
			fullURL += CharPool.SLASH + pathParameter;
		}

		try {
			URIBuilder builder = new URIBuilder(fullURL);

			for (Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
				builder.addParameter(queryParameter.getKey(), queryParameter.getValue());
			}

			for (Map.Entry<String, String[]> repeatedQueryParameter : repeatedQueryParameters.entrySet()) {
				Arrays.stream(
					repeatedQueryParameter.getValue()
				).forEach(
					parameter -> builder.addParameter(repeatedQueryParameter.getKey(), parameter)
				);
			}

			return builder.build();
		}
		catch (URISyntaxException urise) {
			throw new WebServiceException(HttpServletResponse.SC_NOT_FOUND, "Unable to set location", urise);
		}
	}

	@Reference
	protected WebServiceRequestExecutorFactory webServiceRequestExecutorFactory;

	private volatile String _baseURL;
	private volatile String _basicAuthPassword;
	private volatile String _basicAuthUsername;

}