package com.jmace.MaceDrone.msp.parsers;

import java.util.HashMap;
import java.util.Map;

public class MSPEmptyParser implements MSPParser {

	private static MSPEmptyParser instance;
	
	static {
		instance = new MSPEmptyParser();
	}
	
	private MSPEmptyParser() {
	}
	
	public static MSPEmptyParser getInstance() {
		return instance;
	}
	
	@Override
	public Map<String, Object> parser(byte[] reponse) {
		return new HashMap<>();
	}

}
