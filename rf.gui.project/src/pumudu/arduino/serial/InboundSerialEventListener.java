package pumudu.arduino.serial;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

/**
 * Created by pumudu on 12/11/16.
 */
public class InboundSerialEventListener implements SerialPortEventListener {

    private SerialPort serialPort;
    private BufferedReader input;

    public InboundSerialEventListener(SerialPort serialPort) {
        this.serialPort = serialPort;

        // add event listener
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        try {
            switch (serialPortEvent.getEventType() ) {
                case SerialPortEvent.DATA_AVAILABLE:
                    if (input == null) {
                        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                    }
                    String inputLine = input.readLine();
                    System.out.println(inputLine);
                    break;

                default:
                    break;
            }
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
