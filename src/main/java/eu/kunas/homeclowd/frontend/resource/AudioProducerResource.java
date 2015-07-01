package eu.kunas.homeclowd.frontend.resource;

import eu.kunas.homeclowd.backend.service.TextSpeechServiceImpl;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ramazan on 28.06.15.
 */
public class AudioProducerResource extends AbstractResource {

    @SpringBean
    private TextSpeechServiceImpl textSpeechService;

    public AudioProducerResource() {

    }


    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("audio/wav");
        resourceResponse.setTextEncoding("utf-8");

        resourceResponse.setWriteCallback(new WriteCallback() {

            @Override
            public void writeData(Attributes attributes) throws IOException {

                String id = attributes.getParameters().get("audio").toString();

                byte[] audio = null;

                if (id.contains("1")) {
                    audio = textSpeechService.speech("Welcome to youre Cloud !");
                }

                OutputStream outputStream = attributes.getResponse().getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                buff.write(audio);

                buff.close();

            }
        });

        return resourceResponse;
    }
}
