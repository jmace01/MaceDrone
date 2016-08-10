package com.jmace.MaceDrone.ws;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.jmace.MaceDrone.services.StatusService;

@Path("/status")
public class Status {

	@Path("report")
	@POST
	public String report() {
		StatusService.setReportTimestamp();
		return "OK";
	}
	
}
