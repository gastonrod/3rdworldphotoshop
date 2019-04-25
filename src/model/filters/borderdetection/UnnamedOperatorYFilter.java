package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class UnnamedOperatorYFilter extends AbstractFilter {
    public UnnamedOperatorYFilter() {
        mask = new double[][]{
                { 1,  1,  1},
                { 1, -2,  1},
                {-1, -1, -1}
        };
        matrixSum = 1;
    }
}
