package model.filters.borderdetection;

import model.umbrals.OtsuUmbral;
import model.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.toList;

public class HoughTransform {

    private final int t = 10;
    private final int degreesAmount = 180;
    private final int amountOfLines;
    private int eps;

    public HoughTransform(int eps, int amountOfLines) {
        this.eps = eps;
        this.amountOfLines = amountOfLines;
    }

    public Color[][] filter(Color[][] image) {
        image = new BorderSusan(t).filter(image);
        try{
            image = new OtsuUmbral().apply(image);
        } catch (Exception e) {

        }
        int rosAmount = (int)(Math.sqrt(2) * Math.max(image.length, image[0].length));
        int[][] A = new int[rosAmount][degreesAmount];

        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                if(image[i][j].equals(Color.WHITE)) {
                    for(int theta = -90; theta < degreesAmount/2; theta++){
                            double ro = j * Math.cos(Math.toRadians(theta)) + i * Math.sin(Math.toRadians(theta));
                            double total = Math.abs(ro - j * Math.cos(Math.toRadians(theta)) - i * Math.sin(Math.toRadians(theta)));
                            int roIdx = (int)(ro + rosAmount /2.0);
                            int thetaIdx = theta+(degreesAmount)/2;
                            if(total < eps && !Utils.inBounds(A.length, A[0].length, roIdx, thetaIdx)) {
                                A[roIdx][thetaIdx]++;
                            }

                    }
                }
            }
        }
        HashMap<Point, Integer> lines = new HashMap<>();
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                lines.put(new Point(j,i), A[i][j]);
                if(A[i][j] >= max) {
                    max = A[i][j];
                }
                if(A[i][j] <= min) {
                    min = A[i][j];
                }
            }
        }
        Color[][] newimg = new Color[rosAmount][degreesAmount];
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                newimg[i][j] = Utils.getGray(Utils.toRange((int)A[i][j], (int)min, (int)max));
            }
        }

        List<Point> sortedPoints = lines
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .map(e -> e.getKey())
                .limit(amountOfLines)
                .collect(toList());
        for(Point p: sortedPoints) {
            double theta = p.x;
            double ro = p.y;
            for(int x = 0; x < image[0].length; x++) {
                for (int y = 0; y < image.length; y++) {
                    double thetaTerm = x * Math.cos(Math.toRadians(theta))
                            - y * Math.sin(Math.toRadians(theta));
                    double total = ro - thetaTerm;
                    if (Math.abs(total) < eps) {
                        image[y][x] = Color.RED;
                    }
                }
            }
        }
//        return newimg;
        return image;
    }
}
