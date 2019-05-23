package model.filters.borderdetection;

import model.CustomImageFactory;
import model.images.CustomImage;
import model.operators.FilterOperator;
import model.umbrals.HisteresisUmbral;

import java.awt.Color;
import java.util.Arrays;

public class CannyOperator {

    private double sd = 2;
    private int t1;
    private int t2;
    public CannyOperator(int t1, int t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public Color[][] filter(Color[][] image){
        CustomImage img = FilterOperator.gaussianFilter(CustomImageFactory.newImage(image), sd);
        Color[][] Gx = FilterOperator.sobelOperatorX(img).getRGBRepresentation();
        Color[][] Gy = FilterOperator.sobelOperatorY(img).getRGBRepresentation();
        int[][] angles = getAngles(Gx, Gy);
        Color[][] G = FilterOperator.sobelOperatorBoth(img).getRGBRepresentation();
        nonMaximumSuppression(G, angles);
        HisteresisUmbral histeresisUmbral = new HisteresisUmbral(t1, t2);
        if(t1 <= t2) {
            G = histeresisUmbral.apply(G);
        }
        return G;
    }

    private void nonMaximumSuppression(Color[][] G, int[][] angles) {
        Color[][] c = new Color[G.length][G[0].length];
        for(int i = 1; i < G.length - 1; i++){
            for(int j = 1; j < G[0].length - 1; j++) {
                if(G[i][j].getRed() == 0){
                    continue;
                }
                int currMagnitude = G[i][j].getRed();
                int dy = 0;
                int dx = 0;
                switch (angles[i][j]) {
                    case 0:
                        dy = 0;
                        dx = 1;
                        break;
                    case 45:
                        dy = -1;
                        dx = 1;
                        break;
                    case 90:
                        dy = 1;
                        dx = 0;
                        break;
                    case 135:
                        dy = -1;
                        dx = -1;
                        break;
                    default:
                        throw new RuntimeException("Wrong angle, what did you do???");
                }
                boolean adj1IsGreater = G[i+dy][j+dx].getRed() > currMagnitude;
                boolean adj2IsGreater = G[i+dy*(-1)][j+dx*(-1)].getRed() > currMagnitude;
                if(adj1IsGreater || adj2IsGreater) {
                    G[i][j] = Color.BLACK;
                }
            }
        }
    }

    int[][] getAngles(Color[][] Gx, Color[][] Gy) {
        int[][] angles = new int[Gx.length][Gx[0].length];
        for(int i = 0; i < Gx.length; i++){
            for(int j = 0; j < Gx[0].length; j++){
                double angle = Math.toDegrees(Math.atan((double)Gy[i][j].getRed() / (double)Gx[i][j].getRed()));
                if(Gx[i][j].getRed() == 0)
                    angle = 90;
                angles[i][j] = getRealAngle(angle);
            }
        }
        return angles;
    }
    private int getRealAngle(double angle) {
        if(angle <= 22.5  ||angle >= 157.5) {
            return 0;
        }else if(angle <= 67.5) {
            return 45;
        }else if(angle <= 112.5) {
            return  90;
        }else {
            return 135;
        }
    }
}
