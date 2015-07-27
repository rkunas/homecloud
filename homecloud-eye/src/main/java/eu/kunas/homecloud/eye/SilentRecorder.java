package eu.kunas.homecloud.eye;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by ramazan on 27.07.15.
 */
public class SilentRecorder {

    Webcam webcam;
    IMediaWriter writer;
    Dimension size;


    public void openCam(int i) {

        if (webcam == null) {
            size = new Dimension(640,480);
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);
            webcam.open(true);
        }
    }


    public void record(int j) throws Exception {

        if (webcam == null) {
            return;
        }

        if(webcam.getImage()==null){
            return;
        }

        File file = new File("output" + j + ".mp4");

        writer = ToolFactory.makeWriter(file.getName());

        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);


        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {

            System.out.println("Capture frame " + i);

            BufferedImage capturedImage = webcam.getImage();
            if (capturedImage != null) {
                BufferedImage image = ConverterFactory.convertToType(capturedImage, BufferedImage.TYPE_3BYTE_BGR);
                IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

                IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
                frame.setKeyFrame(i == 0);
                frame.setQuality(0);

                writer.encodeVideo(0, frame);

                // 10 FPS
                Thread.sleep(10);
            }

        }

        writer.close();


    }
}
