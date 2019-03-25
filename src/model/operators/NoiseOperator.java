package model.operators;

import model.CustomImageFactory;
import model.Utils;
import model.images.CustomImage;
import model.probabilitydistributions.*;

import java.awt.*;
import java.util.Random;

public class NoiseOperator {
    private static Color SALT   = Color.white;
    private static Color PEPPER = Color.black;
    private static boolean additiveNoise = true;
    private static boolean multiplicativeNoise = false;
    private static Random rand = new Random();
    private NoiseOperator(){}

    public static CustomImage addGaussianNoise(CustomImage image, int mean, int std, double percent){
        GaussianDistribution distribution = new GaussianDistribution(mean, std);
        return addNoise(image, distribution, additiveNoise, percent);
    }

    public static CustomImage rayleighNoise(CustomImage image, double mean, double percent) {
        RayleighDistribution distribution = new RayleighDistribution(mean);
        return addNoise(image, distribution, multiplicativeNoise, percent);
    }

    public static CustomImage exponentialNoise(CustomImage image, double lambda, double percent) {
        ExponentialDistribution distribution = new ExponentialDistribution(lambda);
        return addNoise(image, distribution, multiplicativeNoise, percent);
    }

    public static CustomImage saltAndPepperNoise(CustomImage image, double percent) {
        UniformDistribution distribution = new UniformDistribution();
        Color[][] rgbRepresentation = image.getRGBRepresentation();
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                if(distribution.nextValue() <= percent) {
                    if(distribution.nextValue() <= 0.5) {
                        rgbRepresentation[i][j] = SALT;
                    } else {
                        rgbRepresentation[i][j] = PEPPER;
                    }
                }
            }
        }
        return CustomImageFactory.newImage(rgbRepresentation);
    }

    private static CustomImage addNoise(CustomImage image, ProbabilityDistribution distribution, boolean add, double percent) {
        Color[][] rgbRepresentation = image.getRGBRepresentation();
        return CustomImageFactory.newImage(addNoise(rgbRepresentation, distribution, add, percent));
    }

    private static Color[][] addNoise(Color[][] rgbRepresentation, ProbabilityDistribution distribution, boolean add, double percent) {
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                double noise = distribution.nextValue();
                if(rand.nextDouble() <= percent){
                    Color pixel = rgbRepresentation[i][j];
                    Color newColor = add ? addColor(pixel, noise) : multiplyColor(pixel, noise);
                    rgbRepresentation[i][j] = newColor;
                }
            }
        }
        return rgbRepresentation;
    }

    private static Color addColor(Color pixel, double noise) {
        return new Color(
                        Utils.inColorRange(pixel.getRed()   + noise),
                        Utils.inColorRange(pixel.getGreen() + noise),
                        Utils.inColorRange(pixel.getRed()   + noise)
                );
    }

    private static Color multiplyColor(Color pixel, double noise) {
        return new Color(
                Utils.inColorRange(pixel.getRed()   * noise),
                Utils.inColorRange(pixel.getGreen() * noise),
                Utils.inColorRange(pixel.getRed()   * noise)
        );
    }
}
