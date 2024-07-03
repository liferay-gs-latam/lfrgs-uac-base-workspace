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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Matthew Chan
 */
public class ProducerAccountDetailsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		long parentLayoutId = 0;

		Layout applicationListLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			groupId, true, LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_ACCOUNTS);

		if (applicationListLayout != null) {
			parentLayoutId = applicationListLayout.getLayoutId();
		}

		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Account Details"
		).setParentLayoutId(
			parentLayoutId
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_ACCOUNT_DETAILS
		);

		LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_75_25, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);
	}

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_policy_list_web_portlet_PolicyListWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {};

}