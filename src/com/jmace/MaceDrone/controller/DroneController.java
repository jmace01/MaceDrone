package com.jmace.MaceDrone.controller;

import java.io.IOException;

import com.jmace.MaceDrone.msp.MultiWiiClient;
import com.jmace.MaceDrone.msp.MultiWiiRequest;

public class DroneController
{

	private static boolean isKilled;
	private static DroneController instance;
	private MultiWiiClient mwClient;
	
	private static final String SERIAL_PORT = "/dev/ttyUSB0";
	
	
	static
	{
		isKilled = false;
		instance = new DroneController();
	}
	
	
	private DroneController()
	{
		this.mwClient = new MultiWiiClient(SERIAL_PORT);
	}
	
	
	public static DroneController getInstance()
	{
		return instance;
	}
	
	
	public void moveForward(float rate)
	{
		System.out.println("UP " + rate);
	}
	
	
	public void moveBackward(float rate)
	{
		System.out.println("DOWN " + rate);
	}
	
	
	public void moveLeft(float rate)
	{
		System.out.println("LEFT " + rate);
	}
	
	
	public void moveRight(float rate)
	{
		System.out.println("RIGHT " + rate);
	}
	
	
	public void killMotors()
	{
		if (!isKilled) {
			System.out.println("KILLING MOTORS");
			isKilled = true;
		}
	}
	
	public void wakeMotors()
	{
		if (isKilled) {
			System.out.println("WAKING MOTORS");
			isKilled = false;
		}
	}
	
	public String test() throws IllegalStateException, IOException
	{
		return this.mwClient.sendRequest(MultiWiiRequest.MSP_IDENT);
	}
}
