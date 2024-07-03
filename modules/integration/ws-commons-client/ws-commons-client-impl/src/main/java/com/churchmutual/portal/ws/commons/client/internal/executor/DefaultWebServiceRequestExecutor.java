package com.churchmutual.portal.ws.commons.client.internal.executor;

import com.churchmutual.portal.ws.commons.client.exception.WebServiceException;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequest;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

/**
 * @author Michael C. Han
 */
public class DefaultWebServiceRequestExecutor implements WebServiceRequestExecutor<String> {

	public DefaultWebServiceRequestExecutor(HttpClient httpClient, WebServiceRequest webServiceRequest) {
		_httpClient = httpClient;
		_webServiceRequest = webServiceRequest;
	}

	@Override
	public String execute() throws PortalException {
		HttpRequestBase httpRequestBase = _webServiceRequest.getHttpRequestBase();

		RequestLine requestLine = httpRequestBase.getRequestLine();

		if (_log.isDebugEnabled()) {
			_log.debug(String.format("Request: %s %s", requestLine.getMethod(), requestLine.getUri()));
		}

		if (_log.isDebugEnabled() && (httpRequestBase instanceof HttpEntityEnclosingRequest)) {
			HttpEntity entity = ((HttpEntityEnclosingRequest)httpRequestBase).getEntity();

			if (entity != null) {
				try {
					_log.debug("Request Body: " + EntityUtils.toString(entity));
				}
				catch (IOException | ParseException e) {
					_log.debug("Failed to log request body", e);
				}
			}
		}

		_setBasicAuthHeader(httpRequestBase);

		try (LoggingTimer loggingTimer = new LoggingTimer(requestLine.getUri())) {
			HttpResponse response = _httpClient.execute(httpRequestBase);

			StatusLine statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Response Status Code: %d - %s", statusCode, statusLine));
			}

			if (_log.isInfoEnabled()) {
				Header[] requestIdHeaders = response.getHeaders(_REQUEST_ID_HEADER);

				if (requestIdHeaders.length > 0) {
					_log.info(
						String.format(
							"%s Header: %s", _REQUEST_ID_HEADER, ArrayUtil.toString(requestIdHeaders, "value")));
				}
			}

			HttpEntity httpEntity = response.getEntity();

			if (HttpServletResponse.SC_NO_CONTENT == statusCode) {
				return StringPool.BLANK;
			}

			String payload = EntityUtils.toString(httpEntity);

			if (_log.isDebugEnabled()) {
				_log.debug("Response: " + payload);
			}

			if ((HttpServletResponse.SC_OK == statusCode) || (HttpServletResponse.SC_ACCEPTED == statusCode)) {

				// CUSTOM BEGIN

				if (response.containsHeader(_CONTENT_DISPOSITION_HEADER)) {
					Header[] contentDispositionHeaders = response.getHeaders(_CONTENT_DISPOSITION_HEADER);

					StringBuilder sb = new StringBuilder(payload);

					for (Header header : contentDispositionHeaders) {
						sb.insert(0, header.getValue() + ":");
					}

					return sb.toString();
				}

				//CUSTOM END

				return payload;
			}

			throw new WebServiceException(statusCode, statusLine.getReasonPhrase(), payload, response.getAllHeaders());
		}
		catch (IOException ioe) {
			throw new WebServiceException(ioe);
		}
		finally {
			httpRequestBase.releaseConnection();
		}
	}

	private void _setBasicAuthHeader(HttpRequestBase httpRequestBase) {
		String username = _webServiceRequest.getUsername();

		if (Validator.isNotNull(username)) {
			if (_log.isDebugEnabled()) {
				_log.debug(String.format("%s Header with username: %s", HttpHeaders.AUTHORIZATION, username));
			}

			String auth = username + ":" + _webServiceRequest.getPassword();

			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

			String authHeader = "Basic " + new String(encodedAuth);

			httpRequestBase.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		}
	}

	private static final String _CONTENT_DISPOSITION_HEADER = "Content-Disposition";

	private static final String _REQUEST_ID_HEADER = "X-Request-Id";

	private static final Log _log = LogFactoryUtil.getLog(DefaultWebServiceRequestExecutor.class);

	private final HttpClient _httpClient;
	private final WebServiceRequest _webServiceRequest;

}