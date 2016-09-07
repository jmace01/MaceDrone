package com.jmace.MaceDrone.msp.parsers;

import java.math.BigInteger;
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
	public Map<String, String> parser(byte[] response) {
		Map<String, String> results = new HashMap<>();
		
		if (response.length < 7) {
			System.out.println("Invalid dataset " + new String(response));
			return results;
		}
		
		results.put("hex", String.format("%040x", new BigInteger(1, response)));
		
		results.put("version", Double.toString((response[0] & 0xFF) / 100.0)); 
		results.put("multi type", Integer.toString(response[1] & 0xFF)); 
		results.put("msp version", Integer.toString(response[2] & 0xFF)); 
		
		int capability = (response[3] & 0xFF) | ((response[4] & 0xFF) << 8) | ((response[5] & 0xFF) << 16) | ((response[6] & 0xFF) << 24); 
		
		results.put("rxbind", ((capability & 1) > 0) ? "true" :  "false");
		results.put("motors", ((capability & 4) > 0) ? "true" :  "false");
		results.put("flaps", ((capability & 8) > 0) ? "true" :  "false");
		results.put("nav", ((capability & 16) > 0) ? "true" :  "false");
		results.put("bymis", ((capability & 0x80000000) > 0) ? "true" :  "false");
		
		return results;
	}

}
