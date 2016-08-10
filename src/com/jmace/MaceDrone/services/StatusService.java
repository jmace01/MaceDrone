package com.jmace.MaceDrone.services;

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
		Timestamp threeSecondsAgo = new Timestamp(date.getTime() - 3000);
		return StatusService.lastReport.before(threeSecondsAgo);
	}
	
}
