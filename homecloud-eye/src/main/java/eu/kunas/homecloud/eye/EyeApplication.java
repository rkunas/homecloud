package eu.kunas.homecloud.eye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import javax.swing.*;

/**
 * Created by ramazan on 16.07.15.
 */
@Controller
@EnableAutoConfiguration
public class EyeApplication extends JFrame {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(EyeApplication.class);
        app.setShowBanner(false);
        app.run(args);
    }

    @Bean
    public MainWindow mainWindow() {
        return new MainWindow();
    }

    @Bean
    public SilentRecorder recorder(){
        return new SilentRecorder();
    }


}
