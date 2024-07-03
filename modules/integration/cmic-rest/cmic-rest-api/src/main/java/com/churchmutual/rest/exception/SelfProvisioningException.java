package com.churchmutual.rest.exception;

import com.liferay.portal.kernel.exception.PortalException;

public class SelfProvisioningException extends PortalException {

	public static final int DUPLICATE_USER = 0;

	public SelfProvisioningException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private int _type;

}
