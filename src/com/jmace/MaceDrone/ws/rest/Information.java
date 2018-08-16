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

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.services.MWInformationService;
import com.jmace.MaceDrone.services.RPiInformationService;
import com.jmace.MaceDrone.settings.PropertiesStore;


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

        Map<String, Object> data = null;
        
        if (PropertiesStore.IS_RPI.getBoolean())
        {
        	data = MWInformationService.getGPS();
        }
        
        return Response.ok(gson.toJson(data)).build();
    }

}
