package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICProducerUserDTO extends CMICObjectDTO {
	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public String getRole() {
		return _role;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setRole(String role) {
		_role = role;
	}

	public void setDivisionNumber(String divisionNumber) {
		_divisionNumber = divisionNumber;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public long getProducerId() {
		return _producerId;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	private long _id;
	private long _producerId;
	private String _divisionNumber;
	private String _agentNumber;
	private String _role;

}