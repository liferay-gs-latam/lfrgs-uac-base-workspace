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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom wrapper class for OpenIdConnectProvider to work around class loading issues
 *
 * @author Kayleen Lim
 */
public class CMICOpenIdConnectProvider {

	public CMICOpenIdConnectProvider(OpenIdConnectProvider openIdConnectProvider) {
		_openIdConnectProvider = openIdConnectProvider;
	}

	public OIDCClientMetadata getOIDCClientMetadata() {
		try {
			OIDCProviderMetadata oidcProviderMetadata = getOIDCProviderMetadata();

			OIDCClientMetadata oidcClientMetadata = new OIDCClientMetadata();

			List<JWEAlgorithm> jweAlgorithms = oidcProviderMetadata.getIDTokenJWEAlgs();

			if (ListUtil.isNotEmpty(jweAlgorithms)) {
				oidcClientMetadata.setIDTokenJWEAlg(jweAlgorithms.get(0));
			}

			List<JWSAlgorithm> jwsAlgorithms = oidcProviderMetadata.getIDTokenJWSAlgs();

			if (ListUtil.isNotEmpty(jwsAlgorithms)) {
				oidcClientMetadata.setIDTokenJWSAlg(jwsAlgorithms.get(0));
			}

			oidcClientMetadata.setJWKSetURI(oidcProviderMetadata.getJWKSetURI());

			return oidcClientMetadata;
		}
		catch (OpenIdConnectServiceException.ProviderException oicsepe) {
			_log.error("Could not populate client metadata: " + oicsepe.getMessage(), oicsepe);

			return new OIDCClientMetadata();
		}
	}

	public OIDCProviderMetadata getOIDCProviderMetadata() throws OpenIdConnectServiceException.ProviderException {
		Object object = _openIdConnectProvider.getOIDCProviderMetadata();

		try {
			JSONObject oidcProviderMetadataJSONObject = JSONFactoryUtil.createJSONObject(object.toString());

			String issuerURL = oidcProviderMetadataJSONObject.getString("issuer");

			List<SubjectType> subjectTypesList = new ArrayList<>();

			JSONArray subjectTypesSupported = oidcProviderMetadataJSONObject.getJSONArray("subject_types_supported");

			for (int i = 0; i < subjectTypesSupported.length(); i++) {
				subjectTypesList.add(SubjectType.parse(subjectTypesSupported.getString(i)));
			}

			String jwksURL = oidcProviderMetadataJSONObject.getString("jwks_uri");

			String authorizationEndPointURL = oidcProviderMetadataJSONObject.getString("authorization_endpoint");

			JSONArray idTokenSigningAlgValuesArray = oidcProviderMetadataJSONObject.getJSONArray(
				"id_token_signing_alg_values_supported");

			List<String> idTokenSigningAlgValues = new ArrayList<>();

			for (int i = 0; i < idTokenSigningAlgValuesArray.length(); i++) {
				idTokenSigningAlgValues.add(idTokenSigningAlgValuesArray.getString(i));
			}

			String tokenEndPointURL = oidcProviderMetadataJSONObject.getString("token_endpoint");

			OIDCProviderMetadata oidcProviderMetadata = new OIDCProviderMetadata(
				new Issuer(issuerURL), subjectTypesList, new URI(jwksURL));

			oidcProviderMetadata.setAuthorizationEndpointURI(new URI(authorizationEndPointURL));

			List<JWSAlgorithm> jwsAlgorithms = new ArrayList<>();

			for (String idTokenSigningAlgValue : idTokenSigningAlgValues) {
				jwsAlgorithms.add(JWSAlgorithm.parse(idTokenSigningAlgValue));
			}

			oidcProviderMetadata.setIDTokenJWSAlgs(jwsAlgorithms);

			oidcProviderMetadata.setTokenEndpointURI(new URI(tokenEndPointURL));

			return oidcProviderMetadata;
		}
		catch (JSONException | ParseException | URISyntaxException e) {
			_log.error("Could not get provider metadata:" + e.getMessage(), e);

			throw new OpenIdConnectServiceException.ProviderException(
				"Could not get provider metadata:" + e.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICOpenIdConnectProvider.class);

	private final OpenIdConnectProvider _openIdConnectProvider;

}