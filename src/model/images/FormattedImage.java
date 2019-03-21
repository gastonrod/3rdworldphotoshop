package model.images;

import org.apache.sanselan.Sanselan;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class FormattedImage extends AbstractImage {

    // PGM and PPM Images
    public FormattedImage(@NotNull File file) {
        try {
            BufferedImage bufferedImage = Sanselan.getBufferedImage(file);
            height = bufferedImage.getHeight();
            width = bufferedImage.getWidth();

            colors = new Color[height][width];
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    colors[i][j] = new Color(bufferedImage.getRGB(j, i));
                }
            }
            createImage();
        } catch (Exception e) {
            throw new RuntimeException("Problem opening PPM file " + file.getName(), e);
        }

    }
}
