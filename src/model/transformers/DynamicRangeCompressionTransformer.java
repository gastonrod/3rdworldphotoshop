package model.transformers;

import java.awt.*;

public class DynamicRangeCompressionTransformer implements ColorTransformation{

    private double cRed;
    private double cGreen;
    private double cBlue;
    public DynamicRangeCompressionTransformer(Color[][] rgbRepresentation) {
        int maxRed = -1;
        int maxBlue = -1;
        int maxGreen = -1;
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for(int j = 0; j < rgbRepresentation[0].length; j++) {
                maxRed = Math.max(maxRed, rgbRepresentation[i][j].getRed());
                maxGreen = Math.max(maxGreen, rgbRepresentation[i][j].getGreen());
                maxBlue = Math.max(maxBlue, rgbRepresentation[i][j].getBlue());
            }
        }
        cRed   = 254 / Math.log(1.0+maxRed);
        cGreen = 254 / Math.log(1.0+maxGreen);
        cBlue  = 254 / Math.log(1.0+maxBlue);
    }

    @Override
    public Color transform(Color c) {
        int r = (int)(cRed   * Math.log(1.0+c.getRed()));
        int g = (int)(cGreen * Math.log(1.0+c.getGreen()));
        int b = (int)(cBlue  * Math.log(1.0+c.getBlue()));
        return new Color(r, g, b);
    }
}
