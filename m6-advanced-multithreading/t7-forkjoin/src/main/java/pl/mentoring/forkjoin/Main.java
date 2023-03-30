package pl.mentoring.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String moduleDir = "m6-advanced-multithreading\\t7-forkjoin\\";

        String srcName = "red-tulips.jpg";
        File srcFile = new File(moduleDir + srcName);
        logger.info(srcFile.getAbsolutePath());
        BufferedImage image = ImageIO.read(srcFile);

        logger.info("Source image: {}", srcName);

        BufferedImage blurredImage = blur(image);

        String dstName = "blurred-tulips.jpg";
        File dstFile = new File(moduleDir + dstName);
        ImageIO.write(blurredImage, "jpg", dstFile);

        logger.info("Output image: {}", dstName);
    }

    public static BufferedImage blur(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];

        logger.info("Array size is {}", src.length);
        logger.info("Threshold is {}", ForkBlur.sThreshold);

        int processors = Runtime.getRuntime().availableProcessors();
        logger.info("{} processors are available", processors);

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();

        logger.info("Image blur took {} milliseconds.", endTime - startTime);

        BufferedImage dstImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);

        return dstImage;
    }
}
