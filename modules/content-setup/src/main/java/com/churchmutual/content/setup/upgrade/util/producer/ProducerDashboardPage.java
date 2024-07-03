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

package com.churchmutual.content.setup.upgrade.util.producer;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Matthew Chan
 */
public class ProducerDashboardPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Home"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_DASHBOARD
		);

		Layout layout = LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_66_33, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);

		PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
			layout, _JOURNAL_CONTENT_PORTLET_ID, StringPool.BLANK);

		portletPreferences.setValue("groupId", String.valueOf(groupId));

		JournalArticle article = JournalArticleLocalServiceUtil.getArticleByUrlTitle(
			groupId, ProducerUpgradeConstants.PRODUCER_CONTACT_US_WEB_CONTENT_URL_TITLE);

		portletPreferences.setValue("articleId", article.getArticleId());

		portletPreferences.store();
	}

	private static final String _JOURNAL_CONTENT_PORTLET_ID = PortletIdCodec.encode(
		"com_liferay_journal_content_web_portlet_JournalContentPortlet");

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_account_producer_dashboard_web_portlet_AccountProducerDashboardWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_contact_producer_dashboard_web_portlet_ContactProducerDashboardWebPortlet",
		_JOURNAL_CONTENT_PORTLET_ID
	};

}