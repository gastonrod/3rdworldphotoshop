package model.filters.borderdetection;

public class SobelFilter extends BorderDetectorOperator {

    public SobelFilter() {
        xFilter = new SobelOperatorXFilter();
        yFilter = new SobelOperatorYFilter();
    }
}
