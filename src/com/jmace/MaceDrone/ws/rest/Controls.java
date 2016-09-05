package com.jmace.MaceDrone.ws.rest;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.controller.DroneController;

@Path("Control")
public class Controls {

	//private static Logger log = Logger.getLogger(Controls.class);
	private static DroneController controller;

	static {
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
	
	@GET
	@Path("Test")
	public String test() throws IllegalStateException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson gson = gb.serializeNulls().create();
		return "OK : " + gson.toJson(controller.test());
	}

}
