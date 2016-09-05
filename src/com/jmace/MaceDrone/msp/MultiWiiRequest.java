package com.jmace.MaceDrone.msp;

import java.util.Map;

import com.jmace.MaceDrone.msp.parsers.MSPEmptyParser;
import com.jmace.MaceDrone.msp.parsers.MSPIdentParser;
import com.jmace.MaceDrone.msp.parsers.MSPParser;

public enum MultiWiiRequest {

	MSP_IDENT(100, MSPIdentParser.getInstance()),
	MSP_STATUS(101, MSPEmptyParser.getInstance()),
	MSP_RAW_IMU(102, MSPEmptyParser.getInstance()),
	MSP_SERVO(103, MSPEmptyParser.getInstance()),
	MSP_MOTOR(104, MSPEmptyParser.getInstance()),
	MSP_RC(105, MSPEmptyParser.getInstance()),
	MSP_RAW_GPS(106, MSPEmptyParser.getInstance()),
	MSP_COMP_GPS(107, MSPEmptyParser.getInstance()),
	MSP_ATTITUDE(108, MSPEmptyParser.getInstance()),
	MSP_ALTITUDE(109, MSPEmptyParser.getInstance()),
	MSP_ANALOG(110, MSPEmptyParser.getInstance()),
	MSP_RC_TUNING(111, MSPEmptyParser.getInstance()),
	MSP_PID(112, MSPEmptyParser.getInstance()),
	MSP_BOX(113, MSPEmptyParser.getInstance()),
	MSP_MISC(114, MSPEmptyParser.getInstance()),
	MSP_MOTOR_PINS(115, MSPEmptyParser.getInstance()),
	MSP_BOXNAMES(116, MSPEmptyParser.getInstance()),
	MSP_PIDNAMES(117, MSPEmptyParser.getInstance()),
	MSP_SERVO_CONF(118, MSPEmptyParser.getInstance());
	
	private final int id;
	private final MSPParser parser;
	
	MultiWiiRequest(int id, MSPParser parser) {
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
