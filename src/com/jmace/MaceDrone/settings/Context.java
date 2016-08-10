package com.jmace.MaceDrone.settings;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jmace.MaceDrone.services.CheckingService;

public class Context implements ServletContextListener {

	private Thread statusThread;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		this.statusThread = new CheckingService();
		this.statusThread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		this.statusThread.interrupt();
	}
}
