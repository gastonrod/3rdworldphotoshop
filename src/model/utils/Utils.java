package model.utils;

import java.awt.*;
import java.util.ArrayList;

public class Utils {
    public static final int L = 256;
    public static final int PREWITT_MASK_SIZE = 3;

    private Utils() {}
    public static int byteToInt(byte val){
        return val < 0 ? 256 + val : val;
    }

    public static int averageColor(Color c) {
        return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
    }

    public static boolean inBounds(int height, int width, int y, int x, int maskSize) {
        return ( y - maskSize / 2 < 0 || y + maskSize/2 >= height ||
                x - maskSize / 2 < 0 || x + maskSize/2 >= width);
    }

    public static int inColorRange(int c) {
        return Math.max(0, Math.min(c, L-1));
    }

    public static int inColorRange(double c) {
        return inColorRange((int)c);
    }

    public static Color[][]colorsFromInts(int[][] reds, int[][] greens, int[][] blues) {
        Color[][] colors = new Color[reds.length][reds[0].length];
        for (int i = 0; i < reds.length; i++) {
            for (int j = 0; j < reds[0].length; j++) {
                colors[i][j] = new Color(reds[i][j], greens[i][j], blues[i][j]);
            }
        }
        return colors;
    }

    public static boolean allSameSign(int color, int[] neighbors){
        boolean isPositive = color >= 0;
        for(int i = 0; i < neighbors.length; i++){
            if(isPositive && neighbors[i] < 0) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> getNumbersWithDifferentSign(int color, int[] neighbors) {
        ArrayList<Integer> list = new ArrayList<>();
        boolean isPositive = color >= 0;
        for(int i = 0; i < neighbors.length; i++){
            if(isPositive && neighbors[i] < 0) {
                list.add(neighbors[i]);
            }
        }
        return list;
    }
}
