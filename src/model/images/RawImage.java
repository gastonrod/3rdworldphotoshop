package model.images;

import model.Utils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;

public class RawImage extends AbstractImage {

    // Constructor used by the image factory
    public RawImage(Color[][] colors) {
        this.colors = colors;
        width = colors[0].length;
        height = colors.length;
        createImage();
    }

    public RawImage(byte[][] reds, byte[][] greens, byte[][] blues) {
        width = reds[0].length;
        height = reds.length;
        colors = new Color[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                colors[i][j] = new Color(
                        Utils.byteToInt(reds[i][j]),
                        Utils.byteToInt(greens[i][j]),
                        Utils.byteToInt(blues[i][j])
                );
            }
        }
        createImage();
    }

    // Grayscale raw image
    public RawImage(@NotNull File file) {
        setWidthAndHeight(file.getName());

        try (FileInputStream fileInputStream = new FileInputStream(file)){
            colors = new Color[height][width];
            for(int n = 0; n < width * height * 4; n+=4) {
                byte currentPixel = (byte) fileInputStream.read();
                colors[(n/4)/width][(n/4)%width] = new Color(
                        Utils.byteToInt(currentPixel),
                        Utils.byteToInt(currentPixel),
                        Utils.byteToInt(currentPixel)
                        );
            }
            createImage();
        } catch (IOException e) {
           throw new RuntimeException("Error loading RAW image: " + e.getMessage());
        }
    }
}
