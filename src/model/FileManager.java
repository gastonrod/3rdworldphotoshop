package model;

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
                return null; // TODO
            case "ppm":
                return null; // TODO
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
