package com.jmace.MaceDrone.services;

import com.jmace.MaceDrone.controller.DroneController;
import com.jmace.MaceDrone.settings.SettingsStore;

public class CheckingService extends Thread {

    private static DroneController controller;


    static {
        CheckingService.controller = DroneController.getInstance();
    }


    public void run() {
        try {
            while (true) {
                Thread.sleep(SettingsStore.STALENESS_TIME_MS);
                if (StatusService.reportIsStale()) {
                    controller.killMotors();
                } else {
                    controller.wakeMotors();
                }
            }
        } catch (InterruptedException ex) {}
    }

}
