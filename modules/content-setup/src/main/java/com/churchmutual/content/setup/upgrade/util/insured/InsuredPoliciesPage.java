package com.churchmutual.content.setup.upgrade.util.insured;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseSitePage;
import com.churchmutual.content.setup.upgrade.util.common.InsuredUpgradeConstants;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;

public class InsuredPoliciesPage extends BaseSitePage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Policies"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_INSURED_POLICIES
		);

		Layout layout = LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_75_25, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);

		setJournalArticle(
			groupId, layout.getPlid(), _JOURNAL_CONTENT_PORTLET_ID_1,
			InsuredUpgradeConstants.FILE_A_CLAIM_WEB_CONTENT_TITLE);
	}

	private static final String _JOURNAL_CONTENT_PORTLET_ID_1 = PortletIdCodec.encode(
		"com_liferay_journal_content_web_portlet_JournalContentPortlet");

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_policy_list_web_portlet_PolicyListWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_policy_change_request_web_portlet_PolicyChangeRequestWebPortlet", "com_churchmutual_proof_of_insurance_web_portlet_ProofOfInsuranceWebPortlet",
		_JOURNAL_CONTENT_PORTLET_ID_1, "com_churchmutual_loss_run_report_insured_web_portlet_LossRunReportInsuredWebPortlet"
	};

}