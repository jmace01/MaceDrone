package com.jmace.MaceDrone.services;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.system.SystemInfo;

public class RPiInformationService {

    public static Map<String, Object> getReport() {
        try {
            Map<String, Object> report = new HashMap<>();
            report.put("temp", Float.toString(getTemperature()));
            return report;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }


    public static float getTemperature() {
        try {
            float cel = SystemInfo.getCpuTemperature();
            return (float) ((cel * 9.0) / 5.0 + 32);
        } catch (Throwable ex) {
            return 0;
        }
    }

}
