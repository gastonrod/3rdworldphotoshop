package model.filters.borderdetection;

import java.awt.*;

public class BorderSusan extends Susan{
    public BorderSusan(int t) {
        super(t);
    }

    @Override
    Color setColor(double s) {
        return s >= 0.33 && s <= 0.66 ? Color.WHITE : Color.BLACK;
    }
}
