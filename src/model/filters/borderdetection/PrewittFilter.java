package model.filters.borderdetection;

public class PrewittFilter extends BorderDetectorOperator {

    public PrewittFilter() {
        xFilter = new PrewittOperatorXFilter();
        yFilter = new PrewittOperatorYFilter();
    }
}
