package model.managers;

import model.images.CustomImage;
import model.images.FormattedImage;
import model.images.RawImage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static CustomImage openImage(@NotNull File file) {
        String extension = file.getName().split("\\.")[1];
        switch(extension){
            case "raw":
                return new RawImage(file);
            case "pgm":
            case "ppm":
                return new FormattedImage(file);
            default:
                throw new RuntimeException("FileManager.openMainImage() called with an unsupported format. Filename: " + file.getName());
        }
    }

    public static void saveImage(@NotNull File file, @NotNull CustomImage image) {
        try{
            image.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Problem saving file.", e);
        }
    }
}
