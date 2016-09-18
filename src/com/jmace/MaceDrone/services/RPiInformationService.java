package com.jmace.MaceDrone.services;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.system.SystemInfo;

public class RPiInformationService {

    public static Map<String, Object> getTemperature() {
        try {
            Map<String, Object> report = new HashMap<>();
            report.put("temp", Float.toString(calculateTemperature()));
            return report;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }


    private static float calculateTemperature() {
        try {
            float cel = SystemInfo.getCpuTemperature();
            return (float) ((cel * 9.0) / 5.0 + 32);
        } catch (Throwable ex) {
            return 0;
        }
    }

}
