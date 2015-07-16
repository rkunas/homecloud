package eu.kunas.homeclowd.frontend.template;

import eu.kunas.homeclowd.frontend.pages.profile.DetailsPage;
import eu.kunas.homeclowd.frontend.pages.profile.PasswordRenewPage;
import eu.kunas.homeclowd.frontend.pages.profile.ProfileDashboardPage;
import eu.kunas.homeclowd.frontend.template.TemplatePage;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created by ramazan on 04.07.15.
 */
public abstract class ProfileTemplatePage extends TemplatePage {
    public ProfileTemplatePage() {
        super("Profile Overivew");

        Link dashboardLink = new Link("dashboardLink") {
            @Override
            public void onClick() {
                setResponsePage(ProfileDashboardPage.class);
            }
        };

        Link detailsLink = new Link("detailsLink") {
            @Override
            public void onClick() {
                setResponsePage(DetailsPage.class);
            }
        };

        Link passwordLink = new Link("passwordLink") {
            @Override
            public void onClick() {
                setResponsePage(PasswordRenewPage.class);
            }
        };

        add(dashboardLink);
        add(detailsLink);
        add(passwordLink);
    }
}
