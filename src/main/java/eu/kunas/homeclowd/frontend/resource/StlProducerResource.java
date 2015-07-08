package eu.kunas.homeclowd.frontend.resource;

import eu.kunas.homeclowd.backend.service.ConfigServiceImpl;
import eu.kunas.homeclowd.backend.service.FilesFolderServiceImpl;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardOpenOption.READ;

/**
 * Created by ramazan on 23.06.15.
 */
public class StlProducerResource extends AbstractResource {



    @SpringBean
    private ConfigServiceImpl configService;

    @SpringBean
    private FilesFolderServiceImpl filesFolderService;

    @Override
    protected ResourceResponse newResourceResponse(Attributes a) {

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("application/vnd.ms-pkistl");
        resourceResponse.setTextEncoding("utf-8");

        resourceResponse.setWriteCallback(new WriteCallback() {

            @Override
            public void writeData(Attributes attributes) throws IOException {

                String attr = attributes.getParameters().get("stlfile").toString();

                String filesString = configService.getAllHashMap().get("FOLDER_URL").getValue() + attr;

                byte[] fileBytes = filesFolderService.readFile(filesString);

                OutputStream outputStream = attributes.getResponse().getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                WebResponse response = (WebResponse) attributes.getResponse();

                response.setContentLength(fileBytes.length);

                buff.write(fileBytes);

                buff.close();

            }
        });

        return resourceResponse;

    }
}
