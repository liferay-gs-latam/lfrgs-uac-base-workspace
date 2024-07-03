/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.churchmutual.portal.security.sso.openid.connect.internal;

import com.churchmutual.portal.security.sso.openid.connect.internal.configuration.CMICOpenIdConnectProviderConfiguration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.OpenIdConnectServiceHandlerImpl;
import com.liferay.portal.security.sso.openid.connect.internal.OpenIdConnectSessionImpl;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.GeneralException;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONObject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * Override of OpenIdConnectServiceHandlerImpl
 * For handling user info
 *
 * @author Thuong Dinh
 * @author Edward C. Han
 */
@Component(
	configurationPid = "com.churchmutual.portal.security.sso.openid.connect.internal.configuration.CMICOpenIdConnectProviderConfiguration",
	immediate = true, property = "service.ranking:Integer=100", service = OpenIdConnectServiceHandler.class
)
public class CMICOpenIdConnectServiceHandlerImpl implements OpenIdConnectServiceHandler {

	@Override
	public boolean hasValidOpenIdConnectSession(HttpSession httpSession)
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException {

		CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl = getOpenIdConnectSessionImpl(httpSession);

		if (!hasValidAccessToken(cmicOpenIdConnectSessionImpl)) {
			try {
				return refreshAuthToken(cmicOpenIdConnectSessionImpl);
			}
			catch (OpenIdConnectServiceException oicse) {
				_log.error("Unable to refresh auth token: " + oicse.getMessage(), oicse);

				return false;
			}
		}

		return true;
	}

	@Override
	public void processAuthenticationResponse(
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws PortalException {

		AuthenticationSuccessResponse authenticationSuccessResponse = getAuthenticationSuccessResponse(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl = getOpenIdConnectSessionImpl(httpSession);

		if (!OpenIdConnectFlowState.AUTH_REQUESTED.equals(cmicOpenIdConnectSessionImpl.getOpenIdConnectFlowState())) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"OpenId Connect login flow is not in the ", OpenIdConnectFlowState.AUTH_REQUESTED, " state: ",
					cmicOpenIdConnectSessionImpl.getOpenIdConnectFlowState()));
		}

		validateState(cmicOpenIdConnectSessionImpl.getState(), authenticationSuccessResponse.getState());

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider =
			_openIdConnectProviderRegistry.findOpenIdConnectProvider(
				cmicOpenIdConnectSessionImpl.getOpenIdProviderName());

		CMICOpenIdConnectProvider cmicOpenIdConnectProvider = new CMICOpenIdConnectProvider(openIdConnectProvider);

		OIDCProviderMetadata oidcProviderMetadata = cmicOpenIdConnectProvider.getOIDCProviderMetadata();

		OIDCClientInformation oidcClientInformation = getOIDCClientInformation(openIdConnectProvider);

		URI redirectURI = getLoginRedirectURI(httpServletRequest);

		Tokens tokens = requestIdToken(
			authenticationSuccessResponse, oidcClientInformation, oidcProviderMetadata, redirectURI,
			cmicOpenIdConnectSessionImpl.getNonce());

		updateSessionTokens(cmicOpenIdConnectSessionImpl, tokens, System.currentTimeMillis());

		processUserInfo(
			_portal.getCompanyId(httpServletRequest), tokens, oidcProviderMetadata, cmicOpenIdConnectSessionImpl);

		cmicOpenIdConnectSessionImpl.setOpenIdConnectFlowState(OpenIdConnectFlowState.AUTH_COMPLETE);
	}

	@Override
	public void requestAuthentication(
			String openIdConnectProviderName, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider =
			_openIdConnectProviderRegistry.findOpenIdConnectProvider(openIdConnectProviderName);

		HttpSession httpSession = httpServletRequest.getSession();

		CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl = getOpenIdConnectSessionImpl(
			httpSession, openIdConnectProviderName);

		if (cmicOpenIdConnectSessionImpl == null) {
			cmicOpenIdConnectSessionImpl = createAndSetOpenIdConnectSession(httpSession, openIdConnectProviderName);
		}

		URI authenticationRequestURI = getAuthenticationRequestURI(
			getLoginRedirectURI(httpServletRequest), openIdConnectProvider, cmicOpenIdConnectSessionImpl.getNonce(),
			cmicOpenIdConnectSessionImpl.getState(), Scope.parse(openIdConnectProvider.getScopes()));

		try {
			String servletPath = httpServletRequest.getServletPath();

			if (servletPath.equals(_SIGN_UP_URL)) {
				String signUpQuery = authenticationRequestURI.getQuery();

				if (Validator.isBlank(signUpQuery)) {
					signUpQuery = _SIGN_UP_URL_PARAMETER;
				} else {
					signUpQuery += StringPool.AMPERSAND  + _SIGN_UP_URL_PARAMETER;
				}

				authenticationRequestURI = new URI(authenticationRequestURI.getScheme(), authenticationRequestURI.getAuthority(),
					authenticationRequestURI.getPath(), signUpQuery, authenticationRequestURI.getFragment());
			}

			httpServletResponse.sendRedirect(authenticationRequestURI.toString());

			cmicOpenIdConnectSessionImpl.setOpenIdConnectFlowState(OpenIdConnectFlowState.AUTH_REQUESTED);
		}
		catch (IOException | URISyntaxException e) {
			throw new SystemException(
				StringBundler.concat(
					"Unable to send user to OpenId Connect service ", authenticationRequestURI.toString(), ": ",
					e.getMessage()),
				e);
		}
	}

	/**
	 * Copied from <code>IDTokenValidator</code>
	 */
	protected static JWEKeySelector createJWEKeySelector(
			final OIDCProviderMetadata opMetadata, final OIDCClientInformation clientInfo)
		throws GeneralException {

		final JWEAlgorithm expectedJWEAlg = clientInfo.getOIDCMetadata(
		).getIDTokenJWEAlg();
		final EncryptionMethod expectedJWEEnc = clientInfo.getOIDCMetadata(
		).getIDTokenJWEEnc();

		if (expectedJWEAlg == null) {

			// Encrypted ID tokens not expected

			return null;
		}

		if (expectedJWEEnc == null) {
			throw new GeneralException("Missing required ID token JWE encryption method for " + expectedJWEAlg);
		}

		if ((opMetadata.getIDTokenJWEAlgs() == null) || !opMetadata.getIDTokenJWEAlgs().contains(expectedJWEAlg)) {
			throw new GeneralException("The OpenID Provider doesn't support " + expectedJWEAlg + " ID tokens");
		}

		if ((opMetadata.getIDTokenJWEEncs() == null) || !opMetadata.getIDTokenJWEEncs().contains(expectedJWEEnc)) {
			throw new GeneralException(
				"The OpenID Provider doesn't support " + expectedJWEAlg + " / " + expectedJWEEnc + " ID tokens");
		}

		return new JWEDecryptionKeySelector(expectedJWEAlg, expectedJWEEnc, null);
	}

	/**
	 * Copied from <code>IDTokenValidator</code>
	 */
	protected static JWSKeySelector createJWSKeySelector(
			final OIDCProviderMetadata opMetadata, final OIDCClientInformation clientInfo)
		throws GeneralException {

		final JWSAlgorithm expectedJWSAlg = clientInfo.getOIDCMetadata(
		).getIDTokenJWSAlg();

		if (opMetadata.getIDTokenJWSAlgs() == null) {
			throw new GeneralException("Missing OpenID Provider id_token_signing_alg_values_supported parameter");
		}

		if (!opMetadata.getIDTokenJWSAlgs().contains(expectedJWSAlg)) {
			throw new GeneralException("The OpenID Provider doesn't support " + expectedJWSAlg + " ID tokens");
		}

		if (Algorithm.NONE.equals(expectedJWSAlg)) {

			// Skip creation of JWS key selector, plain ID tokens expected

			return null;
		}
		else if (JWSAlgorithm.Family.RSA.contains(expectedJWSAlg) || JWSAlgorithm.Family.EC.contains(expectedJWSAlg)) {
			URL jwkSetURL;

			try {
				jwkSetURL = opMetadata.getJWKSetURI(
				).toURL();
			}
			catch (MalformedURLException murle) {
				throw new GeneralException("Invalid jwk set URI: " + murle.getMessage(), murle);
			}

			// CUSTOM START- set HTTP connect and read timeout, HTTP size limit

			ResourceRetriever resourceRetriever = new DefaultResourceRetriever(
				_HTTP_CONNECT_TIMEOUT, _HTTP_READ_TIMEOUT, _HTTP_SIZE_LIMIT);

			JWKSource jwkSource = new RemoteJWKSet(jwkSetURL, resourceRetriever);

			// CUSTOM END

			return new JWSVerificationKeySelector(expectedJWSAlg, jwkSource);
		}
		else if (JWSAlgorithm.Family.HMAC_SHA.contains(expectedJWSAlg)) {
			Secret clientSecret = clientInfo.getSecret();

			if (clientSecret == null) {
				throw new GeneralException("Missing client secret");
			}

			return new JWSVerificationKeySelector(expectedJWSAlg, new ImmutableSecret(clientSecret.getValueBytes()));
		}
		else {
			throw new GeneralException("Unsupported JWS algorithm: " + expectedJWSAlg);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cmicOpenIdConnectProviderConfiguration = ConfigurableUtil.createConfigurable(
			CMICOpenIdConnectProviderConfiguration.class, properties);
	}

	protected CMICOpenIdConnectSessionImpl createAndSetOpenIdConnectSession(
		HttpSession httpSession, String openIdConnectProviderName) {

		CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl = new CMICOpenIdConnectSessionImpl(
			openIdConnectProviderName, new Nonce(), new State());

		httpSession.setAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION, cmicOpenIdConnectSessionImpl);

		return cmicOpenIdConnectSessionImpl;
	}

	protected URI getAuthenticationRequestURI(
			URI loginRedirectURI, OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider,
			Nonce nonce, State state, Scope scope)
		throws OpenIdConnectServiceException.ProviderException {

		CMICOpenIdConnectProvider cmicOpenIdConnectProvider = new CMICOpenIdConnectProvider(openIdConnectProvider);

		OIDCProviderMetadata oidcProviderMetadata = cmicOpenIdConnectProvider.getOIDCProviderMetadata();

		OIDCClientInformation oidcClientInformation = getOIDCClientInformation(openIdConnectProvider);

		ResponseType responseType = new ResponseType(ResponseType.Value.CODE);

		AuthenticationRequest authenticationRequest = new AuthenticationRequest(
			oidcProviderMetadata.getAuthorizationEndpointURI(), responseType, scope, oidcClientInformation.getID(),
			loginRedirectURI, state, nonce);

		return authenticationRequest.toURI();
	}

	protected AuthenticationSuccessResponse getAuthenticationSuccessResponse(HttpServletRequest httpServletRequest)
		throws OpenIdConnectServiceException.AuthenticationException {

		StringBuffer requestURL = httpServletRequest.getRequestURL();

		if (Validator.isNotNull(httpServletRequest.getQueryString())) {
			requestURL.append(StringPool.QUESTION);
			requestURL.append(httpServletRequest.getQueryString());
		}

		try {
			URI requestURI = new URI(requestURL.toString());

			AuthenticationResponse authenticationResponse = AuthenticationResponseParser.parse(requestURI);

			if (authenticationResponse instanceof AuthenticationErrorResponse) {
				AuthenticationErrorResponse authenticationErrorResponse =
					(AuthenticationErrorResponse)authenticationResponse;

				ErrorObject errorObject = authenticationErrorResponse.getErrorObject();

				JSONObject jsonObject = errorObject.toJSONObject();

				throw new OpenIdConnectServiceException.AuthenticationException(jsonObject.toString());
			}

			return (AuthenticationSuccessResponse)authenticationResponse;
		}
		catch (ParseException | URISyntaxException e) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat("Unable to process response from ", requestURL.toString(), ": ", e.getMessage()),
				e);
		}
	}

	protected URI getLoginRedirectURI(HttpServletRequest httpServletRequest) {
		try {
			StringBundler sb = new StringBundler(3);

			sb.append(_portal.getPortalURL(httpServletRequest));
			sb.append(_portal.getPathContext());
			sb.append(OpenIdConnectConstants.REDIRECT_URL_PATTERN);

			return new URI(sb.toString());
		}
		catch (URISyntaxException urise) {
			throw new SystemException(
				"Unable to generate OpenId Connect login redirect URI: " + urise.getMessage(), urise);
		}
	}

	protected OIDCClientInformation getOIDCClientInformation(
		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider) {

		ClientID clientID = new ClientID(openIdConnectProvider.getClientId());

		Secret secret = new Secret(openIdConnectProvider.getClientSecret());

		CMICOpenIdConnectProvider cmicOpenIdConnectProvider = new CMICOpenIdConnectProvider(openIdConnectProvider);

		return new OIDCClientInformation(
			clientID, new Date(), cmicOpenIdConnectProvider.getOIDCClientMetadata(), secret);
	}

	protected OIDCTokenResponse getOIDCTokenResponse(
			OIDCClientInformation oidcClientInformation, URI tokenEndpoint, AuthorizationGrant authorizationCodeGrant)
		throws OpenIdConnectServiceException.TokenException {

		ClientAuthentication clientAuthentication = new ClientSecretBasic(
			oidcClientInformation.getID(), oidcClientInformation.getSecret());

		TokenRequest tokenRequest = new TokenRequest(tokenEndpoint, clientAuthentication, authorizationCodeGrant);

		HTTPRequest httpRequest = tokenRequest.toHTTPRequest();

		try {
			HTTPResponse httpResponse = httpRequest.send();

			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(httpResponse);

			if (tokenResponse instanceof TokenErrorResponse) {
				TokenErrorResponse tokenErrorResponse = (TokenErrorResponse)tokenResponse;

				ErrorObject errorObject = tokenErrorResponse.getErrorObject();

				JSONObject jsonObject = errorObject.toJSONObject();

				throw new OpenIdConnectServiceException.TokenException(jsonObject.toString());
			}

			return (OIDCTokenResponse)tokenResponse;
		}
		catch (IOException ioe) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat("Unable to get tokens from ", tokenEndpoint, ": ", ioe.getMessage()), ioe);
		}
		catch (ParseException pe) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat("Unable to parse tokens response from ", tokenEndpoint, ": ", pe.getMessage()),
				pe);
		}
	}

	protected CMICOpenIdConnectSessionImpl getOpenIdConnectSessionImpl(HttpSession httpSession)
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException {

		CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl = getOpenIdConnectSessionImpl(httpSession, null);

		if (cmicOpenIdConnectSessionImpl == null) {
			throw new OpenIdConnectServiceException.NoOpenIdConnectSessionException(
				"HTTP session does contain an OpenId Connect session");
		}

		return cmicOpenIdConnectSessionImpl;
	}

	protected CMICOpenIdConnectSessionImpl getOpenIdConnectSessionImpl(
		HttpSession httpSession, String expectedProviderName) {

		Object openIdConnectSessionObject = httpSession.getAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		if (openIdConnectSessionObject instanceof OpenIdConnectSessionImpl) {
			OpenIdConnectSessionImpl openIdConnectSessionImpl = (OpenIdConnectSessionImpl)openIdConnectSessionObject;

			CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl = new CMICOpenIdConnectSessionImpl(
				openIdConnectSessionImpl);

			if (Validator.isNull(expectedProviderName) ||
				expectedProviderName.equals(cmicOpenIdConnectSessionImpl.getOpenIdProviderName())) {

				return cmicOpenIdConnectSessionImpl;
			}
		}
		else if (openIdConnectSessionObject instanceof CMICOpenIdConnectSessionImpl) {
			CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl =
				(CMICOpenIdConnectSessionImpl)openIdConnectSessionObject;

			if (Validator.isNull(expectedProviderName) ||
				expectedProviderName.equals(cmicOpenIdConnectSessionImpl.getOpenIdProviderName())) {

				return cmicOpenIdConnectSessionImpl;
			}
		}

		return null;
	}

	protected boolean hasValidAccessToken(CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl) {
		AccessToken accessToken = cmicOpenIdConnectSessionImpl.getAccessToken();

		if (accessToken == null) {
			return false;
		}

		long currentTime = System.currentTimeMillis();
		long lifetime = accessToken.getLifetime() * Time.SECOND;
		long loginTime = cmicOpenIdConnectSessionImpl.getLoginTime();

		if ((currentTime - loginTime) < lifetime) {
			return true;
		}

		return false;
	}

	protected void processUserInfo(
			long companyId, Tokens tokens, OIDCProviderMetadata oidcProviderMetadata,
			CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl)
		throws PortalException {

		try {
			OIDCTokens oidcTokens = tokens.toOIDCTokens();

			JWT jwt = oidcTokens.getIDToken();

			ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor();

			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(
					cmicOpenIdConnectSessionImpl.getOpenIdProviderName());

			OIDCClientInformation oidcClientInformation = getOIDCClientInformation(openIdConnectProvider);

			JWSKeySelector<SecurityContext> keySelector = createJWSKeySelector(
				oidcProviderMetadata, oidcClientInformation);

			jwtProcessor.setJWSKeySelector(keySelector);

			JWTClaimsSet claimsSet = jwtProcessor.process(jwt, null);

			UserInfo userInfo = new UserInfo(claimsSet);

			long userId = _cmicOpenIdConnectUserInfoProcessor.processUserInfo(userInfo, companyId);

			cmicOpenIdConnectSessionImpl.setLoginUserId(userId);

			cmicOpenIdConnectSessionImpl.setUserInfo(userInfo);
		}
		catch (BadJOSEException | JOSEException e) {
			_log.error("Could not get user info: " + e.getMessage(), e);

			throw new OpenIdConnectServiceException.UserInfoException("Could not get user info: " + e.getMessage());
		}
		catch (GeneralException ge) {
			throw new OpenIdConnectServiceException.TokenException(
				"Unable to instantiate token validator: " + ge.getMessage(), ge);
		}
	}

	protected boolean refreshAuthToken(CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl)
		throws OpenIdConnectServiceException {

		if (hasValidAccessToken(cmicOpenIdConnectSessionImpl)) {
			return true;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"User session auth token is invalid, attempting to use refresh token to obtain a valid auth token");
		}

		RefreshToken refreshToken = cmicOpenIdConnectSessionImpl.getRefreshToken();

		if (refreshToken == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to refresh auth token because no refresh token is supplied");
			}

			return false;
		}

		String openIdConnectProviderName = cmicOpenIdConnectSessionImpl.getOpenIdProviderName();

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> openIdConnectProvider =
			_openIdConnectProviderRegistry.findOpenIdConnectProvider(openIdConnectProviderName);

		CMICOpenIdConnectProvider cmicOpenIdConnectProvider = new CMICOpenIdConnectProvider(openIdConnectProvider);

		Tokens tokens = requestRefreshToken(
			refreshToken, getOIDCClientInformation(openIdConnectProvider),
			cmicOpenIdConnectProvider.getOIDCProviderMetadata(), cmicOpenIdConnectSessionImpl.getNonce());

		updateSessionTokens(cmicOpenIdConnectSessionImpl, tokens, System.currentTimeMillis());

		return true;
	}

	protected Tokens requestIdToken(
			AuthenticationSuccessResponse authenticationSuccessResponse, OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, URI redirectURI, Nonce nonce)
		throws OpenIdConnectServiceException.TokenException {

		AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(
			authenticationSuccessResponse.getAuthorizationCode(), redirectURI);

		return requestTokens(oidcClientInformation, oidcProviderMetadata, nonce, authorizationCodeGrant);
	}

	protected Tokens requestRefreshToken(
			RefreshToken refreshToken, OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, Nonce nonce)
		throws OpenIdConnectServiceException {

		AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(refreshToken);

		return requestTokens(oidcClientInformation, oidcProviderMetadata, nonce, refreshTokenGrant);
	}

	protected Tokens requestTokens(
			OIDCClientInformation oidcClientInformation, OIDCProviderMetadata oidcProviderMetadata, Nonce nonce,
			AuthorizationGrant authorizationCodeGrant)
		throws OpenIdConnectServiceException.TokenException {

		// CUSTOM START- if exception occurs in first token request, try with a second token request

		try {
			OIDCTokenResponse oidcTokenResponse = getOIDCTokenResponse(
				oidcClientInformation, oidcProviderMetadata.getTokenEndpointURI(), authorizationCodeGrant);

			validateToken(oidcClientInformation, nonce, oidcProviderMetadata, oidcTokenResponse);

			return oidcTokenResponse.getTokens();
		}
		catch (OpenIdConnectServiceException.TokenException oicsete) {
			String additionalTokenEndpoint = _cmicOpenIdConnectProviderConfiguration.additionalTokenEndPoint();

			try {
				URI additionalTokenEndpointURI = new URI(additionalTokenEndpoint);

				OIDCTokenResponse oidcTokenResponse = getOIDCTokenResponse(
					oidcClientInformation, additionalTokenEndpointURI, authorizationCodeGrant);

				validateToken(oidcClientInformation, nonce, oidcProviderMetadata, oidcTokenResponse);

				return oidcTokenResponse.getTokens();
			}
			catch (URISyntaxException urise) {
				throw new OpenIdConnectServiceException.TokenException(
					StringBundler.concat(
						"Unable to get tokens from ", additionalTokenEndpoint, ": ", urise.getMessage()),
					urise);
			}
		}

		// CUSTOM END

	}

	protected void updateSessionTokens(
		CMICOpenIdConnectSessionImpl cmicOpenIdConnectSessionImpl, Tokens tokens, long loginTime) {

		cmicOpenIdConnectSessionImpl.setAccessToken(tokens.getAccessToken());
		cmicOpenIdConnectSessionImpl.setRefreshToken(tokens.getRefreshToken());
		cmicOpenIdConnectSessionImpl.setLoginTime(loginTime);
	}

	protected void validateState(State requestedState, State state) throws OpenIdConnectServiceException {
		if (!state.equals(requestedState)) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"Requested value \"", requestedState.getValue(), "\" and approved state \"", state.getValue(),
					"\" do not match"));
		}
	}

	protected IDTokenClaimsSet validateToken(
			OIDCClientInformation oidcClientInformation, Nonce nonce, OIDCProviderMetadata oidcProviderMetadata,
			OIDCTokenResponse oidcTokenResponse)
		throws OpenIdConnectServiceException.TokenException {

		try {
			// CUSTOM START

			IDTokenValidator idTokenValidator = new IDTokenValidator(
				oidcProviderMetadata.getIssuer(), oidcClientInformation.getID(),
				createJWSKeySelector(oidcProviderMetadata, oidcClientInformation),
				createJWEKeySelector(oidcProviderMetadata, oidcClientInformation));

			// CUSTOM END

			OIDCTokens oidcTokens = oidcTokenResponse.getOIDCTokens();

			return idTokenValidator.validate(oidcTokens.getIDToken(), nonce);
		}
		catch (GeneralException ge) {
			throw new OpenIdConnectServiceException.TokenException(
				"Unable to instantiate token validator: " + ge.getMessage(), ge);
		}
		catch (BadJOSEException | JOSEException e) {
			throw new OpenIdConnectServiceException.TokenException("Unable to validate tokens: " + e.getMessage(), e);
		}
	}

	/**
	 * The HTTP connect timeout for JWK set retrieval, in
	 * milliseconds. Set to 10000 milliseconds. Default was 500.
	 */
	private static final int _HTTP_CONNECT_TIMEOUT = 10000;

	/**
	 * The HTTP read timeout for JWK set retrieval, in
	 * milliseconds. Set to 10000 milliseconds. Default was 500.
	 */
	private static final int _HTTP_READ_TIMEOUT = 10000;

	/**
	 * The default HTTP entity size limit for JWK set retrieval, in bytes.
	 * Set to 50 KBytes.
	 */
	private static final int _HTTP_SIZE_LIMIT = 50 * 1024;

	private static final String _SIGN_UP_URL = "/signup";

	private static final String _SIGN_UP_URL_PARAMETER = "domain_hint=signup";

	private static final Log _log = LogFactoryUtil.getLog(OpenIdConnectServiceHandlerImpl.class);

	private CMICOpenIdConnectProviderConfiguration _cmicOpenIdConnectProviderConfiguration;

	@Reference
	private CMICOpenIdConnectUserInfoProcessor _cmicOpenIdConnectUserInfoProcessor;

	@Reference
	private OpenIdConnectProviderRegistry<OIDCClientMetadata, OIDCProviderMetadata> _openIdConnectProviderRegistry;

	@Reference
	private Portal _portal;

}