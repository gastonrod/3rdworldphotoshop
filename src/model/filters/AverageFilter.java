package model.filters;

import model.Utils;

import java.awt.*;

public class AverageFilter implements Filter {

    @Override
    public Color filter(final Color[][] pixels, final int y, final int x, final int maskSize) {
        if(Utils.inBounds(pixels.length, pixels[0].length, y, x, maskSize)) {
            return pixels[y][x];
        }
        double reds  = 0;
        double blues = 0;
        double greens= 0;
        for(int i = y-maskSize/2; i <= y + maskSize /2; i++) {
            for(int j = x-maskSize/2; j <= x + maskSize /2; j++) {
                reds += pixels[i][j].getRed();
                blues += pixels[i][j].getBlue();
                greens += pixels[i][j].getGreen();
            }
        }
        double scalar = (1 / (maskSize * maskSize));
        return new Color(
                (int)(reds   * scalar),
                (int)(greens * scalar),
                (int)(blues  * scalar)
                );
    }
}
