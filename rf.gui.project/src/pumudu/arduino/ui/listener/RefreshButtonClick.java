package pumudu.arduino.ui.listener;

import gnu.io.CommPortIdentifier;
import pumudu.arduino.serial.ConnectionImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.*;

/**
 * Created by pumudu on 12/11/16.
 */
public class RefreshButtonClick implements ActionListener {

    JComboBox serialPortSelector;
    ConnectionImpl connection;

    public RefreshButtonClick(JComboBox serialPortSelector) {
        this.serialPortSelector = serialPortSelector;

        // create new connectionImplementation Object
        connection = new ConnectionImpl();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // remove all items currently on the list before refresh
        serialPortSelector.removeAllItems();

        Enumeration portEnumeration = connection.getComPortObjects();

        while(portEnumeration.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portEnumeration.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                serialPortSelector.addItem(portId.getName());
            }
            else {
                System.out.println(portId.getName());
            }
        }
    }



}
