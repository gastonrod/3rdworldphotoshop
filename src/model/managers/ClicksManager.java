package model.managers;

import javafx.scene.image.ImageView;
import model.images.CustomImage;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;
import java.awt.Color;

public class ClicksManager {
    // First click, and then click n-1
    private static Point mainImageFirstClick;
    private static Point secondaryImageClick;
    // Click n
    private static Point mainImageSecondClick;


    private static int currentColor;

    private ClicksManager() {}

    public static void mainImageClicked(Point pos, CustomImage image) {
        if(mainImageFirstClick == null) {
            mainImageFirstClick = pos;
        } else {
            Point aux = pos;
            mainImageFirstClick = mainImageSecondClick == null ? mainImageFirstClick : mainImageSecondClick;
            mainImageSecondClick = aux;
        }
        Color color = image.getRGBRepresentation()[pos.y][pos.x];
        byte colorAux = (byte)(color.getRed() * 255);
        currentColor =  colorAux < 0 ? 256 + colorAux : colorAux;
    }

    public static Point getMainImageCurrentClick() {
        return mainImageSecondClick == null ? mainImageFirstClick : mainImageSecondClick;
    }

    public static Point getMainImageSecondClick() {
        return mainImageSecondClick == null ? null : mainImageFirstClick;
    }

    public static void secondaryImageClicked(@NotNull Point pos) {
        secondaryImageClick = pos;
    }

    public static Point getSecondaryImageClick() {
        return secondaryImageClick;
    }

    public static int getCurrentColor() {
        return currentColor;
    }
}
