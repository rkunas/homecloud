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
    private Recorder silentRecorder;

    private java.util.List<WebcamPanel> panels = new ArrayList<WebcamPanel>();

    private JButton silentMode = new JButton("Start Record");
    private JTextField folderTextField = new JTextField();
    private Thread silentThead;

    public MainWindow() {
    }
    
    public void init(){

        setLayout(new FlowLayout());

        silentMode.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                silentThead = new Thread() {
                    @Override
                    public void run() {
                        silentRecorder.startRecord(folderTextField.getText());
                    }
                };

                silentThead.start();
            }
        });

        silentRecorder.openCam();

        WebcamPanel panel = new WebcamPanel(silentRecorder.webcam, false);
        panels.add(panel);
        add(panel);

        add(silentMode);
        folderTextField.setSize(new Dimension(150, 30));
        folderTextField.setPreferredSize(new Dimension(150, 30));
        add(folderTextField);
        setTitle("EyeApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setBounds(100, 100, 370, 500);
        
        panel.start();

    }

}
