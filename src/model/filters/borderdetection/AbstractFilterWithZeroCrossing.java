package model.filters.borderdetection;

import model.utils.Utils;

import java.awt.Color;
import java.util.Arrays;

public abstract class AbstractFilterWithZeroCrossing {
    protected double[][] mask;
    protected double matrixSum = 1;
    protected int threshold = 10;
    public Color[][] applyOperator(Color[][] pixels) {
        int [][][] newValues = new int[3][pixels.length][pixels[0].length];

        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[0].length; j++) {
                int[] newVals = filter(pixels, i, j, mask.length);
                newValues[0][i][j] = newVals[0];
                newValues[1][i][j] = newVals[1];
                newValues[2][i][j] = newVals[2];
            }
        }
        newValues[0] = crossingZeros(newValues[0]);
        newValues[1] = crossingZeros(newValues[1]);
        newValues[2] = crossingZeros(newValues[2]);
        return Utils.colorsFromInts(newValues[0], newValues[1], newValues[2]);
    }

    private int[] filter(Color[][] pixels, int y, int x, int maskSize) {
        if(Utils.inBounds(pixels.length, pixels[0].length, y, x, maskSize)) {
            return new int[]{pixels[y][x].getRed(), pixels[y][x].getGreen(), pixels[y][x].getBlue()};
        }
        double reds  = 0;
        double blues = 0;
        double greens= 0;
        for(int i = y-maskSize/2, indexY = 0; i <= y + maskSize /2; i++, indexY++) {
            for(int j = x-maskSize/2, indexX = 0; j <= x + maskSize /2; j++, indexX++) {
                reds += pixels[i][j].getRed()    * mask[indexY][indexX];
                blues += pixels[i][j].getBlue()  * mask[indexY][indexX];
                greens += pixels[i][j].getGreen()* mask[indexY][indexX];
            }
        }
        return new int[]{
                (int)reds,
                (int)greens,
                (int)blues
        };
    }
    private int[][] crossingZeros(int[][] originalChannel) {
        int[][] newChannel = new int[originalChannel.length][originalChannel[0].length];
        for(int i = 0; i < originalChannel.length; i++) {
            newChannel[i] = originalChannel[i].clone();
        }

        for(int y = mask.length; y < originalChannel.length - mask.length; y++) {
            for(int x = mask.length+ 1; x < originalChannel[0].length - mask.length; x++) {
                int currentPixel = originalChannel[y][x];
                int prevPixel = originalChannel[y][x-1];
                Integer nextPixel = x + 1 < originalChannel[0].length - mask.length ? originalChannel[y][x+1] : null;

                if(originalChannel[y][x] == 0) {
                    /* Check + 0 - or - 0 + */
                    if(nextPixel != null && (prevPixel > 0 && nextPixel < 0 || prevPixel < 0 && nextPixel > 0)) {
                        // Use threshold to detect borders
                        int newColor = Math.abs(prevPixel) + Math.abs(nextPixel);
                        if(newColor > threshold) {
                            newChannel[y][x] = newColor;
                        } else {
                            newChannel[y][x] = 0;
                        }
                    } else {
                        newChannel[y][x] = 0;
                    }
                    newChannel[y][x - 1] = 0;
                } else {
                    /* Check + - or - + */
                    if(prevPixel > 0 && currentPixel < 0 || prevPixel < 0 && currentPixel > 0) {
                        int newColor = Math.abs(prevPixel) + Math.abs(currentPixel);
                        if(newColor > threshold) {
                            newChannel[y][x - 1] = newColor;
                        } else {
                            newChannel[y][x - 1] = 0;
                        }
                    } else {
                        newChannel[y][x - 1] = 0;
                    }
                }
            }
            newChannel[y][originalChannel[0].length - mask.length] = 0;
        }

        return newChannel;
    }
}
