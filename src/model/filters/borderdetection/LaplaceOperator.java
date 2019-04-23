package model.filters.borderdetection;

import model.filters.AbstractFilter;
import model.utils.Utils;

import java.awt.*;
import java.util.List;


public class LaplaceOperator extends AbstractFilter {
    protected double matrixSum;

    public LaplaceOperator() {
        mask = new double[][]{{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
        matrixSum = 1;
    }
}
