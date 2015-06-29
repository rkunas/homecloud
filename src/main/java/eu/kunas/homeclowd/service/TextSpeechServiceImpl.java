package eu.kunas.homeclowd.service;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by ramazan on 29.06.15.
 */
@Service("textSpeechService")
public class TextSpeechServiceImpl {

    public byte[] speech(String text){

        MaryInterface maryInterface = null;
        try {
            maryInterface = new LocalMaryInterface();
            Set<String> voices = maryInterface.getAvailableVoices();
            maryInterface.setVoice(voices.iterator().next());

            AudioInputStream audioInputStream = maryInterface.generateAudio(text);

            AudioFormat audioFormat = audioInputStream.getFormat();

            int size = (int) (audioFormat.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audioArray = new byte[size];

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, baos);

            return baos.toByteArray();

        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
