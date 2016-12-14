package pumudu.arduino.ui.listener;

import pumudu.arduino.serial.OutboundSerialSender;
import pumudu.arduino.serial.SerialPortImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Created by pumudu on 12/12/16.
 */
public class SendButtonClick implements ActionListener {

    JTextField binaryCode;
    JTextField pulseLength;
    JTextField protocolType;
    JTextField repeatIterations;
    JRadioButton is433MHz;
    JRadioButton is315Mhz;

    public SendButtonClick(JTextField pulseLength, JTextField binaryString, JTextField protocolType,
                           JTextField repeatIterations, JRadioButton a315MHz, JRadioButton a433MHz) {
        this.binaryCode = binaryString;
        this.pulseLength = pulseLength;
        this.protocolType = protocolType;
        this.repeatIterations = repeatIterations;

        this.is315Mhz = a315MHz;
        this.is433MHz = a433MHz;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //output channel
        OutboundSerialSender outboundSerialSender =
                new OutboundSerialSender(SerialPortImpl.getInstance().getSerialPort());

        outboundSerialSender.sendStringData(binaryCode.getText(), pulseLength.getText(), protocolType.getText(),
                    repeatIterations.getText(), is433MHz.isSelected(), is315Mhz.isSelected());


    }
}
