package com.churchmutual.core.constants;

public enum PolicyType {

	COMMERCIAL_PACKAGE_POLICY_SPECIAL_MULTI_PERIL("COMMERCIAL PACKAGE POLICY - Special Multiperil", 1),
	WORKERS_COMPENSATION("WORKER'S COMPENSATION", 2), COMMERCIAL_AUTOMOBILE("COMMERCIAL AUTOMOBILE", 3),
	UMBRELLA_81("UMBRELLA 81", 4), UMBRELLA_85("UMBRELLA 85", 5);

	public static int getTypeFromName(String name) {
		for (PolicyType policyType : values()) {
			if (policyType._name.equals(name)) {
				return policyType._type;
			}
		}

		return 0;
	}

	private PolicyType(String name, int type) {
		_name = name;
		_type = type;
	}

	private String _name;
	private int _type;

}