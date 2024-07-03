package com.churchmutual.rest.model;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Kayleen Lim
 */
public class CMICBuildingDTO extends CMICObjectDTO {

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getBuildingNumber() {
		return _buildingNumber;
	}

	public String getCity() {
		return _city;
	}

	public String getCounty() {
		return _county;
	}

	public String getDescription() {
		return _description;
	}

	public String getLocationPremisesNumber() {
		return _locationPremisesNumber;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public String getPostalCodeExtension() {
		return _postalCodeExtension;
	}

	public String getState() {
		return _state;
	}

	public void setAddressLine1(String addressLine1) {
		_addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		_addressLine2 = addressLine2;
	}

	public void setBuildingNumber(String buildingNumber) {
		_buildingNumber = buildingNumber;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setCounty(String county) {
		_county = county;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setLocationPremisesNumber(String locationPremisesNumber) {
		_locationPremisesNumber = locationPremisesNumber;
	}

	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}

	public void setPostalCodeExtension(String postalCodeExtension) {
		_postalCodeExtension = postalCodeExtension;
	}

	public void setState(String state) {
		_state = state;
	}

	public JSONObject toJSONObject() {
		JSONObject building = JSONFactoryUtil.createJSONObject();

		building.put("addressLine1", _addressLine1);
		building.put("addressLine2", _addressLine2);
		building.put("buildingNumber", _buildingNumber);
		building.put("city", _city);
		building.put("county", _county);
		building.put("description", _description);
		building.put("locationPremisesNumber", _locationPremisesNumber);
		building.put("postalCode", _postalCode);
		building.put("postalCodeExtension", _postalCodeExtension);
		building.put("stateOrProvinceCode", _state);

		return building;
	}

	private String _addressLine1;
	private String _addressLine2;
	private String _buildingNumber;
	private String _city;
	private String _county;
	private String _description;
	private String _locationPremisesNumber;
	private String _postalCode;
	private String _postalCodeExtension;
	private String _state;

}