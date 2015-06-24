package eu.kunas.homeclowd;

import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.service.FilesFolderServiceImpl;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ramazan on 23.06.15.
 */
public class VideoProducerResource extends ByteArrayResource {


    public final VideoBin videoBin = new VideoBin();

    @SpringBean
    private ConfigServiceImpl configService;

    @SpringBean
    private FilesFolderServiceImpl filesFolderService;

    public VideoProducerResource() {
        super("video/mp4");
    }

    @Override
    protected byte[] getData(Attributes attributes) {

        System.out.println("getData()");

        return super.getData(attributes);
    }

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {

        String rootFolder = configService.getAllHashMap().get("FOLDER_URL").getValue();

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("video/mp4");
        resourceResponse.setTextEncoding("pplication/octet-stream");

        resourceResponse.setWriteCallback(new WriteCallback() {

            @Override
            public void writeData(Attributes attributes) throws IOException {
                String attr = attributes.getParameters().get("videofile").toString();

                String filesString = configService.getAllHashMap().get("FOLDER_URL").getValue() + attr;

                videoBin.vbin = filesFolderService.readFile(filesString);

                OutputStream outputStream = attributes.getResponse().getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                buff.write(videoBin.vbin);

                buff.close();

            }
        });

        if (videoBin.vbin != null) {
            resourceResponse.setContentLength(videoBin.vbin.length);
        }

        return resourceResponse;

    }
}
