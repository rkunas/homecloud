package eu.kunas.homecloud.eye;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by ramazan on 28.07.15.
 */
public class MainWindow extends JFrame {

    @Autowired
    private SilentRecorder silentRecorder;

    private java.util.List<WebcamPanel> panels = new ArrayList<WebcamPanel>();

    private JButton startCam = new JButton("Start Cam");
    private JButton silentMode = new JButton("Silent Mode");
    private JTextField folderTextField = new JTextField();
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

                        while (true) {

                            try {
                                String folder = folderTextField.getText();

                                silentRecorder.init(folder);
                                silentRecorder.openCam();
                                silentRecorder.record();
                            } catch (Exception exc) {
                                // Do nothing.
                            }
                        }
                    }
                };

                silentThead.start();
            }
        });

        java.util.List<Webcam> webcams = Webcam.getWebcams();


        if(!webcams.isEmpty()){
            Webcam webcam = webcams.get(0);
            webcam.setViewSize(WebcamResolution.QVGA.getSize());
            WebcamPanel panel = new WebcamPanel(webcam, false);
            panels.add(panel);
            add(panel);
        }




        add(startCam);
        add(silentMode);
        folderTextField.setSize(new Dimension(150, 30));
        folderTextField.setPreferredSize(new Dimension(150, 30));
        add(folderTextField);
        setTitle("EyeApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setBounds(100, 100, 370, 500);

    }


}
