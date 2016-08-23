package com.jmace.MaceDrone.msp;

import java.util.LinkedList;
import java.util.List;

public class MultiWiiClient {

	private List<Byte> createMessage(MultiWiiRequest request, Character[] payload) {
		if(request == null) {
			return null;
		}
		
		List<Byte> bf = new LinkedList<Byte>();
		for (byte c : "$M>".getBytes()) {
			bf.add(c);
		}
		
		int datalength = payload != null ? payload.length : 0;
		
		byte checksum=0;
		byte pl_size = (byte) (datalength & 0xFF);
		bf.add(pl_size);
		checksum ^= (pl_size & 0xFF);
		
		bf.add((byte)(request.getId() & 0xFF));
		checksum ^= (request.getId() & 0xFF);
		
		if (payload != null) {
			for (char c :payload){
				bf.add((byte)(c & 0xFF));
				checksum ^= (c & 0xFF);
			}
		}
		
		bf.add(checksum);
		return (bf);
	}
	
}
