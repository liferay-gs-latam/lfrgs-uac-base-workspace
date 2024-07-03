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

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseSitePage;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;

/**
 * @author Matthew Chan
 */
public class ProducerContactPage extends BaseSitePage {

	public static void addPage(long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Contact Us"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_CONTACT
		).addPortletsKeys(
			_PORTLETS_COLUMN_1
		);

		Layout layout = LayoutHelper.addLayoutWithPortlets(userId, groupId, layoutConfig);

		setJournalArticle(
			groupId, layout.getPlid(), _JOURNAL_CONTENT_PORTLET_ID,
			ProducerUpgradeConstants.PRODUCER_CONTACT_US_WEB_CONTENT_TITLE);
	}

	private static final String _JOURNAL_CONTENT_PORTLET_ID = PortletIdCodec.encode(
		"com_liferay_journal_content_web_portlet_JournalContentPortlet");

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_contact_producer_list_web_portlet_ContactProducerListWebPortlet", _JOURNAL_CONTENT_PORTLET_ID
	};

}