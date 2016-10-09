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
        } catch (Exception ex) {}
    }

}
