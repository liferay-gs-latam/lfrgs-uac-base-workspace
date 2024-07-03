package com.churchmutual.core.model;

import com.churchmutual.commons.util.URLBuilderUtil;
import com.churchmutual.core.constants.ProducerType;
import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;

public class CMICOrganizationDisplay extends CMICBusinessDisplay {

	public CMICOrganizationDisplay(CMICProducerDTO cmicProducerDTO, CMICContactDTO cmicContactDTO) {
		_active = "Active".equalsIgnoreCase(cmicProducerDTO.getStatus());
		_agentNumber = cmicProducerDTO.getAgent();
		_divisionNumber = cmicProducerDTO.getDivision();
		_name = cmicProducerDTO.getName();
		_producerId = cmicProducerDTO.getId();
		_producerType = ProducerType.getTypeFromName(cmicProducerDTO.getProducerType());

		_addressLine1 = cmicContactDTO.getAddressLine1();
		_addressLine2 = cmicContactDTO.getAddressLine2();
		_city = cmicContactDTO.getCity();
		_state = cmicContactDTO.getState();
		_postalCode = cmicContactDTO.getPostalCode();

		_phoneNumber = cmicContactDTO.getPhoneNumber();
		_phoneNumberURL = URLBuilderUtil.formatPhoneNumberURL(cmicContactDTO.getPhoneNumber());
	}

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getCity() {
		return _city;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public String getName() {
		return _name;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getPhoneNumberURL() {
		return _phoneNumberURL;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public long getProducerId() {
		return _producerId;
	}

	public ProducerType getProducerType() {
		return _producerType;
	}

	public String getState() {
		return _state;
	}

	public boolean isActive() {
		return _active;
	}

	private boolean _active;
	private String _addressLine1;
	private String _addressLine2;
	private String _agentNumber;
	private String _city;
	private String _divisionNumber;
	private String _name;
	private String _phoneNumber;
	private String _phoneNumberURL;
	private String _postalCode;
	private long _producerId;
	private ProducerType _producerType;
	private String _state;

}