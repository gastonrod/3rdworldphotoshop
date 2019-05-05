package model.filters.borderdetection;

import model.filters.AbstractFilter;

import java.text.DecimalFormat;

public class LaplacianOfGaussianOperator2 extends AbstractFilter {
    public LaplacianOfGaussianOperator2(double sd) {
        int maskSize = 7;
        mask = new double[maskSize][maskSize];
        double y = -maskSize/2.;
        DecimalFormat df = new DecimalFormat("#.#####");
        for(int i = 0 ; i < maskSize; i++, y++) {
            double x = -maskSize/2.0;
            for(int j = 0; j < maskSize; j++, x++) {
                double a = 1.0 / ( Math.sqrt(2.0 * Math.PI) * Math.pow(sd, 3) );
                double b = 2.0 - ( x * x + y * y) / ( sd * sd );
                double c = Math.exp( - ( x * x + y * y) / ( 2.0 * sd * sd) );
                mask[i][j] = Double.parseDouble(df.format(-a * b * c));
                matrixSum += mask[i][j];
            }
        }
    }

}
