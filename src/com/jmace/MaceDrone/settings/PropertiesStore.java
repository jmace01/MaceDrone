package com.jmace.MaceDrone.settings;

public enum PropertiesStore {
	IS_RPI(System.getProperty("os.name").toLowerCase().contains("raspbian"));
	
	Object value;
	
	private PropertiesStore(Object value) {
		this.value = value;
	}
	
	public boolean getBoolean()
	{
		return (Boolean) this.value;
	}
}
