package presenter;

import javafx.scene.image.WritableImage;
import javafx.stage.Window;
import model.operators.FilterOperator;
import model.utils.TriFunction;
import model.operators.NoiseOperator;
import model.operators.SpatialOperator;
import model.managers.ClicksManager;
import model.images.CustomImage;
import model.CustomImageFactory;
import model.managers.FileManager;
import org.jetbrains.annotations.NotNull;
import view.components.ImagesList;
import view.error.ErrorCodes;
import view.error.ErrorsWindowController;
import view.images.ImageController;
import view.tabs.tab1.Tab1Controller;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImagesService {

    private Stack<CustomImage> mainImages = new Stack<>();
    private CustomImage secondaryImage;

    private ImageController imageController;
    private Tab1Controller tab1Controller;
    private Window scene;
    private ImagesList secondWindow;
    private ArrayList<CustomImage> imagesInSecondWindow;

    public void openImage(@NotNull File file, boolean isMainImage) {
        if (isMainImage) {
            mainImages.push(FileManager.openImage(file));
            imageController.setMainImage(getMainImageAsWritableImage());
        } else {
            secondaryImage = FileManager.openImage(file);
            imageController.setSecondaryImage(getSecondaryImageAsWritableImage());
        }
    }

    public void previousImage() {
        if(mainImages.size() == 1){
            ErrorsWindowController.newErrorCode(ErrorCodes.ONLY_ONE);
            return;
        }
        mainImages.pop();
        imageController.setMainImage(getMainImageAsWritableImage());
    }
    public void mainImageClicked(@NotNull Point point) {
        ClicksManager.mainImageClicked(point, getMainImage());
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
        if(getMainImage() == null || ClicksManager.getMainImageCurrentClick() == null) {
            return;
        }
        getMainImage().modifyPixel(newValue, ClicksManager.getMainImageCurrentClick());
        imageController.setMainImage(getMainImageAsWritableImage());
    }

    public void saveCurrentImage(@NotNull File file, boolean isMainImage) {
        FileManager.saveImage(file, isMainImage ? getMainImage(): secondaryImage);
    }

    public void saveImage(@NotNull File file, int id) {
        FileManager.saveImage(file, imagesInSecondWindow.get(id));
    }

    public CustomImage getMainImage() {
        return mainImages.isEmpty() ? null : mainImages.peek();
    }
    public WritableImage getMainImageAsWritableImage() {
        return getMainImage().asWritableImage();
    }

    public WritableImage getSecondaryImageAsWritableImage() {
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
        tab1Controller.setSecondaryImagePixelValueText("Secondary createimage clicked: (" + pos.x + ", " + pos.y + ")");
    }

    public void setTab1Controller(@NotNull Tab1Controller tab1Controller) {
        this.tab1Controller = tab1Controller;
    }

    public void copyFromMainToSec() {
        if(getMainImage() == null || secondaryImage == null ||
                ClicksManager.getMainImageSecondClick() == null ||
                ClicksManager.getSecondaryImageClick() == null) {
            return;
        }
        getMainImage().copySection(secondaryImage, ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick(), ClicksManager.getSecondaryImageClick());
    }

    public void getAverageAndPaint() {
        if(getMainImage() == null || ClicksManager.getMainImageSecondClick() == null) {
            return;
        }
        getMainImage().markArea(ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick());
        imageController.setMainImage(getMainImage().asWritableImage());
        int[] averages = getMainImage().getAverage(ClicksManager.getMainImageCurrentClick(), ClicksManager.getMainImageSecondClick());
        tab1Controller.setAveragesInfoText("Averages:\nR: " + averages[0] + ", G: " + averages[1] + ", B: " + averages[2]);
    }

    public void showHSV() {
        if(getMainImage() == null){
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        imagesInSecondWindow = getMainImage().getHSVRepresentations();
        ArrayList<WritableImage> writableImages = new ArrayList<>(imagesInSecondWindow.stream().map(i -> i.asWritableImage()).collect(Collectors.toList()));
        secondWindow = new ImagesList(writableImages);
    }

    public void addToSecondWindow() {
        if(getMainImage() == null){
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        CustomImage mainImage = getMainImage();
        if(secondWindow == null) {
            imagesInSecondWindow = new ArrayList<>();
            ArrayList<WritableImage> writableImages = new ArrayList<>();

            imagesInSecondWindow.add(mainImage);
            writableImages.add(mainImage.asWritableImage());
            secondWindow = new ImagesList(writableImages);
        } else {
            imagesInSecondWindow.add(mainImage);
            secondWindow.addImage(mainImage.asWritableImage(), imagesInSecondWindow.size()-1);
        }
    }

    // Spatial Operator methods
    public void addImages() {
        applyTransformation(getMainImage(), secondaryImage, SpatialOperator::addImages);
    }

    public void subtractImages() {
        applyTransformation(getMainImage(), secondaryImage, SpatialOperator::subtractImages);
    }

    public void drc() {
        applyTransformation(getMainImage(), SpatialOperator::dynamicRange);
    }

    public void equalizeHistogram() {
        applyTransformation(getMainImage(), SpatialOperator::equalizeHistogram);
    }

    public void potencyFunction(double phi) {
        applyTransformation(getMainImage(), phi,SpatialOperator::potencyFunction);
    }

    public void imageNegative() {
        applyTransformation(getMainImage(), SpatialOperator::negativeImage);
    }

    public void setContrast(int value) {
        applyTransformation(getMainImage(), value, SpatialOperator::setContrast);
    }

    public void setUmbral(int value) {
        applyTransformation(getMainImage(), value, SpatialOperator::setUmbral);
    }
    // End Spatial Operator methods

    // NoiseOperator methods
    public void saltAndPepper(double percent) {
        applyTransformation(getMainImage(), percent, NoiseOperator::saltAndPepperNoise);
    }

    public void rayleighNoise(double mean, double percent) {
        applyTransformation(getMainImage(), mean, percent, NoiseOperator::rayleighNoise);
    }

    public void exponentialNoise(double lambda, double percent) {
        applyTransformation(getMainImage(), lambda, percent, NoiseOperator::rayleighNoise);
    }

    public void gaussianNoise(int mean, int std, double percent) {
        // Replace with QuadFunction if necessary
        if(getMainImage() == null) {
            return;
        }
        mainImages.push(NoiseOperator.addGaussianNoise(getMainImage(), mean, std, percent));
        imageController.setMainImage(getMainImageAsWritableImage());
    }
    // End noise operator methods

    // Filter operator methods
    public void averageFilter(int maskSize) {
        applyTransformation(getMainImage(), maskSize, FilterOperator::averageFilter);
    }

    public void borderHighlight(int maskSize) {
        applyTransformation(getMainImage(), maskSize, FilterOperator::borderHighlight);
    }

    public void gaussianFilter(double sd) {
        applyTransformation(getMainImage(), sd, FilterOperator::gaussianFilter);
    }

    public void medianFilter(int maskSize) {
        applyTransformation(getMainImage(), maskSize, FilterOperator::medianFilter);
    }

    public void weightedMedianFilter(int maskSize, int weight) {
        applyTransformation(getMainImage(), maskSize, weight, FilterOperator::weightedMedianFilter);
    }

    public void prewittOperatorX() {
        applyTransformation(getMainImage(), FilterOperator::prewittOperatorX);
    }

    public void prewittOperatorY() {
        applyTransformation(getMainImage(), FilterOperator::prewittOperatorY);
    }

    public void prewittOperatorBoth() {
        applyTransformation(getMainImage(), FilterOperator::prewittOperatorBoth);
    }

    // This function assumes that the 2nd window is not open
    public void prewittOperatorOpen3() {
        if(getMainImage() == null){
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        imagesInSecondWindow = new ArrayList<>();
        imagesInSecondWindow.add(FilterOperator.prewittOperatorX(getMainImage()));
        imagesInSecondWindow.add(FilterOperator.prewittOperatorY(getMainImage()));
        imagesInSecondWindow.add(FilterOperator.prewittOperatorBoth(getMainImage()));
        ArrayList<WritableImage> writableImages = new ArrayList<>(imagesInSecondWindow.stream().map(i -> i.asWritableImage()).collect(Collectors.toList()));
        secondWindow = new ImagesList(writableImages);
    }

    // End filter operator methods

    // Id is index in images in second window list index
    public void setImageInSecondWindowAsMain(int idx) {
        mainImages = new Stack<>();
        mainImages.push(imagesInSecondWindow.get(idx));
        imageController.setMainImage(getMainImageAsWritableImage());
    }

    public void showHistogram() {
        imageController.showHistogram(getMainImage().getColorsRepetition());
    }

    // Create images
    public void createWhiteImage() {
        createImage(CustomImageFactory.whiteImage());
    }

    public void createBlackImage() {
        createImage(CustomImageFactory.blackImage());
    }

    public void createCircle() {
        createImage(CustomImageFactory.circle());
    }

    public void createColorGradient() {
        createImage(CustomImageFactory.colorGradient());
    }

    public void createBlackAndWhiteGradient() {
        createImage(CustomImageFactory.blackAndWhiteGradient());
    }

    public void createSquare() {
        createImage(CustomImageFactory.square());
    }

    private void createImage(CustomImage image){
        mainImages.push(image);
        imageController.setMainImage(getMainImageAsWritableImage());
    }
    // End create images

    // Apply transformation variants.
    private void applyTransformation(CustomImage image, Function<CustomImage, CustomImage> f) {
        if(getMainImage() == null) {
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        mainImages.push(f.apply(image));
        imageController.setMainImage(getMainImageAsWritableImage());
    }

    private <T> void applyTransformation(CustomImage image, T param, BiFunction<CustomImage, T, CustomImage> f){
        if(getMainImage() == null || param == null) {
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        mainImages.push(f.apply(image, param));
        imageController.setMainImage(getMainImageAsWritableImage());
    }

    private <T,K> void applyTransformation(CustomImage image, T param1, K param2, TriFunction<CustomImage, T, K, CustomImage> f) {
        if(getMainImage() == null || param1 == null || param2 == null) {
            ErrorsWindowController.newErrorCode(ErrorCodes.LOAD_MAIN);
            return;
        }
        mainImages.push(f.apply(image, param1, param2));
        imageController.setMainImage(getMainImageAsWritableImage());
    }

    public void secondWindowClosed() {
        secondWindow = null;
        imagesInSecondWindow = null;
    }
    // End applyTransformation variants.
}
