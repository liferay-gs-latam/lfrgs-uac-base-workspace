package com.churchmutual.commons.enums;

public enum BusinessUserStatus implements DisplayEnum {

	ACTIVE("Active", "active"), INVITED("Invited", "invited");

	BusinessUserStatus(String statusName, String messageKey) {
		_messageKey = messageKey;
		_statusName = statusName;
	}

	public static BusinessUserStatus[] businessUserStatuses() { return _businessUserStatuses; }

	@Override
	public String getMessageKey() {
		return _messageKey;
	}

	public String getUserStatusName() {
		return _statusName;
	}

	private static BusinessUserStatus[] _businessUserStatuses = {ACTIVE, INVITED};

	private String _messageKey;
	private String _statusName;
}