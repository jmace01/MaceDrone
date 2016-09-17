package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.msp.MultiWiiClient;
import com.jmace.MaceDrone.msp.MultiWiiClientFactory;

public class InformationService {

	private static MultiWiiClient client;
	
	public static void setReportTimestamp() {
		client = MultiWiiClientFactory.getInstance();
	}
	
}
