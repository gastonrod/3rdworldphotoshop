package model.filters.borderdetection;

public class LaplaceCrossingZeroOperator extends AbstractFilterWithZeroCrossing {
    public LaplaceCrossingZeroOperator(){
        mask = new double[][]{{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
        matrixSum = 0;
    }
}
