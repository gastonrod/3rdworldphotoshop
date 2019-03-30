package model.filters;

import model.operators.FilterOperator;
import model.utils.Utils;

import java.awt.*;

public class PrewittFilter implements Filter{

    BorderDetectionXFilter xFilter = new BorderDetectionXFilter();
    BorderDetectionYFilter yFilter = new BorderDetectionYFilter();

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
        return (int)Math.sqrt(a*a + b*b);
    }
}
