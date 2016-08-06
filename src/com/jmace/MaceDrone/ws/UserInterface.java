package com.jmace.MaceDrone.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/ui")
public class UserInterface
{

	@GET
	public String sayHi()
	{
		return "Hi!";
	}
	
}
