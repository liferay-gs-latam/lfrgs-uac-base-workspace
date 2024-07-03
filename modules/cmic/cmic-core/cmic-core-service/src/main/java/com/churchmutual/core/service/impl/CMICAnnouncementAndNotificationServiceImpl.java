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

package com.churchmutual.core.service.impl;

import com.churchmutual.core.model.CMICAnnouncementDisplay;
import com.churchmutual.core.model.CMICNotificationDisplay;
import com.churchmutual.core.service.base.CMICAnnouncementAndNotificationServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic announcement and notification remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICAnnouncementAndNotificationService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAnnouncementAndNotificationServiceBaseImpl
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICAnnouncementAndNotification"},
	service = AopService.class
)
public class CMICAnnouncementAndNotificationServiceImpl extends CMICAnnouncementAndNotificationServiceBaseImpl {

	@Override
	public void deleteNotification(long userNotificationEventId) throws PortalException {
		UserNotificationEvent userNotificationEvent = userNotificationEventLocalService.getUserNotificationEvent(
			userNotificationEventId);

		long userId = getUserId();

		if (userId != userNotificationEvent.getUserId()) {
			throw new PrincipalException(
				"Error while deleting notification: User " + userId +
					" cannot change notification because they were not found as its recipient");
		}

		cmicAnnouncementAndNotificationLocalService.deleteNotification(userNotificationEventId);
	}

	@Override
	public void dismissAnnouncement(long announcementEntryId) throws PortalException {
		cmicAnnouncementAndNotificationLocalService.dismissAnnouncement(announcementEntryId, getUserId());
	}

	@Override
	public List<CMICAnnouncementDisplay> getAnnouncementDisplays() throws PortalException {
		return cmicAnnouncementAndNotificationLocalService.getAnnouncementDisplays(getUserId());
	}

	@Override
	public List<CMICNotificationDisplay> getNotificationDisplays() throws PortalException {
		return cmicAnnouncementAndNotificationLocalService.getNotificationDisplays(getUserId());
	}

	@Override
	public void markAsReadAnnouncement(long announcementEntryId) throws PortalException {
		cmicAnnouncementAndNotificationLocalService.markAsReadAnnouncement(announcementEntryId, getUserId());
	}

	@Override
	public void markAsReadNotification(long userNotificationEventId) throws PortalException {
		UserNotificationEvent userNotificationEvent = userNotificationEventLocalService.getUserNotificationEvent(
			userNotificationEventId);

		long userId = getUserId();

		if (userId != userNotificationEvent.getUserId()) {
			throw new PrincipalException(
				"Error while marking notification as read: User " + userId +
					" cannot change notification because they were not found as its recipient");
		}

		cmicAnnouncementAndNotificationLocalService.markAsReadNotification(userNotificationEventId);
	}

	@Reference
	protected UserNotificationEventLocalService userNotificationEventLocalService;

}