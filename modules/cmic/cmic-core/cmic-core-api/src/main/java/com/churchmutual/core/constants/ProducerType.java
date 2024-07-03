package com.churchmutual.core.constants;

public enum ProducerType {

	AGENCY("Agency", 1), BROKER("Broker", 2), DIRECT("Direct", 3);

	public static ProducerType getTypeFromName(String name) {
		for (ProducerType producerType : values()) {
			if (producerType._name.equals(name)) {
				return producerType;
			}
		}

		return null;
	}

	private ProducerType(String name, int type) {
		_name = name;
		_type = type;
	}

	private String _name;
	private int _type;

}