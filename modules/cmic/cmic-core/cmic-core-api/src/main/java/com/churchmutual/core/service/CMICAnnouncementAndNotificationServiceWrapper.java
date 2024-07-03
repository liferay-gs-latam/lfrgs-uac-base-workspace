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
 * Provides a wrapper for {@link CMICAnnouncementAndNotificationService}.
 *
 * @author Kayleen Lim
 * @see CMICAnnouncementAndNotificationService
 * @generated
 */
public class CMICAnnouncementAndNotificationServiceWrapper
	implements CMICAnnouncementAndNotificationService,
			   ServiceWrapper<CMICAnnouncementAndNotificationService> {

	public CMICAnnouncementAndNotificationServiceWrapper(
		CMICAnnouncementAndNotificationService
			cmicAnnouncementAndNotificationService) {

		_cmicAnnouncementAndNotificationService =
			cmicAnnouncementAndNotificationService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICAnnouncementAndNotificationServiceUtil} to access the cmic announcement and notification remote service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICAnnouncementAndNotificationServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void deleteNotification(long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAnnouncementAndNotificationService.deleteNotification(
			userNotificationEventId);
	}

	@Override
	public void dismissAnnouncement(long announcementEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAnnouncementAndNotificationService.dismissAnnouncement(
			announcementEntryId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAnnouncementDisplay>
			getAnnouncementDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAnnouncementAndNotificationService.
			getAnnouncementDisplays();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICNotificationDisplay>
			getNotificationDisplays()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicAnnouncementAndNotificationService.
			getNotificationDisplays();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicAnnouncementAndNotificationService.
			getOSGiServiceIdentifier();
	}

	@Override
	public void markAsReadAnnouncement(long announcementEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAnnouncementAndNotificationService.markAsReadAnnouncement(
			announcementEntryId);
	}

	@Override
	public void markAsReadNotification(long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicAnnouncementAndNotificationService.markAsReadNotification(
			userNotificationEventId);
	}

	@Override
	public CMICAnnouncementAndNotificationService getWrappedService() {
		return _cmicAnnouncementAndNotificationService;
	}

	@Override
	public void setWrappedService(
		CMICAnnouncementAndNotificationService
			cmicAnnouncementAndNotificationService) {

		_cmicAnnouncementAndNotificationService =
			cmicAnnouncementAndNotificationService;
	}

	private CMICAnnouncementAndNotificationService
		_cmicAnnouncementAndNotificationService;

}