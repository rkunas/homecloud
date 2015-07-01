package eu.kunas.homeclowd.frontend.pages;

import eu.kunas.homeclowd.common.model.HCConfigEntity;
import eu.kunas.homeclowd.backend.service.ConfigServiceImpl;
import eu.kunas.homeclowd.frontend.template.TemplatePage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by ramazan on 05.05.15.
 */
@AuthorizeInstantiation("ADMIN")
public class Settings extends TemplatePage {

    @SpringBean
    private ConfigServiceImpl configServiceImpl;

    public HCConfigEntity configEntity;



    public Settings(final PageParameters parameters) {
        super("HOMECLOWD - SETTINGS");
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        configEntity = configServiceImpl.getAllHashMap().get("FOLDER_URL");

        if(configEntity == null){
            configEntity = new HCConfigEntity();
            configEntity.setKey("FOLDER_URL");
        }

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form settingsForm = new Form("settingsForm"){

            @Override
            protected void onSubmit() {

                configEntity = configServiceImpl.save(configEntity);
                setResponsePage(MediaPage.class);
            }
        };

        settingsForm.setModel(new CompoundPropertyModel(this));

        settingsForm.add(new TextField("configEntity.value"){
            @Override
            public boolean isEnabled() {
                return true;
            }
        });


        add(settingsForm);
    }
}
