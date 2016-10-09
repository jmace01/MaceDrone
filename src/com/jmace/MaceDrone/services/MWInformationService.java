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

import java.util.HashMap;
import java.util.Map;
import com.jmace.MaceDrone.msp.MultiWiiClient;
import com.jmace.MaceDrone.msp.MultiWiiClientFactory;
import com.jmace.MaceDrone.msp.MultiWiiRequest;


public class MWInformationService {

    private static MultiWiiClient client;


    static {
        client = MultiWiiClientFactory.getInstance();
    }


    public static Map<String, Object> getGPS() {
        try {
            return client.sendRequest(MultiWiiRequest.MSP_RAW_GPS);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

}
