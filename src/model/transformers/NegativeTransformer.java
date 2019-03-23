package model.transformers;

import java.awt.*;

public class NegativeTransformer implements ColorTransformation{
    @Override
    public Color transform(Color c) {
        return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
    }
}
