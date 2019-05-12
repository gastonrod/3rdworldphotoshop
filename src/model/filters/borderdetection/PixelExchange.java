package model.filters.borderdetection;

import model.filters.GaussianFilter;
import model.utils.Utils;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PixelExchange {
    static int[][] DIRECTIONS = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static int GAUSS_MASK_SIZE = 5;
    private static double GAUSS_SIGMA = 0.5;
    private double[] averageIn = new double[3];
    private double[] averageOut = new double[3];
    private int[][] phi;
    private Color[][] image;
    private List<Point> Lin;
    private List<Point> Lout;


    public Color[][] filter(Color[][] image, Point upperLeft, Point lowerRight) {
        this.image = image;
        initAveragesAndPhi(upperLeft, lowerRight);

        Lin = new ArrayList<>();
        fillLin();
        Lout = new ArrayList<>();
        fillLout();

        List<Point> affectedPoints = new ArrayList<>();
        while(!stoppingCondition()) {
            for (Point p : Lout) {
                if (calcF(p) > 0) {
                    affectedPoints.add(p);
                }
            }
            for (Point p : affectedPoints) {
                switchIn(p);
            }
            affectedPoints.clear();
            shrinkLimitIn();
            for (Point p : Lin){
                if (calcF(p) < 0) {
                    affectedPoints.add(p);
                }
            }
            for (Point p : affectedPoints) {
                switchOut(p);
            }
            affectedPoints.clear();
            shrinkLimitOut();
            if (stoppingCondition()) {
                smoothCurve();
            }
        }
        for(Point p : getFinalArea()) {
            image[p.y][p.x] = Color.MAGENTA;
        }
        return image;
    }

    private void initAveragesAndPhi(Point upperLeft, Point lowerRight){
        phi = new int[image.length][image[0].length];
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                averageOut[0] = image[i][j].getRed();
                averageOut[1] = image[i][j].getGreen();
                averageOut[2] = image[i][j].getBlue();
                if(i >= upperLeft.y && i <= lowerRight.y &&
                        j >= upperLeft.x && j <= lowerRight.x) {
                    averageIn[0] = image[i][j].getRed();
                    averageIn[1] = image[i][j].getGreen();
                    averageIn[2] = image[i][j].getBlue();
                    phi[i][j] = -3;
                } else {
                    phi[i][j] = 3;
                }
            }
        }
        for(int i = 0; i < 3; i++){
            averageIn[i] /= image.length * image[0].length;
            averageOut[i] /= image.length * image[0].length;
        }
    }

    private void fillLin(){
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                if (phi[i][j] == 3) {
                    for (int[] dir : DIRECTIONS) {
                        int newW = j + dir[0];
                        int newH = i + dir[1];
                        if(!Utils.inBounds(image.length, image[0].length, newH, newW)) continue;
                        if(newH <= 0 || newH >= phi.length || newW <= 0 || newW >= phi[0].length) continue;
                        if (phi[newH][newW] == 3) {
                            phi[i][j] = -1;
                            Lin.add(new Point(j, i));
                        }
                    }
                }
            }
        }
    }

    private void fillLout(){
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                if (phi[i][j] == -3) {
                    for (int[] dir : DIRECTIONS) {
                        int newW = j + dir[0];
                        int newH = i + dir[1];
                        if (!Utils.inBounds(image.length, image[0].length, newH, newW)) continue;
                        if (phi[newH][newW] == -1) {
                            phi[i][j] = 1;
                            Lout.add(new Point(j, i));
                        }
                    }
                }
            }
        }
    }

    private int calcF(Point p){
        double p1, p2;
        double red, green, blue;
        red = image[p.y][p.x].getRed();
        blue = image[p.y][p.x].getBlue();
        green = image[p.y][p.x].getGreen();

        p1 = Math.sqrt(Math.pow((averageIn[0] - red), 2) + Math.pow((averageIn[1] - green), 2) + Math.pow((averageIn[2] - blue), 2));
        p2 = Math.sqrt(Math.pow((averageOut[0] - red), 2) + Math.pow((averageOut[1] - green), 2) + Math.pow((averageOut[2] - blue), 2));
        double psigma1 = 1 - p1 / (Math.sqrt(3)*255);
        double psigma2 = 1 - p2 / (3*255);

        return (int)Math.signum(Math.log(psigma1/psigma2));
    }

    public boolean stoppingCondition(){
        for (Point p : Lin) {
            if (calcF(p) < 0) {
                return false;
            }
        }
        for (Point p : Lout) {
            if (calcF(p) > 0) {
                return false;
            }
        }
        return true;
    }
    public void switchIn(Point p) {
        Lout.remove(p);
        Lin.add(p);
        phi[p.y][p.x] = -1;
        for (int[] dir : DIRECTIONS) {
            int w = p.x + dir[0];
            int h = p.y + dir[1];
            if (!Utils.inBounds(image.length, image[0].length, h, w)) continue;
            if (phi[w][h] == 3) {
                phi[w][h] = 1;
                Lout.add(new Point(h, w));
            }
        }
    }

    public void shrinkLimitIn() {
        List<Point> affectedPoints = new ArrayList<Point>();
        for (Point p : Lin) {
            int count = 0;
            for (int[] dir : DIRECTIONS) {
                int w = p.x + dir[0];
                int h = p.y + dir[1];
                if (!Utils.inBounds(image.length, image[0].length, h, w)) continue;
                if (phi[h][w] < 0) {
                    count++;
                }
            }
            if (count == 4) {
                affectedPoints.add(p);
            }
        }
        for (Point p : affectedPoints) {
            Lin.remove(p);
            phi[p.y][p.x] = -3;
        }
    }

    public void switchOut(Point p) {
        Lin.remove(p);
        Lout.add(p);
        phi[p.y][p.x] = 1;
        for (int[] dir : DIRECTIONS) {
            int w = p.x + dir[0];
            int h = p.y + dir[1];
            if (!Utils.inBounds(image.length, image[0].length, h, w)) continue;
            if (phi[h][w] == -3) {
                phi[h][w] = -1;
                Lin.add(new Point(w, h));
            }
        }
    }

    public void shrinkLimitOut() {
        List<Point> affectedPoints = new ArrayList<Point>();
        for (Point p : Lout) {
            int count = 0;
            for (int[] dir : DIRECTIONS) {
                int w = p.x + dir[0];
                int h = p.y + dir[1];
                if (!Utils.inBounds(image.length, image[0].length, h, w)) continue;
                if (phi[h][w] > 0) {
                    count++;
                }
            }
            if (count == 4) {
                affectedPoints.add(p);
            }
        }
        for (Point p : affectedPoints) {
            Lout.remove(p);
            phi[p.y][p.x] = 3;
        }
    }

    public void smoothCurve() {
        List<Point> affectedPoints = new ArrayList<Point>();
        for (int i = 0; i < GAUSS_MASK_SIZE; i ++) {
            for (Point p : Lout) {
                if (gauss(p) < 0) {
                    affectedPoints.add(p);
                }
            }
            for (Point p : affectedPoints) {
                switchIn(p);
            }
            affectedPoints.clear();
            for (Point p : Lin) {
                if (gauss(p) > 0) {
                    affectedPoints.add(p);
                }
            }
            for (Point p : affectedPoints) {
                switchOut(p);
            }
            affectedPoints.clear();
        }
    }

    private int gauss(Point p) {
        double value = 0;
        double[][] GAUSS_MASK = new double[GAUSS_MASK_SIZE][GAUSS_MASK_SIZE];
        for(int i = 0, y = -GAUSS_MASK_SIZE/2; i < GAUSS_MASK_SIZE; i++, y++) {
            for(int j = 0,x = -GAUSS_MASK_SIZE/2; j < GAUSS_MASK_SIZE; j++, x++) {
                GAUSS_MASK[i][j] = (1/ (2.0 * Math.PI * GAUSS_SIGMA * GAUSS_SIGMA)) * Math.pow(Math.E, -(y*y + x*x)/ (GAUSS_SIGMA*GAUSS_SIGMA));
            }
        }
        for(int i = - GAUSS_MASK_SIZE / 2 ; i <= GAUSS_MASK_SIZE / 2; i++) {
            for(int j = - GAUSS_MASK_SIZE/ 2; j <= GAUSS_MASK_SIZE / 2; j++) {
                if(Utils.inBounds(image.length, image[0].length, p.y + i, p.x + j)) {
                    value += phi[p.y + i][p.x + j] * GAUSS_MASK[i+GAUSS_MASK_SIZE/2][j+GAUSS_MASK_SIZE/2];
                }
            }
        }
        return (int) Math.signum(value);
    }
    public List<Point> getFinalArea() {
        List<Point> area = new ArrayList<Point>();
        for (int w = 0; w < image[0].length; w ++) {
            for (int h = 0; h < image.length; h ++) {
                if (phi[w][h] < 0) {
                    area.add(new Point(w, h));
                }
            }
        }
        return area;
    }
}
