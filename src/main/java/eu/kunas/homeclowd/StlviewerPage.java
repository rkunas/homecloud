package eu.kunas.homeclowd;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.template.TemplatePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
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
public class StlviewerPage extends TemplatePage {

    public StlviewerPage(final PageParameters parameters, MediaDto mediaDto) {
        super("HOMECLOWD - STL Viewer");
    }

    public StlviewerPage(){
        super("STL - VIEWER");
    }

}
