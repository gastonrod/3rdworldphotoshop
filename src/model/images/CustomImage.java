package model.images;

import javafx.scene.image.WritableImage;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface CustomImage {


    void save(@NotNull File file) throws IOException;
    WritableImage asWritableImage();
    void modifyPixel(@NotNull int value, @NotNull Point pos);
    void copySection(@NotNull CustomImage destinationImage,
                     @NotNull Point x1y1, @NotNull Point x2y2, @NotNull Point destinationPos);

    void pasteImage(Color[][] newColors, @NotNull Point pos);

    int[] getAverage(@NotNull Point p1, @NotNull Point p2);
    void markArea(@NotNull Point p1, @NotNull Point p2);

    ArrayList<CustomImage> getHSVRepresentations();

    Color[][] getRGBRepresentation();

    // Amount of time a color is repeated
    int[] getColorsRepetition();

    int getAmountOfPixels();
}
