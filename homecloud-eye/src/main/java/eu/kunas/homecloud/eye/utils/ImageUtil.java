package eu.kunas.homecloud.eye.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created by Kunas on 03.08.2015.
 */
public class ImageUtil {

    /**
     * Writes Timestamp to the current image
     *
     * @param image
     * @return
     */
    public static BufferedImage writeTime(BufferedImage image, int width, int height) {
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
                + localDateTime.getSecond(), width - 190, height - 30);
        return image;
    }

    public static double diff(BufferedImage img1, BufferedImage img2) {
        // long start = System.currentTimeMillis();

        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);

        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);

        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }

        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;

        // long stop = System.currentTimeMillis();

        double percent = (p * 100.0);
        
        // System.out.println(stop - start + " -  diff percent: " + percent);

        return percent;
    }
}
