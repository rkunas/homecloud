package eu.kunas.homeclowd;

import eu.kunas.homeclowd.template.TemplatePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by ramazan on 20.06.15.
 */
public class AccessDenied extends TemplatePage {

    public AccessDenied(final PageParameters parameters) {
        super("ACCESS DENIED");
    }

}
