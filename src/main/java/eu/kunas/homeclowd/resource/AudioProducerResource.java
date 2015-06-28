package eu.kunas.homeclowd.resource;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.util.data.audio.AudioPlayer;
import org.apache.wicket.request.resource.AbstractResource;

import javax.sound.sampled.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by ramazan on 28.06.15.
 */
public class AudioProducerResource extends AbstractResource {

    private MaryInterface maryInterface = null;

    public AudioProducerResource() {
        try {
            this.maryInterface = new LocalMaryInterface();
            Set<String> voices = maryInterface.getAvailableVoices();
            maryInterface.setVoice(voices.iterator().next());

        } catch (Exception exc) {
            exc.printStackTrace();
        }
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

                String talk = "";

                if(id.contains("1")){
                    talk = "Welcome to youre Cloud !";
                }

                try {

                    AudioInputStream audioInputStream = maryInterface.generateAudio(talk);

                    AudioFormat audioFormat = audioInputStream.getFormat();

                    int size = (int) (audioFormat.getFrameSize() * audioInputStream.getFrameLength());
                    byte[] audioArray = new byte[size];

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,baos);

                    OutputStream outputStream = attributes.getResponse().getOutputStream();
                    BufferedOutputStream buff = new BufferedOutputStream(outputStream);

                    buff.write(baos.toByteArray());

                    buff.close();

                }catch (Exception exc){
                    exc.printStackTrace();
                }

            }
        });

        return resourceResponse;
    }
}
