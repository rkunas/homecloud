package eu.kunas.homecloud.eye;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ramazan on 28.07.15.
 */
public class MainWindow extends JFrame {


    private java.util.List<WebcamPanel> panels = new ArrayList<WebcamPanel>();

    private JButton startCam = new JButton("Start Cam");
    private JButton silentMode = new JButton("Silent Mode");

    private Thread silentThead;

    public MainWindow() {

        setLayout(new FlowLayout());

        startCam.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        for (WebcamPanel panel : panels) {
                            panel.start();
                        }
                    }
                });
            }
        });

        silentMode.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                silentThead = new Thread() {
                    @Override
                    public void run() {

                        SilentRecorder recorder = new SilentRecorder();
                        int i = 0;
                        while (true) {

                            try {
                                recorder.openCam(i);
                                recorder.record(i);
                            } catch (Exception e1) {
                                System.out.println(e1.getMessage());
                            }
                            i++;

                        }
                    }
                };

                silentThead.start();


            }
        });

        Webcam webcam = Webcam.getWebcams().get(0);

        webcam.setViewSize(WebcamResolution.QVGA.getSize());
        WebcamPanel panel = new WebcamPanel(webcam, false);

        panels.add(panel);
        add(panel);
        add(startCam);
        add(silentMode);

        setTitle("EyeApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }


}
