package com.churchmutual.rest.exception;

import com.liferay.portal.kernel.exception.PortalException;

public class UserRegistrationException extends PortalException {

	public static final int ACCOUNT_NUMBER = 2;

	public static final int PRODUCER_CODE = 0;

	public static final int REGISTRATION_CODE = 3;

	public static final int ZIP_CODE = 1;

	public UserRegistrationException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private int _type;

}