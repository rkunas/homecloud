package eu.kunas.homeclowd.backend.service;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by ramazan on 13.07.15.
 */
public class TextSpeechServiceTest {

    private TextSpeechServiceImpl textSpeechService;

    @Before
    public void init(){
        textSpeechService = new TextSpeechServiceImpl();
    }

    @Test
    public void testSpeech(){
        textSpeechService.speech("Ayse i love you !");
    }

    @Test
    public void testSpeechDirectly(){
        textSpeechService.speechDirectly("Hello Aisha! I love you !");
    }
}
