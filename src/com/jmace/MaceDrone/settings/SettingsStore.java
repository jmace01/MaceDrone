package com.jmace.MaceDrone.settings;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SettingsStore {

    public static Long STALENESS_TIME_MS = 3000l;

    
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
    
    
    public static Object getProperty(String name) {
        Field[] fields = SettingsStore.class.getDeclaredFields();
        
        try {
            for (Field field : fields) {
                if (field.getName().equals(name)) {
                    return field.get(null);
                }
            }
        } catch (Exception e) {}
        
        return "";
    }
    
    
    public static boolean setProperty(String name, String value) {
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
                setValue(toSet, value);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private static void setValue(Field toSet, String value) throws IllegalArgumentException, IllegalAccessException {
        if (toSet.getType().isAssignableFrom(Long.class)) {
            toSet.set(SettingsStore.class, Long.parseLong(value));
        } else {
            toSet.set(SettingsStore.class, value);
        }
    }

}
