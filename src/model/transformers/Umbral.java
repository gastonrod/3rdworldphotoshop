package model.transformers;

import java.awt.*;

public class Umbral implements ColorTransformation {

    private int val;
    public Umbral(int val){
       this.val = val;
    }
    @Override
    public Color transform(Color c) {
        int newColor = (c.getRed() + c.getGreen() + c.getBlue()) / 3 > val ? 255 : 0;
        return new Color(newColor, newColor, newColor);
    }
}
