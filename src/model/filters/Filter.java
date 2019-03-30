package model.filters;

import java.awt.*;

@FunctionalInterface
public interface Filter {
    Color filter(final Color[][] pixels, final int y, final int x, int maskSize);
}
