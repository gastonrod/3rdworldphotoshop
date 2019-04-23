package model.filters.borderdetection;

import model.utils.Utils;

import java.awt.Color;
import java.util.List;

public abstract class AbstractFilterWithZeroCrossing {
    protected double[][] mask;
    protected double matrixSum;
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
        newValues[0] = applyZeroCrossing(newValues[0]);
        newValues[1] = applyZeroCrossing(newValues[1]);
        newValues[2] = applyZeroCrossing(newValues[2]);
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
        double scalar = 1.0;
        return new int[]{
                ((int)(reds   * scalar)),
                ((int)(greens * scalar)),
                ((int)(blues  * scalar))
        };
    }

    int[][] applyZeroCrossing(int[][] color){
        int[][] newColor = new int[color.length][color[0].length];
        for (int y = 1; y < color.length - 1; y++) {
            for (int x = 1; x < color[0].length - 1; x++) {

                int left = color[y][x-1];
                int right = color[y][x+1];
                int up = color[y+1][x];
                int down = color[y-1][x];
                int[] neighbors = new int[]{left, right, up, down};

                if(!Utils.allSameSign(color[y][x], neighbors)){
                    List<Integer> differentSigns = Utils.getNumbersWithDifferentSign(color[y][x], neighbors);
                    int min = Math.abs(color[y][x]);
                    for(Integer i : differentSigns) {
                        if(min > Math.abs(i)){
                            min = Math.abs(i);
                        }
                    }
                    if(min != Math.abs(color[y][x])){
                        newColor[y][x] = Utils.inColorRange(Math.abs(color[y][x]) + min);
                    } else {
                        newColor[y][x] = 0;
                    }
                } else {
                    newColor[y][x] = 0;
                }
            }
        }
        return newColor;
    }
}
