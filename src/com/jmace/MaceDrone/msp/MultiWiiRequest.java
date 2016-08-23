package com.jmace.MaceDrone.msp;

public enum MultiWiiRequest {

	MSP_IDENT(100),
	MSP_STATUS(101),
	MSP_RAW_IMU(102),
	MSP_SERVO(103),
	MSP_MOTOR(104),
	MSP_RC(105),
	MSP_RAW_GPS(106),
	MSP_COMP_GPS(107),
	MSP_ATTITUDE(108),
	MSP_ALTITUDE(109),
	MSP_ANALOG(110),
	MSP_RC_TUNING(111),
	MSP_PID(112),
	MSP_BOX(113),
	MSP_MISC(114),
	MSP_MOTOR_PINS(115),
	MSP_BOXNAMES(116),
	MSP_PIDNAMES(117),
	MSP_SERVO_CONF(118);
	
	private final int id;
	
	MultiWiiRequest(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
}
