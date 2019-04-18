package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class SobelOperatorXFilter extends AbstractFilter {

    public SobelOperatorXFilter() {
        int maskSize = 3;
        mask = new double[maskSize][maskSize];
        mask[0][0] = -1;
        mask[0][maskSize-1] = 1;
        mask[1][0] = -2;
        mask[1][maskSize-1] = 2;
        mask[2][0] = -1;
        mask[2][maskSize-1] = 1;
        matrixSum = 1;
    }
}
