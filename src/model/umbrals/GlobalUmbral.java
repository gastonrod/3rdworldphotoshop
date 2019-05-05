package model.umbrals;

import model.operators.SpatialOperator;
import view.Utils;

import java.awt.*;

public class GlobalUmbral implements Umbral {
    private int DeltaT = 5;
    private int T = Utils.L / 2;

    public Color[][] apply(Color[][] pixels) {
        Point p;
        do{
            p = getAmountInEachGroup(pixels);
            int newT = (p.x + p.y) / 2;
            if(Math.abs(T - newT) <= DeltaT) {
                break;
            }
            T = newT;
        }while(true);

        return SpatialOperator.setUmbral(pixels, T);
    }

    // Returns m1 and m2.
    private Point getAmountInEachGroup(Color[][] pixels) {
        Point n    = new Point(0,0);
        Point acum = new Point(0,0);
        for(int i = 0; i < pixels.length; i++){
            for(int j = 0; j < pixels[0].length; j++) {
                int grayLevel = (pixels[i][j].getRed() + pixels[i][j].getGreen() + pixels[i][j].getBlue()) / 3;
                if(grayLevel >= T) {
                    acum.y += grayLevel;
                    n.y++;
                } else {
                    acum.x += grayLevel;
                    n.x++;
                }
            }
        }
        if(n.x == 0) {
            T += T/2;
            return getAmountInEachGroup(pixels);
        } else if (n.y == 0) {
            T -= T/2;
            return getAmountInEachGroup(pixels);
        }
        return new Point(acum.x / n.x, acum.y / n.y);
    }
}
