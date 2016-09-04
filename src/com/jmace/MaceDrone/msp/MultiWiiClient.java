package com.jmace.MaceDrone.msp;


import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;
import java.io.IOException;
import java.math.BigInteger;

public class MultiWiiClient {

	private final Serial serial;
	
	//The preamble is defined by the protocol.
	//Every message must begin with the characters $M and the direction <
	private static final String PREAMBLE = "$M<";
	
	public MultiWiiClient(String usbPort) {
		SerialConfig config = new SerialConfig();
		config.device(usbPort)
              .baud(Baud._115200)
              .dataBits(DataBits._8)
              .parity(Parity.NONE)
              .stopBits(StopBits._1)
              .flowControl(FlowControl.NONE);
		
		this.serial = SerialFactory.createInstance();
		
		try {
			this.serial.open(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String sendRequest(MultiWiiRequest request) throws IllegalStateException, IOException {
		String message = createMessage(request.getId(), false, null);
		//////////////////////////////////////////////////////////////////////////////////
		System.out.println(message);
		System.out.println(String.format("%040x", new BigInteger(1, message.getBytes())));
		//////////////////////////////////////////////////////////////////////////////////
		return sendMessage(message);
	}
	
	
	public String sendCommand(MultiWiiCommand command, String payload) throws IllegalStateException, IOException {
		String message = createMessage(command.getId(), true, payload);
		return sendMessage(message);
	}
	
	/**
	 * This method creates the message that will be sent to the MultiWii
	 * 
	 * Message format is as follows:
	 * +--------+---------+----+-------+----+---+
	 * |preamble|direction|size|command|data|crc|
	 * +--------+---------+----+-------+----+---+
	 * 
	 * Preamble (2 bytes):
	 * 		Marks the start of a new message; always "$M"
	 * 
	 * Direction (1 byte):
	 * 		Either '<' for a command going to the MultiWii or '>' for
	 * 		information being coming back from the MultiWii
	 * 
	 * Size (1 byte):
	 * 		The number of bytes in the payload
	 * 
	 * Command (1 byte):
	 * 		The message ID of the command, as defined in the protocol
	 * 		100's for requesting data, and 200's for requesting an action
	 * 
	 * Data (variable bytes):
	 * 		The data to pass along with the command
	 * 
	 * CRC (1 byte):
	 * 		Calculated with an XOR of the size, command, and each byte of data 
	 */
	private String createMessage(int mutliWiiCommandnumber, boolean isCommand, String payload) {
		StringBuilder message = new StringBuilder(PREAMBLE);
		byte checksum=0;
		
		int datalength = (payload != null) ? payload.length() : 0;
		
		message.append((char) datalength);
		checksum ^= datalength;
		
		message.append((char) mutliWiiCommandnumber);
		checksum ^= ((int) mutliWiiCommandnumber);
		
		if (payload != null) {
			for (char c : payload.toCharArray()){ 
				message.append(c);
				checksum ^= (int) c;
			}
		}
		
		message.append((char) checksum);
		return message.toString();
	}
	
	
	private String sendMessage(String message) throws IllegalStateException, IOException {
        serial.write(message.getBytes());
        serial.flush();
        
        int count = 0;
        while (serial.available() == 0) {
        	if (count++ > 150) {
        		break;
        	}
        	try{
        		Thread.sleep(10);
        	} catch (Exception e) {}
        }
        
        StringBuilder response = new StringBuilder();
        while (serial.available() != 0) {
        	response.append(serial.read());
        }
        
        return response.toString();
	}
	
}
