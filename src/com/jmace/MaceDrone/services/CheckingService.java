package com.jmace.MaceDrone.services;

public class CheckingService extends Thread {

	public void run() {
		try {
			while (true) {
				Thread.sleep(2000l);
				if (StatusService.reportIsStale()) {
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
		} catch (InterruptedException ex) {}
	}
	
}
