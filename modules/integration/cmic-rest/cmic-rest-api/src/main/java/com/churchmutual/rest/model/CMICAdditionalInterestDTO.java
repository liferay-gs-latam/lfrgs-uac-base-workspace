package com.churchmutual.rest.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Kayleen Lim
 */
public class CMICAdditionalInterestDTO extends CMICObjectDTO {

	public CMICAdditionalInterestDTO fromJSONObject(JSONObject jsonObject) {
		_addressLine1 = jsonObject.getString("addressLine1");
		_addressLine2 = jsonObject.getString("addressLine2");
		_city = jsonObject.getString("city");
		_mortgagee = jsonObject.getBoolean("mortgagee");
		_name = jsonObject.getString("name");
		_postalCode = jsonObject.getString("postalCode");
		_state = jsonObject.getString("stateOrProvinceCode");
		_type = jsonObject.getString("type");

		return this;
	}

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getCity() {
		return _city;
	}

	public String getName() {
		return _name;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public String getState() {
		return _state;
	}

	public String getType() {
		return _type;
	}

	public boolean isMortgagee() {
		if (Validator.isBlank(_addressLine1) && Validator.isBlank(_addressLine2) && Validator.isBlank(_city) &&
				Validator.isBlank(_name) && Validator.isBlank(_postalCode) && Validator.isBlank(_state) &&
				Validator.isBlank(_type)) {
			return false;
		}

		return _mortgagee;
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

	public void setMortgagee(boolean mortgagee) {
		_mortgagee = mortgagee;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _addressLine1;
	private String _addressLine2;
	private String _city;
	private boolean _mortgagee;
	private String _name;
	private String _postalCode;
	private String _state;
	private String _type;
}