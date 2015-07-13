package eu.kunas.homeclowd.backend.service;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.language.tr.TurkishConfig;
import marytts.util.data.audio.AudioPlayer;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.util.Set;

/**
 * Created by ramazan on 29.06.15.
 */
@Service("textSpeechService")
public class TextSpeechServiceImpl {

    public void speechDirectly(String text) {
        MaryInterface marytts = null;

        try {
            marytts = new LocalMaryInterface();
            marytts.setVoice(TurkishConfig.getVoiceConfigs().iterator().next().getName());
            Set<String> voices = marytts.getAvailableVoices();
            marytts.setVoice(voices.iterator().next());

            AudioInputStream audio = marytts.generateAudio(text);
            AudioPlayer player = new AudioPlayer(audio);
            player.start();
            player.join();

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public byte[] speech(String text) {

        MaryInterface maryInterface = null;
        try {

            maryInterface = new LocalMaryInterface();
            Set<String> voices = maryInterface.getAvailableVoices();
            maryInterface.setVoice(voices.iterator().next());

            AudioInputStream audioInputStream = maryInterface.generateAudio(text);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, baos);

            return baos.toByteArray();
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
