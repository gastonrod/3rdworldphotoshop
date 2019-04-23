package model.diffusors;

import model.utils.Utils;

import java.awt.*;

public abstract class AbstractDiffusion implements Diffusion {

    @Override
    public Color[][] apply(double lambda, int iterations,
                    EdgeDetector bd, Color[][] pixels){
        Color[][] newPixels = new Color[pixels.length][pixels[0].length];
        for(int i = 0; i < pixels.length; i++){
            newPixels[i] = pixels[i].clone();
        }
        for (int n = 0; n < iterations; n++) {
            for(int i = 1; i < pixels.length - 1; i++){
                for(int j = 1; j < pixels[0].length - 1; j++){
                    Color pixel = newPixels[i][j];
                    Color up = newPixels[i-1][j];
                    Color down = newPixels[i+1][j];
                    Color left = newPixels[i][j-1];
                    Color right = newPixels[i][j+1];
                    newPixels[i][j] = new Color(
                            Utils.inColorRange(applyDiffusion(pixel.getRed(), up.getRed(), down.getRed(), left.getRed(), right.getRed(), lambda, bd)),
                            Utils.inColorRange(applyDiffusion(pixel.getGreen(), up.getGreen(), down.getGreen(), left.getGreen(), right.getGreen(), lambda, bd)),
                            Utils.inColorRange(applyDiffusion(pixel.getBlue(), up.getBlue(), down.getBlue(), left.getBlue(), right.getBlue(), lambda, bd))
                    );
                }
            }
        }
        return newPixels;
    }

    protected abstract int applyDiffusion(int pixel, int up, int down, int left, int right,
                                          double lambda, EdgeDetector bd);
}
