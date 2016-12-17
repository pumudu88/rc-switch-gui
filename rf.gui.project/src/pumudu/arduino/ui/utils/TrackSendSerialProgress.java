package pumudu.arduino.ui.utils;

import javax.swing.*;

/**
 * Created by pumudu on 12/17/16.
 */
public class TrackSendSerialProgress extends Thread {

    private JProgressBar progressBar;

    public TrackSendSerialProgress(JProgressBar serialProgressBar) {
        this.progressBar = serialProgressBar;
    }

    public void run() {
        // TODO: 12/17/16 Need to get progress from Arduino itself through inbound serial listener
        for (int i = 0; i <= 100; i += 10) {
            final int progress = i;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    progressBar.setValue(progress);
                }
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }


}
