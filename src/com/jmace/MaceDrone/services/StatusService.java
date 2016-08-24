package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.settings.Settings;
import com.pi4j.system.SystemInfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatusService {

	private static Timestamp lastReport;
	
	public static void setReportTimestamp() {
		StatusService.lastReport = new Timestamp(new Date().getTime());
	}
	
	public static Map<String, String> getReport() {
		Map<String, String> report = new HashMap<>();
		report.put("temp", Float.toString(getTemperature()));
		return report;
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
			return cel * (9/5) + 32;
		} catch (Throwable ex) {
			return 0;
		}
	}	
}
