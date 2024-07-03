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
import com.churchmutual.core.service.base.CMICAnnouncementAndNotificationLocalServiceBaseImpl;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.announcements.kernel.util.AnnouncementsUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic announcement and notification local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICAnnouncementAndNotificationLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAnnouncementAndNotificationLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.churchmutual.core.model.CMICAnnouncementAndNotification",
	service = AopService.class
)
public class CMICAnnouncementAndNotificationLocalServiceImpl
	extends CMICAnnouncementAndNotificationLocalServiceBaseImpl {

	@Override
	public void deleteNotification(long userNotificationEventId) throws PortalException {
		userNotificationEventLocalService.deleteUserNotificationEvent(userNotificationEventId);
	}

	@Override
	public void dismissAnnouncement(long announcementEntryId, long userId) {
		if (!_hasAnnouncementsFlag(userId, announcementEntryId, AnnouncementsFlagConstants.HIDDEN)) {
			announcementsFlagLocalService.addFlag(userId, announcementEntryId, AnnouncementsFlagConstants.HIDDEN);
		}
	}

	@Override
	public long getAnnouncementsAndNotificationsCount(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		boolean alert = false;

		LinkedHashMap<Long, long[]> scopes = AnnouncementsUtil.getAnnouncementScopes(user);

		List<AnnouncementsEntry> announcementEntries = announcementsEntryLocalService.getEntries(
			userId, scopes, alert, AnnouncementsFlagConstants.NOT_HIDDEN, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		long announcementsCount = announcementEntries.stream(
		).filter(
			announcementsEntry -> !_hasAnnouncementsFlag(
				userId, announcementsEntry.getEntryId(), AnnouncementsFlagConstants.READ)
		).count();

		int userNotificationEventCount = userNotificationEventLocalService.getUserNotificationEventsCount(
			userId, UserNotificationDeliveryConstants.TYPE_WEBSITE, true, false);

		return announcementsCount + userNotificationEventCount;
	}

	@Override
	public List<CMICAnnouncementDisplay> getAnnouncementDisplays(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		boolean alert = false;

		LinkedHashMap<Long, long[]> scopes = AnnouncementsUtil.getAnnouncementScopes(user);

		List<AnnouncementsEntry> announcementEntries = announcementsEntryLocalService.getEntries(
			userId, scopes, alert, AnnouncementsFlagConstants.NOT_HIDDEN, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<CMICAnnouncementDisplay> cmicAnnouncementDisplays = new ArrayList<>();

		for (AnnouncementsEntry announcementsEntry : announcementEntries) {
			boolean isMarkedAsRead = _hasAnnouncementsFlag(
				userId, announcementsEntry.getEntryId(), AnnouncementsFlagConstants.READ);

			cmicAnnouncementDisplays.add(new CMICAnnouncementDisplay(announcementsEntry, isMarkedAsRead));
		}

		return cmicAnnouncementDisplays;
	}

	@Override
	public List<CMICNotificationDisplay> getNotificationDisplays(long userId) throws PortalException {
		List<UserNotificationEvent> userNotificationEvents =
			userNotificationEventLocalService.getUserNotificationEvents(
				userId, UserNotificationDeliveryConstants.TYPE_WEBSITE);

		List<CMICNotificationDisplay> cmicNotificationDisplays = new ArrayList<>();

		for (UserNotificationEvent userNotificationEvent : userNotificationEvents) {
			cmicNotificationDisplays.add(new CMICNotificationDisplay(userNotificationEvent));
		}

		return cmicNotificationDisplays;
	}

	@Override
	public void markAsReadAnnouncement(long announcementEntryId, long userId) {
		if (!_hasAnnouncementsFlag(userId, announcementEntryId, AnnouncementsFlagConstants.READ)) {
			announcementsFlagLocalService.addFlag(userId, announcementEntryId, AnnouncementsFlagConstants.READ);
		}
	}

	@Override
	public void markAsReadNotification(long userNotificationEventId) throws PortalException {
		UserNotificationEvent userNotificationEvent = userNotificationEventLocalService.getUserNotificationEvent(
			userNotificationEventId);

		if (!userNotificationEvent.isArchived()) {
			userNotificationEvent.setArchived(true);

			userNotificationEventLocalService.updateUserNotificationEvent(userNotificationEvent);
		}
	}

	@Reference
	protected AnnouncementsEntryLocalService announcementsEntryLocalService;

	@Reference
	protected AnnouncementsFlagLocalService announcementsFlagLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected UserNotificationEventLocalService userNotificationEventLocalService;

	private boolean _hasAnnouncementsFlag(long userId, long announcementsEntryId, int value) {
		try {
			announcementsFlagLocalService.getFlag(userId, announcementsEntryId, value);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

}