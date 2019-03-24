package presenter;

import javafx.scene.image.WritableImage;
import javafx.stage.Window;
import model.operators.FilterOperator;
import model.operators.NoiseOperator;
import model.operators.SpatialOperator;
import model.managers.ClicksManager;
import model.images.CustomImage;
import model.CustomImageFactory;
import model.managers.FileManager;
import org.jetbrains.annotations.NotNull;
import view.error.ErrorCodes;
import view.error.ErrorsWindowController;
import view.images.ImageController;
import view.tabs.tab1.Tab1Controller;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ImagesService {

    private CustomImage mainImage;
    private CustomImage secondaryImage;

    private ImageController imageController;
    private Tab1Controller tab1Controller;
    private Window scene;
    private ArrayList<CustomImage> imagesInSecondWindow;

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
            text = "Pixel value byte: " + ClicksManager.getCurrentColor() +
                    "\nFirst pos clicked: (" + currentPos.x + ", " + currentPos.y + ")";
        } else {
            text = "Pixel value byte: " + ClicksManager.getCurrentColor() +
                    "\nFirst pos clicked: (" + secondaryPos.x + ", " + secondaryPos.y + ")" +
                    "\nSecond pos clicked: (" + currentPos.x + ", " + currentPos.y + ")";
        }
        tab1Controller.setMainImagePixelValueText(text);
    }

    public void modifyPixel(@NotNull int newValue) {
        if(mainImage == null || ClicksManager.getMainImageCurrentClick() == null) {
            return;
        }
        mainImage.modifyPixel(newValue, ClicksManager.getMainImageCurrentClick());
        imageController.setMainImage(getMainImage());
    }

    public void saveCurrentImage(@NotNull File file, boolean isMainImage) {
        FileManager.saveImage(file, isMainImage ? mainImage : secondaryImage);
    }

    public void saveImage(@NotNull File file, int id) {
        FileManager.saveImage(file, imagesInSecondWindow.get(id));
    }

    public WritableImage getMainImage() {
        return mainImage.asWritableImage();
    }

    public WritableImage getSecondaryImage() {
        return secondaryImage.asWritableImage();
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

    public void setImageController(@NotNull ImageController imageController) {
        this.imageController = imageController;
    }

    public void secondaryImageClicked(@NotNull Point pos) {
        ClicksManager.secondaryImageClicked(pos);
        tab1Controller.setSecondaryImagePixelValueText("Secondary image clicked: (" + pos.x + ", " + pos.y + ")");
    }

    public void setTab1Controller(@NotNull Tab1Controller tab1Controller) {
        this.tab1Controller = tab1Controller;
    }

    public void copyFromMainToSec() {
        if(mainImage == null || secondaryImage == null ||
                ClicksManager.getMainImageSecondClick() == null ||
                ClicksManager.getSecondaryImageClick() == null) {
            return;
        }
        mainImage.copySection(secondaryImage, ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick(), ClicksManager.getSecondaryImageClick());
    }

    public void getAverageAndPaint() {
        if(mainImage == null || ClicksManager.getMainImageSecondClick() == null) {
            return;
        }
        mainImage.markArea(ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick());
        imageController.setMainImage(mainImage.asWritableImage());
        int[] averages = mainImage.getAverage(ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick());
        tab1Controller.setAveragesInfoText("Averages:\nR: " + averages[0] + ", G: " + averages[1] + ", B: " + averages[2]);
    }

    public void showHSV() {
        if(mainImage == null){
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        imagesInSecondWindow = mainImage.getHSVRepresentations();
        ArrayList<WritableImage> writableImages = new ArrayList<>(imagesInSecondWindow.stream().map(i -> i.asWritableImage()).collect(Collectors.toList()));
        imageController.showNewImage(writableImages);
    }

    public void addImages() {
        if(mainImage == null || secondaryImage == null) {
            return;
        }
        mainImage = SpatialOperator.addImages(mainImage,secondaryImage);
        imageController.setMainImage(getMainImage());
    }

    public void subtractImages() {
        if(mainImage == null || secondaryImage == null) {
            return;
        }
        mainImage = SpatialOperator.subtractImages(mainImage,secondaryImage);
        imageController.setMainImage(getMainImage());
    }

    public void drc() {
        if(mainImage == null) {
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        mainImage = SpatialOperator.dynamicRange(mainImage);
        imageController.setMainImage(getMainImage());
    }

    public void imageNegative() {
        if(mainImage == null) {
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        mainImage = SpatialOperator.negativeImage(mainImage);
        imageController.setMainImage(getMainImage());
//        ArrayList<WritableImage> l = new ArrayList<>();
//        l.add(SpatialOperator.negativeImage(mainImage).asWritableImage());
//        imageController.showNewImage(l);
    }

    public void setContrast(int value) {
        if(mainImage == null) {
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        imageController.setMainImage(SpatialOperator.setContrast(mainImage, value).asWritableImage());
//        ArrayList<WritableImage> l = new ArrayList<>();
//        l.add(CustomImageFactory.blackImage().asWritableImage());
//        imageController.showNewImage(l);
    }

    public void setUmbral(int value) {
        if(mainImage == null) {
            return;
        }
//        imageController.setMainImage(SpatialOperator.setUmbral(mainImage, value).asWritableImage());
        ArrayList<WritableImage> l = new ArrayList<>();
        l.add(SpatialOperator.setUmbral(mainImage, value).asWritableImage());
        imageController.showNewImage(l);
    }

    public void gaussianNoise(int mean, int std, double percent) {
        if(mainImage == null) {
            return;
        }
//        imageController.setMainImage(SpatialOperator.setUmbral(mainImage, value).asWritableImage());
        imagesInSecondWindow = new ArrayList<>();
        imagesInSecondWindow.add(NoiseOperator.addGaussianNoise(mainImage, mean, std, percent));
        ArrayList<WritableImage> l = new ArrayList<>();
        l.add(imagesInSecondWindow.get(0).asWritableImage());
        imageController.showNewImage(l);
    }

    public void averageFilter(int maskSize) {
        if(mainImage == null) {
            return;
        }
//        imageController.setMainImage(SpatialOperator.setUmbral(mainImage, value).asWritableImage());
        imagesInSecondWindow = new ArrayList<>();
        imagesInSecondWindow.add(FilterOperator.averageFilter(mainImage, maskSize));
        ArrayList<WritableImage> l = new ArrayList<>();
        l.add(imagesInSecondWindow.get(0).asWritableImage());
        imageController.showNewImage(l);
    }
    public void medianFilter(int maskSize) {
        if(mainImage == null) {
            return;
        }
//        imageController.setMainImage(SpatialOperator.setUmbral(mainImage, value).asWritableImage());
        imagesInSecondWindow = new ArrayList<>();
        imagesInSecondWindow.add(FilterOperator.medianFilter(mainImage, maskSize));
        ArrayList<WritableImage> l = new ArrayList<>();
        l.add(imagesInSecondWindow.get(0).asWritableImage());
        imageController.showNewImage(l);
    }

    public void weightedMedianFilter(int maskSize, int weight) {
        if(mainImage == null) {
            return;
        }
//        imageController.setMainImage(SpatialOperator.setUmbral(mainImage, value).asWritableImage());
        imagesInSecondWindow = new ArrayList<>();
        imagesInSecondWindow.add(FilterOperator.weightedMedianFilter(mainImage, maskSize, weight));
        ArrayList<WritableImage> l = new ArrayList<>();
        l.add(imagesInSecondWindow.get(0).asWritableImage());
        imageController.showNewImage(l);
    }

    public void equalizeHistogram() {
        if(mainImage == null) {
            return;
        }
        mainImage = SpatialOperator.equalizeHistogram(mainImage);
        imageController.setMainImage(getMainImage());
    }

    // Id is index in images in second window list index
    public void setImageInSecondWindowAsMain(int idx) {
        mainImage = imagesInSecondWindow.get(idx);
        imageController.setMainImage(getMainImage());
    }

    public void showHistogram() {
        imageController.showHistogram(mainImage.getColorsRepetition());
    }

    public void createWhiteImage() {
        mainImage = CustomImageFactory.whiteImage();
        imageController.setMainImage(getMainImage());
//        ArrayList<WritableImage> l = new ArrayList<>();
//        l.add(CustomImageFactory.whiteImage().asWritableImage());
//        imageController.showNewImage(l);
    }

    public void createBlackImage() {
        mainImage = CustomImageFactory.blackImage();
        imageController.setMainImage(getMainImage());
//        ArrayList<WritableImage> l = new ArrayList<>();
//        l.add(CustomImageFactory.blackImage().asWritableImage());
//        imageController.showNewImage(l);
    }

    public void createCircle() {
        mainImage = CustomImageFactory.circle();
        imageController.setMainImage(mainImage.asWritableImage());
    }

    public void createColorGradient() {
        mainImage = CustomImageFactory.colorGradient();
        imageController.setMainImage(mainImage.asWritableImage());
    }

    public void createBlackAndWhiteGradient() {
        mainImage = CustomImageFactory.blackAndWhiteGradient();
        imageController.setMainImage(mainImage.asWritableImage());
    }

    public void createSquare() {
        mainImage = CustomImageFactory.square();
        imageController.setMainImage(mainImage.asWritableImage());
    }

}
