package com.jmace.MaceDrone.msp.parsers;

import java.util.HashMap;
import java.util.Map;

public class MSPIdentParser implements MSPParser {
	
	private static MSPIdentParser instance;
	
	static {
		instance = new MSPIdentParser();
	}
	
	private MSPIdentParser() {
	}
	
	public static MSPIdentParser getInstance() {
		return instance;
	}
	
	@Override
	public Map<String, String> parser(String response) {
		Map<String, String> results = new HashMap<>();
		char resp[] = response.toCharArray();
		
		results.put("version", Integer.toString((int) resp[4])); 
		results.put("multi type", Integer.toString((int) resp[5])); 
		results.put("msp version", Integer.toString((int) resp[6])); 
		
		int capability = (resp[7] & 0xff) + ((resp[8] & 0xff) << 8) + ((resp[9] & 0xff) << 16) + ((resp[10] & 0xff) << 24); 
		
		results.put("rxbind", ((capability & 1) > 0) ? "true" :  "false");
		results.put("motors", ((capability & 4) > 0) ? "true" :  "false");
		results.put("flaps", ((capability & 8) > 0) ? "true" :  "false");
		results.put("nav", ((capability & 16) > 0) ? "true" :  "false");
		results.put("bymis", ((capability & 0x80000000) > 0) ? "true" :  "false");
		
		return results;
	}

}
