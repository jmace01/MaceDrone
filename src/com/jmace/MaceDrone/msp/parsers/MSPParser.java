package com.jmace.MaceDrone.msp.parsers;

import java.util.Map;

public interface MSPParser {
	
	public Map<String, String> parser(byte[] response);
	
}
