package model.images;

import model.images.AbstractImage;
import org.apache.sanselan.Sanselan;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

public class FormattedImage extends AbstractImage {

    // PGM and PPM Images
    public FormattedImage(@NotNull File file) {
        try {
            BufferedImage bufferedImage = Sanselan.getBufferedImage(file);
            height = bufferedImage.getHeight();
            width = bufferedImage.getWidth();

            imageRedBytes  = new byte[height][width];
            imageBlueBytes = new byte[height][width];
            imageGreenBytes = new byte[height][width];
            alphaBytes = new byte[height][width];
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    Color color = new Color(bufferedImage.getRGB(j, i));
                    imageRedBytes[i][j] = (byte)color.getRed();
                    imageGreenBytes[i][j] = (byte)color.getGreen();
                    imageBlueBytes[i][j] = (byte)color.getBlue();
                    alphaBytes[i][j] = (byte)color.getAlpha();
                }
            }
            createImage();
        } catch (Exception e) {
            throw new RuntimeException("Problem opening PPM file " + file.getName(), e);
        }

    }

    @Override
    public float[][] getHSVRepresentation() {
        return new float[0][];
    }
}
