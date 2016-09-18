package com.jmace.MaceDrone.ws.context;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import com.jmace.MaceDrone.services.StatusService;

public class MaceDroneApplicationEventListener implements ApplicationEventListener {

    @Override
    public void onEvent(ApplicationEvent event) {
        //
    }


    @Override
    public RequestEventListener onRequest(RequestEvent event) {
        StatusService.setReportTimestamp();
        return null;
    }

}
