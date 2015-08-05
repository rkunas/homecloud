package eu.kunas.homecloud.eye.modes;

import eu.kunas.homecloud.eye.ui.MainWindow;
import eu.kunas.homecloud.eye.business.RecorderService;
import org.springframework.context.annotation.Bean;

/**
 * Created by Kunas on 03.08.2015.
 */
public class EyeModeWindow {

    @Bean
    public MainWindow mainWindow() {
        MainWindow main = new MainWindow();
        return main;
    }

    @Bean
    public RecorderService recorderService() {
        RecorderService rec = new RecorderService();
        return rec;
    }
}
