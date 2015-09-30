package eu.kunas.homecloud.eye.business;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import eu.kunas.homecloud.eye.utils.ImageUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;

/**
 * Created by ramazan on 27.07.15.
 */
public class RecorderService implements CommandLineRunner {
    
    public final Logger log = Logger.getLogger(RecorderService.class);

    private double moves = 0;

    @Override
    public void run(String... strings) throws Exception {

    }

    @Async
    public void startRecord(String folder,int width, int heigth) {
        while (true) {
            try {
                init(folder);
                openCam(width,heigth);
                record();
            } catch (Exception exc) {
                // Do nothing.
            }
        }
    }

    @Async
    public void compareImage(BufferedImage img1, BufferedImage img2) {
        moves = ImageUtil.diff(img1, img2);
    }

    public Webcam webcam;
    private IMediaWriter writer;
    private Dimension size;

    private String folder;

    public void init(String folder) {
        this.folder = folder;
        System.out.println(folder);
    }

    public void openCam(int width, int height) {

        Dimension[] nonStandardResolutions = new Dimension[] {
                new Dimension(width, height)
        };


        if (webcam == null) {
            webcam = Webcam.getDefault();
            webcam.setCustomViewSizes(nonStandardResolutions);
            webcam.setViewSize(new Dimension(width, height));
            webcam.open(true);
        }
    }

    public StringBuffer createFilename() {
        LocalDateTime time = LocalDateTime.now();

        StringBuffer filename = new StringBuffer();
        filename.append("Video-");
        filename.append(time.getDayOfMonth());
        filename.append("-");
        filename.append(time.getMonth());
        filename.append("-");
        filename.append(time.getYear());
        filename.append("_");
        filename.append(time.getHour());
        filename.append("-");
        filename.append(time.getMinute());
        filename.append("-");
        filename.append(time.getSecond());
        filename.append(".mp4");

        return filename;
    }

    private BufferedImage oldImage;

    /**
     * The Record Algorithm
     * 
     * @throws Exception 
     */
    public void record() throws Exception {

        if (webcam == null) {
            return;
        }

        if (webcam.getImage() == null) {
            return;
        }

        File file = new File(folder + createFilename().toString());

        writer = ToolFactory.makeWriter(file.getAbsolutePath());

        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);

        int compareTimer = 0;

        int keyfrmaeTimer = 0;

        int picTimer = 1;

        while (file.length() < 10000000) {
            BufferedImage capturedImage = webcam.getImage();
            if (capturedImage != null) {
                BufferedImage image = ConverterFactory.convertToType(capturedImage, BufferedImage.TYPE_3BYTE_BGR);

                if (oldImage == null) {
                    oldImage = image;
                }

                // Compare every 20 Pictures
                if (compareTimer == 20) {
                    compareImage(oldImage, image);
                    compareTimer = 0;
                    oldImage = image;
                }

                // Maybe there are some moves in the current picture
                if (moves > 1.5) {
                    image = ImageUtil.writeTime(image, size.width, size.height);
                    log.info("Aufnahme " + moves);
                    IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

                    IVideoPicture frame = converter.toPicture(image, picTimer * 2000);
                    frame.setKeyFrame(keyfrmaeTimer == 0);
                    frame.setQuality(0);

                    writer.encodeVideo(0, frame);
                    picTimer++;
                } else {
                    log.info("Keine Aufnahme " + moves);
                }

                keyfrmaeTimer++;
                compareTimer++;

                // 10 FPS
                Thread.sleep(10);
            }
        }

        writer.close();
    }
}
