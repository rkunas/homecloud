package eu.kunas.homecloud.eye;

import org.springframework.context.annotation.Bean;

/**
 * Created by Kunas on 03.08.2015.
 */
public class EyeModeWindow {

    @Bean
    public MainWindow mainWindow() {
        return new MainWindow();
    }

    @Bean
    public SilentRecorder recorder() {
        return new SilentRecorder();
    }
}
