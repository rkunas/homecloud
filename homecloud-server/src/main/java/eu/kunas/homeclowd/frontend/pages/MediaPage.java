package eu.kunas.homeclowd.frontend.pages;

import eu.kunas.homeclowd.backend.service.FilesFolderServiceImpl;
import eu.kunas.homeclowd.common.model.dto.MediaDto;
import eu.kunas.homeclowd.frontend.pages.player.PlayerPage;
import eu.kunas.homeclowd.frontend.pages.player.StlviewerPage;
import eu.kunas.homeclowd.frontend.template.TemplatePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
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

    private MediaDto previous;

    private MediaDto selected;

    public MediaPage(final PageParameters parameters) {
        super("HOMECLOWD - MEDIA");

        List<MediaDto> mediasModel = Collections.emptyList();

        Form mediaDropzone = new Form("mediaDropzone");

        mediaDropzone.add(AttributeModifier.replace("action", "fileupload?uploadfolder=" + "/"));

        add(mediaDropzone);

        add(new Label("folder", new Model("/")));

        add(new ListView<MediaDto>("medias", mediasModel) {
            @Override
            protected void populateItem(ListItem<MediaDto> item) {
                item.add(new Label("description", new PropertyModel(item.getModel(), "description")));

                item.add(new Label("modified", new PropertyModel(item.getModel(), "modified")));
                
                Label type = new Label("type");

                if(item.getModel().getObject().getType().equals("Folder")){
                   type.add(AttributeModifier.replace("class","glyphicon glyphicon-folder-close"));
                }
                if(item.getModel().getObject().getType().equals("File")){
                    type.add(AttributeModifier.replace("class","glyphicon glyphicon-file"));
                }

                item.add(type);
                item.add(new Label("size", new PropertyModel<Long>(item.getModel(), "size")) {
                    @Override
                    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
                        Long currentLabelsVal = (Long) getDefaultModelObject();
                        if (currentLabelsVal == 0) {
                            replaceComponentTagBody(markupStream, openTag, "--");
                        } else {
                            if (currentLabelsVal < 1024) {
                                replaceComponentTagBody(markupStream, openTag, currentLabelsVal + " Byte");
                            }
                            if (currentLabelsVal <= 1024000 && currentLabelsVal > 1024) {
                                replaceComponentTagBody(markupStream, openTag, String.format("%.2f%n", new Double(currentLabelsVal / 1024D)) + " Kilobyte");

                            }
                            if (currentLabelsVal <= 1024000000 && currentLabelsVal > 1024000) {
                                replaceComponentTagBody(markupStream, openTag, String.format("%.2f%n", new Double(currentLabelsVal / 1024D / 1024D)) + " Megabyte");

                            }
                            if (currentLabelsVal <= 1024000000000L && currentLabelsVal > 1024000000) {
                                replaceComponentTagBody(markupStream, openTag, String.format("%.2f%n", new Double(currentLabelsVal / 1024D / 1024D / 1024D)) + " Gigabyte");
                            }

                            if (currentLabelsVal >= 1024000000001L) {
                                super.onComponentTagBody(markupStream, openTag);
                            }
                        }
                    }
                });

                item.add(new Link("deleteButton") {
                    @Override
                    public void onClick() {
                        filesFolderService.deleteFile(item.getModelObject());
                        setResponsePage(MediaPage.this);
                    }


                });

                
                item.add(new DownloadLink("downloadButton", new File(item.getModel().getObject().getAbsolutePath()), "download") {
                    @Override
                    public void onClick() {
                        File file = (File) getModelObject();
                        IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));

                        getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(resourceStream, file.getName()));

                    }

                    @Override
                    public boolean isVisible() {
                        if (item.getModel().getObject().getType().contains("Folder") || item.getModel().getObject().getDescription().endsWith(".mp4") || item.getModel().getObject().getDescription().endsWith(".stl")) {
                            return false;
                        }
                        return true;
                    }

                });

                item.add(new Link("playVideoButton") {
                    @Override
                    public void onClick() {
                        PlayerPage newPlayerPage = new PlayerPage(parameters, item.getModelObject());

                        setResponsePage(newPlayerPage);
                    }

                    @Override
                    public boolean isVisible() {
                        if (item.getModel().getObject().getDescription().endsWith(".mp4") || item.getModel().getObject().getDescription().endsWith(".avi")) {
                            return true;
                        }
                        return false;
                    }

                });


                item.add(new Link("playStlButton" ) {
                    @Override
                    public void onClick() {
                        StlviewerPage stlviewerPage = new StlviewerPage(parameters, item.getModelObject());

                        stlviewerPage.getPageParameters().add("stlfile", item.getModel().getObject().getAbsolutePath().replace(filesFolderService.getRootFolderEntity().getValue(), ""));
                        setResponsePage(stlviewerPage);
                    }

                    @Override
                    public boolean isVisible() {
                        if (item.getModel().getObject().getDescription().endsWith(".stl")) {
                            return true;
                        }
                        return false;
                    }

                });

                item.add(new Link<Void>("viewFolderButton") {
                    @Override
                    public void onClick() {

                        previous = selected;
                        selected = item.getModelObject();
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

        if (selected == null) {

            refill(null);
        } else {
            remove("folder");

            String s = selected.getAbsolutePath().replace(filesFolderService.getRootFolderEntity().getValue(), "/");
            if (s.startsWith("//")) {
                s = s.replace("//", "/");
            }

            add(new Label("folder", new Model(s)));
            refill(new File(selected.getAbsolutePath()));

            Form mediaDropzone = (Form)get("mediaDropzone");

            mediaDropzone.add(AttributeModifier.replace("action", "fileupload?uploadfolder=" + s));

            add(mediaDropzone);


        }


    }

    public void refill(File to) {
        ListView<MediaDto> listView = (ListView<MediaDto>) get("medias");
       // listView.getModel().getObject().clear();

        listView.setModelObject(filesFolderService.getFolderItems(to));

    }


    public List<MediaDto> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaDto> medias) {
        this.medias = medias;
    }
}
