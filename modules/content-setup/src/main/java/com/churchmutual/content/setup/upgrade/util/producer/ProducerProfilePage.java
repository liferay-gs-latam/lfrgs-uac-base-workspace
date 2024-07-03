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

/**
 * @author Matthew Chan
 */
public class ProducerProfilePage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Profile"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_PROFILE
		).setHiddenPage(
			true
		);

		LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_25_75, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);
	}

	private static final String[] _PORTLETS_COLUMN_1 = {"com_churchmutual_profile_web_portlet_ProfileWebPortlet"};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_organization_profile_web_portlet_OrganizationProfileWebPortlet",
		"com_churchmutual_self_provisioning_web_portlet_SelfProvisioningWebPortlet"
	};

}