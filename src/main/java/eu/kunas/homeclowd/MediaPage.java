package eu.kunas.homeclowd;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.service.FilesFolderServiceImpl;
import eu.kunas.homeclowd.template.TemplatePage;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by ramazan on 17.04.15.
 */
public class MediaPage extends TemplatePage {

    @SpringBean
    private FilesFolderServiceImpl filesFolderService;

    private List<MediaDto> medias;

    private MediaDto selected;

    public MediaPage(final PageParameters parameters) {
        super("HOMECLOWD - MEDIA");

        List<MediaDto> mediasModel = Collections.emptyList();

        add(new Label("folder",new PropertyModel<MediaDto>(new MediaDto(),"absolutPath")));

        add(new ListView<MediaDto>("medias", mediasModel) {
            @Override
            protected void populateItem(ListItem<MediaDto> item) {
                item.add(new Label("description", new PropertyModel(item.getModel(), "description")));
                item.add(new Label("type", new PropertyModel(item.getModel(), "type")));
                item.add(new Label("size", new PropertyModel(item.getModel(),"size")));
                item.add(new DownloadLink("downloadButton", new File(item.getModel().getObject().getAbsolutePath()), "download") {
                    @Override
                    public void onClick() {
                        File file = (File) getModelObject();
                        IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));

                        getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(resourceStream, file.getName()));

                    }

                    @Override
                    public boolean isVisible() {
                        if (item.getModel().getObject().getType().contains("Folder")) {
                            return false;
                        }
                        return true;
                    }

                });

                item.add(new Form<MediaDto>("viewFolderForm"){
                    @Override
                    protected void onSubmit() {

                    }
                }.setDefaultModel(item.getModel()));



                item.add(new Link<Void>("viewFolderButton") {
                    @Override
                    public void onClick() {

                    }

                    @Override
                    public boolean isVisible() {
                        if (item.getModel().getObject().getType().contains("File")) {
                            return false;
                        }
                        return true;
                    }

                });
            }
        });

    }

    @Override
    protected void onConfigure() {
        AuthenticatedWebApplication app = (AuthenticatedWebApplication) AuthenticatedWebApplication.get();

        if (!AuthenticatedWebSession.get().isSignedIn())
            app.restartResponseAtSignInPage();

        if(selected == null) {
            refill(null);
        }

    }

    public void refill(File f){
        ListView<MediaDto> listView = (ListView<MediaDto>) get("medias");
        listView.getModel().getObject().clear();
        listView.setModelObject(filesFolderService.getFolderItems(f));
    }


    public List<MediaDto> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaDto> medias) {
        this.medias = medias;
    }
}
