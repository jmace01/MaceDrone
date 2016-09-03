package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import com.jmace.MaceDrone.controller.DroneController;
import com.jmace.MaceDrone.gps.PiGPS;

@Path("Control")
public class Controls {

	//private static Logger log = Logger.getLogger(Controls.class);
	private static PiGPS gps;
	private static DroneController controller;

	static {
		gps = PiGPS.getInstance();
		controller = DroneController.getInstance();
	}

	@POST
	@Path("forward")
	public String forward(@FormParam("rate") float rate) {
		controller.moveForward(rate);
		return "OK";
	}

	@POST
	@Path("backward")
	public String backward(@FormParam("rate") float rate) {
		controller.moveBackward(rate);
		return "OK";
	}

	@POST
	@Path("left")
	public String left(@FormParam("rate") float rate) {
		controller.moveLeft(rate);
		return "OK";
	}

	@POST
	@Path("right")
	public String right(@FormParam("rate") float rate) {
		controller.moveRight(rate);
		return "OK";
	}

	@POST
	@Path("killEngines")
	public String killEngines() {
		controller.killMotors();
		return "OK";
	}
	
	@POST
	@Path("Test")
	public String test() {
		controller.test();
		return "OKAY";
	}

}
