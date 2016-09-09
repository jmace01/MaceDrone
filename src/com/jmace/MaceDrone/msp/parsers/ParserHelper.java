package com.jmace.MaceDrone.msp.parsers;

public class ParserHelper {
	
	private final byte[] data;
	private int pointer;
	
	
	public ParserHelper(byte[] data) {
		this.data = data;
		pointer = 0;
	}
	
	
	public char readChar() {
		return (char) (data[pointer++] & 0xFF);
	}
	
	
	public int read8BitInt() {
		return (int) (data[pointer++] & 0xFF);
	}
	
	
	public int read16BitInt() {
		return (int) (data[pointer++] & 0xFF) | ((data[pointer++] & 0xFF) << 8);
	}
	
	
	public int read32BitInt() {
		return (int) (data[pointer++] & 0xFF) | ((data[pointer++] & 0xFF) << 8) | ((data[pointer++] & 0xFF) << 16) | ((data[pointer++] & 0xFF) << 24);
	}
	
	
	public boolean bitIsSet(int binary, int bitNumber) {
		return (binary & (1 << bitNumber)) > 0;
	}
	
}
