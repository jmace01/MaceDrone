package com.jmace.MaceDrone.msp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultiWiiClient {

	private static final List<Byte> PREAMBLE;
	
	
	static {
		PREAMBLE = new ArrayList<>();
		for (byte b : "$M".getBytes()) {
			PREAMBLE.add(b);
		}
	}
	
	
	public void sendRequest(MultiWiiRequest request) {
		List<Byte> message = createMessage(request.getId(), false, null);
	}
	
	
	public void sendCommand(MultiWiiCommand command, String payload) {
		List<Byte> message = createMessage(command.getId(), true, payload);
	}
	
	
	private List<Byte> createMessage(int mutliWiiCommandnumber, boolean isCommand, String payload) {
		List<Byte> bf = new LinkedList<Byte>();
		byte checksum=0;
		
		bf.addAll(PREAMBLE);
		
		if (isCommand) {
			bf.add((byte) '<');
		} else {
			bf.add((byte) '>');
		}
		
		int datalength = (payload != null) ? payload.length() : 0;
		byte payloadSize = (byte) (datalength & 0xFF);
		
		bf.add(payloadSize);
		checksum ^= (payloadSize & 0xFF);
		
		bf.add((byte) (mutliWiiCommandnumber & 0xFF));
		checksum ^= (mutliWiiCommandnumber & 0xFF);
		
		if (payload != null) {
			for (char c : payload.toCharArray()){ 
				bf.add((byte)(c & 0xFF));
				checksum ^= (c & 0xFF);
			}
		}
		
		bf.add(checksum);
		return (bf);
	}
	
}
