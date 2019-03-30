package model.probabilitydistributions;

import java.util.Random;

public class UniformDistribution implements ProbabilityDistribution {

    private static Random rand = new Random();

    @Override
    public double nextValue() {
            return rand.nextDouble();
    }
}
