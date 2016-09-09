package com.jmace.MaceDrone.ws.rest;

import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.services.StatusService;

@Path("/status")
public class Status {

	@Path("report")
	@POST
	public String report() {
		StatusService.setReportTimestamp();
		Map<String, Object> report = StatusService.getReport();
		
		GsonBuilder gb = new GsonBuilder();
		Gson gson = gb.serializeNulls().create();
		
		return gson.toJson(report);
	}
	
}
