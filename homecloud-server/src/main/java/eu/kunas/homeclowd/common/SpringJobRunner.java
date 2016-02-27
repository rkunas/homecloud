package eu.kunas.homeclowd.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ramazan on 26.07.15.
 */
@Component
public class SpringJobRunner {

    @Scheduled(cron="*/1 * * * * ?")
    public void doSome(){
    }
}
