package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
        id = "com.churchmutual.rest.configuration.MockQsPdsPolicyServiceConfiguration", localization = "content/Language",
        name = "mock-qs-pds-policy-service"
)
public interface MockQsPdsPolicyWebServiceConfiguration {

    @Meta.AD(
            deflt = "false",
            description = "Enable mock for QS PDS Policy Service method /v1/additional-interests/on-building",
            required = false
    )
    public boolean enableMockGetAdditionalInterestsOnBuilding();

    @Meta.AD(
            deflt = "false",
            description = "Enable mock for Policy Service method /v1/insurable-objects/buildings/on-policy",
            required = false
    )
    public boolean enableMockGetBuildingsOnPolicy();

    @Meta.AD(deflt = "false", description = "Enable mock for Policy Service method /v1/transactions", required = false)
    public boolean enableMockGetTransaction();

    @Meta.AD(
            deflt = "false", description = "Enable mock for Policy Service method /v1/policy-summaries/on-account",
            required = false
    )
    public boolean enableMockGetPolicyAccountSummariesByAccounts();

    @Meta.AD(
            deflt = "false", description = "Enable mock for Policy Service method /v1/transactions/on-policy",
            required = false
    )
    public boolean enableMockGetTransactionsOnPolicy();

    @Meta.AD(deflt = "false", description = "Enable mock for Policy Service method /v1/policies", required = false)
    public boolean enableMockGetPoliciesByPolicyNumbers();

    @Meta.AD(
            deflt = "false", description = "Enable mock for Policy Service method /v1/policies/on-account", required = false
    )
    public boolean enableMockGetPoliciesOnAccount();
}
