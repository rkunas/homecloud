package eu.kunas.homeclowd.frontend.pages.player;

import eu.kunas.homeclowd.common.dto.MediaDto;
import eu.kunas.homeclowd.frontend.template.TemplatePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
