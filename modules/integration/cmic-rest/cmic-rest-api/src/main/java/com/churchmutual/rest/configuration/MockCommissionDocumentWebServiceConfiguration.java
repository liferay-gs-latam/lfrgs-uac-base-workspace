package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockCommissionDocumentWebServiceConfiguration",
	localization = "content/Language", name = "mock-commission-document-web-service"
)
public interface MockCommissionDocumentWebServiceConfiguration {

	@Meta.AD(
		deflt = "false", description = "Enable mock for Commission Document Service method /v1/download/ids",
		required = false
	)
	public boolean enableMockDownloadDocuments();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Commission Document Service method /v1/search", required = false
	)
	public boolean enableMockSearchDocuments();

}