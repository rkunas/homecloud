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
public class VideoProducerResource extends AbstractResource {

    private static final Pattern RANGE_PATTERN = Pattern.compile("bytes=(?<start>\\d*)-(?<end>\\d*)");
    private static final int BUFFER_LENGTH = 1024 * 16;


    @SpringBean
    private ConfigServiceImpl configService;

    @SpringBean
    private FilesFolderServiceImpl filesFolderService;

    @Override
    protected ResourceResponse newResourceResponse(Attributes a) {

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("video/mp4");
        resourceResponse.setTextEncoding("utf-8");

        resourceResponse.setWriteCallback(new WriteCallback() {

            @Override
            public void writeData(Attributes attributes) throws IOException {

                String attr = attributes.getParameters().get("videofile").toString();

                String filesString = configService.getAllHashMap().get("FOLDER_URL").getValue() + attr;

                //byte[] fileBytes = filesFolderService.readFile(filesString);

                Path video = Paths.get(filesString);

                OutputStream outputStream = attributes.getResponse().getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                int length = (int) Files.size(video);
                int start = 0;
                int end = length - 1;

                // Class Cast Exception !!!!!!!!!
                Request req = attributes.getRequest();

                String range = ((HttpServletRequest) req).getHeader("Range");
                Matcher matcher = RANGE_PATTERN.matcher(range);

                if (matcher.matches()) {
                    String startGroup = matcher.group("start");
                    start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
                    start = start < 0 ? 0 : start;

                    String endGroup = matcher.group("end");
                    end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
                    end = end > length - 1 ? length - 1 : end;
                }

                int contentLength = end - start + 1;
                WebResponse response = (WebResponse) attributes.getResponse();
                response.reset();
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
                response.setHeader("Content-Length", String.format("%s", contentLength));

                int bytesRead;
                int bytesLeft = contentLength;
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);

                try (SeekableByteChannel input = Files.newByteChannel(video, READ);
                     OutputStream output = response.getOutputStream()) {

                    input.position(start);

                    while ((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
                        buffer.clear();
                        output.write(buffer.array(), 0, bytesLeft < bytesRead ? bytesLeft : bytesRead);
                        bytesLeft -= bytesRead;
                    }
                }
            }
        });

        return resourceResponse;

    }
}
