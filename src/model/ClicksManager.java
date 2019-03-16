package model;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.Point;

public class ClicksManager {
    // First click, and then click n-1
    private static Point mainImageFirstClick;
    private static Point secondaryImageClick;
    // Click n
    private static Point mainImageSecondClick;

    public static int currentColor;

    public static void mainImageClicked(Point pos, ImageView imageView) {
        if(mainImageFirstClick == null) {
            mainImageFirstClick = pos;
        } else {
            Point aux = pos;
            mainImageFirstClick = mainImageSecondClick == null ? mainImageFirstClick : mainImageSecondClick;
            mainImageSecondClick = aux;
        }
        Color color = imageView.getImage().getPixelReader().getColor(pos.x, pos.y);
        byte colorAux = (byte)(color.getRed() * 255);
        currentColor =  colorAux < 0 ? 256 + colorAux : colorAux;
    }

    public static Point getMainImageCurrentClick() {
        return mainImageSecondClick == null ? mainImageFirstClick : mainImageSecondClick;
    }

    public static Point getMainImageSecondClick() {
        return mainImageSecondClick == null ? null : mainImageFirstClick;
    }

    public static void secondaryImageClicked(Point pos, ImageView secondaryImageView) {
        secondaryImageClick = pos;
    }
}
