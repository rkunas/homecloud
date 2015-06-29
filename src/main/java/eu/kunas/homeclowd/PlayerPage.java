package eu.kunas.homeclowd;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.template.TemplatePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.wicketstuff.html5.media.MediaSource;
import org.wicketstuff.html5.media.video.Html5Video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramazan on 13.04.15.
 */
public class PlayerPage extends TemplatePage {

    private MediaDto selectedMedia;

    @SpringBean
    private ConfigServiceImpl configService;

    public PlayerPage(final PageParameters parameters, MediaDto mediaDto) {
        super("HOMECLOWD - Player");
        setVersioned(false);
        selectedMedia = mediaDto;

        setDefaultModel(new CompoundPropertyModel<Object>(selectedMedia));

        String contextPath = WebApplication.get().getServletContext().getContextPath();

        final List<MediaSource> mm = new ArrayList<>();

        mm.add(new MediaSource(contextPath + "/stream?video=" + selectedMedia.getAbsolutePath().replace(configService.getAllHashMap().get("FOLDER_URL").getValue(), ""), "video/mp4"));
        mm.add(new MediaSource(contextPath + "/stream?video=" + selectedMedia.getAbsolutePath().replace(configService.getAllHashMap().get("FOLDER_URL").getValue(), ""), "video/ogg"));
        mm.add(new MediaSource(contextPath + "/stream?video=" + selectedMedia.getAbsolutePath().replace(configService.getAllHashMap().get("FOLDER_URL").getValue(), ""), "video/webm"));

        IModel<List<MediaSource>> mediaSourceList = new AbstractReadOnlyModel<List<MediaSource>>() {

            private static final long serialVersionUID = 1L;

            public List<MediaSource> getObject() {
                return mm;
            }
        };

        add(new DownloadLink("downloadButton", new File(selectedMedia.getAbsolutePath()), "download") {
            @Override
            public void onClick() {
                File file = (File) getModelObject();
                IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));

                getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(resourceStream, file.getName()));
            }
        });

        add(new Html5Video("currentVideoPlayer", mediaSourceList) {

            private static final long serialVersionUID = 1L;

            @Override
            protected boolean isControls() {
                return true;
            }

            @Override
            protected boolean isAutoPlay() {
                return false;
            }

            @Override
            protected boolean isAutoBuffer() {
                return true;
            }


        });

        add(new Label("filename", "File: " + selectedMedia.getDescription()));
    }

}
