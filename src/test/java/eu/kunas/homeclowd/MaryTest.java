package eu.kunas.homeclowd;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.util.data.audio.AudioPlayer;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import java.util.Set;

/**
 * Created by developer on 27.06.15.
 */
public class MaryTest {

    @Test
    public void testMary() throws Exception {
        MaryInterface marytts = new LocalMaryInterface();
        Set<String> voices = marytts.getAvailableVoices();
        marytts.setVoice(voices.iterator().next());
        AudioInputStream audio = marytts.generateAudio("System is running and online!");
        AudioPlayer player = new AudioPlayer(audio);
        player.start();
        player.join();
    }
}
