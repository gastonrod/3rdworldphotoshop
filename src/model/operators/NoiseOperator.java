package model.operators;

import model.CustomImageFactory;
import model.Utils;
import model.images.CustomImage;
import model.probabilitydistributions.GaussianDistribution;
import model.probabilitydistributions.ProbabilityDistribution;

import java.awt.*;

public class NoiseOperator {
    private NoiseOperator(){}

    public static CustomImage addGaussianNoise(CustomImage image, int mean, int std, double percent){
        GaussianDistribution distribution = new GaussianDistribution(mean, std, percent);
        return addNoise(image, distribution);
    }

    private static CustomImage addNoise(CustomImage image, ProbabilityDistribution distribution) {
        Color[][] rgbRepresentation = image.getRGBRepresentation();
        return CustomImageFactory.newImage(addNoise(rgbRepresentation, distribution));
    }

    private static Color[][] addNoise(Color[][] rgbRepresentation, ProbabilityDistribution distribution) {
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                double noise = distribution.nextValue();
                Color pixel = rgbRepresentation[i][j];
                rgbRepresentation[i][j] = new Color(
                        Utils.inColorRange(pixel.getRed()   + noise),
                        Utils.inColorRange(pixel.getGreen() + noise),
                        Utils.inColorRange(pixel.getRed()   + noise)
                );
            }
        }
        return rgbRepresentation;
    }
}
