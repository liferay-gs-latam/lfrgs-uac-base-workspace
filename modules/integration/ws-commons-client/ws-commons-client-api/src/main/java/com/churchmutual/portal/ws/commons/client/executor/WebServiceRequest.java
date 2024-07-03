package com.churchmutual.portal.ws.commons.client.executor;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author Michael C. Han
 */
public class WebServiceRequest {

	public WebServiceRequest(HttpRequestBase httpRequestBase, String username, String password) {
		_httpRequestBase = httpRequestBase;
		_username = username;
		_password = password;
	}

	public HttpRequestBase getHttpRequestBase() {
		return _httpRequestBase;
	}

	public String getPassword() {
		return _password;
	}

	public String getUsername() {
		return _username;
	}

	private HttpRequestBase _httpRequestBase;
	private String _password;
	private String _username;

}