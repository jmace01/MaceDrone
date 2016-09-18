package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.services.MWInformationService;
import com.jmace.MaceDrone.services.RPiInformationService;

@Path("information")
public class Information {

    @Path("temperature")
    @GET
    public Response temperature() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.serializeNulls().create();

        return Response.ok(gson.toJson(RPiInformationService.getTemperature())).build();
    }


    @Path("gps")
    @GET
    public Response gsp() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.serializeNulls().create();

        return Response.ok(gson.toJson(MWInformationService.getGPS())).build();
    }

}
