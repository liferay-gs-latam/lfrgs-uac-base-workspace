package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(description = "Configure settings for accessing API Gateway to Church Mutual REST Services.", id = "com.churchmutual.rest.configuration.CMICWebServiceAuthenticationConfiguration", localization = "content/Language", name = "church-mutual-web-service-authentication")
public interface CMICWebServiceAuthenticationConfiguration {

	@Meta.AD(deflt = "", description = "Base URL for accessing Church Mutual REST Services")
	public String baseURL();

	@Meta.AD(deflt = "", description = "Username for Basic Auth")
	public String basicAuthUsername();

	@Meta.AD(deflt = "", description = "Password for Basic Auth")
	public String basicAuthPassword();

}