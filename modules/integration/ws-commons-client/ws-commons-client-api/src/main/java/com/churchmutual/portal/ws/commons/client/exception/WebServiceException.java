package com.churchmutual.portal.ws.commons.client.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

/**
 * @author Kayleen Lim
 */
public class WebServiceException extends PortalException {

	public WebServiceException(Exception e) {
		super(e);
	}

	public WebServiceException(int responseCode, String msg) {
		super(msg);

		_responseCode = responseCode;
	}

	public WebServiceException(int responseCode, String msg, String payload, Header[] responseHeaders) {
		super(msg);

		_responseCode = responseCode;
		_payload = payload;

		for (Header header: responseHeaders) {
			_responseHeaders.put(header.getName(), header.getValue());
		}
	}

	public WebServiceException(int responseCode, String msg, Throwable throwable) {
		super(msg, throwable);

		_responseCode = responseCode;
	}

	public String getPayload() {
		return _payload;
	}

	public int getResponseCode() {
		return _responseCode;
	}

	public Map<String, String> getResponseHeaders() {
		return _responseHeaders;
	}

	@Override
	public String toString() {
		String s = getClass().getName();
		String message = getLocalizedMessage();

		if (message != null) {
			return s + ": Status Code " + _responseCode + " - " + message;
		}
		else if (_payload != null) {
			return s + ": Status Code " + _responseCode + " - " + _payload;
		}

		return s;
	}

	private String _payload;
	private int _responseCode;
	private Map<String, String> _responseHeaders = new HashMap<>();

}