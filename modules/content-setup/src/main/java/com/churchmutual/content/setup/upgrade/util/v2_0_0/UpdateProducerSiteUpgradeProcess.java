package com.churchmutual.content.setup.upgrade.util.v2_0_0;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;
import com.churchmutual.core.service.CMICUserLocalService;

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
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;

public class UpdateProducerSiteUpgradeProcess extends BaseSiteUpgradeProcess {

	public UpdateProducerSiteUpgradeProcess(
			AssetEntryLocalService assetEntryLocalService, CMICUserLocalService cmicUserLocalService,
			CompanyLocalService companyLocalService, DDM ddm, DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService, DLAppService dlAppService, DLFileEntryLocalService dlFileEntryLocalService,
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService, ExpandoColumnLocalService expandoColumnLocalService,
			ExpandoTableLocalService expandoTableLocalService, GroupLocalService groupLocalService,
			JournalArticleLocalService journalArticleLocalService, LayoutLocalService layoutLocalService,
			LayoutSetLocalService layoutSetLocalService, PermissionCheckerFactory permissionCheckerFactory, Portal portal,
			RoleLocalService roleLocalService, StorageEngineManager storageEngineManager, UserLocalService userLocalService,
			VirtualHostLocalService virtualHostLocalService) {

		super(
			assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
			ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService, expandoColumnLocalService,
			expandoTableLocalService, groupLocalService, journalArticleLocalService, layoutSetLocalService,
			permissionCheckerFactory, portal, roleLocalService, storageEngineManager, userLocalService,
			virtualHostLocalService);

		this.groupLocalService = groupLocalService;
		this.layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		updateProducerSite();
	}

	protected void addDashboardCommissionStatements(long groupId, long userId) throws Exception {
		Layout layout = layoutLocalService.getFriendlyURLLayout(
			groupId, true, LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_DASHBOARD);

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletId(userId, _COMMISSION_STATEMENTS_PORTLET, LayoutConstants.COL_ID_COLUMN_1, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), layout.getTypeSettings());
	}

	protected void updateLayoutPortlets(long groupId, long userId, String friendlyURL) throws Exception {
		Layout layout = layoutLocalService.fetchLayoutByFriendlyURL(groupId, true, friendlyURL);

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletIds(userId, _PORTLETS_COLUMN_2, LayoutConstants.COL_ID_COLUMN_2, true);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), layout.getTypeSettings());
	}

	protected void updateProducerSite() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		Group producerPortalGroup = groupLocalService.getFriendlyURLGroup(
				companyId, CMICSite.PRODUCER.getFriendlyURL());

		long groupId = producerPortalGroup.getGroupId();

		long userId = userLocalService.getDefaultUserId(companyId);

		updateLayoutPortlets(groupId, userId, LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_ACCOUNT_DETAILS);

		updateLayoutPortlets(groupId, userId, LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_POLICY_DETAILS);

		setLayoutScript(
			groupId, true, LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_DASHBOARD,
			ProducerUpgradeConstants.SURVEY_MONKEY);

		addDashboardCommissionStatements(groupId, userId);
	}

	protected GroupLocalService groupLocalService;
	protected LayoutLocalService layoutLocalService;

	private static final String _COMMISSION_STATEMENTS_PORTLET =
		"com_churchmutual_commission_web_portlet_CommissionWebPortlet";

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_loss_run_report_web_portlet_LossRunReportWebPortlet",
		"com_churchmutual_self_provisioning_invite_insured_web_portlet_SelfProvisioningInviteInsuredWebPortlet"
	};

}