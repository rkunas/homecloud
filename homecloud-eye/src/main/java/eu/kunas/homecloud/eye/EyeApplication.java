package eu.kunas.homecloud.eye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import javax.swing.*;

/**
 * Created by ramazan on 16.07.15.
 */

public class EyeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(EyeApplication.class);

        builder.headless(false);

        builder.run(args);
    }

    @Bean
    public MainWindow mainWindow() {
        return new MainWindow();
    }

    @Bean
    public SilentRecorder recorder() {
        return new SilentRecorder();
    }


}
