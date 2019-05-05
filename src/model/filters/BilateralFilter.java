package model.filters;

import model.utils.Utils;

import java.awt.*;

public class BilateralFilter implements Filter{
    private double powSdsDenom;
    private double powSdrDenom;

    public BilateralFilter(double sds, double sdr) {
        powSdsDenom = (2 * Math.pow(sds, 2));
        powSdrDenom = (2 * Math.pow(sdr, 2));
    }

    @Override
    public Color filter(Color[][] pixels, int y, int x, int maskSize) {
        if(Utils.inBounds(pixels.length, pixels[0].length, y, x, maskSize)) {
            return pixels[y][x];
        }

        double denom     = 0;
        double redsNum   = 0;
        double bluesNum  = 0;
        double greensNum = 0;
        double[][] mask = buildMask(pixels, y, x, maskSize);

        for(int i = y-maskSize/2, indexY = 0; i <= y + maskSize /2; i++, indexY++) {
            for(int j = x-maskSize/2, indexX = 0; j <= x + maskSize /2; j++, indexX++) {
                denom     += mask[indexY][indexX];
                redsNum   += pixels[i][j].getRed()  * mask[indexY][indexX];
                greensNum += pixels[i][j].getGreen()* mask[indexY][indexX];
                bluesNum  += pixels[i][j].getBlue() * mask[indexY][indexX];
            }
        }

        Color color = new Color(
                Utils.inColorRange((int)(redsNum / denom)),
                Utils.inColorRange((int)(greensNum / denom)),
                Utils.inColorRange((int)(bluesNum / denom))
        );
        return color;
    }

    private double[][] buildMask(Color[][] pixels, int k, int l, int maskSize) {
        double[][] mask = new double[maskSize][maskSize];
        int x = l;
        int y = k;
        k -= maskSize /2;
        for(int i = 0; i < maskSize; i++, k++) {
            l = x - maskSize/2;
            for(int j = 0; j < maskSize; j++, l++) {
                double first_term = (Math.pow((double)k - y,2) + Math.pow((double)x - l, 2)) / powSdsDenom;
                double second_term = getDiffSquared(pixels, k, l, y, x) / powSdrDenom;
                double w = Math.exp(-first_term - second_term);
                mask[i][j] = w;
            }
        }
        return mask;
    }

    private double getDiffSquared(Color[][] pixels, int k, int l, int y, int x){
        double r = Math.pow((double)pixels[k][l].getRed()-pixels[y][x].getRed(),2);
        double g = Math.pow((double)pixels[k][l].getGreen()-pixels[y][x].getGreen(),2);
        double b = Math.pow((double)pixels[k][l].getBlue()-pixels[y][x].getBlue(),2);
        return r + g + b;
    }
}
