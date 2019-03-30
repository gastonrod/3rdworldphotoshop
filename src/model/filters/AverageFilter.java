package model.filters;

public class AverageFilter extends AbstractFilter {
    public AverageFilter(int maskSize) {
        mask = new double[maskSize][maskSize];
        matrixSum = (double)maskSize * maskSize;
        for(int i = 0; i < maskSize; i++) {
            for(int j = 0; j < maskSize; j++) {
                mask[i][j] = 1.0;
            }
        }
    }

}
