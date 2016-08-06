package com.jmace.MaceDrone.controller;

public class DroneController
{

	private static DroneController instance;
	
	
	static
	{
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
	
	
	public void moveForward()
	{
		System.out.println("UP");
	}
	
	
	public void moveBackward()
	{
		System.out.println("DOWN");
	}
	
	
	public void moveLeft()
	{
		System.out.println("LEFT");
	}
	
	
	public void moveRight()
	{
		System.out.println("RIGHT");
	}
	
	
	public void killEngines()
	{
		System.out.println("KILL");
	}
}
