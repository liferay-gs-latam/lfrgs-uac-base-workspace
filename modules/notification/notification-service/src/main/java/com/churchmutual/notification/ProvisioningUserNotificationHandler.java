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

package com.churchmutual.notification;

import com.churchmutual.notification.constants.NotificationConstants;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + NotificationConstants.NOTIFICATION_PORTLET,
	service = UserNotificationHandler.class
)
public class ProvisioningUserNotificationHandler extends BaseModelUserNotificationHandler {

	public ProvisioningUserNotificationHandler() {
		setPortletId(NotificationConstants.NOTIFICATION_PORTLET);
	}

	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		String message = jsonObject.getString("message");

		return StringUtil.replace(_BODY_TEMPLATE, _BODY_REPLACEMENTS, new String[] {message});
	}

	private static final String[] _BODY_REPLACEMENTS = {"[$BODY$]"};

	private static final String _BODY_TEMPLATE = "<div class=\"body\">[$BODY$]</div>";

}