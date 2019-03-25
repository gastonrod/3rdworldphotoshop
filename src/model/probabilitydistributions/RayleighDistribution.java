package model.probabilitydistributions;


import net.sf.doodleproject.numerics4j.random.RNG;
import net.sf.doodleproject.numerics4j.random.RandomRNG;
import net.sf.doodleproject.numerics4j.random.RayleighRandomVariable;

import java.util.Random;

public class RayleighDistribution implements ProbabilityDistribution {

    private double mean;
    private static RNG rng = new RandomRNG(System.currentTimeMillis());

    public RayleighDistribution(double mean) {
        this.mean = mean;
    }

    @Override
    public double nextValue() {
        return RayleighRandomVariable.nextRandomVariable(mean, rng);
    }
}
