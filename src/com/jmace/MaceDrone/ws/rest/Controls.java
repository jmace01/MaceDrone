package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.jmace.MaceDrone.controller.DroneController;
import com.jmace.MaceDrone.gps.PiGPS;

@Path("Control")
public class Controls {

	private static PiGPS gps;
	private static DroneController controller;

	static {
		gps = PiGPS.getInstance();
		controller = DroneController.getInstance();
	}

	@POST
	@Path("forward")
	public String forward() {
		controller.moveForward();
		return "OK";
	}

	@POST
	@Path("backward")
	public String backward() {
		controller.moveBackward();
		return "OK";
	}

	@POST
	@Path("left")
	public String left() {
		controller.moveLeft();
		return "OK";
	}

	@POST
	@Path("right")
	public String right() {
		controller.moveRight();
		return "OK";
	}

	@POST
	@Path("killEngines")
	public String killEngines() {
		controller.killEngines();
		return "OK";
	}

}
