package model.probabilitydistributions;

import java.util.Random;

public class GaussianDistribution implements ProbabilityDistribution {

    private double mean;
    private double std;
    private double amountOfImage;
    private Random rand = new Random();

    public GaussianDistribution() {
    mean = 0;
    std = 80;
    amountOfImage = 0.2;
    }
    public GaussianDistribution(double mean, double std, double amountOfImage) {
        this.mean = mean;
        this.std = std;
        this.amountOfImage = amountOfImage;
    }

    @Override
    public double nextValue() {
        if(rand.nextDouble() <= amountOfImage) {
            return mean + rand.nextGaussian() * std;
        }
        return 0;
    }
}
