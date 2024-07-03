package com.churchmutual.content.setup.upgrade.util.insured;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseSitePage;
import com.churchmutual.content.setup.upgrade.util.common.InsuredUpgradeConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;

public class InsuredPaymentPage extends BaseSitePage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Payment"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_INSURED_PAYMENT
		).addPortletKey(
			_JOURNAL_CONTENT_PORTLET_ID
		);

		Layout layout = LayoutHelper.addLayoutWithPortlet(userId, groupId, layoutConfig);

		setJournalArticle(
			groupId, layout.getPlid(), _JOURNAL_CONTENT_PORTLET_ID,
			InsuredUpgradeConstants.PAYMENT_WEB_CONTENT_TITLE);
	}

	private static final String _JOURNAL_CONTENT_PORTLET_ID = PortletIdCodec.encode(
		"com_liferay_journal_content_web_portlet_JournalContentPortlet");

}