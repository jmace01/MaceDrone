package com.jmace.MaceDrone.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.jmace.MaceDrone.msp.MultiWiiClient;
import com.jmace.MaceDrone.msp.MultiWiiClientFactory;
import com.jmace.MaceDrone.msp.MultiWiiRequest;

public class MWInformationService {

    private static MultiWiiClient client;


    static {
        client = MultiWiiClientFactory.getInstance();
    }


    public static Map<String, Object> getGPS() {
        try {
            return client.sendRequest(MultiWiiRequest.MSP_RAW_GPS);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

}
