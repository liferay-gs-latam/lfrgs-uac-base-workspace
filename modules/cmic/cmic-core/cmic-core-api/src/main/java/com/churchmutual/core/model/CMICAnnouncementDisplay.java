package com.churchmutual.core.model;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;

import java.text.SimpleDateFormat;

public class CMICAnnouncementDisplay {

	public CMICAnnouncementDisplay(AnnouncementsEntry announcementsEntry, boolean isMarkedAsRead) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(_FORMAT_MM_DD_YYYY);

		_announcementId = announcementsEntry.getEntryId();
		_date = dateFormat.format(announcementsEntry.getDisplayDate());
		_isImportant = announcementsEntry.isAlert() || announcementsEntry.getPriority() > 0;
		_isRead = isMarkedAsRead;
		_message = announcementsEntry.getContent();
		_url = announcementsEntry.getUrl();
		_title = announcementsEntry.getTitle();
	}

	public long getAnnouncementId() {
		return _announcementId;
	}

	public String getDate() {
		return _date;
	}

	public boolean getIsImportant() {
		return _isImportant;
	}

	public boolean getIsRead() {
		return _isRead;
	}

	public String getMessage() {
		return _message;
	}

	public String getTitle() {
		return _title;
	}

	public String getURL() {
		return _url;
	}

	private static final String _FORMAT_MM_DD_YYYY = "MM/dd/yy";

	private long _announcementId;
	private String _date;
	private boolean _isImportant;
	private boolean _isRead;
	private String _message;
	private String _url;
	private String _title;

}
