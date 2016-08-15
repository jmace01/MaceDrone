package com.jmace.MaceDrone.controller;

import org.apache.log4j.Logger;

import com.jmace.MaceDrone.ws.rest.Controls;

public class DroneController
{

	private static Logger log = Logger.getLogger(Controls.class);
	private static boolean isKilled;
	private static DroneController instance;
	
	
	static
	{
		isKilled = false;
		instance = new DroneController();
	}
	
	
	private DroneController()
	{
		//
	}
	
	
	public static DroneController getInstance()
	{
		return instance;
	}
	
	
	public void moveForward(float rate)
	{
		log.debug("UP " + rate);
	}
	
	
	public void moveBackward(float rate)
	{
		log.debug("DOWN " + rate);
	}
	
	
	public void moveLeft(float rate)
	{
		log.debug("LEFT " + rate);
	}
	
	
	public void moveRight(float rate)
	{
		log.debug("RIGHT " + rate);
	}
	
	
	public void killMotors()
	{
		if (!isKilled) {
			log.debug("KILLING MOTORS");
			isKilled = true;
		}
	}
	
	public void wakeMotors()
	{
		if (isKilled) {
			log.debug("WAKING MOTORS");
			isKilled = false;
		}
	}
}
