package model.probabilitydistributions;


import net.sf.doodleproject.numerics4j.random.RNG;
import net.sf.doodleproject.numerics4j.random.RandomRNG;
import net.sf.doodleproject.numerics4j.random.RayleighRandomVariable;
import net.sf.doodleproject.numerics4j.random.UniformRandomVariable;

import java.util.Random;

public class UniformDistribution implements ProbabilityDistribution {

    private static Random rand = new Random();

    public UniformDistribution() {
    }

    @Override
    public double nextValue() {
            return rand.nextDouble();
    }
}
