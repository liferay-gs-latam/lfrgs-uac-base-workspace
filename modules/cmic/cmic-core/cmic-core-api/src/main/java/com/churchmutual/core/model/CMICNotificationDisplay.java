package com.churchmutual.core.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.text.SimpleDateFormat;

import java.util.Locale;

public class CMICNotificationDisplay {

	public CMICNotificationDisplay(UserNotificationEvent userNotificationEvent) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		UserNotificationFeedEntry userNotificationFeedEntry = UserNotificationManagerUtil.interpret(
			StringPool.BLANK, userNotificationEvent, serviceContext);

		SimpleDateFormat dateFormat = new SimpleDateFormat(_FORMAT_MM_DD_YYYY);

		_date = dateFormat.format(userNotificationEvent.getTimestamp());
		_isRead = userNotificationEvent.isArchived();
		_message = userNotificationFeedEntry.getBody();
		_notificationId = userNotificationEvent.getUserNotificationEventId();
	}

	public String getDate() {
		return _date;
	}

	public boolean getIsRead() {
		return _isRead;
	}

	public String getMessage() {
		return _message;
	}

	public long getNotificationId() {
		return _notificationId;
	}

	private static final String _FORMAT_MM_DD_YYYY = "MM/dd/yy";

	private String _date;
	private boolean _isRead;
	private String _message;
	private long _notificationId;

}