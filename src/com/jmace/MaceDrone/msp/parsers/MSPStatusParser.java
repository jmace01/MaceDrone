package com.jmace.MaceDrone.msp.parsers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.jmace.MaceDrone.msp.MSPMultiType;

public class MSPStatusParser implements MSPParser {
	
	private static MSPStatusParser instance;
	
	static {
		instance = new MSPStatusParser();
	}
	
	private MSPStatusParser() {
	}
	
	public static MSPStatusParser getInstance() {
		return instance;
	}
	
	@Override
	public Map<String, Object> parser(byte[] response) {
		Map<String, Object> results = new HashMap<>();
		
		return results;
	}

}
