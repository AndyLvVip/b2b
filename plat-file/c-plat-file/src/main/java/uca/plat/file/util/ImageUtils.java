package uca.plat.file.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    public static void addImageWatermark(File watermarkFile, File srcFile, File destFile) {
        try {
            BufferedImage watermarkImage = ImageIO.read(watermarkFile);
            BufferedImage srcImage = ImageIO.read(srcFile);

            Graphics2D g2d = (Graphics2D) srcImage.getGraphics();
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
            g2d.setComposite(alphaComposite);

            int topLeftX = (srcImage.getWidth() - watermarkImage.getWidth()) / 2;
            int topLeftY = (srcImage.getHeight() - watermarkImage.getHeight()) / 2;

            g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);
            String suffix = "";
            String srcFilename = srcFile.getName();
            if(srcFilename.contains(".")) {
                suffix = srcFilename.substring(srcFilename.lastIndexOf(".") + 1);
            }
            ImageIO.write(srcImage, suffix, destFile);
            g2d.dispose();

            LOG.info("The watermark is added to the image => " + srcFile.getAbsolutePath());
        } catch (IOException e) {
            LOG.error("Failed to add watermark to the image", e);
        }
    }
}
