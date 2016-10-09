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


public enum MSPMultiType {

    UNKNOWN(0),
    TRI(1),
    QUADP(2),
    QUADX(3),
    BI(4),
    GIMBAL(5),
    Y6(6),
    HEX6(7),
    FLYING_WING(8),
    Y4(9),
    HEX6X(10),
    OCTOX8(11),
    OCTOFLATP(12),
    OCTOFLATX(13),
    AIRPLANE(14),
    HELI_120_CCPM(15),
    HELI_90_DEG(16),
    VTAIL4(17),
    HEX6H(18),
    DUALCOPTER(20),
    SINGLECOPTER(21);


    private final int id;


    MSPMultiType(int id) {
        this.id = id;
    }


    public static MSPMultiType getFromID(int id) {
        for (MSPMultiType type : MSPMultiType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return UNKNOWN;
    }

}
