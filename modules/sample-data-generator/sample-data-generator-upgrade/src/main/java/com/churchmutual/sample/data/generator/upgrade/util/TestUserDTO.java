package com.churchmutual.sample.data.generator.upgrade.util;

/**
 * @author Matthew Chan
 */
public class TestUserDTO {

	public TestUserDTO(String emailAddress, String password, String cmicUserId, String cmicUUID) {
		setEmailAddress(emailAddress);
		setPassword(password);
		setCmicUserId(cmicUserId);
		setCmicUUID(cmicUUID);
	}

	public String getCmicUserId() {
		return _cmicUserId;
	}

	public String getCmicUUID() {
		return _cmicUUID;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getPassword() {
		return _password;
	}

	public void setCmicUserId(String cmicUserId) {
		_cmicUserId = cmicUserId;
	}

	public void setCmicUUID(String cmicUUID) {
		_cmicUUID = cmicUUID;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setPassword(String password) {
		_password = password;
	}

	private String _cmicUserId;
	private String _cmicUUID;
	private String _emailAddress;
	private String _password;

}