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

package com.jmace.MaceDrone.controller;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import com.jmace.MaceDrone.msp.MultiWiiClient;
import com.jmace.MaceDrone.msp.MultiWiiClientFactory;
import com.jmace.MaceDrone.msp.MultiWiiCommand;

public class DroneController
{
    private boolean isArmed;
    private boolean isKilled;
    private static DroneController instance;
    private MultiWiiClient mwClient;


    static
    {
        instance = new DroneController();
    }


    private DroneController()
    {
        this.isArmed  = false;
        this.isKilled = false;
        this.mwClient = MultiWiiClientFactory.getInstance();
    }


    public static DroneController getInstance()
    {
        return instance;
    }
    
    
    public void killMotors() throws Exception
    {
        this.isKilled = true;
        this.disarm();
    }
    
    
    public void reviveMotors() throws Exception
    {
        this.isKilled = false;
        this.arm();
    }
    
    
    public void arm() throws Exception
    {
        byte[] data = intArrayToByteArray(new int[]{ 1500, 1500, 2000, 1000 });
        this.mwClient.sendCommand(MultiWiiCommand.MSP_SET_RAW_RC, data);
        isArmed = true;
    }
    
    
    public void disarm() throws Exception
    {
        byte[] data = intArrayToByteArray(new int[]{ 1500, 1500, 1000, 1000 });
        this.mwClient.sendCommand(MultiWiiCommand.MSP_SET_RAW_RC, data);
        isArmed = false;
    }

    
    public void stopMotors() throws Exception
    {
        if (this.isArmed) {
            System.out.println("STOPPING MOTORS");
            this.disarm();
        }
    }


    public void wakeMotors() throws Exception
    {
        if (!this.isKilled && !this.isArmed) {
            System.out.println("WAKING MOTORS");
            this.arm();
        }
    }
    
    
    private byte[] intArrayToByteArray(int[] values)
    {
        if (values == null)
        {
            return null;
        }
        
        ByteBuffer byteBuffer = ByteBuffer.allocate(values.length * 4);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(values);
        
        return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).array();
    }
}
