package model.filters;

import java.awt.*;

public class MedianFilter implements Filter {

    @Override
    public Color filter(final Color[][] pixels, final int y, final int x, final int maskSize) {
        return new WeightedMedianFilter(1).filter(pixels, y, x, maskSize);
    }
}
