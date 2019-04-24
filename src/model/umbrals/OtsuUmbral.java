package model.umbrals;

import model.CustomImageFactory;
import model.operators.SpatialOperator;
import view.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class OtsuUmbral implements Umbral{
    @Override
    public Color[][] apply(Color[][] pixels) {
        int[] histogram = CustomImageFactory.newImage(pixels).getColorsRepetition();
        double[] ps = new double[Utils.L];
        double[] Ps = new double[Utils.L];
        double[] ms = new double[Utils.L];
        int sum = pixels.length * pixels[0].length;
        ps[0] = (double) histogram[0] / (double) sum;
        Ps[0] = ps[0];
        ms[0] = 0;
        for(int i = 1; i < histogram.length; i++) {
            ps[i] = (double) histogram[i] / (double) sum;
            Ps[i] = Ps[i-1] + ps[i];
            ms[i] = ms[i-1] + ps[i] * i;
        }

        List<Integer> maxes = new ArrayList<>();
        double currentMax = Double.MIN_VALUE;
        double threshold = 0.000_1;
        for(int i = 0; i < histogram.length; i++) {
            double denom = Math.pow(ms[Utils.L-1] * Ps[i] - ms[i], 2);
            double num   = Ps[i] * (1 - Ps[i]);
            double dB = denom / num;
            if(currentMax != Double.MIN_VALUE && Math.abs(dB - currentMax) <= threshold) {
                maxes.add(i);
            } else if (dB >= currentMax) {
                currentMax = dB;
                maxes = new ArrayList<>();
                maxes.add(i);
            }
        }
        int T = 0;
        for(Integer i: maxes) {
            T += i;
        }
        T /= maxes.size();

        return SpatialOperator.setUmbral(pixels, T);
    }

}
