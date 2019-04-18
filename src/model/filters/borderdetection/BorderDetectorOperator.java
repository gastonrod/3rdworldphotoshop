package model.filters.borderdetection;

import model.filters.Filter;
import model.utils.Utils;

import java.awt.*;

public class BorderDetectorOperator implements Filter {
    Filter xFilter;
    Filter yFilter;
    @Override
    public Color filter(Color[][] pixels, int y, int x, int maskSize) {
        Color c1 = xFilter.filter(pixels,y,x,maskSize);
        Color c2 = yFilter.filter(pixels,y,x,maskSize);
        return new Color(
                Utils.inColorRange(getVal(c1.getRed(), c2.getRed())),
                Utils.inColorRange(getVal(c1.getGreen(), c2.getGreen())),
                Utils.inColorRange(getVal(c1.getBlue(), c2.getBlue()))
        );
    }
    private int getVal(int a, int b){
        return (int)Math.sqrt((double)(a*a + b*b));
    }
}
