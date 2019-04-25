package model.filters.borderdetection;

public class KirshFilter extends BorderDetectorOperator{
    public KirshFilter() {
        xFilter = new KirshOperatorXFilter();
        yFilter = new KirshOperatorYFilter();
    }
}
