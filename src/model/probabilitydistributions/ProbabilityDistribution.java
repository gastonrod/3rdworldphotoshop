package model.probabilitydistributions;

@FunctionalInterface
public interface ProbabilityDistribution {
    double nextValue();
}
