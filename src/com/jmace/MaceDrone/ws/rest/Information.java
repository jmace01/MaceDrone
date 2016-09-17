package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.services.StatusService;

@Path("information")
public class Information {

	@Path("gps")
	@GET
	public String gsp() {
		GsonBuilder gb = new GsonBuilder();
		Gson gson = gb.serializeNulls().create();
		
		return gson.toJson(StatusService.getGPS());
	}
	
}
