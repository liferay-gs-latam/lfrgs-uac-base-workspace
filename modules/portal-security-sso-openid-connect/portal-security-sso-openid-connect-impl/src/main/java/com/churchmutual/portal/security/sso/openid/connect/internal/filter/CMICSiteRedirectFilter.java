package com.churchmutual.portal.security.sso.openid.connect.internal.filter;

import com.churchmutual.commons.constants.ExpandoConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.portal.ws.commons.client.exception.WebServiceException;
import com.churchmutual.rest.PortalUserWebService;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true,
	property = {
		"servlet-context-name=", "servlet-filter-name=CMIC Site Redirect Filter", "url-pattern=/c/portal/login",
		"url-pattern=/group/*", "url-pattern=/home", "url-pattern=/login", "url-pattern=/search", "url-pattern=/signup",
		"url-pattern=/web/*"
	},
	service = {Filter.class, CMICSiteRedirectFilter.class}
)
public class CMICSiteRedirectFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(_AZURE_AD_PROVIDER_NAME);

			if (openIdConnectProvider != null) {
				return true;
			}
		}
		catch (OpenIdConnectServiceException.ProviderException oicsepe) {
			getLog().error(oicsepe, oicsepe);
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
			processFilter(CMICSiteRedirectFilter.class.getName(), httpServletRequest, httpServletResponse, filterChain);
		}
	}

	protected boolean redirectUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			User user = _portal.getUser(httpServletRequest);

			if (user == null) {
				if (_isPortalUserServiceUnavailable(httpServletResponse)) return true;

				_openIdConnectServiceHandler.requestAuthentication(
					_AZURE_AD_PROVIDER_NAME, httpServletRequest, httpServletResponse);

				return true;
			}

			long userId = user.getUserId();

			long companyId = _portal.getCompanyId(httpServletRequest);

			boolean isUserCMICEmployee = _isUserCMICEmployee(userId);

			boolean isUserAdministrator = _isUserAdministrator(companyId, userId);

			boolean isOnInsuredProducerPortalPage = (_isOnSite(httpServletRequest, CMICSite.REGISTRATION) || _isOnSite(httpServletRequest, CMICSite.PRODUCER) || _isOnSite(httpServletRequest, CMICSite.INSURED))
				&& !_isControlPanel(httpServletRequest);

			if ((!isUserCMICEmployee && !isUserAdministrator) || ((isUserCMICEmployee || isUserAdministrator) && isOnInsuredProducerPortalPage)) {
				if (_isPortalUserServiceUnavailable(httpServletResponse)) return true;
			}

			if (isUserCMICEmployee || isUserAdministrator) {
				if (_isOnSite(httpServletRequest, _CMIC_HOME_FRIENDLY_URL) &&
					!_isOnSite(httpServletRequest, _CMIC_GUEST_SITE_FRIENDLY_URL)) {

					_redirect(httpServletResponse, _CMIC_GUEST_SITE_FRIENDLY_URL, false);
				}
				else {
					return false;
				}
			}
			else if (_isUserDeactivated(userId)) {
				if (!_isOnSite(httpServletRequest, _CMIC_DEACTIVATED_FRIENDLY_URL)) {
					_redirect(httpServletResponse, _CMIC_DEACTIVATED_FRIENDLY_URL, true);
				}
				else {
					return false;
				}
			}
			else if (!_isUserRegistered(userId)) {
				if (!_isOnSite(httpServletRequest, CMICSite.REGISTRATION)) {
					_redirect(httpServletResponse, CMICSite.REGISTRATION.getFriendlyURL(), true);
				}
				else {
					return false;
				}
			}
			else if (_isUserTypeProducer(companyId, userId) && !_isOnSite(httpServletRequest, CMICSite.PRODUCER)) {
				_redirect(httpServletResponse, CMICSite.PRODUCER.getFriendlyURL(), true);
			}
			else if (_isUserTypeInsured(companyId, userId) && !_isOnSite(httpServletRequest, CMICSite.INSURED)) {
				_redirect(httpServletResponse, CMICSite.INSURED.getFriendlyURL(), true);
			}
			else {
				return false;
			}

			return true;
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return false;
	}

	private boolean _isControlPanel(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		return requestURI.contains(LayoutConstants.TYPE_CONTROL_PANEL);
	}

	private boolean _isOnSite(HttpServletRequest httpServletRequest, CMICSite cmicSite) {
		return _isOnSite(httpServletRequest, cmicSite.getFriendlyURL());
	}

	private boolean _isOnSite(HttpServletRequest httpServletRequest, String cmicSiteURL) {
		String requestURI = httpServletRequest.getRequestURI();

		return requestURI.contains(cmicSiteURL);
	}

	private boolean _isPortalUserServiceUnavailable(HttpServletResponse httpServletResponse) {
		boolean isUnavailable = false;

		if (!_portalUserWebService.isServiceHealthy()) {
			isUnavailable = _redirectToHTML(httpServletResponse, _PAGE_UNAVAILABLE_ERROR_PAGE);
		}

		return isUnavailable;
	}

	private boolean _isUserAdministrator(long companyId, long userId) throws PortalException {
		return _roleLocalService.hasUserRole(userId, companyId, RoleConstants.ADMINISTRATOR, true);
	}

	private Boolean _isUserCMICEmployee(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		return (Boolean)expandoBridge.getAttribute(ExpandoConstants.CMIC_EMPLOYEE, false);
	}

	private boolean _isUserDeactivated(long userId) throws PortalException {
		return _cmicUserLocalService.isDeactivated(userId);
	}

	private boolean _isUserRegistered(long userId) throws PortalException {
		return _cmicUserLocalService.isUserRegistered(userId);
	}

	private boolean _isUserTypeInsured(long companyId, long userId) throws PortalException {
		Group insuredGroup = _groupLocalService.getFriendlyURLGroup(companyId, CMICSite.INSURED.getFriendlyURL());

		return _userLocalService.hasGroupUser(insuredGroup.getGroupId(), userId);
	}

	private boolean _isUserTypeProducer(long companyId, long userId) throws PortalException {
		Group producerGroup = _groupLocalService.getFriendlyURLGroup(companyId, CMICSite.PRODUCER.getFriendlyURL());

		return _userLocalService.hasGroupUser(producerGroup.getGroupId(), userId);
	}

	private void _redirect(HttpServletResponse httpServletResponse, String friendlyURL, boolean privatePage)
		throws Exception {

		String url = privatePage ? _portal.getPathFriendlyURLPrivateGroup() : _portal.getPathFriendlyURLPublic();

		httpServletResponse.sendRedirect(url + friendlyURL);
	}

	private boolean _redirectToHTML(HttpServletResponse httpServletResponse, String htmlFilePath) {
		try (InputStream stream = CMICSiteRedirectFilter.class.getResourceAsStream(htmlFilePath)) {
			String errorMessage = StringUtil.read(stream);

			httpServletResponse.setContentType("text/html");

			PrintWriter printWriter = httpServletResponse.getWriter();
			printWriter.write(errorMessage);
			printWriter.close();

			return true;
		}
		catch (IOException ioe) {
			_log.error("Unable to read from " + htmlFilePath, ioe);
		}

		return false;
	}

	private static final String _AZURE_AD_PROVIDER_NAME = "Azure AD B2C";

	private static final String _CMIC_DEACTIVATED_FRIENDLY_URL =
		CMICSite.REGISTRATION.getFriendlyURL() + LayoutURLKeyConstants.LAYOUT_FURL_REGISTRATION_DEACTIVATED;

	private static final String _CMIC_GUEST_SITE_FRIENDLY_URL = "/guest";

	private static final String _CMIC_HOME_FRIENDLY_URL = "/home";

	private static final String _PAGE_UNAVAILABLE_ERROR_PAGE = "/META-INF/resources/page_unavailable.html";

	private static final Log _log = LogFactoryUtil.getLog(CMICSiteRedirectFilter.class);

	@Reference
	private CMICUserLocalService _cmicUserLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OpenIdConnectProviderRegistry<OIDCClientMetadata, OIDCProviderMetadata> _openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectServiceHandler _openIdConnectServiceHandler;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
