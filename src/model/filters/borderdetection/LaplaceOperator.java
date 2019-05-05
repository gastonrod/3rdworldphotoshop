package model.filters.borderdetection;

import model.filters.AbstractFilter;

public class LaplaceOperator extends AbstractFilter {
    public LaplaceOperator() {
        mask = new double[][]{{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
    }
}
