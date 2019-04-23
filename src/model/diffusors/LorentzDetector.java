package model.diffusors;


public class LorentzDetector implements EdgeDetector {

    private double sigma;

    public LorentzDetector(double sigma){
        this.sigma = sigma;
    }

    @Override
    public double g(double x) {
        double den = Math.pow(Math.abs(x), 2) / Math.pow(sigma, 2) + 1.0;
        return 1.0/den;
    }

}
