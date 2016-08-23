package com.jmace.MaceDrone.msp;

public enum MultiWiiCommand {

	MSP_SET_RAW_RC(200),
	MSP_SET_RAW_GPS(201),
	MSP_SET_PID(202),
	MSP_SET_BOX(203),
	MSP_SET_RC_TUNING(204),
	MSP_ACC_CALIBRATION(205),
	MSP_MAG_CALIBRATION(206),
	MSP_SET_MISC(207),
	MSP_RESET_CONF(208),
	MSP_SELECT_SETTING(210),
	MSP_SET_HEAD(211),
	MSP_SET_SERVO_CONF(212),
	MSP_SET_MOTOR(214);
	
	private final int id;
	
	MultiWiiCommand(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
}
