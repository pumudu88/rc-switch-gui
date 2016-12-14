package pumudu.arduino.serial;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by pumudu on 12/11/16.
 */
public class OutboundSerialSender {

    static private final String RF_433MHz_TRANSMIT_PINOUT = "10";
    static private final String RF_315MHz_TRANSMIT_PINOUT = "12";

    SerialPort serialPort;

    public OutboundSerialSender(SerialPort serialPort) {
        this.serialPort = serialPort;
    }


    public void sendStringData(String binaryCode, String pulseLength, String protocolType, String repeatIterations,
                               boolean is433MHz, boolean is315Mhz) {

        // Default transmit pin set to 433.92MHz
        String rfTransmitPin = RF_433MHz_TRANSMIT_PINOUT;

        if(is433MHz) {
            rfTransmitPin = RF_433MHz_TRANSMIT_PINOUT;
        } else if(is315Mhz) {
            rfTransmitPin = RF_315MHz_TRANSMIT_PINOUT;
        }

        // send data
        System.out.println("going to send data");

        // sample payload = binaryCode:010101010010010010001100&pulseLength:321&rfTransmitPin:10&rfType:1&repeatIterations:5
        StringBuilder payload = new StringBuilder();

        payload.append("binaryCode");
        payload.append(":");
        payload.append(binaryCode);
        payload.append("&");

        payload.append("pulseLength");
        payload.append(":");
        payload.append(pulseLength);
        payload.append("&");

        payload.append("rfTransmitPin");
        payload.append(":");
        payload.append(rfTransmitPin);
        payload.append("&");

        payload.append("rfType");
        payload.append(":");
        payload.append(protocolType);
        payload.append("&");

        payload.append("repeatIterations");
        payload.append(":");
        payload.append(repeatIterations);

        System.out.println("payload : " + payload.toString());

        OutputStream output = null;
        try {
            output = serialPort.getOutputStream();
            output.write(payload.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
