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

package com.jmace.MaceDrone.msp.parsers;

import java.util.HashMap;
import java.util.Map;

public class MSPEmptyParser implements MSPParser {

    private static MSPEmptyParser instance;

    
    static {
        instance = new MSPEmptyParser();
    }

    
    private MSPEmptyParser() {
    }

    
    public static MSPEmptyParser getInstance() {
        return instance;
    }

    
    @Override
    public Map<String, Object> parser(byte[] reponse) {
        return new HashMap<>();
    }

}
