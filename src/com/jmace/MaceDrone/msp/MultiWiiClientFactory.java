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

package com.jmace.MaceDrone.msp;

public class MultiWiiClientFactory {

    private static final String SERIAL_PORT = "/dev/ttyUSB0";
    private static final MultiWiiClient instance;


    static {
        instance = new MultiWiiClient(SERIAL_PORT);
    }


    public static MultiWiiClient getInstance() {
        return instance;
    }
}
