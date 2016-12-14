package pumudu.arduino.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.util.Enumeration;

/**
 * Created by pumudu on 12/11/16.
 */
public class ConnectionImpl {


    public Enumeration getComPortObjects() {
        Enumeration portObjects = CommPortIdentifier.getPortIdentifiers();
        return portObjects;
    }

    //connect method
    SerialPort serialPort;
    private static final int TIME_OUT = 2000;

    public SerialPort getSerialConnection(CommPortIdentifier portId, int baudRate) throws PortInUseException,NullPointerException,
                                                                            UnsupportedCommOperationException {
        //connect to port

            //downcast the comm port to serial port
            //and give the name of the application and timeout
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            System.out.println("Port open successful : " + portId.getName());

            //set serial port parameters
            serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        return serialPort;
    }





}
