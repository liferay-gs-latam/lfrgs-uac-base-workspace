package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICBuildingDTO;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

public class CMICBuildingDisplay {

	public CMICBuildingDisplay() {
	}

	public CMICBuildingDisplay(CMICBuildingDTO cmicBuildingDTO) {
		_addressLine1 = cmicBuildingDTO.getAddressLine1();
		_addressLine2 = cmicBuildingDTO.getAddressLine2();
		_buildingNumber = cmicBuildingDTO.getBuildingNumber();
		_city = cmicBuildingDTO.getCity();
		_county = cmicBuildingDTO.getCounty();
		_description = cmicBuildingDTO.getDescription();
		_locationPremisesNumber = cmicBuildingDTO.getLocationPremisesNumber();
		_postalCode = cmicBuildingDTO.getPostalCode();
		_postalCodeExtension = cmicBuildingDTO.getPostalCodeExtension();
		_state = cmicBuildingDTO.getState();
	}

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
