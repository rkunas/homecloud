package eu.kunas.homecloud.eye;

import java.awt.*;

/**
 * Created by Kunas on 03.08.2015.
 */
public class Tray extends TrayIcon {

    private SystemTray  tray;


    public Tray(){
        super(null,"sad");
        tray = SystemTray.getSystemTray();
    }
}
