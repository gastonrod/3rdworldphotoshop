package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class PrewittOperatorXFilter extends AbstractFilter {

    public PrewittOperatorXFilter() {
        int maskSize = 3;
        mask = new double[maskSize][maskSize];
        for(int i = 0; i < maskSize; i++){
            mask[i][0] = -1;
            mask[i][maskSize-1] = 1;
        }
        matrixSum = 1;
    }
}
