package eu.kunas.homecloud.eye;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by ramazan on 16.07.15.
 */

public class EyeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(EyeModeWindow.class);

        builder.headless(false);

        builder.run(args);
    }




}
