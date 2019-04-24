package model.umbrals;

import java.awt.*;

@FunctionalInterface
public interface Umbral {
    Color[][] apply(Color[][] pixels);
}
