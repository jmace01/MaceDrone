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

import com.jmace.MaceDrone.services.CheckingService;
import com.jmace.MaceDrone.settings.PropertiesStore;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Context implements ServletContextListener {

    private Thread statusThread;


    /*
     * Server startup process
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	if (PropertiesStore.IS_RPI.getBoolean())
    	{
	    	try {
	            PlatformManager.setPlatform(Platform.RASPBERRYPI);
	        } catch (PlatformAlreadyAssignedException e) {
	            e.printStackTrace();
	        }
	        this.statusThread = new CheckingService();
	        this.statusThread.start();
    	}
    }


    /*
     * Server teardown process
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        this.statusThread.interrupt();
    }
}
