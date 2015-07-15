package eu.kunas.homeclowd.frontend.template;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ramazan on 10.04.15.
 */
public class FooterPanel extends Panel {

    Properties p;

    public FooterPanel(String id) {
        super(id);
        handleProperties();
        add(new Label("buildNo", new Model<>(p.getProperty("version") + " " + p.getProperty("build.date"))));
    }

    private void handleProperties() {

        try {
            p = new Properties();
            p.load(FooterPanel.class.getClassLoader().getResourceAsStream("build.properties"));

        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}