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

package com.churchmutual.content.setup;

import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util._any.UpdateProfileLinksUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.producer.ProfileLinks;
import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddInsuredSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddProducerSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddRegistrationSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddRolesAndUserGroupsUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v1_0_1.UpdateRolePermissionsUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v2_0_0.UpdateInsuredSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v2_0_0.UpdateProducerSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.v2_0_0.UpdateUserGroupsUpgradeProcess;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.user.registration.constants.UserRegistrationPortletKeys;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContentSetupUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new AddProducerSiteUpgradeProcess(
				assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
				ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
				expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService,
				layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager,
				userLocalService, virtualHostLocalService),
			new AddInsuredSiteUpgradeProcess(
				assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
				ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
				expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService,
				layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager,
				userLocalService, virtualHostLocalService),
			new AddRegistrationSiteUpgradeProcess(
				assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
				ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
				expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService,
				layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager,
				userLocalService, virtualHostLocalService),
			new AddRolesAndUserGroupsUpgradeProcess(
				groupLocalService, permissionCheckerFactory, portal, roleLocalService, userGroupLocalService,
				userLocalService));

		registry.register(
			"1.0.0", "1.0.1",
			new UpdateRolePermissionsUpgradeProcess(
				permissionCheckerFactory, portal, roleLocalService, userLocalService));

		registry.register(
			"1.0.1", "2.0.0",
			new UpdateProducerSiteUpgradeProcess(
				assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
				ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
				expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService,
				layoutLocalService, layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService,
				storageEngineManager, userLocalService, virtualHostLocalService),
			new UpdateInsuredSiteUpgradeProcess(
				assetEntryLocalService, classNameLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
				ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
				expandoColumnLocalService, expandoTableLocalService, fragmentCollectionContributorTracker,
			fragmentEntryLinkLocalService, groupLocalService, journalArticleLocalService, layoutLocalService, layoutPageTemplateStructureLocalService,
				layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager,
				userLocalService, virtualHostLocalService),
			new UpdateUserGroupsUpgradeProcess(
				groupLocalService, permissionCheckerFactory, portal, roleLocalService, userGroupLocalService,
				userLocalService));

		_alwaysUpgrade();
	}

	/**
	 * Due to limitations of Liferay, there are certain things we always want to upgrade without it necessarily being
	 * constrained to a specific schema version.
	 */
	private void _alwaysUpgrade() {
		try {
			UpdateProfileLinksUpgradeProcess updateProfileLinksUpgradeProcess = new UpdateProfileLinksUpgradeProcess(assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService, ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService, expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService, layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager, userLocalService, virtualHostLocalService);
			updateProfileLinksUpgradeProcess.doUpgradeAsAdmin();
		} catch (Exception e) {
			// ignore - non-critical
		}
	}

	@Reference(target = "(javax.portlet.name=com_churchmutual_account_list_web_portlet_AccountListWebPortlet)")
	protected Portlet accountListWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_account_producer_dashboard_web_portlet_AccountProducerDashboardWebPortlet)"
	)
	protected Portlet accountProducerDashboardWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_account_profile_web_portlet_AccountProfileWebPortlet)")
	protected Portlet accountProfileWebPortlet;

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected CMICUserLocalService cmicUserLocalService;

	@Reference(target = "(javax.portlet.name=com_churchmutual_commission_web_portlet_CommissionWebPortlet)")
	protected Portlet commissionWebPortlet;

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_contact_insured_dashboard_web_portlet_ContactInsuredDashboardWebPortlet)"
	)
	protected Portlet contactInsuredDashboardWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_contact_producer_dashboard_web_portlet_ContactProducerDashboardWebPortlet)"
	)
	protected Portlet contactProducerDashboardWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_contact_producer_list_web_portlet_ContactProducerListWebPortlet)"
	)
	protected Portlet contactProducerListWebPortlet;

	@Reference
	protected DDM ddm;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DDMTemplateLocalService ddmTemplateLocalService;

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected DLFileEntryLocalService dlFileEntryLocalService;

	@Reference
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;

	@Reference
	protected ExpandoColumnLocalService expandoColumnLocalService;

	@Reference
	protected ExpandoTableLocalService expandoTableLocalService;

	@Reference
	protected FragmentCollectionContributorTracker fragmentCollectionContributorTracker;

	@Reference
	protected FragmentEntryLinkLocalService fragmentEntryLinkLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference(target = "(javax.portlet.name=com_liferay_layout_admin_web_portlet_GroupPagesPortlet)")
	protected Portlet groupPagesPortlet;

	@Reference
	protected JournalArticleLocalService journalArticleLocalService;

	@Reference(target = "(javax.portlet.name=com_liferay_journal_content_web_portlet_JournalContentPortlet)")
	protected Portlet journalContentPortlet;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPageTemplateStructureLocalService layoutPageTemplateStructureLocalService;

	@Reference
	protected LayoutSetLocalService layoutSetLocalService;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_loss_run_report_insured_web_portlet_LossRunReportInsuredWebPortlet)"
	)
	protected Portlet lossRunReportInsuredWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_loss_run_report_web_portlet_LossRunReportWebPortlet)")
	protected Portlet lossRunReportWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_organization_profile_web_portlet_OrganizationProfileWebPortlet)"
	)
	protected Portlet organizationProfileWebPortlet;

	@Reference
	protected PermissionCheckerFactory permissionCheckerFactory;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_policy_change_request_web_portlet_PolicyChangeRequestWebPortlet)"
	)
	protected Portlet policyChangeRequestWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_policy_insured_dashboard_web_portlet_PolicyInsuredDashboardWebPortlet)"
	)
	protected Portlet policyInsuredDashboardWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_policy_list_web_portlet_PolicyListWebPortlet)")
	protected Portlet policyListWebPortlet;

	@Reference
	protected Portal portal;

	@Reference(target = "(javax.portlet.name=com_churchmutual_profile_web_portlet_ProfileWebPortlet)")
	protected Portlet profileWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_proof_of_insurance_dashboard_web_portlet_ProofOfInsuranceDashboardWebPortlet)"
	)
	protected Portlet proofOfInsuranceDashboardWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_proof_of_insurance_web_portlet_ProofOfInsuranceWebPortlet)"
	)
	protected Portlet proofOfInsuranceWebPortlet;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.adaptive.media.document.library.thumbnails)(release.schema.version>=1.0.1))",
		unbind = "-"
	)
	protected Release release;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_self_provisioning_invite_insured_web_portlet_SelfProvisioningInviteInsuredWebPortlet)"
	)
	protected Portlet selfProvisioningInviteInsuredWebPortlet;

	@Reference(
		target = "(javax.portlet.name=com_churchmutual_self_provisioning_web_portlet_SelfProvisioningWebPortlet)"
	)
	protected Portlet selfProvisioningWebPortlet;

	@Reference
	protected StorageEngineManager storageEngineManager;

	@Reference(target = "(javax.portlet.name=com_churchmutual_transaction_list_web_portlet_TransactionListWebPortlet)")
	protected Portlet transactionListWebPortlet;

	@Reference
	protected UserGroupLocalService userGroupLocalService;

	@Reference
	protected UserLocalService userLocalService;

	@Reference(target = "(javax.portlet.name=" + UserRegistrationPortletKeys.USER_REGISTRATION_WEB + ")")
	protected Portlet userRegistrationWebPortlet;

	@Reference
	protected VirtualHostLocalService virtualHostLocalService;

}