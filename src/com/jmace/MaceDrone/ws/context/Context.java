package com.jmace.MaceDrone.ws.context;

import com.jmace.MaceDrone.services.CheckingService;
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
		try {
			PlatformManager.setPlatform(Platform.RASPBERRYPI);
		} catch (PlatformAlreadyAssignedException e) {
			e.printStackTrace();
		}
		this.statusThread = new CheckingService();
		this.statusThread.start();
	}

	/*
	 * Server teardown process
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		this.statusThread.interrupt();
	}
}
