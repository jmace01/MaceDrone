package com.jmace.MaceDrone.msp.parsers;

import java.util.HashMap;
import java.util.Map;

public class MSPRawGPSParser implements MSPParser {
	
	private static MSPRawGPSParser instance;
	
	static {
		instance = new MSPRawGPSParser();
	}
	
	private MSPRawGPSParser() {
	}
	
	public static MSPRawGPSParser getInstance() {
		return instance;
	}
	
	@Override
	public Map<String, Object> parser(byte[] response) {
		Map<String, Object> results = new HashMap<>();
		
		if (response.length < 16) {
			System.out.println("Invalid dataset " + new String(response));
			return results;
		}
		
		ParserHelper helper = new ParserHelper(response);
		
		results.put("fix", helper.read8BitInt());
		results.put("satellites", helper.read8BitInt());
		results.put("latitude", helper.read32BitInt() / 10000000.0);
		results.put("longitude", helper.read32BitInt() / 10000000.0);
		results.put("altitude", helper.read16BitInt() * 3.28084); //meters to feet
		results.put("speed", (helper.read16BitInt() / 160934.0) * 360.0); //CM/S to MPH
		results.put("groundcourse", helper.read16BitInt() / 10.0);
		
		return results;
	}

}
