package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockAccountWebServiceConfiguration", localization = "content/Language",
	name = "mock-account-web-service"
)
public interface MockAccountWebServiceConfiguration {

	@Meta.AD(
		deflt = "false", description = "Enable mock for Account Service method /v1/accounts/{accountNumber}",
		required = false
	)
	public boolean enableMockGetAccounts();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Account Service method /v1/accounts/search/by-producer",
		required = false
	)
	public boolean enableMockGetAccountsSearchByProducer();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Account Service method /v1/addresses/account", required = false
	)
	public boolean enableMockGetAddressAccount();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Account Service method /v1/addresses/mailing", required = false
	)
	public boolean enableMockGetMailingAddressAccount();

}