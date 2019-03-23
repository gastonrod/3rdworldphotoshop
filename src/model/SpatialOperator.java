package model;

import model.images.CustomImage;
import model.transformers.*;

import java.awt.*;

public class SpatialOperator {

    private SpatialOperator() {}

    // TODO: Refactor for a 2 images color transformation, if it's worth it.
    public static CustomImage addImages(CustomImage im1, CustomImage im2) {
        Color[][] rgb1 = im1.getRGBRepresentation();
        Color[][] rgb2 = im2.getRGBRepresentation();
        if(rgb1.length > rgb2.length || rgb1[0].length > rgb2[0].length) {
            throw new IllegalArgumentException("Image 1 must be smaller than image 2!");
        }
        Color[][] rgbAux = new Color[rgb1.length][rgb1[0].length];
        for (int i = 0; i < rgbAux.length; i++) {
            for (int j = 0; j < rgbAux[0].length; j++) {
                int valR = rgb1[i][j].getRed() / 2 + rgb2[i][j].getRed() / 2;
                int valG = rgb1[i][j].getGreen() / 2 + rgb2[i][j].getGreen() / 2;
                int valB = rgb1[i][j].getBlue() / 2 + rgb2[i][j].getBlue() / 2;
                rgbAux[i][j] = new Color(valR, valG, valB);
            }
        }
        return CustomImageFactory.newImage(rgbAux);
    }

    public static CustomImage subtractImages(CustomImage im1, CustomImage im2) {
        Color[][] rgb1 = im1.getRGBRepresentation();
        Color[][] rgb2 = im2.getRGBRepresentation();
        if(rgb1.length != rgb2.length || rgb1[0].length != rgb2[0].length) {
            throw new IllegalArgumentException("Image 1 must be the same size as image 2!");
        }
        Color[][] rgbAux = new Color[rgb1.length][rgb1[0].length];
        for (int i = 0; i < rgbAux.length; i++) {
            for (int j = 0; j < rgbAux[0].length; j++) {
                int valR = Math.abs(rgb1[i][j].getRed() - rgb2[i][j].getRed());
                int valG = Math.abs(rgb1[i][j].getGreen() - rgb2[i][j].getGreen());
                int valB = Math.abs(rgb1[i][j].getBlue() - rgb2[i][j].getBlue());
                rgbAux[i][j] = new Color(valR, valG, valB);
            }
        }
        return CustomImageFactory.newImage(rgbAux);
    }

    public static CustomImage dynamicRange(CustomImage im) {
        DynamicRangeCompressionTransformer transformer = new DynamicRangeCompressionTransformer(im.getRGBRepresentation());
        return transform(im, transformer);
    }

    public static CustomImage negativeImage(CustomImage im) {
        NegativeTransformer transformer = new NegativeTransformer();
        return transform(im, transformer);
    }

    public static CustomImage setContrast(CustomImage mainImage, int value) {
        Contrast transformer = new Contrast(value);
        return transform(mainImage, transformer);
    }

    public static CustomImage setUmbral(CustomImage mainImage, int value) {
        Umbral transformer = new Umbral(value);
        return transform(mainImage,transformer);
    }

    private static CustomImage transform(CustomImage image, ColorTransformation transformer) {
        Color[][] rgbRepresentation = image.getRGBRepresentation();
        return CustomImageFactory.newImage(transform(rgbRepresentation, transformer));

    }

    private static Color[][] transform(Color[][] rgbRepresentation, ColorTransformation transformer) {
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                rgbRepresentation[i][j] = transformer.transform(rgbRepresentation[i][j]);
            }
        }
        return rgbRepresentation;
    }
}
