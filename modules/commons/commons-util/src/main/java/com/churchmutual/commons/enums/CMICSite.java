package com.churchmutual.commons.enums;

public enum CMICSite {
	INSURED("Insured", "/insured"),
	PRODUCER("Producer", "/producer"),
	REGISTRATION("Registration", "/registration");

	private CMICSite(String name, String friendlyURL) {
		_friendlyURL = friendlyURL;
		_name = name;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public String getName() {
		return _name;
	}

	private String _friendlyURL;
	private String _name;

}
