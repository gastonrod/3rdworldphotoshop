package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class UnnamedOperatorXFilter extends AbstractFilter {
    public UnnamedOperatorXFilter() {
        mask = new double[][]{
                {-1,  1, 1},
                {-1, -2, 1},
                {-1,  1, 1}
        };
        matrixSum = 1;
    }
}

