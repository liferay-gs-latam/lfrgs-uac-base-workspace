package com.churchmutual.portal.security.sso.openid.connect.internal.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true,
	property = {
		"servlet-context-name=", "servlet-filter-name=CMIC Logout Filter", "url-pattern=/c/portal/logout",
		"url-pattern=/group/registration/cancel"
	},
	service = {Filter.class, CMICLogoutFilter.class}
)
public class CMICLogoutFilter extends BaseFilter {

	public static final String AZURE_AD_PROVIDER_NAME = "Azure AD B2C";

	@Override
	public boolean isFilterEnabled(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(AZURE_AD_PROVIDER_NAME);

			if (openIdConnectProvider != null) {
				return true;
			}
		}
		catch (OpenIdConnectServiceException.ProviderException oicsepe) {
			_log.error(oicsepe, oicsepe);
		}

		return false;
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		boolean redirected = redirectUser(httpServletRequest, httpServletResponse);

		if (!redirected) {
			processFilter(CMICLogoutFilter.class.getName(), httpServletRequest, httpServletResponse, filterChain);
		}
	}

	protected boolean redirectUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			String requestURI = httpServletRequest.getRequestURI();

			if (requestURI.contains(GroupConstants.CONTROL_PANEL_FRIENDLY_URL)) {
				return false;
			}

			httpServletRequest.getSession(
			).invalidate();

			if (requestURI.endsWith("/c/portal/logout")) {
				httpServletResponse.sendRedirect(_LOGOUT_REDIRECT_URL);
			}
			else if (requestURI.endsWith("/group/registration/cancel")) {
				_openIdConnectServiceHandler.requestAuthentication(
					AZURE_AD_PROVIDER_NAME, httpServletRequest, httpServletResponse);
			}

			return true;
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return false;
	}

	private static final String _LOGOUT_REDIRECT_URL = "https://www.churchmutual.com";

	private static final Log _log = LogFactoryUtil.getLog(CMICLogoutFilter.class);

	@Reference
	private OpenIdConnectProviderRegistry<OIDCClientMetadata, OIDCProviderMetadata> _openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectServiceHandler _openIdConnectServiceHandler;

}