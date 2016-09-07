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
		
		results.put("version", Integer.toString((int) response[0])); 
		results.put("multi type", Integer.toString((int) response[1])); 
		results.put("msp version", Integer.toString((int) response[2])); 
		
		int capability = (response[3] & 0xff) + ((response[4] & 0xff) << 8) + ((response[5] & 0xff) << 16) + ((response[6] & 0xff) << 24); 
		
		results.put("rxbind", ((capability & 1) > 0) ? "true" :  "false");
		results.put("motors", ((capability & 4) > 0) ? "true" :  "false");
		results.put("flaps", ((capability & 8) > 0) ? "true" :  "false");
		results.put("nav", ((capability & 16) > 0) ? "true" :  "false");
		results.put("bymis", ((capability & 0x80000000) > 0) ? "true" :  "false");
		
		return results;
	}

}
