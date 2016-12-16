package pumudu.arduino.ui.listener;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import pumudu.arduino.serial.ConnectionImpl;
import pumudu.arduino.serial.InboundSerialEventListener;
import pumudu.arduino.serial.SerialPortImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.*;

/**
 * Created by pumudu on 12/10/16.
 */
public class ConnectionButtonClick implements ActionListener {

    JTabbedPane mainTabbedPane;
    JComboBox serialPortSelector;
    JTextField baudRate;
    JLabel connectionStatus;
    JButton connectButton;

    ConnectionImpl connection;

    public ConnectionButtonClick(JTabbedPane mainTabbedPane, JComboBox serialPortSelector,
                                 JTextField baudRate, JLabel connectionStatus,JButton connectButton) {
        this.mainTabbedPane =mainTabbedPane;
        this.serialPortSelector = serialPortSelector;
        this.baudRate = baudRate;
        this.connectionStatus = connectionStatus;
        this.connectButton = connectButton;

        // create new connectionImplementation Object
        connection = new ConnectionImpl();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Enumeration portEnumeration = connection.getComPortObjects();

        if (connectButton.getText().contentEquals("Disconnect")) {
            // Disconnect any serial connection already established.
            portDisconnect();
        } else {
            while (portEnumeration.hasMoreElements()) {
                CommPortIdentifier portId = (CommPortIdentifier) portEnumeration.nextElement();
                if (portId.getName().contentEquals(serialPortSelector.getSelectedItem().toString())) {
                    System.out.println("PORT MATCHED!!!");
                    // Connect to selected serial port.
                    portConnect(portId);
                    break;
                }
            }
        }
    }

    private void portDisconnect() {
        SerialPort serialPort = SerialPortImpl.getInstance().getSerialPort();

        try {
            if(serialPort != null) {
                serialPort.getOutputStream().close();
                serialPort.getInputStream().close();
            }
        } catch (IOException e) {
            System.out.println("Disconnecting serial connection..");
            e.printStackTrace();
        } finally {
            connectionStatus.setText("Ready to connect");
            connectionStatus.setForeground(new Color(0, 0, 0));
            connectButton.setText("Connect");
            if(serialPort != null) {
                SerialPortImpl.getInstance().getSerialPort().close();
            }
        }
    }

    private void portConnect(CommPortIdentifier portId) {
        try {
            //connect to port
            SerialPortImpl.getInstance().setSerialPort(connection.getSerialConnection(portId,
                                                            Integer.parseInt(baudRate.getText())));


            connectionStatus.setText("Connected : " + portId.getName());
            connectionStatus.setForeground(new Color(28, 153, 21));
            connectButton.setText("Disconnect");

            // enable transmit tab after successful connection
            mainTabbedPane.setEnabledAt(1, true);

        } catch (PortInUseException e) {
            System.out.println("Port already in use");
            connectionStatus.setText("Port already in use");
            connectionStatus.setForeground(new Color(255, 0, 0));
//                    System.exit(1);
        } catch (NullPointerException e2) {
            System.out.println("COM port maybe disconnected");
            connectionStatus.setText("COM port maybe disconnected");
            connectionStatus.setForeground(new Color(255, 0, 0));
        } catch (UnsupportedCommOperationException e3) {
            System.out.println(e3.toString());
        }

        SerialPortImpl.getInstance().getSerialPort().notifyOnDataAvailable(true);
        SerialPortImpl.getInstance().getSerialPort().notifyOnOutputEmpty(true);

//        wait till arduino finish initializing connection established
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Create the listener to make sure we don't miss out listening
        InboundSerialEventListener inboundSerialEventListener =
                new InboundSerialEventListener(SerialPortImpl.getInstance().getSerialPort());


    }
}
