/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.kunas.homeclowd;


import eu.kunas.homeclowd.dto.UserDto;
import eu.kunas.homeclowd.template.LoginTemplatePage;
import eu.kunas.homeclowd.template.TemplatePage;
import eu.kunas.homeclowd.utils.ExactErrorLevelFilter;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SignInPage extends LoginTemplatePage {

    private UserDto user = new UserDto();

    Properties p;

    FeedbackPanel feedbackPanel;
    public SignInPage() {
        super("Sign In");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        handleProperties();

        add(new Label("buildNo",new Model<>(p.getProperty("version") + " " + p.getProperty("build.date"))));

        StatelessForm form = new StatelessForm("loginForm") {
            @Override
            protected void onSubmit() {

                boolean authResult = AuthenticatedWebSession.get().signIn(user.getUsername(), user.getPassword());

                if (authResult) {
                    feedbackPanel.setVisible(false);
                    continueToOriginalDestination();
                }else{
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

        }catch (FileNotFoundException exc){
            exc.printStackTrace();
        }catch (IOException exc){
        exc.printStackTrace();
        }
    }
}
