package com.churchmutual.core.constants;

public enum CommissionDocumentType {

	BROKER_STMT("Broker Stmt", "Broker Statement", "BS"), REMITT_STMT("Remitt Stmt", "Commission Remittance Statement", "RS");

	public static CommissionDocumentType fromCmicName(String cmicName) {
		for (CommissionDocumentType commissionDocumentType : values()) {
			if (commissionDocumentType._cmicName.equals(cmicName)) {
				return commissionDocumentType;
			}
		}

		return null;
	}

	public String getAbbreviation() {
		return _abbreviation;
	}

	public String getName() {
		return _name;
	}

	private CommissionDocumentType(String cmicName, String name, String abbreviation) {
		_abbreviation = abbreviation;
		_cmicName = cmicName;
		_name = name;
	}

	private String _abbreviation;
	private String _cmicName;
	private String _name;

}