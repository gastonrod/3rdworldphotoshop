package model.transformers;

import model.utils.Utils;

import java.awt.*;

public class HistogramEqualizationTransformation implements ColorTransformation{

    private int[] s;

    public HistogramEqualizationTransformation(int[] grayLevels, int totalPixels) {
        s = new int[grayLevels.length];
        double constLDividedByPixels = (double)(Utils.L - 1) / (double)totalPixels;
        for(int k = 0; k < s.length; k++) {
            double sk = 0;
            for(int j = 0; j < k; j++) {
                sk +=  constLDividedByPixels * (double)grayLevels[j];
            }
            s[k] = (int)Math.round(sk);
        }
    }

    @Override
    public Color transform(Color c) {
        int grayLevel = Utils.averageColor(c);
        return new Color(s[grayLevel], s[grayLevel], s[grayLevel]);
    }
}
