package eu.kunas.homeclowd.frontend.pages.profile;

import eu.kunas.homeclowd.backend.service.UserServiceImpl;
import eu.kunas.homeclowd.frontend.auth.BasicAuthenticationSession;
import eu.kunas.homeclowd.frontend.template.ProfileTemplatePage;
import eu.kunas.homeclowd.frontend.util.ExactErrorLevelFilter;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by ramazan on 04.07.15.
 */
public class PasswordRenewPage extends ProfileTemplatePage {

    @SpringBean
    private UserServiceImpl userService;

    private String oldPassword;

    private String newPassword;

    private String newPasswordRetype;

    private FeedbackPanel feedbackPanel;

    public PasswordRenewPage() {
        Form form = new Form("renewPasswordForm") {
            @Override
            protected void onSubmit() {
                BasicAuthenticationSession session = (BasicAuthenticationSession) getSession();

                if (session.getUserEntity() != null) {

                    if (newPassword.equals(newPasswordRetype)) {
                        Boolean changed = userService.changePassword(session.getUserEntity().getUsername(), oldPassword, newPassword);
                        if (changed) {
                            this.success("Password is changed");
                            feedbackPanel.setVisible(true);
                        }
                    } else {
                        this.error("New password and retype not same");
                        feedbackPanel.setVisible(true);
                    }
                }

                oldPassword = null;
                newPassword = null;
                newPasswordRetype = null;
            }

            @Override
            protected void onError() {
                super.onError();

            }
        };
        form.setDefaultModel(new CompoundPropertyModel(this));

        form.add(new PasswordTextField("oldPassword").add(new PropertyValidator()));
        form.add(new PasswordTextField("newPassword").add(new PropertyValidator()));
        form.add(new PasswordTextField("newPasswordRetype").add(new PropertyValidator()));

        feedbackPanel = new FeedbackPanel("successFeedbackMessage", new ExactErrorLevelFilter(FeedbackMessage.SUCCESS));
        feedbackPanel.setVisible(false);
        form.add(feedbackPanel);

        add(form);
    }
}
