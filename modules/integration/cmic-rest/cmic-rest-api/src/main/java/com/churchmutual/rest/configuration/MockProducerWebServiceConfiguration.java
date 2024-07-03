package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockProducerWebServiceConfiguration", localization = "content/Language",
	name = "mock-producer-web-service"
)
public interface MockProducerWebServiceConfiguration {

	@Meta.AD(deflt = "false", description = "Enable mock for Producer Service method /v1/contacts", required = false)
	public boolean enableMockGetContacts();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Producer Service method /v1/contacts/with-assignment",
		required = false
	)
	public boolean enableMockGetPrimaryContact();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Producer Service method /v1/producers/{id}", required = false
	)
	public boolean enableMockGetProducerById();

	@Meta.AD(deflt = "false", description = "Enable mock for Producer Service method /v1/producers", required = false)
	public boolean enableMockGetProducers();

	@Meta.AD(
		deflt = "false", description = "Enable mock for Producer Service method /v1/role-assignments", required = false
	)
	public boolean enableMockGetRoleAssignments();

}