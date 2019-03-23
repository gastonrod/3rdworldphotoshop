package model.transformers;

import java.awt.*;

public class Contrast implements ColorTransformation{

    private int contrastUmbral = 16;
    private int r1;
    private int r2;

    public Contrast(int value) {
        r1 = Math.max(value - contrastUmbral, 0);
        r2 = Math.min(value + contrastUmbral, 255);

    }
    @Override
    public Color transform(Color c) {
        int r = contrastValue(c.getRed());
        int g = contrastValue(c.getGreen());
        int b = contrastValue(c.getBlue());
        return new Color(r, g, b);
    }

    private int contrastValue(int c) {
        if(c < r1) {
            return (int)(c * 0.5);
        } else if (c < r2) {
            return  (int)(r1 * 0.5 + 3 * (c - r1));
        } else {
            return  (int)(r1 * 0.5 + 3 * (r2 - r1) + 0.5 * (c - r2));
        }
    }
}
