package com.jmace.MaceDrone.settings;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SettingsStore {

    public static long STALENESS_TIME_MILLISECONDS = 3000;

    
    public static Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        Field[] fields = SettingsStore.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                properties.put(field.getName(), field.get(null));
            } catch (Exception e) {}
        }
        
        return properties;
    }
    
    
    public static boolean setProperty(String name, Object value) {
        try {
            Field toSet = null;
            for (Field field : SettingsStore.class.getDeclaredFields()) {
                if (field.getName().equalsIgnoreCase(name)) {
                    toSet = field;
                }
            }
            
            if (toSet == null) {
                return false;
            } else {
                toSet.set(SettingsStore.class, toSet.getType().cast(value));
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
