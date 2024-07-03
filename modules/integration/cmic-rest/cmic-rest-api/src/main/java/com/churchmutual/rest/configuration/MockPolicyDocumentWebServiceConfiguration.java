package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration",
	localization = "content/Language", name = "mock-policy-document-web-service"
)
public interface MockPolicyDocumentWebServiceConfiguration {

	@Meta.AD(
		deflt = "false", description = "Enable mock for Policy Document Service method /v1/download/transactions",
		required = false
	)
	public boolean enableMockDownloadTransaction();

}