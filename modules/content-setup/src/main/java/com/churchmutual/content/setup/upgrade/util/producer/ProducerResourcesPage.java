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

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;

/**
 * @author Matthew Chan
 */
public class ProducerResourcesPage extends BaseSitePage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Resources"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_PRODUCER_RESOURCES
		).addPortletKey(
			_ASSET_PUBLISHER_PORTLET_ID
		);

		Layout layout = LayoutHelper.addLayoutWithPortlet(userId, groupId, layoutConfig);

		setAssetPublisher(
			companyId, layout.getPlid(), _ASSET_PUBLISHER_PORTLET_ID,
			ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT_FILE_TYPE,
			ProducerUpgradeConstants.CMIC_RESOURCE_FILES_TEMPLATE_KEY, StringPool.BLANK, StringPool.BLANK);
	}

	private static final String _ASSET_PUBLISHER_PORTLET_ID = PortletIdCodec.encode(
		AssetPublisherPortletKeys.ASSET_PUBLISHER);

}