package eu.kunas.homecloud.eye.ui;

import com.github.sarxos.webcam.WebcamPanel;
import eu.kunas.homecloud.eye.business.RecorderService;
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
    private RecorderService recorderService;

    private java.util.List<WebcamPanel> panels = new ArrayList<WebcamPanel>();

    private JButton recordButton = new JButton("Start Record");
    private JButton viewCam = new JButton("View Cam");

    private JTextField folderTextField = new JTextField();
    private JTextField xWidthTextField = new JTextField();
    private JTextField yWidthTextField = new JTextField();

    private JPanel camPanel = new JPanel();
    private Thread silentThead;

    WebcamPanel webcamPanel = null;

    public MainWindow() {
    }

    public void init() {

        setLayout(new FlowLayout());

        recordButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                silentThead = new Thread() {
                    @Override
                    public void run() {
                        recorderService.startRecord(folderTextField.getText(), new Integer(xWidthTextField.getText()), new Integer(yWidthTextField.getText()));

                    }
                };

                silentThead.start();
            }
        });

        viewCam.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Dimension[] nonStandardResolutions = new Dimension[]{
                        new Dimension(1280, 720)
                };
                webcamPanel.stop();
                recorderService.webcam.close();

                recorderService.webcam.setCustomViewSizes(nonStandardResolutions);
                recorderService.webcam.setViewSize(new Dimension(1280, 720));
                recorderService.webcam.open();
                webcamPanel.start();
            }
        });

        recorderService.openCam(640, 480);
        webcamPanel = new WebcamPanel(recorderService.webcam, false);
        camPanel.add(webcamPanel);
        webcamPanel.start();

        camPanel.setSize(new Dimension(1024, 576));
        camPanel.setPreferredSize(new Dimension(1024, 576));

        add(camPanel);

        add(viewCam);
        add(recordButton);


        folderTextField.setSize(new Dimension(400, 30));
        folderTextField.setPreferredSize(new Dimension(400, 30));

        yWidthTextField.setText("600");
        yWidthTextField.setSize(new Dimension(150, 30));
        yWidthTextField.setPreferredSize(new Dimension(150, 30));

        xWidthTextField.setText("800");
        xWidthTextField.setSize(new Dimension(150, 30));
        xWidthTextField.setPreferredSize(new Dimension(150, 30));

        add(xWidthTextField);
        add(yWidthTextField);

        folderTextField.setText("/home/developer/");

        add(folderTextField);
        setTitle("EyeApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setBounds(100, 100, 900, 700);


    }

}
