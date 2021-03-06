package eu.kunas.homecloud.eye;

import eu.kunas.homecloud.eye.modes.EyeModeSilent;
import eu.kunas.homecloud.eye.modes.EyeModeWindow;
import eu.kunas.homecloud.eye.ui.MainWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by ramazan on 16.07.15.
 */
@SpringBootApplication
public class EyeApplication {

    public static void main(String[] args) throws Exception {

        Boolean silent = Boolean.FALSE;

        for (String arg : args) {
            if (arg.contains("silent")) {
                silent = Boolean.TRUE;
            }
        }
        if (!silent) {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(EyeModeWindow.class);

            builder.headless(false);

            builder.run(args);
            
            builder.context().getBean(MainWindow.class).init();
        }else{
            SpringApplicationBuilder builder = new SpringApplicationBuilder(EyeModeSilent.class);

            builder.headless(true);

            builder.run(args);
        }
    }


}
