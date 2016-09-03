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

public class MultiWiiClient {

	private final Serial serial;
	
	//The preamble is defined by the protocol.
	//Every message must begin with the characters $M
	private static final String PREAMBLE = "$M";
	//Character that denotes information being passed to the MultiWii
	private static final char TO_MUTLIWII = '<';
	//Character that denotes information being requested from by the MultiWii
	private static final char FROM_MUTLIWII = '>';
	
	public MultiWiiClient(String usbPort) {
		SerialConfig config = new SerialConfig();

        config.device(usbPort)
              .baud(Baud._38400)
              .dataBits(DataBits._8)
              .parity(Parity.NONE)
              .stopBits(StopBits._1)
              .flowControl(FlowControl.NONE);
		
		
		this.serial = SerialFactory.createInstance();
		serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    System.out.println(event.getAsciiString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
		
		try {
			this.serial.open(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendRequest(MultiWiiRequest request) {
		String message = createMessage(request.getId(), false, null);
		sendMessage(message);
	}
	
	
	public void sendCommand(MultiWiiCommand command, String payload) {
		String message = createMessage(command.getId(), true, payload);
		sendMessage(message);
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
	 * 		information being requested from the MultiWii
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
		
		//Get the direction of the message
		if (isCommand) {
			message.append(TO_MUTLIWII);
		} else {
			message.append(FROM_MUTLIWII);
		}
		
		int datalength = (payload != null) ? payload.length() : 0;
		byte payloadSize = (byte) (datalength & 0xFF);
		
		message.append(payloadSize);
		checksum ^= (payloadSize & 0xFF);
		
		message.append((byte) (mutliWiiCommandnumber & 0xFF));
		checksum ^= (mutliWiiCommandnumber & 0xFF);
		
		if (payload != null) {
			for (char c : payload.toCharArray()){ 
				message.append((byte)(c & 0xFF));
				checksum ^= (c & 0xFF);
			}
		}
		
		message.append(checksum);
		return message.toString();
	}
	
	
	private void sendMessage(String message) {
        try {
            serial.write(message.getBytes());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
	}
	
}
