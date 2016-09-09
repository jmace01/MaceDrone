package com.jmace.MaceDrone.msp.parsers;

import java.util.HashMap;
import java.util.Map;

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
		
		if (response.length < 11) {
			System.out.println("Invalid dataset " + new String(response));
			return results;
		}
		
		ParserHelper helper = new ParserHelper(response);
		
		results.put("cycletime", helper.read16BitInt());
		results.put("i2cerror", helper.read16BitInt());
		int sensor = helper.read16BitInt();
		results.put("accActive",helper.bitIsSet(sensor, 1));
		results.put("baroActive",helper.bitIsSet(sensor, 2));
		results.put("magActive",helper.bitIsSet(sensor, 3));
		results.put("GPSActive",helper.bitIsSet(sensor, 4));
		results.put("sonarActive",helper.bitIsSet(sensor, 5));
		results.put("mode", helper.read32BitInt());
		results.put("conf", helper.read8BitInt());
		
		return results;
	}

}
