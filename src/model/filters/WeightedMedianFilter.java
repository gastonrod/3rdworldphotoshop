package model.filters;

import model.Utils;

import java.awt.*;
import java.util.Arrays;

public class WeightedMedianFilter implements Filter{

    private int weight;
    public WeightedMedianFilter(int weight) {
        this.weight = weight;
    }

    @Override
    public Color filter(Color[][] pixels, int y, int x, int maskSize) {
        if(Utils.inBounds(pixels.length, pixels[0].length, y, x, maskSize)) {
            return pixels[y][x];
        }
        int[] reds  = new int[maskSize * maskSize * weight];
        int[] blues = new int[maskSize * maskSize * weight];
        int[] greens= new int[maskSize * maskSize * weight];
        int cont = 0;
        for(int i = y-maskSize/2; i < y + maskSize /2; i++) {
            for(int j = x-maskSize/2; j < x + maskSize /2; j++) {
                for(int repetitions = 0; repetitions < weight; repetitions++){
                    reds[cont] = pixels[i][j].getRed();
                    blues[cont] = pixels[i][j].getBlue();
                    greens[cont] = pixels[i][j].getGreen();
                    cont++;
                }
            }
        }
        Arrays.sort(reds);
        Arrays.sort(greens);
        Arrays.sort(blues);
        int i = maskSize * maskSize / 2;
        return new Color(reds[i], greens[i], blues[i]);
    }
}
