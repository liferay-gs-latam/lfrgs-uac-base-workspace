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

package com.churchmutual.content.setup.upgrade.util.registration;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseSitePage;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;

/**
 * @author Kayleen Lim
 */
public class RegistrationDeactivatedPage extends BaseSitePage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Deactivated"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_REGISTRATION_DEACTIVATED
		).addPortletKey(
			_JOURNAL_CONTENT_PORTLET_ID
		).setPrivatePage(
			true
		).setHiddenPage(
			true
		);

		Layout layout = LayoutHelper.addLayoutWithPortlet(userId, groupId, layoutConfig);

		setJournalArticle(
			groupId, layout.getPlid(), _JOURNAL_CONTENT_PORTLET_ID,
			ProducerUpgradeConstants.DEACTIVATED_WEB_CONTENT_TITLE);
	}

	private static final String _JOURNAL_CONTENT_PORTLET_ID = PortletIdCodec.encode(
		"com_liferay_journal_content_web_portlet_JournalContentPortlet");

}