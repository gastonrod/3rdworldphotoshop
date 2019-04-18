package model.operators;

import model.CustomImageFactory;
import model.filters.*;
import model.filters.borderdetection.*;
import model.images.CustomImage;
import model.utils.Utils;

import java.awt.*;

public class FilterOperator {
    private FilterOperator(){}

    public static CustomImage borderHighlight(CustomImage image, int maskSize) {
        BorderHighlight filter = new BorderHighlight(maskSize);
        return filter(image, filter, maskSize);
    }

    public static CustomImage gaussianFilter(CustomImage image, double sd) {
        GaussianFilter filter = new GaussianFilter(sd);
        int maskSize = (int)(sd * 2 + 1);
        return filter(image, filter, maskSize);
    }

    public static CustomImage medianFilter(CustomImage image, int maskSize) {
        MedianFilter filter = new MedianFilter();
        return filter(image, filter, maskSize);
    }

    public static CustomImage weightedMedianFilter(CustomImage image, int maskSize, int weight) {
        WeightedMedianFilter filter = new WeightedMedianFilter(weight);
        return filter(image, filter, maskSize);
    }

    public static CustomImage prewittOperatorBoth(CustomImage image) {
        PrewittFilter filter = new PrewittFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage prewittOperatorY(CustomImage image) {
        PrewittOperatorYFilter filter = new PrewittOperatorYFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage prewittOperatorX(CustomImage image) {
        PrewittOperatorXFilter filter = new PrewittOperatorXFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage sobelOperatorX(CustomImage image) {
        SobelOperatorXFilter filter = new SobelOperatorXFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }


    public static CustomImage sobelOperatorY(CustomImage image) {
        SobelOperatorYFilter filter = new SobelOperatorYFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage sobelOperatorBoth(CustomImage image) {
        SobelFilter filter = new SobelFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage averageFilter(CustomImage image, int maskSize) {
        AverageFilter filter = new AverageFilter(maskSize);
        return filter(image, filter, maskSize);
    }

    private static CustomImage filter(CustomImage image, Filter filter, int maskSize) {
        Color[][] rgbRepresentation = image.getRGBRepresentation();
        return CustomImageFactory.newImage(filter(rgbRepresentation, filter, maskSize));

    }

    private static Color[][] filter(Color[][] rgbRepresentation, Filter filter, int maskSize) {
        Color[][] auxRgb = new Color[rgbRepresentation.length][rgbRepresentation[0].length];
        for(int i = 0; i < rgbRepresentation.length; i++) {
            for (int j = 0; j < rgbRepresentation[0].length; j++) {
                auxRgb[i][j] = filter.filter(rgbRepresentation, i, j, maskSize);
            }
        }
        return auxRgb;
    }
}
