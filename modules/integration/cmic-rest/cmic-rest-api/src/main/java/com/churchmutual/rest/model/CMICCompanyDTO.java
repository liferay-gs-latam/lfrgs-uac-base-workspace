package com.churchmutual.rest.model;

public class CMICCompanyDTO extends CMICObjectDTO {

	public String getRefAbbreviation() {
		return _refAbrreviation;
	}

	public long getRefCompanyId() {
		return _refCompanyId;
	}

	public String getRefDescription() {
		return _refDescription;
	}

	public void setRefAbbreviation(String refAbbreviation) {
		_refAbrreviation = refAbbreviation;
	}

	public void setRefCompanyId(long refCompanyId) {
		_refCompanyId = refCompanyId;
	}

	public void setRefDescription(String refDescription) {
		_refDescription = refDescription;
	}

	private String _refAbrreviation;
	private long _refCompanyId;
	private String _refDescription;

}
