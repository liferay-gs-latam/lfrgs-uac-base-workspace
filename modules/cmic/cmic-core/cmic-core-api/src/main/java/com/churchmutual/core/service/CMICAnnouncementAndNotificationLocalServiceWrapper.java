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

package com.churchmutual.core.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CMICAnnouncementAndNotificationLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICAnnouncementAndNotificationLocalService
 * @generated
 */
public class CMICAnnouncementAndNotificationLocalServiceWrapper
	implements CMICAnnouncementAndNotificationLocalService,
			   ServiceWrapper<CMICAnnouncementAndNotificationLocalService> {

	public CMICAnnouncementAndNotificationLocalServiceWrapper(
		CMICAnnouncementAndNotificationLocalService
			cmicAnnouncementAndNotificationLocalService) {

		_cmicAnnouncementAndNotificationLocalService =
			cmicAnnouncementAndNotificationLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAnnouncementAndNotificationLocalServiceUtil} to access the cmic announcement and notification local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAnnouncementAndNotificationLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void deleteNotification(long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAnnouncementAndNotificationLocalService.deleteNotification(
			userNotificationEventId);
	}

	@Override
	public void dismissAnnouncement(long announcementEntryId, long userId) {
		_cmicAnnouncementAndNotificationLocalService.dismissAnnouncement(
			announcementEntryId, userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAnnouncementDisplay>
			getAnnouncementDisplays(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAnnouncementAndNotificationLocalService.
			getAnnouncementDisplays(userId);
	}

	@Override
	public long getAnnouncementsAndNotificationsCount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAnnouncementAndNotificationLocalService.
			getAnnouncementsAndNotificationsCount(userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICNotificationDisplay>
			getNotificationDisplays(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAnnouncementAndNotificationLocalService.
			getNotificationDisplays(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicAnnouncementAndNotificationLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public void markAsReadAnnouncement(long announcementEntryId, long userId) {
		_cmicAnnouncementAndNotificationLocalService.markAsReadAnnouncement(
			announcementEntryId, userId);
	}

	@Override
	public void markAsReadNotification(long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAnnouncementAndNotificationLocalService.markAsReadNotification(
			userNotificationEventId);
	}

	@Override
	public CMICAnnouncementAndNotificationLocalService getWrappedService() {
		return _cmicAnnouncementAndNotificationLocalService;
	}

	@Override
	public void setWrappedService(
		CMICAnnouncementAndNotificationLocalService
			cmicAnnouncementAndNotificationLocalService) {

		_cmicAnnouncementAndNotificationLocalService =
			cmicAnnouncementAndNotificationLocalService;
	}

	private CMICAnnouncementAndNotificationLocalService
		_cmicAnnouncementAndNotificationLocalService;

}