package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICContactDTO extends CMICObjectDTO {

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getCity() {
		return _city;
	}

	public String getCountry() {
		return _country;
	}

	public String getDepartment() {
		return _department;
	}

	public String getEmail() {
		return _email;
	}

	public String getFirstName() {
		return _firstName;
	}

	public long getId() {
		return _id;
	}

	public String getLastName() {
		return _lastName;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public String getState() {
		return _state;
	}

	public String getSuffix() {
		return _suffix;
	}

	public void setAddressLine1(String addressLine1) {
		_addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		_addressLine2 = addressLine2;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setCountry(String country) {
		_country = country;
	}

	public void setDepartment(String department) {
		_department = department;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	private String _addressLine1;
	private String _addressLine2;
	private String _city;
	private String _country;
	private String _department;
	private String _email;
	private String _firstName;
	private long _id;
	private String _lastName;
	private String _phoneNumber;
	private String _postalCode;
	private String _state;
	private String _suffix;

}