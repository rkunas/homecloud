package eu.kunas.homeclowd;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.template.TemplatePage;
import org.apache.wicket.Component;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by ramazan on 22.06.15.
 */
public class CurrentVideo extends TemplatePage {


    @SpringBean
    private ConfigServiceImpl configService;

    private byte[] fileBytes;

    private StringValue fileParam = null;

    public CurrentVideo(final PageParameters parameters) {
        super("HOMECLOWD - Video");
        setVersioned(false);

        FileInputStream fileInputStream = null;

        fileParam = parameters.get("videofile");

        try {

            File file = new File(configService.getAllHashMap().get("FOLDER_URL").getValue() + fileParam);

            fileBytes = new byte[(int) file.length()];

            fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileBytes);
            fileInputStream.close();


        } catch (Exception exc) {
            exc.printStackTrace();
        }


        getRequestCycle().scheduleRequestHandlerAfterCurrent(new IRequestHandler() {
            @Override
            public void respond(IRequestCycle iRequestCycle) {

            }

            @Override
            public void detach(IRequestCycle iRequestCycle) {
                try {
                    Response response = iRequestCycle.getResponse();

                    response.write(fileBytes);

                    response.close();
                }
                catch (Exception exc){

                }

            }
        });

    }


}
