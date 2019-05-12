package model.filters.borderdetection;

import model.CustomImageFactory;
import model.operators.SpatialOperator;
import model.umbrals.OtsuUmbral;
import model.utils.Utils;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toList;

public class HoughCircleTransform {

    private final int t = 10;
    private final int amountOfCircles;
    private double eps;

    public HoughCircleTransform(double eps, int amountOfLines) {
        this.eps = eps;
        this.amountOfCircles = amountOfLines;
    }

    public Color[][] filter(Color[][] image) {
        Color[][] filteredImage = new BorderSusan(t).filter(image);
        try{
            filteredImage = new OtsuUmbral().apply(filteredImage);
        } catch (Exception e) {
        }
        int[][][] A = new int[image.length][image[0].length][Math.min(image.length, image[0].length)/2];

        accumulate(filteredImage, A);

        HashMap<Point3D, Integer> lines = new HashMap<>();
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                for(int r = 0; r < A[0][0].length; r++) {
                    if(A[i][j][r] > 0)
                        lines.put(new Point3D(i,j,r), A[i][j][r]);
                }
            }
        }
        // No need to continue
        if(lines.isEmpty())
            return image;

        List<Point3D> sortedPoints = lines
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(amountOfCircles)
                .collect(toList());
        for(Point3D p: sortedPoints) {
            int a = p.a;
            int b = p.b;
            int r = p.r;
            System.out.println(a + " - " + b + " - " + r );
            for(int y = 0; y < image.length; y++){
                for(int x = 0; x < image[0].length; x++){
                    double aTerm = Math.pow(x - a, 2);
                    double bTerm = Math.pow(y - b, 2);
                    double rTerm = Math.pow(r, 2);
                    double total = rTerm - aTerm - bTerm;
                    if (Math.abs(total) < eps) {
                        image[y][x] = Color.RED;
                    }
                }
            }
        }
        System.out.println("finished");
        return image;
    }

    private void accumulate(Color[][] filteredImage, int[][][] A) {
        for(int i = 0; i < filteredImage.length; i++){
            for(int j = 0; j < filteredImage[0].length; j++){
                if(filteredImage[i][j].equals(Color.WHITE)) {
                    int maxA = i+(filteredImage.length-i)/2;
                    int maxB = j+(filteredImage[0].length-j)/2;
//                    int maxR = Math.min(Math.min(i/2,j/2), Math.min(maxA, maxB));
                    int maxR = Math.min(filteredImage.length, filteredImage[0].length)/2;
                    System.out.println("i: " + i + " j: " + j + " maxA: " + maxA + " maxB: " + maxB + " maxR: " + maxR);
//                    for(int aPos = i / 2; aPos < maxA; aPos++) {
                    for(int aPos = 0; aPos < filteredImage.length; aPos++) {
                        double a = Math.pow(j - aPos, 2);
                        for(int bPos = 0; bPos < filteredImage[0].length; bPos++) {
//                        for(int bPos = j / 2; bPos < maxB; bPos++) {
                            double b = Math.pow(i - bPos, 2);

                            for(int rPos = 1; rPos < maxR; rPos++){
                                double r = Math.pow(rPos, 2);
                                double total = r - a - b;
                                if(aPos == 100 && bPos == 100 && rPos == 75) {
                                    System.out.println("total: " + total);
                                }
                                if (Math.abs(total) < eps) {
                                    A[aPos][bPos][rPos] += 1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private class Point3D {
        int a, b, r;
        Point3D(int a, int b, int r) {
            this.a = a;
            this.b = b;
            this.r = r;
        }
    }
}
