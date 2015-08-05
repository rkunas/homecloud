package eu.kunas.homecloud.eye;

import com.github.sarxos.webcam.Webcam;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ramazan on 27.07.15.
 */
public class Recorder implements CommandLineRunner {

    private double moves = 0;

    @Override
    public void run(String... strings) throws Exception {

    }

    @Async
    public void startRecord(String folder) {
        while (true) {
            try {
                init(folder);
                openCam();
                record();
            } catch (Exception exc) {
                // Do nothing.
            }
        }
    }

    @Async
    public void compareImage(BufferedImage img1, BufferedImage img2) {
        moves = ImageDiffUtil.diff(img1, img2);
    }

    public Webcam webcam;
    private IMediaWriter writer;
    private Dimension size;

    private String folder;

    public void init(String folder) {
        this.folder = folder;
        System.out.println(folder);
    }

    public void openCam() {

        if (webcam == null) {
            size = new Dimension(640, 480);
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);
            webcam.open(true);
        }
    }

    public StringBuffer createFilename() {
        LocalDateTime time = LocalDateTime.now();

        StringBuffer filename = new StringBuffer();
        filename.append("video-");
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

    private BufferedImage temp;

    protected BufferedImage writeTime(BufferedImage image) {
        Graphics2D gO = image.createGraphics();
        gO.setColor(Color.WHITE);
        gO.setFont(new Font("SansSerif", Font.BOLD, 18));

        LocalDateTime localDateTime = LocalDateTime.now();

        gO.drawString(localDateTime.getDayOfMonth()
                + "."
                + localDateTime.getMonthValue()
                + "."
                + localDateTime.getYear()
                + " "
                + localDateTime.getHour()
                + ":"
                + localDateTime.getMinute()
                + ":"
                + localDateTime.getSecond(), size.width - 190, size.height - 30);
        return image;
    }

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

        long start = System.currentTimeMillis();

        int recordTimer = 0;

        int frametimer = 0;

        int pictimer = 1;

        while (file.length() < 10000000) {
            BufferedImage capturedImage = webcam.getImage();
            if (capturedImage != null) {
                BufferedImage image = ConverterFactory.convertToType(capturedImage, BufferedImage.TYPE_3BYTE_BGR);

                if (temp == null) {
                    temp = image;
                }

                // Compare every 20 Pictures
                if (recordTimer == 20) {
                    compareImage(temp, image);
                    recordTimer = 0;
                    temp = image;
                }

                if (moves > 1.5) {
                    image = writeTime(image);
                    System.out.println("Aufnahme " + moves);
                    IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

                    IVideoPicture frame = converter.toPicture(image, pictimer * 2000);
                    frame.setKeyFrame(frametimer == 0);
                    frame.setQuality(0);

                    writer.encodeVideo(0, frame);
                    pictimer++;
                } else {
                    System.out.println("Keine Aufnahme " + moves);
                }

                frametimer++;
                recordTimer++;

                // 10 FPS
                Thread.sleep(10);
            }
        }

        writer.close();
    }
}
