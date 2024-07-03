package com.churchmutual.rest.model;

public class CMICProductDTO extends CMICObjectDTO {

	public String getRefAbbreviation() {
		return _refAbrreviation;
	}

	public String getRefDescription() {
		return _refDescription;
	}

	public void setRefAbbreviation(String refAbbreviation) {
		_refAbrreviation = refAbbreviation;
	}

	public void setRefDescription(String refDescription) {
		_refDescription = refDescription;
	}

	private String _refAbrreviation;
	private String _refDescription;

}
