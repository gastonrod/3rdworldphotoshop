package model.filters.borderdetection;

import model.images.CustomImage;
import model.utils.Utils;

import java.awt.Color;

public abstract class Susan {
    private int t;
    private boolean[][] mask;
    private static final int MASK_SIZE = 7;
    private static final int AMOUNT_OF_TRUES_IN_MASK = 37;
    protected Susan(int t) {
        this.t = t;
        // oh god why
        mask = new boolean[][] {
                {false, false, true, true, true, false, false},
                {false, true,  true, true, true, true,  false},
                {true,  true,  true, true, true, true,  true},
                {true,  true,  true, true, true, true,  true},
                {true,  true,  true, true, true, true,  true},
                {false, true,  true, true, true, true,  false},
                {false, false, true, true, true, false, false},
        };
    }

    public Color[][] filter(Color[][] image) {
        Color[][] newImage = new Color[image.length][image[0].length];
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                newImage[i][j] = Color.BLACK;
            }
        }
        for(int i = MASK_SIZE / 2; i < image.length - MASK_SIZE / 2 - 1;i++){
            for(int j = MASK_SIZE / 2; j < image[0].length - MASK_SIZE / 2 - 1;j++) {
                double amountOfPixelsInMask = 0.0;
                for(int maskPosY = 0, deltaImageY = -MASK_SIZE / 2; maskPosY < MASK_SIZE;maskPosY++,deltaImageY++){
                    for(int maskPosX = 0, deltaImageX = -MASK_SIZE / 2; maskPosX < MASK_SIZE;maskPosX++,deltaImageX++) {
                        if(mask[maskPosY][maskPosY] &&
                                Math.abs(Utils.toGray(image[i+deltaImageY][j+deltaImageX]) - Utils.toGray(image[i][j])) < t) {
                            amountOfPixelsInMask++;
                        }
                    }
                }
                double s0 = 1 - amountOfPixelsInMask / AMOUNT_OF_TRUES_IN_MASK;
                newImage[i][j] = setColor(s0);
            }
        }
        return newImage;
    }

    abstract Color setColor(double s);
}
