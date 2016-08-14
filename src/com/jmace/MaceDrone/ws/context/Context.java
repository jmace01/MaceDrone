package com.jmace.MaceDrone.ws.context;

import com.jmace.MaceDrone.services.CheckingService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Context implements ServletContextListener {

	private Thread statusThread;
	
	/*
	 * Server startup process
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
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
