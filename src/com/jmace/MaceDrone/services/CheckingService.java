package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.controller.DroneController;
import com.jmace.MaceDrone.settings.Settings;

public class CheckingService extends Thread {

	private static DroneController controller;
	
	static {
		CheckingService.controller = DroneController.getInstance();
	}
	
	public void run() {
		try {
			while (true) {
				Thread.sleep(Settings.STALENESS_TIME_MILLISECONDS);
				if (StatusService.reportIsStale()) {
					controller.killEngines();
				}
			}
		} catch (InterruptedException ex) {}
	}
	
}
