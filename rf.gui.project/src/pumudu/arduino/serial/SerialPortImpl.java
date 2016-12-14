package pumudu.arduino.serial;

import gnu.io.SerialPort;

/**
 * Created by pumudu on 12/12/16.
 */
public class SerialPortImpl {

    private static SerialPortImpl serialPortImplObject;

    SerialPort serialPort = null;

    protected SerialPortImpl() {
        // make this protected so it can't be instantiated else where.
    }

    public static SerialPortImpl getInstance() {
        if (serialPortImplObject == null) {
            serialPortImplObject = new SerialPortImpl();
        }
        return serialPortImplObject;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }
}
