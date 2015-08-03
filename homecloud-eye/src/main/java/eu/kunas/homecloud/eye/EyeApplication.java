package eu.kunas.homecloud.eye;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by ramazan on 16.07.15.
 */

public class EyeApplication {

    public static void main(String[] args) throws Exception {

        Boolean silent = Boolean.FALSE;

        for (String arg : args){
            if(arg.contains("silent")){
                silent = Boolean.TRUE;
            }
        }
        if(!silent) {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(EyeModeWindow.class);

            builder.headless(false);

            builder.run(args);
        }
    }




}
