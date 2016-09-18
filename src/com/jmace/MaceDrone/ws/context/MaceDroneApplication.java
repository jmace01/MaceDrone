package com.jmace.MaceDrone.ws.context;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MaceDroneApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(MaceDroneApplicationEventListener.class);
        return s;
    }

}
