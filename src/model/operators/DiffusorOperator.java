package model.operators;

import model.CustomImageFactory;
import model.diffusors.*;
import model.images.CustomImage;

import java.awt.*;

public class DiffusorOperator {
    private DiffusorOperator(){}
    public static CustomImage anisotropicLorentz(CustomImage image, double lambda, int iterations, double sigma) {
        return anisotropic(image, lambda, iterations, new LorentzDetector(sigma));
    }

    public static CustomImage anisotropicLeclerc(CustomImage image, double lambda, int iterations, double sigma) {
        return anisotropic(image, lambda, iterations, new LeclercDetector(sigma));
    }

    public static CustomImage isotropicLeclerc(CustomImage image, double lambda, int iterations, double sigma) {
        return isotropic(image, lambda, iterations, new LeclercDetector(sigma));
    }

    public static CustomImage isotropicLorentz(CustomImage image, double lambda, int iterations, double sigma) {
        return anisotropic(image, lambda, iterations, new LorentzDetector(sigma));
    }

    private static CustomImage isotropic(CustomImage image, double lambda, int iterations, EdgeDetector ed) {
        return difuse(new IsotropicDiffusion(), lambda, iterations, ed, image.getRGBRepresentation());
    }
    private static CustomImage anisotropic(CustomImage image, double lambda, int iterations, EdgeDetector ed) {
        return difuse(new AnisotropicDiffusion(), lambda, iterations, ed, image.getRGBRepresentation());
    }

    private static CustomImage difuse(Diffusion diffusor, double lambda, int iterations, EdgeDetector ed, Color[][] rgbRepresentation) {
        return CustomImageFactory.newImage(diffusor.apply(lambda,iterations,ed, rgbRepresentation));
    }

}
