package model;

import java.awt.*;

public class Utils {
    public static final int L = 256;

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
}
