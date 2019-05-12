package model.filters.borderdetection;

import model.CustomImageFactory;
import model.operators.FilterOperator;
import model.umbrals.OtsuUmbral;
import model.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toList;

public class HoughLineTransform {

    private final int t = 10;
    private final int degreesAmount = 180;
    private int rosAmount;
    private final int amountOfLines;
    private double eps;

    public HoughLineTransform(double eps, int amountOfLines) {
        this.eps = eps;
        this.amountOfLines = amountOfLines;
    }

    public Color[][] filter(Color[][] image) {
//        Color[][] filteredImage = new BorderSusan(t).filter(image);
        Color[][] filteredImage = FilterOperator.borderHighlight(CustomImageFactory.newImage(image), 7).getRGBRepresentation();
        try{
            filteredImage = new OtsuUmbral().apply(filteredImage);
        } catch (Exception e) {

        }
        rosAmount = (int)(Math.sqrt(2) * Math.max(filteredImage.length, filteredImage[0].length));
        int[][] A = new int[rosAmount][degreesAmount];
//        int[][] A = new int[image.length][image[0].length];

        accumulate(filteredImage, A);

        HashMap<Point, Integer> lines = new HashMap<>();
        for(int i = 0; i < rosAmount; i++) {
            for(int j = 0; j < degreesAmount; j++) {
                lines.put(new Point(j - degreesAmount / 2,i), A[i][j]);
            }
        }

        List<Point> sortedPoints = lines
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(amountOfLines)
                .collect(toList());

        for(Point p: sortedPoints) {
            double theta = p.x;
            double ro = p.y;
            for (int xIdx = 0; xIdx < filteredImage[0].length; xIdx++) {
                for (int yIdx = 0; yIdx < filteredImage.length; yIdx++) {
                    double thetaTerm = (xIdx) * Math.cos(Math.toRadians(theta))
                            - (yIdx) * Math.sin(Math.toRadians(theta));
                    double total = ro - thetaTerm;
                    if (Math.abs(total) <= eps) {
                        image[yIdx][xIdx] = Color.RED;
                    }
                }
            }
        }
//        Color[][] newimg = Utils.getIntMatrixAsGrayscaledImage(A);
//        return newimg;
        return image;
    }


    private void accumulate(Color[][] filteredImage, int[][] A) {
        int imageHeight = filteredImage.length;
        int imageWidth = filteredImage[0].length;
        for(int i = 0; i < imageHeight; i++){
            for(int j = 0; j < imageWidth; j++){
                if(filteredImage[i][j].equals(Color.WHITE)) {
                    for(int theta = -degreesAmount/2, thetaIdx = 0; theta < degreesAmount/2; theta++, thetaIdx++){
                        for(int ro = 1; ro < rosAmount ; ro++) {
                            double total = Math.abs(ro - (double)(j) * Math.cos(Math.toRadians(theta)) - (double)(-i) * Math.sin(Math.toRadians(theta)));
                            if(total <= eps && !Utils.inBounds(A.length, A[0].length, ro, thetaIdx)) {
                                A[ro][thetaIdx]++;
                            }
                        }
                    }
                }
            }
        }
    }
}
