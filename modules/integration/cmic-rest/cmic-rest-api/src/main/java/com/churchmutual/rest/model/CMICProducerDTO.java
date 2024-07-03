package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICProducerDTO extends CMICObjectDTO {

	public String getAgent() {
		return _agent;
	}

	public String getDivision() {
		return _division;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getProducerType() {
		return _producerType;
	}

	public String getStatus() {
		return _status;
	}

	public void setAgent(String agent) {
		_agent = agent;
	}

	public void setDivision(String division) {
		_division = division;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setProducerType(String producerType) {
		_producerType = producerType;
	}

	public void setStatus(String status) {
		_status = status;
	}

	private String _agent;
	private String _division;
	private long _id;
	private String _name;
	private String _producerType;
	private String _status;

}