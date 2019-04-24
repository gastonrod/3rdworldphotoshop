package model;

import model.images.CustomImage;
import model.images.RawImage;

import java.awt.*;

public class CustomImageFactory {
    private static int imageSize = 200; // 200x200 px

    private CustomImageFactory() {}
    public static CustomImage circle(){
        int centerX = imageSize /2;
        int centerY = imageSize /2;
        int radius = 75;
        byte[][] reds   = new byte[imageSize][imageSize];
        byte[][] greens = new byte[imageSize][imageSize];
        byte[][] blues  = new byte[imageSize][imageSize];
        for(int i = 0; i < imageSize; i++) {
            for(int j = 0; j < imageSize; j++) {
                if (Math.sqrt(Math.pow((double)i - centerY, 2) + Math.pow((double)j - centerX, 2)) >= radius) {
                    reds[i][j] = (byte)0;
                    greens[i][j] = (byte)0;
                    blues[i][j] = (byte)0;
                } else {
                    reds[i][j] = (byte)255;
                    greens[i][j] = (byte)255;
                    blues[i][j] = (byte)255;
                }
            }
        }
        return new RawImage(reds,greens, blues);
    }

    public static CustomImage square() {
        int centerX = imageSize /2;
        int centerY = imageSize /2;
        byte[][] reds   = new byte[imageSize][imageSize];
        byte[][] greens = new byte[imageSize][imageSize];
        byte[][] blues  = new byte[imageSize][imageSize];
        for(int i = 0; i < imageSize; i++) {
            for(int j = 0; j < imageSize; j++) {
                if (i >= centerY - centerY /2 && i < centerY + centerY /2 &&
                    j >= centerX - centerX /2 && j < centerX + centerX /2) {
//                    reds[i][j] = (byte)255;
//                    greens[i][j] = (byte)255;
//                    blues[i][j] = (byte)255;
                    reds[i][j] = (byte)70;
                    greens[i][j] = (byte)70;
                    blues[i][j] = (byte)70;
                } else {
                    reds[i][j] = (byte)0;
                    greens[i][j] = (byte)0;
                    blues[i][j] = (byte)0;
                }
            }
        }
        return new RawImage(reds,greens, blues);
    }

    public static CustomImage colorGradient() {
        // R -> R+B -> B -> B+G -> G
        int transitions = 6;
        byte[][] reds   = new byte[imageSize][imageSize];
        byte[][] greens = new byte[imageSize][imageSize];
        byte[][] blues  = new byte[imageSize][imageSize];
        // TO-DO: Fixear esto, pensarlo mejor
        // (No queda justo de la longitud asi que hay que pintar hasta que te pases y es medio feo)
        int widthOfTransition = imageSize / transitions +7;
        int currentColumn = 0;
        // Set red
        for(int i = 0; i < reds.length; i++) {
            reds[i][currentColumn] = (byte) 255;
            greens[i][currentColumn] = (byte) 0;
            blues[i][currentColumn] = (byte) 0;
        }
        currentColumn = 1;
        // First transition: R-> R+B
        transition(reds, widthOfTransition, currentColumn, 0);
        transition(blues, widthOfTransition, currentColumn, 255 / widthOfTransition);
        currentColumn+=widthOfTransition;
        // Second transition: R+B -> B
        transition(blues, widthOfTransition, currentColumn, 0);
        transition(reds, widthOfTransition, currentColumn, -255 / widthOfTransition);
        currentColumn+=widthOfTransition;
        // Third transition: B -> B+G
        transition(blues, widthOfTransition, currentColumn, 0);
        transition(greens, widthOfTransition, currentColumn, 255 / widthOfTransition);
        currentColumn+=widthOfTransition;
        // Fourth transition: B+G -> G
        transition(greens, widthOfTransition, currentColumn, 0);
        transition(blues, widthOfTransition, currentColumn, -255 / widthOfTransition);
        currentColumn += widthOfTransition;
        // Fifth transition: G -> G+R
        transition(greens, widthOfTransition, currentColumn, 0);
        transition(reds, widthOfTransition, currentColumn, 255 / widthOfTransition);

        return new RawImage(reds, greens, blues);
    }

    private static void transition(byte[][] matrix, int widthOfTransition, int currentColumn, int transitionAmount) {
        for(int j = 1; j <= widthOfTransition; j++, currentColumn++){
            for(int i = 0; i < imageSize; i++){
                if(currentColumn >= matrix.length){
                    return;
                }
                matrix[i][currentColumn] = (byte)(matrix[i][currentColumn-1] + transitionAmount);
            }
        }

    }
    public static CustomImage blackAndWhiteGradient() {
        byte[][] reds   = new byte[imageSize][imageSize];
        byte[][] greens = new byte[imageSize][imageSize];
        byte[][] blues  = new byte[imageSize][imageSize];
        int widthOfTransition = imageSize;
        int currentColumn = 0;
        // Set red
        for(int i = 0; i < reds.length; i++) {
            reds[i][currentColumn] = (byte) 0;
            greens[i][currentColumn] = (byte) 0;
            blues[i][currentColumn] = (byte) 0;
        }
        currentColumn = 1;
        transition(reds, widthOfTransition, currentColumn, 255 / imageSize);
        transition(blues, widthOfTransition, currentColumn, 255 / imageSize);
        transition(greens, widthOfTransition, currentColumn, 255 / imageSize);
        return new RawImage(reds, greens, blues);
    }

    public static CustomImage newImage(byte[][] reds, byte[][] greens, byte[][] blues) {
        return newImage(reds, greens, blues);
    }

    public static CustomImage newImage(Color[][] colors) {
        return new RawImage(colors);
    }

    public static CustomImage whiteImage() {
        return singleColorImage(254, 254, 254);
    }

    public static CustomImage blackImage() {
        return singleColorImage(1, 1, 1);
    }

    public static CustomImage singleColorImage(int r, int g, int b) {
        Color[][] colors = new Color[imageSize][imageSize];
        for(int i = 0; i < imageSize; i++) {
            for(int j = 0; j < imageSize; j++) {
                colors[i][j] = new Color(r , g , b );
            }

        }
        return new RawImage(colors);
    }
}
