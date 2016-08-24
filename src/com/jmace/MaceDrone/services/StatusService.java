package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.settings.Settings;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;
import com.pi4j.util.ExecUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
			String result[] = execute("/opt/vc/bin/vcgencmd measure_temp", null);
			System.out.println(result);
			System.out.println("---------\n--------\n");
	        if(result != null && result.length > 0){
	            for(String line : result) {
	                String parts[] = line.split("[=']", 3);
	                return Float.parseFloat(parts[1]);
	            }
	        }
			float cel = SystemInfo.getCpuTemperature();
			return cel * (9/5) + 32;
		} catch (Throwable ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	
	
	
	
	
	
	public static String[] execute(String command, String split) throws IOException, InterruptedException {
        List<String> result = new ArrayList<>();

        // create external process
        Process p = Runtime.getRuntime().exec(command);

        // wait for external process to complete
        p.waitFor();

        // if the external proess returns an error code (non-zero), then build out and return null
        if(p.exitValue() != 0) {
        	System.out.println("!!!!!!!!!!!");
        	System.out.println(p.exitValue());
            p.destroy();
            return null;
        }

        // using try-with-resources to ensure closure
        try(InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader reader = new BufferedReader(isr)) {
            // read lines from buffered reader
            String line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    if (split == null || split.isEmpty()) {
                        result.add(line.trim());
                    } else {
                        String[] parts = line.trim().split(split);
                        for(String part : parts) {
                            if (part != null && !part.isEmpty()) {
                                result.add(part.trim());
                            }
                        }
                    }
                }

                // read next line
                line = reader.readLine();
            }
        }

        // destroy process
        p.destroy();

        System.out.println(result + "++++++++++++++++");
        
        // return result
        if (result.size() > 0) {
            return result.toArray(new String[result.size()]);
        } else {
            return new String[0];
        }
    }
	
}
