package model.probabilitydistributions;


import net.sf.doodleproject.numerics4j.random.NormalRandomVariable;
import net.sf.doodleproject.numerics4j.random.RNG;
import net.sf.doodleproject.numerics4j.random.RandomRNG;

import java.util.Random;

public class GaussianDistribution implements ProbabilityDistribution {

    private double mean;
    private double std;
    private static RNG rng = new RandomRNG(System.currentTimeMillis());

    public GaussianDistribution() {
        mean = 0;
        std = 80;
    }

    public GaussianDistribution(double mean, double std) {
        this.mean = mean;
        this.std = std;
    }

    @Override
    public double nextValue() {
        return NormalRandomVariable.nextRandomVariable(mean, std, rng);
    }
}
