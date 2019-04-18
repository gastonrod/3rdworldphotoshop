package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class PrewittOperatorYFilter extends AbstractFilter {

    public PrewittOperatorYFilter() {
        int maskSize = 3;
        mask = new double[maskSize][maskSize];
        for(int i = 0; i < maskSize; i++){
            mask[0][i] = -1;
            mask[maskSize-1][i] = 1;
        }
        matrixSum = 1;
    }
}
