package eu.kunas.homecloud.eye;

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
    public Recorder recorder() {
        Recorder rec = new Recorder();
        return rec;
    }
}
