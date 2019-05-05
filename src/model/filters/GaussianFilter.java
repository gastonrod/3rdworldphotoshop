package model.filters;

public class GaussianFilter extends AbstractFilter {

    public GaussianFilter(double sd) {
        int maskSize = Math.min((int)(sd * 2 + 1), 3);
        mask = new double[maskSize][maskSize];
        for(int i = 0, y = -maskSize/2; i < maskSize; i++, y++) {
            for(int j = 0,x = -maskSize/2; j < maskSize; j++, x++) {
                mask[i][j] = (1/ (2.0 * Math.PI * sd * sd)) * Math.pow(Math.E, -(y*y + x*x)/ (sd*sd));
                matrixSum += mask[i][j];
            }
        }
    }

}
