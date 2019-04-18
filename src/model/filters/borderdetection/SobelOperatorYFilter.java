package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class SobelOperatorYFilter extends AbstractFilter {

    public SobelOperatorYFilter() {
        int maskSize = 3;
        mask = new double[maskSize][maskSize];
        mask[0][0] = -1;
        mask[maskSize-1][0] = 1;
        mask[0][1] = -2;
        mask[maskSize-1][1] = 2;
        mask[0][2] = -1;
        mask[maskSize-1][2] = 1;
        matrixSum = 1;
    }
}
