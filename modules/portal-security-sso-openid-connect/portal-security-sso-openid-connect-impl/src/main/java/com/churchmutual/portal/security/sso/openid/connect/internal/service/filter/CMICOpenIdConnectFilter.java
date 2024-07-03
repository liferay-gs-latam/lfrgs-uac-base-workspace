/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.churchmutual.portal.security.sso.openid.connect.internal.service.filter;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "servlet-context-name=", "servlet-filter-name=SSO OpenId Connect Filter",
		"url-pattern=" + OpenIdConnectConstants.REDIRECT_URL_PATTERN, "service.ranking:Integer=100"
	},
	service = {Filter.class, CMICOpenIdConnectFilter.class}
)
public class CMICOpenIdConnectFilter extends BaseFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();
	}

	@Override
	public boolean isFilterEnabled(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return _openIdConnect.isEnabled(_portal.getCompanyId(httpServletRequest));
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected boolean processAuthenticationResponse(
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws Exception {

		HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession == null) {
			return true;
		}

		try {
			OpenIdConnectSession openIdConnectSession = (OpenIdConnectSession)httpSession.getAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

			if (openIdConnectSession == null) {
				return true;
			}

			OpenIdConnectFlowState openIdConnectFlowState = openIdConnectSession.getOpenIdConnectFlowState();

			if (OpenIdConnectFlowState.INITIALIZED.equals(openIdConnectFlowState)) {
				throw new OpenIdConnectServiceException.AuthenticationException(
					"OpenId Connect authentication flow not started");
			}
			else if (OpenIdConnectFlowState.AUTH_COMPLETE.equals(openIdConnectFlowState) ||
					 OpenIdConnectFlowState.PORTAL_AUTH_COMPLETE.equals(openIdConnectFlowState)) {

				if (_log.isDebugEnabled()) {
					_log.debug("User has already been logged in");
				}
			}
			else {
				_openIdConnectServiceHandler.processAuthenticationResponse(httpServletRequest, httpServletResponse);

				String actionURL = (String)httpSession.getAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_ACTION_URL);

				if (actionURL != null) {
					httpServletResponse.sendRedirect(actionURL);
				}
			}
		}
		catch (UserEmailAddressException.MustNotUseCompanyMx exception) {
			Class<?> clazz = exception.getClass();

			httpSession.removeAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

			sendError(clazz.getSimpleName(), httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {

			// CUSTOM START

			httpSession.removeAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

			String fileName = _SESSION_EXPIRED_LANDING_PAGE;

			if (StringUtil.startsWith(exception.getMessage(), _RESET_PASSWORD_RESPONSE)) {
				fileName = _RESET_PASSWORD_LANDING_PAGE;
			}
			else if (StringUtil.startsWith(exception.getMessage(), _INVALID_EMAIL_RESPONSE)) {
				fileName = _INVALID_EMAIL_LANDING_PAGE;
			}
			else {
				_log.error(
					"Unable to process OpenID Connect authentication response: " + exception.getMessage(), exception);
			}

			try (InputStream stream = CMICOpenIdConnectFilter.class.getResourceAsStream(_ERROR_HTML_PATH + fileName)) {
				String errorMessage = StringUtil.read(stream);

				httpServletResponse.setContentType("text/html");

				PrintWriter printWriter = httpServletResponse.getWriter();

				printWriter.write(errorMessage);

				printWriter.close();

				return false;
			}
			catch (IOException ioe) {
				if (_log.isErrorEnabled()) {
					_log.error("Unable to read from " + fileName, ioe);
				}

				return true;
			}

			// CUSTOM END

		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		// CUSTOM START - use processFilter to determine if filter chain should continue

		boolean processFilter = processAuthenticationResponse(httpServletRequest, httpServletResponse);

		if (processFilter) {
			processFilter(
				CMICOpenIdConnectFilter.class.getName(), httpServletRequest, httpServletResponse, filterChain);
		}

		// CUSTOM END

	}

	protected void sendError(
			String error, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws Exception {

		HttpSession session = httpServletRequest.getSession(false);

		if (session == null) {
			return;
		}

		String actionURL = (String)session.getAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_ACTION_URL);

		if (actionURL == null) {
			return;
		}

		actionURL = _http.addParameter(actionURL, "error", error);

		httpServletResponse.sendRedirect(actionURL);
	}

	private static final String _ERROR_HTML_PATH = "/META-INF/resources/";

	private static final String _INVALID_EMAIL_LANDING_PAGE = "invalid_email_landing_page.html";

	private static final String _INVALID_EMAIL_RESPONSE =
		"{\"error_description\":\"AADB2C99002: An account could not be found for the provided user ID.";

	private static final String _RESET_PASSWORD_LANDING_PAGE = "reset_password_landing_page.html";

	private static final String _RESET_PASSWORD_RESPONSE =
		"{\"error_description\":\"AADB2C90088: The provided grant has not been issued for this endpoint. Actual Value : B2C_1A_Liferay_su and Expected Value : B2C_1A_Liferay_sspr";

	private static final String _SESSION_EXPIRED_LANDING_PAGE = "open_id_connect_error.html";

	private static final Log _log = LogFactoryUtil.getLog(CMICOpenIdConnectFilter.class);

	@Reference
	private Http _http;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OpenIdConnect _openIdConnect;

	@Reference
	private OpenIdConnectServiceHandler _openIdConnectServiceHandler;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}