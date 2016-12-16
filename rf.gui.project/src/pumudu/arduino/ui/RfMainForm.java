package pumudu.arduino.ui;

import pumudu.arduino.ui.listener.ConnectionButtonClick;
import pumudu.arduino.ui.listener.RefreshButtonClick;
import pumudu.arduino.ui.listener.SendButtonClick;

import javax.swing.*;

/**
 * Created by pumudu on 12/10/16.
 */
public class RfMainForm {

    // connection tab
    private JPanel mainPanel;
    private JTabbedPane mainTabbedPane;
    private JComboBox serialPortSelector;
    private JTextField baudRate;
    private JButton connectButton;
    private JLabel connectionStatus;
    private JButton refreshButton;

    // transmit tab
    private JButton sendButton;
    private JTextField pulseLength;
    private JTextField binaryString;
    private JTextField protocolType;
    private JTextField repeatIterations;
    private JRadioButton a43392MHzRadioButton;
    private JRadioButton a315MHzRadioButton;
    private JTextField pin433Mhz;
    private JTextField pin315Mhz;


    public RfMainForm() {

        // Initialize form by setting up initial component properties
        initialize();

        // Connection tab action listeners
        connectButton.addActionListener(new ConnectionButtonClick(mainTabbedPane,serialPortSelector,
                                        baudRate,connectionStatus,connectButton));
        refreshButton.addActionListener(new RefreshButtonClick(serialPortSelector));

        // Transmit tab action listeners
        sendButton.addActionListener(new SendButtonClick(pulseLength,binaryString,protocolType,
                                        repeatIterations,a43392MHzRadioButton,a315MHzRadioButton,pin433Mhz,pin315Mhz));

    }

    private void initialize() {
        // Disable transmit tab when starting up the form. No point of accessing transmit tab before connect.
//        mainTabbedPane.setEnabledAt(1,false);

        // Group radio buttons
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(a315MHzRadioButton);
        bg1.add(a43392MHzRadioButton);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arduino RF Transmitter");
        frame.setContentPane(new RfMainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
