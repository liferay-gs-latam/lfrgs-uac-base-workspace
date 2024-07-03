package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration", localization = "content/Language",
	name = "mock-portal-user-web-service"
)
public interface MockPortalUserWebServiceConfiguration {

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v2/insured-users/{targetUserId}",
		required = false
	)
	public boolean enableMockDeleteInsuredEntityUser();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v2/producer-users/{targetUserId}",
		required = false
	)
	public boolean enableMockDeleteProducerEntityUser();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v1/loss-run/for-account/pdf",
		required = false
	)
	public boolean enableMockGenerateLossRunReport();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v2/insureds/{accountNumber}/users",
		required = false
	)
	public boolean enableMockGetAccountEntityUsers();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v2/producers/{producerId}/users",
		required = false
	)
	public boolean enableMockGetProducerEntityUsers();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v2/users/{userId}", required = false
	)
	public boolean enableMockGetUserDetails();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/users/{userId}/is-user-registered",
		required = false
	)
	public boolean enableMockIsUserRegistered();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/insured-users/{inviteeUserId}/self-provision-existing",
		required = false
	)
	public boolean enableMockProvisionExistingInsuredUser();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/producer-users/{inviteeUserId}/self-provision-existing",
		required = false
	)
	public boolean enableMockProvisionExistingProducerUser();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/insured-users/self-provision-new",
		required = false
	)
	public boolean enableMockProvisionNewInsuredUsers();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/producer-users/self-provision-new",
		required = false
	)
	public boolean enableMockProvisionNewProducerUsers();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/insured-users/{targetUserId}/role",
		required = false
	)
	public boolean enableMockUpdateInsuredUserRole();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/producer-users/{targetUserId}/role",
		required = false
	)
	public boolean enableMockUpdateProducerUserRole();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/insured-users/{userId}/registration",
		required = false
	)
	public boolean enableMockValidateInsuredUser();

	@Meta.AD(
		deflt = "false",
		description = "Enable mock for Portal User Service method /v2/producer-users/{userId}/registration",
		required = false
	)
	public boolean enableMockValidateProducerUser();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Portal User Service method /v2/users/registration",
		required = false
	)
	public boolean enableMockValidateUser();

}