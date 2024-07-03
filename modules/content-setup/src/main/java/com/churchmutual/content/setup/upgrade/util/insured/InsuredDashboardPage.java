package com.churchmutual.content.setup.upgrade.util.insured;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;

public class InsuredDashboardPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Home"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_INSURED_DASHBOARD
		);

		LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_66_33, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);
	}

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_policy_insured_dashboard_web_portlet_PolicyInsuredDashboardWebPortlet",
		"com_churchmutual_proof_of_insurance_dashboard_web_portlet_ProofOfInsuranceDashboardWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_contact_insured_dashboard_web_portlet_ContactInsuredDashboardWebPortlet"
	};

}