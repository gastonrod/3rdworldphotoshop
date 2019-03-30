package model.transformers;

import view.Utils;

import java.awt.*;

public class PotencyTransformation implements ColorTransformation{
    private double phi;
    private double c;

    public PotencyTransformation(double phi) {
        this.phi = phi;
        this.c = Math.pow(Utils.L-1.0, 1-phi);
    }

    @Override
    public Color transform(Color color) {
        return new Color(
                (int)(c * Math.pow(color.getRed(), phi)),
                (int)(c * Math.pow(color.getGreen(), phi)),
                (int)(c * Math.pow(color.getBlue(), phi))
        );
    }
}
