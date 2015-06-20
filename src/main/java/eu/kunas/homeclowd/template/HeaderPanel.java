package eu.kunas.homeclowd.template;

import eu.kunas.homeclowd.Settings;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
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
