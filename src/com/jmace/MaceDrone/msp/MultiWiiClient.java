/**************************************************************************************************
 * 
 *                                          MACEDRONE
 *                                github.com/jmace01/MaceDrone/
 * 
 * This software was written by Jason Mace for the Mace Drone project. This file may be modified
 * and used in accordance with the MIT License.
 * 
 * Copyright (c) 2016 by Jason Mace.
 * 
 **************************************************************************************************/

package com.jmace.MaceDrone.msp;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Map;

/**************************************************************************************************
 * 
 * <p>This class controls communication with the MultiWii via the MultiWii serial protocol (MSP).
 * The low level handling of the serial communication is handled by the pi4j library, while the
 * protocol is handled in this class.</p>
 * 
 * <p>The MSP format, including a list of valid commands, is defined
 * <a href="http://www.multiwii.com/wiki/index.php?title=Multiwii_Serial_Protocol">here</a></p>
 * 
 * 
 * <p>Message format is as follows:</p>
 * <pre>
 * +--------+---------+----+-------+----+---+
 * |preamble|direction|size|command|data|crc|
 * +--------+---------+----+-------+----+---+
 * </pre>
 * 
 * 
 * <p>
 *  Preamble (2 bytes):
 *      Marks the start of a new message; always "$M"
 * </p><p>
 *  Direction (1 byte):
 *      Either '<' for a command going to the MultiWii or '>' for information being coming back
 *      from the MultiWii
 * </p><p>
 * Size (1 byte):
 *      The number of bytes in the payload
 * </p><p>
 * Command (1 byte):
 *      The message ID of the command, as defined in the protocol 100's for requesting data, and
 *      200's for requesting an action
 * </p><p>
 * Data (variable bytes):
 *      The data to pass along with the command
 * </p><p>
 * CRC (1 byte):
 *      Calculated with an XOR of the size, command, and each byte of data
 * </p> 
 **************************************************************************************************/
public class MultiWiiClient {

    private Serial serial;
    private BufferedInputStream inStream;


    //The preamble and direction byte of messages are both defined by the protocol.
    //Every message must begin with the characters $M, and message going to the MultiWii must have the direction <
    private static final String OUTGOING_PREAMBLE_WITH_DIRECTION = "$M<";
    //Every response must begin with the characters $M, and message coming from the MultiWii must have the direction >
    private static final String INCOMING_PREAMBLE_WITH_DIRECTION = "$M>";

    /**
     * Creates a new instance of the client
     * 
     * @param   port    The path to the port to send and receive data (such as "/dev/ttyUSB0")
     */
    public MultiWiiClient(String port) {
        try {
            //Set up the serial port connection
            SerialConfig config = new SerialConfig();
            config.device(port)
                  .baud(Baud._115200)
                  .dataBits(DataBits._8)
                  .parity(Parity.NONE)
                  .stopBits(StopBits._1)
                  .flowControl(FlowControl.NONE);
            this.serial = SerialFactory.createInstance();
            this.serial.open(config);

            //Create the input stream
            this.inStream = new BufferedInputStream(this.serial.getInputStream());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * Requests information from the MultiWii
     * 
     * @param   request The specific MSP command for the data being requested
     * 
     * @return  A key value pair; this varies, depending on the data being asked for. See /parsers.
     * 
     * @throws  IllegalStateException   thrown if the connection is not open
     * @throws  IOException             thrown if the streams fail
     * @throws  Exception               thrown if the data coming back is incorrect (for example, the checksum does not match)
     */
    public Map<String, Object> sendRequest(MultiWiiRequest request) throws IllegalStateException, IOException, Exception {
        String message = createMessage(request.getId(), null);
        byte[] response = sendMessage(message); 
        return request.parse(response);
    }


    /**
     * Sends a command to the MultiWii
     *
     * @param   command   The specific MSP command the MultiWii will execute
     * @param   payload   The data to be sent to the MultiWii along with the command
     * 
     * @return  A key value pair; this varies, depending on the data being asked for. See /parsers.
     * 
     * @throws  IllegalStateException   thrown if the connection is not open
     * @throws  IOException             thrown if the streams fail
     * @throws  Exception               thrown if the data coming back is incorrect (for example, the checksum does not match)
     */
    public Map<String, Object> sendCommand(MultiWiiCommand command, byte[] payload) throws IllegalStateException, IOException, Exception {
        String message = createMessage(command.getId(), payload);
        byte[] response = sendMessage(message);
        return command.parse(response);
    }


    /**
     * This method creates the message that will be sent to the MultiWii
     * 
     * @param   mutliWiiCommandnumber   The MSP command number
     * @param   payload                 Data
     * 
     * @return  The MSP message that can be passed to the MultiWii
     */
    private String createMessage(int mutliWiiCommandnumber, byte[] payload) {
        StringBuilder message = new StringBuilder(OUTGOING_PREAMBLE_WITH_DIRECTION);
        byte checksum=0;

        int datalength = (payload != null) ? payload.length : 0;

        message.append((char) datalength);
        checksum ^= datalength;

        message.append((char) mutliWiiCommandnumber);
        checksum ^= ((int) mutliWiiCommandnumber);

        if (payload != null) {
            for (byte b : payload){ 
                message.append(b);
                checksum ^= (int) b;
            }
        }

        message.append((char) checksum);
        return message.toString();
    }


    /**
     * This method sends the request to the MultiWii, receives the response, and insures that is is correct.
     * 
     * @param   message The MSP formatted message to send to the MultiWii
     * 
     * @return  The raw data sent back from the MultiWii, not including the preamble, direction, or CRC
     * 
     * @throws  Exception   thrown if the data that comes back is invalid
     */
    private synchronized byte[] sendMessage(String message) throws Exception {
        //Clear out any old responses or requests that were not handled
        serial.discardAll();
        inStream.skip(inStream.available());

        //Send the request
        serial.write(message.getBytes());
        serial.flush();

        //Wait for the response to come back
        //(Note that, in the event of an error, inStream.available() == -1 and will skip both loops)
        int count = 0;
        while (inStream.available() == 0) {
            //After 1.5 seconds, give up on the response
            if (count++ > 150) {
                break;
            }
            try{
                Thread.sleep(10);
            } catch (Exception e) {}
        }

        //Get the response
        StringBuilder responseHeader = new StringBuilder();
        for (int i = 0; i < 3 && inStream.available() > 0; i++) {
            responseHeader.append((char) inStream.read());
        }

        if (!responseHeader.toString().equals(INCOMING_PREAMBLE_WITH_DIRECTION) || inStream.available() <= 1)
        {
            throw new Exception("Invalid header: " + responseHeader.toString());
        }

        int length = inStream.read();
        int computedChecksum = length ^ inStream.read(); //command
        byte[] response = new byte[length];

        for (int i = 0; i < length && inStream.available() > 0; i++) {
            int value =  inStream.read();
            response[i] = (byte) value;
            computedChecksum ^= value;
        }

        int checksum = inStream.read();

        if (computedChecksum != checksum) {
            throw new Exception("Invalid checksum");
        }

        //Return the response
        return response;
    }

}
