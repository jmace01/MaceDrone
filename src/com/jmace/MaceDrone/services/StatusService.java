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

import com.jmace.MaceDrone.settings.SettingsStore;
import java.sql.Timestamp;
import java.util.Date;


public class StatusService {

    private static volatile Timestamp lastReport;


    public static void setReportTimestamp() {
        StatusService.lastReport = new Timestamp(new Date().getTime());
    }


    public static Timestamp getLastReportTime() {
        return StatusService.lastReport;
    }


    public static boolean reportIsStale() {
        if (StatusService.lastReport == null) {
            return true;
        }

        Date date = new Date();
        Timestamp cutOfftime = new Timestamp(date.getTime() - SettingsStore.STALENESS_TIME_MS);
        return StatusService.lastReport.before(cutOfftime);
    }

}
