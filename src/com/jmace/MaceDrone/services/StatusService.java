package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.settings.Settings;
import java.sql.Timestamp;
import java.util.Date;

public class StatusService {

	private static Timestamp lastReport;
	
	public static void setReportTimestamp() {
		StatusService.lastReport = new Timestamp(new Date().getTime());
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
	
}
