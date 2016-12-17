package pumudu.arduino.ui.listener;

import pumudu.arduino.serial.OutboundSerialSender;
import pumudu.arduino.serial.SerialPortImpl;
import pumudu.arduino.ui.utils.TrackSendSerialProgress;

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

    // Progress bar
    JProgressBar serialProgress;

    // Configurations
    JTextField pin433Mhz;
    JTextField pin315Mhz;

    // Task which tracks sending progress
    private TrackSendSerialProgress progressTask;

    public SendButtonClick(JTextField pulseLength, JTextField binaryString, JTextField protocolType,
                           JTextField repeatIterations, JRadioButton a433MHz, JRadioButton a315MHz,
                           JTextField pin433Mhz, JTextField pin315Mhz, JProgressBar serialProgress) {
        this.binaryCode = binaryString;
        this.pulseLength = pulseLength;
        this.protocolType = protocolType;
        this.repeatIterations = repeatIterations;

        this.is315Mhz = a315MHz;
        this.is433MHz = a433MHz;

        this.pin315Mhz = pin315Mhz;
        this.pin433Mhz = pin433Mhz;

        this.serialProgress = serialProgress;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Output channel
        OutboundSerialSender outboundSerialSender =
                new OutboundSerialSender(SerialPortImpl.getInstance().getSerialPort());

        outboundSerialSender.sendStringData(binaryCode.getText(), pulseLength.getText(), protocolType.getText(),
                    repeatIterations.getText(), is433MHz.isSelected(), is315Mhz.isSelected(), pin433Mhz.getText(),
                    pin315Mhz.getText());

        // Track sending progress through progress bar
        progressTask = new TrackSendSerialProgress(serialProgress);
        progressTask.start();
    }
}
