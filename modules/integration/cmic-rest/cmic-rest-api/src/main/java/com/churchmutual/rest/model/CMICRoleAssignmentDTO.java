package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICRoleAssignmentDTO extends CMICObjectDTO {

	public String getEmail() {
		return _email;
	}

	public String getEmployeeId() {
		return _employeeId;
	}

	public long getId() {
		return _id;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getName() {
		return _name;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getRole() {
		return _role;
	}

	public int getVersion() {
		return _version;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setEmployeeId(String employeeId) {
		_employeeId = employeeId;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	public void setRole(String role) {
		_role = role;
	}

	public void setVersion(int version) {
		_version = version;
	}

	private String _email;
	private String _employeeId;
	private long _id;
	private String _jobTitle;
	private String _name;
	private String _phoneNumber;
	private String _role;
	private int _version;

}