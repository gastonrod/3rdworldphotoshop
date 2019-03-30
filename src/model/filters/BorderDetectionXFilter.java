package model.filters;

public class BorderDetectionXFilter extends AbstractFilter {

    public BorderDetectionXFilter() {
        int maskSize = 3;
        mask = new double[maskSize][maskSize];
        for(int i = 0; i < maskSize; i++){
            mask[i][0] = -1;
            mask[i][maskSize-1] = 1;
        }
        matrixSum = 1;
    }
}
