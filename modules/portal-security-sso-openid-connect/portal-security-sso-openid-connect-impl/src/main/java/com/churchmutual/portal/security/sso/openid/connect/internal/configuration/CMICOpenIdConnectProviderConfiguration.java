package com.churchmutual.portal.security.sso.openid.connect.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(category = "sso", scope = ExtendedObjectClassDefinition.Scope.SYSTEM)
@Meta.OCD(
	id = "com.churchmutual.portal.security.sso.openid.connect.internal.configuration.CMICOpenIdConnectProviderConfiguration",
	localization = "content/Language", name = "cmic-open-id-connect-provider-configuration-name"
)
public interface CMICOpenIdConnectProviderConfiguration {

	@Meta.AD(deflt = "", description = "token-endpoint-help", name = "additional-token-endpoint", required = false)
	public String additionalTokenEndPoint();

}