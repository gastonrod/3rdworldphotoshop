package model.filters;


public class BorderHighlight extends AbstractFilter {

    public BorderHighlight(int maskSize) {
        mask = new double[maskSize][maskSize];
        for(int i = 0; i < maskSize; i++){
            for(int j = 0; j < maskSize; j++){
                mask[i][j] = -1;
            }
        }
        mask[maskSize/2][maskSize/2] = (double)maskSize * maskSize - 1;
        matrixSum = (double)maskSize * maskSize;
    }
}
