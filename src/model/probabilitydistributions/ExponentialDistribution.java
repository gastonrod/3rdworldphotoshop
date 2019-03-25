package model.probabilitydistributions;


import net.sf.doodleproject.numerics4j.random.ExponentialRandomVariable;
import net.sf.doodleproject.numerics4j.random.RNG;
import net.sf.doodleproject.numerics4j.random.RandomRNG;

public class ExponentialDistribution implements ProbabilityDistribution {

    private double lambda;
    private static RNG rng = new RandomRNG(System.currentTimeMillis());

    public ExponentialDistribution(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double nextValue() {
        return ExponentialRandomVariable.nextRandomVariable(lambda, rng);
    }
}
