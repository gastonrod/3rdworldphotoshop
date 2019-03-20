package model.images;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class RawImage extends AbstractImage {

    // Constructor used by the image factory
    public RawImage(byte[][] reds, byte[][] greens, byte[][] blues, byte[][] alphas){
        width = reds[0].length;
        height = reds.length;
        imageRedBytes = reds;
        imageGreenBytes = greens;
        imageBlueBytes = blues;
        alphaBytes = alphas;
        createImage();
    }

    // Grayscale raw image
    public RawImage(@NotNull File file) {
        setWidthAndHeight(file.getName());

        try (FileInputStream fileInputStream = new FileInputStream(file)){
            imageRedBytes = new byte[height][width];
            imageGreenBytes = new byte[height][width];
            imageBlueBytes = new byte[height][width];
            alphaBytes = new byte[height][width];
            for(int n = 0; n < width * height * 4; n+=4) {
                byte currentPixel = (byte) fileInputStream.read();
                imageRedBytes[(n/4)/width][(n/4)%width] = currentPixel;
                imageGreenBytes[(n/4)/width][(n/4)%width] = currentPixel;
                imageBlueBytes[(n/4)/width][(n/4)%width] = currentPixel;
                alphaBytes[(n/4)/width][(n/4)%width] = (byte)255;
            }
            createImage();
        } catch (IOException e) {
           throw new RuntimeException("Error loading RAW image: " + e.getMessage());
        }
    }
}
