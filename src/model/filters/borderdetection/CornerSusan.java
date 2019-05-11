package model.filters.borderdetection;

import java.awt.Color;

public class CornerSusan extends Susan{
    public CornerSusan(int t) {
        super(t);
    }

    @Override
    Color setColor(double s) {
        return s >= 0.66 ? Color.WHITE : Color.BLACK;
    }
}
