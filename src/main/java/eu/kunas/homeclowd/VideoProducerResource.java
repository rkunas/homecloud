package eu.kunas.homeclowd;

import eu.kunas.homeclowd.service.ConfigServiceImpl;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.*;

/**
 * Created by ramazan on 23.06.15.
 */
public class VideoProducerResource extends ByteArrayResource {



    @SpringBean
    private ConfigServiceImpl configService;

    public VideoProducerResource(){
        super("video/mp4");
    }


    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {

        String rootFolder = configService.getAllHashMap().get("FOLDER_URL").getValue();

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("video/mp4");
        resourceResponse.setTextEncoding("utf-8");



        resourceResponse.setWriteCallback(new WriteCallback()
        {


            @Override
            public void writeData(Attributes attributes) throws IOException
            {
                String attr = attributes.getParameters().get("videofile").toString();

                FileInputStream fileInputStream = null;
                byte[] fileBytes = null;

                try {

                    File file = new File(configService.getAllHashMap().get("FOLDER_URL").getValue() + attr);

                    fileBytes = new byte[(int) file.length()];

                    fileInputStream = new FileInputStream(file);
                    fileInputStream.read(fileBytes);
                    fileInputStream.close();
                    resourceResponse.setContentLength(fileBytes.length);




                } catch (Exception exc) {
                    exc.printStackTrace();
                }



                OutputStream outputStream = attributes.getResponse().getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                buff.write(fileBytes);

                buff.close();


            }
        });

        return resourceResponse;

    }
}
