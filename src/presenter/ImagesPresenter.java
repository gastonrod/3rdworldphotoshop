package presenter;

import javafx.scene.image.WritableImage;
import javafx.stage.Window;
import model.ClicksManager;
import model.CustomImage;
import model.FileManager;
import org.jetbrains.annotations.NotNull;
import view.MainController;
import view.images.ImageController;
import view.tabs.tab1.Tab1Controller;

import java.awt.Point;
import java.io.File;

public class ImagesPresenter {

    private CustomImage mainImage;
    private CustomImage secondaryImage;

    private MainController controller;
    private ImageController imageController;
    private Tab1Controller tab1Controller;
    private Window scene;

    public void openImage(@NotNull File file, boolean isMainImage) {
        if (isMainImage) {
            mainImage = FileManager.openImage(file);
            imageController.setMainImage(getMainImage());
        } else {
            secondaryImage = FileManager.openImage(file);
            imageController.setSecondaryImage(getSecondaryImage());
        }
    }

    public void mainImageClicked(@NotNull Point point) {
        ClicksManager.mainImageClicked(point, imageController.mainImageView);
        Point secondaryPos = ClicksManager.getMainImageSecondClick();
        Point currentPos = ClicksManager.getMainImageCurrentClick();
        String text;
        // Esto esta horrible
        if(secondaryPos == null) {
            text = "Pixel value byte: " + ClicksManager.currentColor +
                    "\nFirst pos clicked: (" + currentPos.x + ", " + currentPos.y + ")";
        } else {
            text = "Pixel value byte: " + ClicksManager.currentColor +
                    "\nFirst pos clicked: (" + secondaryPos.x + ", " + secondaryPos.y + ")" +
                    "\nSecond pos clicked: (" + currentPos.x + ", " + currentPos.y + ")";
        }
        tab1Controller.setMainImagePixelValueText(text);
    }

    public void modifyPixel(@NotNull int newValue) {
        mainImage.modifyPixel(newValue, ClicksManager.getMainImageCurrentClick());
        imageController.setMainImage(getMainImage());
    }

    public void saveCurrentImage(@NotNull File file, boolean isMainImage) {
        FileManager.saveImage(file, isMainImage ? mainImage : secondaryImage);
    }

    public WritableImage getMainImage() {
        return mainImage.asWritableImage();
    }

    public WritableImage getSecondaryImage() {
        return secondaryImage.asWritableImage();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public Window getWindow() {
        if (scene != null) {
            return scene;
        }
        if (imageController.mainImageView != null) {
            scene = imageController.getScene().getWindow();
        }
        return scene;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    public void secondaryImageClicked(Point pos) {
        ClicksManager.secondaryImageClicked(pos);
        tab1Controller.setSecondaryImagePixelValueText("Secondary image clicked: (" + pos.x + ", " + pos.y + ")");
    }

    public void setTab1Controller(Tab1Controller tab1Controller) {
        this.tab1Controller = tab1Controller;
    }

    public void copyFromMainToSec() {
        mainImage.copySection(secondaryImage, ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick(), ClicksManager.getSecondaryImageClick());
    }
}
