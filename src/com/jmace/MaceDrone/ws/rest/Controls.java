/**************************************************************************************************
 * 
 *                                          MACEDRONE
 *                                github.com/jmace01/MaceDrone/
 * 
 * This software was written by Jason Mace for the Mace Drone project. This file may be modified
 * and used in accordance with the MIT License.
 * 
 * Copyright (c) 2016 by Jason Mace.
 * 
 **************************************************************************************************/

package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.jmace.MaceDrone.controller.DroneController;


@Path("control")
public class Controls {

    //private static Logger log = Logger.getLogger(Controls.class);
    private static DroneController controller;


    static {
        controller = DroneController.getInstance();
    }


    @POST
    @Path("kill")
    public Response killEngines() throws Exception {
        controller.killMotors();
        return Response.ok().build();
    }
    
    
    @POST
    @Path("revive")
    public Response reviveEngines() throws Exception {
        controller.reviveMotors();
        return Response.ok().build();
    }
}
