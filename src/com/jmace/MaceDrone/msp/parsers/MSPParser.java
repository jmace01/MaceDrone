package com.jmace.MaceDrone.msp.parsers;

import java.util.Map;

public interface MSPParser {

    public Map<String, Object> parser(byte[] response);

}
