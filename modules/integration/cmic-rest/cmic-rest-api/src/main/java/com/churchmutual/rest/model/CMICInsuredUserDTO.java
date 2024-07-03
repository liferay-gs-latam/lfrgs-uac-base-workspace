package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICInsuredUserDTO extends CMICObjectDTO {

	public String getRole() {
		return _role;
	}

	public long getId() {
		return _id;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setRole(String role) {
		_role = role;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	private long _id;
	private String _companyNumber;
	private String _accountNumber;
	private String _role;

}