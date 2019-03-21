package model;

import model.images.CustomImage;

import java.awt.*;

public class SpatialOperator {

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
}
