/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.kunas.homeclowd.frontend.pages.auth;


import eu.kunas.homeclowd.common.model.dto.UserDto;
import eu.kunas.homeclowd.frontend.template.LoginTemplatePage;
import eu.kunas.homeclowd.frontend.util.ExactErrorLevelFilter;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.html5.media.MediaSource;
import org.wicketstuff.html5.media.audio.Html5Audio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SignInPage extends LoginTemplatePage {

    Properties p;
    FeedbackPanel feedbackPanel;
    private UserDto user = new UserDto();

    public SignInPage() {
        super("Sign In");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        handleProperties();

        final List<MediaSource> mm = new ArrayList<>();

        String contextPath = WebApplication.get().getServletContext().getContextPath();

        mm.add(new MediaSource(contextPath + "/audio?audio=1", "audio/wav"));

        IModel<List<MediaSource>> mediaSourceList = new AbstractReadOnlyModel<List<MediaSource>>() {

            private static final long serialVersionUID = 1L;

            public List<MediaSource> getObject() {
                return mm;
            }
        };

        add(new Html5Audio("audioPlayer", mediaSourceList) {

            private static final long serialVersionUID = 1L;

            @Override
            protected boolean isControls() {
                return true;
            }

            @Override
            protected boolean isAutoPlay() {
                return true;
            }

            @Override
            protected boolean isAutoBuffer() {
                return true;
            }


        });

        Form form = new StatelessForm("loginForm") {
            @Override
            protected void onSubmit() {

                boolean authResult = AuthenticatedWebSession.get().signIn(user.getUsername(), user.getPassword());

                if (authResult) {
                    feedbackPanel.setVisible(false);
                    continueToOriginalDestination();
                } else {
                    feedbackPanel.setVisible(true);
                    getPage().error("Login Failed");
                }
            }

            @Override
            protected void onError() {
                feedbackPanel.setVisible(true);
                super.onError();

            }
        };

        form.setDefaultModel(new CompoundPropertyModel(user));
        form.add(new Label("buildNo", new Model<>(p.getProperty("version") + " " + p.getProperty("build.date"))));

        form.add(new TextField("username").add(new PropertyValidator()));
        form.add(new PasswordTextField("password").add(new PropertyValidator()));

        add(form);

        feedbackPanel = new FeedbackPanel("feedbackMessage", new ExactErrorLevelFilter(FeedbackMessage.ERROR));
        feedbackPanel.setVisible(false);

        form.add(feedbackPanel);
    }

    private void handleProperties() {

        try {
            p = new Properties();
            p.load(SignInPage.class.getClassLoader().getResourceAsStream("build.properties"));

        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
