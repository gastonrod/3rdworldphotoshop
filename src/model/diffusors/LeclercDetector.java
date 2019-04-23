package model.diffusors;

public class LeclercDetector implements EdgeDetector{
    private double sigma;

    public LeclercDetector(double sigma){
        this.sigma = sigma;
    }

    @Override
    public double g(double x) {
        return Math.exp( -Math.pow(Math.abs(x), 2) / Math.pow(sigma, 2));
    }

}
