package model.diffusors;

import java.awt.*;

public interface Diffusion {
    Color[][] apply(double lambda, int iterations,
                    EdgeDetector bd, Color[][] pixels);
}
