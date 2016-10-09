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

import com.jmace.MaceDrone.msp.MSPMultiType;

public class MSPIdentParser implements MSPParser {

    private static MSPIdentParser instance;


    static {
        instance = new MSPIdentParser();
    }


    private MSPIdentParser() {
    }


    public static MSPIdentParser getInstance() {
        return instance;
    }


    @Override
    public Map<String, Object> parser(byte[] response) {
        Map<String, Object> results = new HashMap<>();

        if (response.length < 7) {
            System.out.println("Invalid dataset " + new String(response));
            return results;
        }

        ParserHelper helper = new ParserHelper(response);

        //results.put("hex", String.format("%040x", new BigInteger(1, response)));

        results.put("version", helper.read8BitInt() / 100.0); 
        results.put("multi type", MSPMultiType.getFromID(helper.read8BitInt()).name()); 
        results.put("msp version", helper.read8BitInt()); 

        int capability = helper.read32BitInt(); 

        results.put("rxbind", helper.bitIsSet(capability, 1));
        results.put("motors", helper.bitIsSet(capability, 3));
        results.put("flaps", helper.bitIsSet(capability, 4));
        results.put("nav", helper.bitIsSet(capability, 5));
        results.put("bymis", helper.bitIsSet(capability, 32));

        return results;
    }

}
