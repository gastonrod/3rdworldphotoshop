package model.umbrals;

import model.utils.Utils;

import java.awt.*;

public class HisteresisUmbral implements Umbral{

    private int t1;
    private int t2;
    public HisteresisUmbral(int t1, int t2) {
        this.t1 = t1;
        this.t2 = t2;
    }


    @Override
    public Color[][] apply(Color[][] pixels) {
        for(int i = 1; i < pixels.length - 1; i++) {
            for(int j = 1; j < pixels[0].length - 1; j++) {
                if(Utils.toGray(pixels[i][j]) >= t2) {
                    pixels[i][j] = Color.WHITE;
                } else if(Utils.toGray(pixels[i][j]) >= t1) {
                    if(surroundedByBorders(pixels, i, j)) {
                        pixels[i][j] = Color.WHITE;
                    } else {
                        pixels[i][j] = Color.BLACK;
                    }
                } else {
                    pixels[i][j] = Color.BLACK;
                }
            }
        }
        return pixels;
    }

    private boolean surroundedByBorders(Color[][] pixels, int i, int j) {
        int up = Utils.toGray(pixels[i-1][j]);
        int down = Utils.toGray(pixels[i+1][j]);
        int right = Utils.toGray(pixels[i][j+1]);
        int left = Utils.toGray(pixels[i][j-1]);
        return up >= t1 && down >= t1 && right >= t1 && left >= t1;
    }
}
