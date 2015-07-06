package eu.kunas.homeclowd.frontend.template;

import eu.kunas.homeclowd.frontend.auth.BasicAuthenticationSession;
import eu.kunas.homeclowd.frontend.pages.administration.Settings;
import eu.kunas.homeclowd.frontend.pages.profile.PasswordRenewPage;
import eu.kunas.homeclowd.frontend.pages.profile.ProfileDashboardPage;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created by ramazan on 10.04.15.
 */
public class HeaderPanel extends Panel {

    public HeaderPanel(String id, String title) {
        super(id);

        String contextPath = WebApplication.get().getServletContext().getContextPath();

        //  PackageResourceReference resourceReference =
        //         new PackageResourceReference(getClass(), "logo.png");
        ExternalLink li = new ExternalLink("contextPath", contextPath);

        //li.add(new Image("logoResPicture", resourceReference));

        add(li);

    }


    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Link("logOut") {

            @Override
            public void onClick() {
                AuthenticatedWebSession.get().invalidate();
                setResponsePage(getApplication().getHomePage());
            }
        });

        Link profile = new Link("profile") {
            @Override
            public void onClick() {
                setResponsePage(ProfileDashboardPage.class);
            }
        };

        BasicAuthenticationSession session = (BasicAuthenticationSession) getSession();
        if (session.getUserEntity() != null) {
            Label profileLabel = new Label("profileName", new Model<>("Hello " + session.getUserEntity().getUsername()));

            profile.add(profileLabel);

            add(profile);
        }else{
            Label profileLabel = new Label("profileName", new Model<>(""));

            profile.add(profileLabel);

            add(profile);
        }

        add(new Link("settings") {

            @Override
            public void onClick() {
                setResponsePage(Settings.class);
            }

            @Override
            public boolean isVisible() {
                if (AuthenticatedWebSession.get().isSignedIn()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
