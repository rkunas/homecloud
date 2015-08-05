package eu.kunas.homecloud.eye.modes;

import eu.kunas.homecloud.eye.business.RecorderService;
import org.springframework.context.annotation.Bean;

/**
 * Created by Kunas on 03.08.2015.
 */
public class EyeModeSilent {

    @Bean
    public RecorderService recorderService() {
        return new RecorderService();
    }
}
