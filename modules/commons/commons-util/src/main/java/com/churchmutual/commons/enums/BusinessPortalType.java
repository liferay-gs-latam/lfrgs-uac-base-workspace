package com.churchmutual.commons.enums;

public enum BusinessPortalType {

	PRODUCER("Producer Portal", "producer", "/producer"), INSURED("Insured Portal", "insured", "/insured");

	public String getFriendlyURL() {
		return friendlyURL;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public String getName() {
		return name;
	}

	private BusinessPortalType(String name, String groupKey, String friendlyURL) {
		this.name = name;
		this.groupKey = groupKey;
		this.friendlyURL = friendlyURL;
	}

	private String friendlyURL;
	private String groupKey;
	private String name;

}