package com.jmace.MaceDrone.ws.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmace.MaceDrone.settings.SettingsStore;

@Path("settings")
public class Settings {

    @GET
    public Response getSettings() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.serializeNulls().create();

        return Response.ok(gson.toJson(SettingsStore.getProperties())).build();
    }


    @Path("value")
    @POST
    public Response setStalenessTime(@FormParam("name") String name, @FormParam("value") String value) {
        if (SettingsStore.setProperty(name, value)) {
            return Response.ok(SettingsStore.getProperty(name)).build();
        } else {
            return Response.notModified(SettingsStore.getProperty(name).toString()).build();
        }
    }

}
