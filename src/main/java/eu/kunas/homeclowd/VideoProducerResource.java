package eu.kunas.homeclowd;

import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.service.FilesFolderServiceImpl;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ramazan on 23.06.15.
 */
public class VideoProducerResource extends AbstractResource {

    @SpringBean
    private ConfigServiceImpl configService;

    @SpringBean
    private FilesFolderServiceImpl filesFolderService;

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {

        String rootFolder = configService.getAllHashMap().get("FOLDER_URL").getValue();

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("video/mp4");
        resourceResponse.setTextEncoding("application/octet-stream");

        resourceResponse.setWriteCallback(new WriteCallback() {

            @Override
            public void writeData(Attributes attributes) throws IOException {
                String attr = attributes.getParameters().get("videofile").toString();

                String filesString = configService.getAllHashMap().get("FOLDER_URL").getValue() + attr;

                OutputStream outputStream = attributes.getResponse().getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                buff.write(filesFolderService.readFile(filesString));

                buff.close();

            }
        });

        return resourceResponse;

    }
}
