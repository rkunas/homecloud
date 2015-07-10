package eu.kunas.homeclowd.frontend.pages.player;

import eu.kunas.homeclowd.common.model.dto.MediaDto;
import eu.kunas.homeclowd.frontend.template.TemplatePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

import java.io.File;

/**
 * Created by ramazan on 13.04.15.
 */
public class StlviewerPage extends TemplatePage {

    private MediaDto selectedMediaDto;

    public StlviewerPage(final PageParameters parameters, MediaDto mediaDto) {
        super("HOMECLOWD - STL Viewer");
        this.selectedMediaDto = mediaDto;

        add(new DownloadLink("downloadButton", new File(selectedMediaDto.getAbsolutePath()), "download") {
            @Override
            public void onClick() {
                File file = (File) getModelObject();
                IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));

                getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(resourceStream, file.getName()));
            }
        });

        add(new Label("filename", "File: " + selectedMediaDto.getDescription()));
    }

    public StlviewerPage(){
        super("STL - VIEWER");
    }

}
