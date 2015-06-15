package eu.kunas.homeclowd.template;

import eu.kunas.homeclowd.template.FooterPanel;
import eu.kunas.homeclowd.template.HeaderPanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by ramazan on 10.04.15.
 */
public class TemplatePage extends WebPage {

    public static final String CONTENT_ID = "contentComponent";

    private Component headerPanel;
    private Component footerPanel;

    public TemplatePage( String headerTitle) {
        add(headerPanel = new HeaderPanel("headerPanel", headerTitle));

        add(footerPanel = new FooterPanel("footerPanel"));

    }

    protected Component getHeaderPanel() {
        return headerPanel;
    }


    protected Component getFooterPanel() {
        return footerPanel;
    }
}
