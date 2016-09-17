package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.msp.MultiWiiClient;
import com.jmace.MaceDrone.msp.MultiWiiClientFactory;
import com.jmace.MaceDrone.msp.MultiWiiRequest;
import com.jmace.MaceDrone.settings.Settings;
import com.pi4j.system.SystemInfo;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatusService {

	private static Timestamp lastReport;
	private static MultiWiiClient client;
	
	static {
		client = MultiWiiClientFactory.getInstance();
	}
	
	public static void setReportTimestamp() {
		StatusService.lastReport = new Timestamp(new Date().getTime());
	}
	
	public static Map<String, Object> getReport() {
		try {
			Map<String, Object> report = client.sendRequest(MultiWiiRequest.MSP_STATUS);
			report.put("temp", Float.toString(getTemperature()));
			return report;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}
	
	public static Timestamp getLastReportTime() {
		return StatusService.lastReport;
	}
	
	public static boolean reportIsStale() {
		if (StatusService.lastReport == null) {
			return true;
		}
		
		Date date = new Date();
		Timestamp cutOfftime = new Timestamp(date.getTime() - Settings.STALENESS_TIME_MILLISECONDS);
		return StatusService.lastReport.before(cutOfftime);
	}
	
	public static float getTemperature() {
		try {
			float cel = SystemInfo.getCpuTemperature();
			return (float) ((cel * 9.0) / 5.0 + 32);
		} catch (Throwable ex) {
			return 0;
		}
	}
	
	public static Map<String, Object> getGPS() {
		try {
			return client.sendRequest(MultiWiiRequest.MSP_RAW_GPS);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}
}
