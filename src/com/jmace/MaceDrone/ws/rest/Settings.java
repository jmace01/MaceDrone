package com.jmace.MaceDrone.ws.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.settings.SettingsStore;

@Path("settings")
public class Settings {

    @Path("staleness")
    @GET
    public Response getStalenessTime() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.serializeNulls().create();

        Map<String, Long> data = new HashMap<>();
        data.put("staleness_time_ms", SettingsStore.STALENESS_TIME_MILLISECONDS);

        return Response.ok(gson.toJson(data)).build();
    }


    @Path("staleness")
    @POST
    public Response setStalenessTime(@FormParam("stalenessTime") Long stalenessTime) {
        if (stalenessTime == null || stalenessTime < 500) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        SettingsStore.STALENESS_TIME_MILLISECONDS = stalenessTime;
        return Response.ok().build();
    }

}
