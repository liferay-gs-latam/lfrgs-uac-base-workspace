package com.churchmutual.commons.enums;

/**
 * @author Kayleen Lim
 */
public enum CMICRole {

	ADMINISTRATOR_READ_ONLY("Administrator (Read-Only)"),
	CONTENT_MANAGER("Content Manager");

	public String getRoleName() {
		return _roleName;
	}

	private CMICRole(String roleName) {
		_roleName = roleName;
	}

	private String _roleName;

}