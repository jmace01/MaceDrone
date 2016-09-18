package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.FormParam;
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
    @Path("forward")
    public Response forward(@FormParam("rate") float rate) {
        controller.moveForward(rate);
        return Response.ok().build();
    }


    @POST
    @Path("backward")
    public Response backward(@FormParam("rate") float rate) {
        controller.moveBackward(rate);
        return Response.ok().build();
    }


    @POST
    @Path("left")
    public Response left(@FormParam("rate") float rate) {
        controller.moveLeft(rate);
        return Response.ok().build();
    }


    @POST
    @Path("right")
    public Response right(@FormParam("rate") float rate) {
        controller.moveRight(rate);
        return Response.ok().build();
    }


    @POST
    @Path("killEngines")
    public Response killEngines() {
        controller.killMotors();
        return Response.ok().build();
    }
}
