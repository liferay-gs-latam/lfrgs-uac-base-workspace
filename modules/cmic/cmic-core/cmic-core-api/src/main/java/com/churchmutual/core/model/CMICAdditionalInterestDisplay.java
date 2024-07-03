package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICAdditionalInterestDTO;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

public class CMICAdditionalInterestDisplay {

	public CMICAdditionalInterestDisplay() {
	}

	public CMICAdditionalInterestDisplay(CMICAdditionalInterestDTO cmicAdditionalInterestDTO) {
		_addressLine1 = cmicAdditionalInterestDTO.getAddressLine1();
		_addressLine2 = cmicAdditionalInterestDTO.getAddressLine2();
		_city = cmicAdditionalInterestDTO.getCity();
		_mortgagee = cmicAdditionalInterestDTO.isMortgagee();
		_name = cmicAdditionalInterestDTO.getName();
		_postalCode = cmicAdditionalInterestDTO.getPostalCode();
		_state = cmicAdditionalInterestDTO.getState();
		_type = cmicAdditionalInterestDTO.getType();
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
		return _mortgagee;
	}

	public JSONObject toJSONObject() {
		JSONObject additionalInterest = JSONFactoryUtil.createJSONObject();

		additionalInterest.put("addressLine1", _addressLine1);
		additionalInterest.put("addressLine2", _addressLine2);
		additionalInterest.put("city", _city);
		additionalInterest.put("mortgagee", _mortgagee);
		additionalInterest.put("name", _name);
		additionalInterest.put("postalCode", _postalCode);
		additionalInterest.put("stateOrProvinceCode", _state);
		additionalInterest.put("type", _type);

		return additionalInterest;
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