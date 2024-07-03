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

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Matthew Chan
 */
public class ProfileLinks {

	public static void addLinks(long companyId, long userId, long groupId) throws Exception {
		for (String profileLink : _PROFILE_LINKS) {
			LayoutConfig layoutConfig = new LayoutConfig().setName(
				profileLink
			).setFriendlyURL(
				_getFriendlyURL(profileLink)
			).setPrivatePage(
				true
			).setHiddenPage(
				true
			);

			LayoutHelper.addLayoutWithURL(userId, groupId, layoutConfig, _getURLFromPortalProperty(profileLink));
		}
	}

	private static String _getFriendlyURL(String profileLink) {
		profileLink = StringUtil.toLowerCase(profileLink);
		profileLink = StringUtil.replace(profileLink, CharPool.SPACE, CharPool.DASH);

		return StringPool.SLASH + profileLink;
	}

	private static String _getURLFromPortalProperty(String profileLink) {
		profileLink = StringUtil.toLowerCase(profileLink);
		profileLink = StringUtil.replace(profileLink, CharPool.SPACE, CharPool.PERIOD);
		profileLink = StringUtil.replace(profileLink, "b2c", "b2c.url");

		return PropsUtil.get(profileLink);
	}

	private static final String[] _PROFILE_LINKS = {
		"B2C Password Reset", "B2C Edit Profile", "B2C Edit MFA", "B2C Edit Email", "B2C Edit"
	};

}