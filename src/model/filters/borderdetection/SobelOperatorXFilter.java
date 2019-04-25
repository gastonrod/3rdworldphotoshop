package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class SobelOperatorXFilter extends AbstractFilter {

    public SobelOperatorXFilter() {
        mask = new double[][]{
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };
        matrixSum = 1;
    }
}
