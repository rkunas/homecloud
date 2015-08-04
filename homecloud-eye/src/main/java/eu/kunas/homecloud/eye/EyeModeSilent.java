package eu.kunas.homecloud.eye;

import org.springframework.context.annotation.Bean;

/**
 * Created by Kunas on 03.08.2015.
 */
public class EyeModeSilent {

    @Bean
    public Recorder recorder() {
        return new Recorder();
    }
}
