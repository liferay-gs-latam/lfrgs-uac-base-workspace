package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matthew Chan
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockCertificateOfInsuranceWebServiceConfiguration",
	localization = "content/Language", name = "mock-certificate-of-insurance-web-service"
)
public interface MockCertificateOfInsuranceWebServiceConfiguration {

	@Meta.AD(
		deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /v1/forms/acord25",
		required = false
	)
	public boolean enableMockDownloadCOIDocument();

	@Meta.AD(
			deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /v1/forms/acord25",//change once known
			required = false
		)
		public boolean enableMockDownloadNYWCDocument();
	
	@Meta.AD(
		deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /v1/forms/acord27",
		required = false
	)
	public boolean enableMockDownloadEOPDocument();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /v1/forms/acord25/eligible-policies",
		required = false
	)
	public boolean enableMockGetCOIEligiblePolicies();

	@Meta.AD(
			deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /policy-service/v1/new-york-workers-compensation/policy-numbers-on-account",
			required = false
		)
		public boolean enableMockGetNYWCEligiblePolicies();
	
	@Meta.AD(
			deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /policy-service/v1/new-york-workers-compensation/policy-numbers-on-account",
			required = false
		)
		public boolean enableMockGetNYWCDisplay();
	
	@Meta.AD(
		deflt = "false", description = "Enable mock for Certificate Of Insurance Service method /v1/forms/acord27/eligible-policies",
		required = false
	)
	public boolean enableMockGetEOPEligiblePolicies();

}