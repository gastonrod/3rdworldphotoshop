package model.transformers;

import java.awt.*;

@FunctionalInterface
public interface ColorTransformation {
    public Color transform(Color c);
}
