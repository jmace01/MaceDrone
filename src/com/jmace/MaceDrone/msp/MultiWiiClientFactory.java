package com.jmace.MaceDrone.msp;

public class MultiWiiClientFactory {

	private static final String SERIAL_PORT = "/dev/ttyUSB0";
	private static final MultiWiiClient instance;

	static {
		instance = new MultiWiiClient(SERIAL_PORT);
	}
	
	public static MultiWiiClient getInstance() {
		return instance;
	}
}
