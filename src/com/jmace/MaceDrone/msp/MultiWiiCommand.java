package com.jmace.MaceDrone.msp;

import java.util.Map;

import com.jmace.MaceDrone.msp.parsers.MSPEmptyParser;
import com.jmace.MaceDrone.msp.parsers.MSPParser;

public enum MultiWiiCommand {

	MSP_SET_RAW_RC(200, MSPEmptyParser.getInstance()),
	MSP_SET_RAW_GPS(201, MSPEmptyParser.getInstance()),
	MSP_SET_PID(202, MSPEmptyParser.getInstance()),
	MSP_SET_BOX(203, MSPEmptyParser.getInstance()),
	MSP_SET_RC_TUNING(204, MSPEmptyParser.getInstance()),
	MSP_ACC_CALIBRATION(205, MSPEmptyParser.getInstance()),
	MSP_MAG_CALIBRATION(206, MSPEmptyParser.getInstance()),
	MSP_SET_MISC(207, MSPEmptyParser.getInstance()),
	MSP_RESET_CONF(208, MSPEmptyParser.getInstance()),
	MSP_SELECT_SETTING(210, MSPEmptyParser.getInstance()),
	MSP_SET_HEAD(211, MSPEmptyParser.getInstance()),
	MSP_SET_SERVO_CONF(212, MSPEmptyParser.getInstance()),
	MSP_SET_MOTOR(214, MSPEmptyParser.getInstance());
	
	private final int id;
	private final MSPParser parser;
	
	MultiWiiCommand(int id, MSPParser parser) {
		this.id = id;
		this.parser = parser;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Map<String, String> parse(String response) {
		return parser.parser(response);
	}
	
}
