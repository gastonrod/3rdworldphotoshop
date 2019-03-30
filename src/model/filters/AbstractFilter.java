package model.filters;

import model.utils.Utils;

import java.awt.*;

public abstract class AbstractFilter implements Filter{
    protected double[][] mask;
    protected double matrixSum = 0;
    @Override
    public Color filter(Color[][] pixels, int y, int x, int maskSize) {
        if(Utils.inBounds(pixels.length, pixels[0].length, y, x, maskSize)) {
            return pixels[y][x];
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
        double scalar = 1.0 / matrixSum;
        return new Color(
                Utils.inColorRange((int)(reds   * scalar)),
                Utils.inColorRange((int)(greens * scalar)),
                Utils.inColorRange((int)(blues  * scalar))
        );
    }
}
