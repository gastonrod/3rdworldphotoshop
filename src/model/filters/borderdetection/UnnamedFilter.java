package model.filters.borderdetection;

public class UnnamedFilter extends BorderDetectorOperator{
    public UnnamedFilter() {
        xFilter = new UnnamedOperatorXFilter();
        yFilter = new UnnamedOperatorYFilter();
    }
}

