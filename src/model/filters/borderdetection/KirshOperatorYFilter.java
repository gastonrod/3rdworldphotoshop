package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class KirshOperatorYFilter extends AbstractFilter {
    public KirshOperatorYFilter() {
        mask = new double[][]{
                {5,   5,  5},
                {-3,  0, -3},
                {-3, -3, -3}
        };
        matrixSum = 1;
    }
}
