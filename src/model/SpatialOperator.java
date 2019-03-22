package model;

import javafx.scene.image.WritableImage;
import model.images.CustomImage;

import java.awt.*;

public class SpatialOperator {

    private static int contrastUmbral = 16;

    public static CustomImage addImages(CustomImage im1, CustomImage im2) throws IllegalArgumentException {
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

    public static CustomImage subtractImages(CustomImage im1, CustomImage im2) throws IllegalArgumentException {
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
        Color[][] rgbRepresentation = im.getRGBRepresentation();
        int maxRed = -1;
        int maxBlue = -1;
        int maxGreen = -1;
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for(int j = 0; j < rgbRepresentation[0].length; j++) {
                maxRed = Math.max(maxRed, rgbRepresentation[i][j].getRed());
                maxGreen = Math.max(maxGreen, rgbRepresentation[i][j].getGreen());
                maxBlue = Math.max(maxBlue, rgbRepresentation[i][j].getBlue());
            }
        }
        // c = (L - 1) / log(1+R)
        double cRed = 254 / Math.log(1+maxRed);
        double cGreen = 254 / Math.log(1+maxGreen);
        double cBlue = 254 / Math.log(1+maxBlue);
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                // T(r) = c * log(1+r)
                int r = (int)(cRed   * Math.log(1+rgbRepresentation[i][j].getRed()));
                int g = (int)(cGreen * Math.log(1+rgbRepresentation[i][j].getGreen()));
                int b = (int)(cBlue  * Math.log(1+rgbRepresentation[i][j].getBlue()));
                rgbRepresentation[i][j] = new Color(r, g, b);
            }
        }
       return CustomImageFactory.newImage(rgbRepresentation);
    }

    public static CustomImage negativeImage(CustomImage im) {
        Color[][] rgbRepresentation = im.getRGBRepresentation();
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                int r = 255 - rgbRepresentation[i][j].getRed();
                int g = 255 - rgbRepresentation[i][j].getGreen();
                int b = 255 - rgbRepresentation[i][j].getBlue();
                rgbRepresentation[i][j] = new Color(r, g, b);
            }
        }
        return CustomImageFactory.newImage(rgbRepresentation);
    }

    public static CustomImage setContrast(CustomImage mainImage, int value) {
        Color[][] rgbRepresentation = mainImage.getRGBRepresentation();
        int r1 = Math.max(value - contrastUmbral, 0);
        int r2 = Math.min(value + contrastUmbral, 255);
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                int r = contrastValue(rgbRepresentation[i][j].getRed(), r1, r2);
                int g = contrastValue(rgbRepresentation[i][j].getGreen(), r1, r2);
                int b = contrastValue(rgbRepresentation[i][j].getBlue(), r1, r2);
                rgbRepresentation[i][j] = new Color(r, g, b);
            }
        }
        return CustomImageFactory.newImage(rgbRepresentation);
    }

    private static int contrastValue(int c, int r1, int r2){
        if(c < r1) {
           return (int)(c * 0.5);
        } else if (c < r2) {
            return  (int)(r1 * 0.5 + 3 * (c - r1));
        } else {
            return  (int)(r1 * 0.5 + 3 * (r2 - r1) + 0.5 * (c - r2));
        }
    }

    public static CustomImage setUmbral(CustomImage mainImage, int value) {
        Color[][] rgbRepresentation = mainImage.getRGBRepresentation();
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                int r = rgbRepresentation[i][j].getRed();
                int g = rgbRepresentation[i][j].getGreen();
                int b = rgbRepresentation[i][j].getBlue();
                int val = (r + g + b) / 3 > value ? 255 : 0;
//                System.out.println(r + ", " + g +", " + b);
//                System.out.println(val);
                rgbRepresentation[i][j] = new Color(val, val, val);
            }
        }
        return CustomImageFactory.newImage(rgbRepresentation);
    }
}
