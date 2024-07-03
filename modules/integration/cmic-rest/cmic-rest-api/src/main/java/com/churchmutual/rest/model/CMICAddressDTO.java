package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICAddressDTO extends CMICObjectDTO {

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getAddressLine3() {
		return _addressLine3;
	}

	public String getCity() {
		return _city;
	}

	public String getState() {
		return _state;
	}

	public String getStreet() {
		return _street;
	}

	public String getStreetName() {
		return _streetName;
	}

	public String getZipCode() {
		return _zipCode;
	}

	public void setAddressLine1(String addressLine1) {
		_addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		_addressLine2 = addressLine2;
	}

	public void setAddressLine3(String addressLine3) {
		_addressLine3 = addressLine3;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStreet(String street) {
		_street = street;
	}

	public void setStreetName(String streetName) {
		_streetName = streetName;
	}

	public void setZipCode(String zipCode) {
		_zipCode = zipCode;
	}

	private String _addressLine1;
	private String _addressLine2;
	private String _addressLine3;
	private String _city;
	private String _state;
	private String _street;
	private String _streetName;
	private String _zipCode;

}