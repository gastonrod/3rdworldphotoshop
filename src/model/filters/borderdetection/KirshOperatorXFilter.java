package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class KirshOperatorXFilter extends AbstractFilter {
    public KirshOperatorXFilter() {
        mask = new double[][]{
                {-3, -3,  5},
                {-3,  0, 5},
                {-3, -3, 5}
        };
        matrixSum = 1;
    }
}
