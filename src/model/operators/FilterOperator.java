package model.operators;

import model.CustomImageFactory;
import model.filters.*;
import model.filters.borderdetection.*;
import model.images.CustomImage;
import model.utils.Utils;

import javax.swing.border.Border;
import java.awt.*;

public class FilterOperator {
    private FilterOperator(){}

    public static CustomImage bilateralFilter(CustomImage image, double sds, double sdr, int maskSize) {
        BilateralFilter filter = new BilateralFilter(sds, sdr);
        return filter(image, filter, maskSize);
    }

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
    public static CustomImage laplaceCrossingZeroOperator(CustomImage image) {
        LaplaceCrossingZeroOperator laplaceCrossingZeroOperator = new LaplaceCrossingZeroOperator();
        return CustomImageFactory.newImage(laplaceCrossingZeroOperator.applyOperator(image.getRGBRepresentation()));
    }
    public static CustomImage laplaceOperator(CustomImage image) {
        LaplaceOperator filter = new LaplaceOperator();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage exchangePixels(CustomImage image, Point p1, Point p2) {
        PixelExchange pixelExchange = new PixelExchange();
        return CustomImageFactory.newImage(pixelExchange.filter(image.getRGBRepresentation(),p1, p2));
    }

    public static CustomImage laplacianOfGaussianOperator(CustomImage image, double sd) {
        LaplacianOfGaussianOperator laplacianOfGaussianOperator = new LaplacianOfGaussianOperator(sd);
        return CustomImageFactory.newImage(laplacianOfGaussianOperator.applyOperator(image.getRGBRepresentation()));
    }

    public static CustomImage prewittOperatorBoth(CustomImage image) {
        PrewittFilter filter = new PrewittFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage cannyOperator(CustomImage image, int t1, int t2) {
        CannyOperator cannyOperator = new CannyOperator(t1, t2);
        return CustomImageFactory.newImage(cannyOperator.filter(image.getRGBRepresentation()));
    }

    public static CustomImage cornerSusanOperator(CustomImage image, int t) {
        CornerSusan cornerSusanOperator = new CornerSusan(t);
        image.getRGBRepresentation();
        return CustomImageFactory.newImage(cornerSusanOperator.filter(image.getRGBRepresentation()));
    }

    public static CustomImage borderSusanOperator(CustomImage image, int t) {
        BorderSusan borderSusanOperator = new BorderSusan(t);
        return CustomImageFactory.newImage(borderSusanOperator.filter(image.getRGBRepresentation()));
    }

    public static CustomImage prewittOperatorY(CustomImage image) {
        PrewittOperatorYFilter filter = new PrewittOperatorYFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage prewittOperatorX(CustomImage image) {
        PrewittOperatorXFilter filter = new PrewittOperatorXFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage kirshOperatorX(CustomImage image) {
        KirshOperatorXFilter filter = new KirshOperatorXFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage kirshOperatorY(CustomImage image) {
        KirshOperatorYFilter filter = new KirshOperatorYFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage kirshOperatorBoth(CustomImage image) {
        KirshFilter filter = new KirshFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage unnamedOperatorX(CustomImage image) {
        UnnamedOperatorXFilter filter = new UnnamedOperatorXFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage unnamedOperatorY(CustomImage image) {
        UnnamedOperatorYFilter filter = new UnnamedOperatorYFilter();
        return filter(image, filter, Utils.PREWITT_MASK_SIZE);
    }

    public static CustomImage unnamedOperatorBoth(CustomImage image) {
        UnnamedFilter filter = new UnnamedFilter();
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
