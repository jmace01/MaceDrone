/**************************************************************************************************
 * 
 *                                          MACEDRONE
 *                                github.com/jmace01/MaceDrone/
 * 
 * This software was written by Jason Mace for the Mace Drone project. This file may be modified
 * and used in accordance with the MIT License.
 * 
 * Copyright (c) 2016 by Jason Mace.
 * 
 **************************************************************************************************/

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
