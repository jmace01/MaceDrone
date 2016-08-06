package com.jmace.MaceDrone.gps;

public class PiGPS {

	private static PiGPS instance;
	
	
	static
	{
		instance = new PiGPS();
	}
	
	
	private PiGPS()
	{
		//
	}
	
	
	public static PiGPS getInstance()
	{
		return instance;
	}
	
}
