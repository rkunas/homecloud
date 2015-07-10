package eu.kunas.homeclowd.frontend.pages.profile;

import eu.kunas.homeclowd.frontend.auth.BasicAuthenticationSession;
import eu.kunas.homeclowd.frontend.template.ProfileTemplatePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by ramazan on 04.07.15.
 */
public class DetailsPage extends ProfileTemplatePage {

    public DetailsPage(){

        BasicAuthenticationSession session = (BasicAuthenticationSession) getSession();

        this.setDefaultModel(new CompoundPropertyModel(session.getUserEntity()));

        add(new Label("firstName"));
        add(new Label("lastName"));
    }
}
