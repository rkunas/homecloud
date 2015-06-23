package eu.kunas.homeclowd;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.template.TemplatePage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.html5.media.MediaSource;
import org.wicketstuff.html5.media.video.Html5Video;

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

        String contextPath = WebApplication.get().getServletContext().getContextPath();

        final List<MediaSource> mm = new ArrayList<>();

        mm.add(new MediaSource(contextPath + "/videoroot?videofile=" + selectedMedia.getAbsolutePath().replace(configService.getAllHashMap().get("FOLDER_URL").getValue(), ""), "video/mp4"));
        mm.add(new MediaSource(contextPath + "/videoroot?videofile=" + selectedMedia.getAbsolutePath().replace(configService.getAllHashMap().get("FOLDER_URL").getValue(), ""), "video/ogg"));
        mm.add(new MediaSource(contextPath + "/videoroot?videofile=" + selectedMedia.getAbsolutePath().replace(configService.getAllHashMap().get("FOLDER_URL").getValue(), ""), "video/webm"));

        IModel<List<MediaSource>> mediaSourceList = new AbstractReadOnlyModel<List<MediaSource>>() {

            private static final long serialVersionUID = 1L;

            public List<MediaSource> getObject() {
                return mm;
            }
        };

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
    }

}
