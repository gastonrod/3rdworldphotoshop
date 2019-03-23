package view.images;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presenter.ImagesService;
import sample.Main;
import view.components.ImageAndSaveHBox;
import view.components.ImageHistogram;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class ImageController extends VBox {
    @FXML
    public ImageView mainImageView;

    @FXML
    public ImageView secondaryImageView;
    @FXML
    public ImageView h;
    @FXML
    public ImageView s;
    @FXML
    public ImageView v;
    private ImagesService imagesPresenter;

    public ImageController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("images_area.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("Error loading images_area.fxml", e);
        }
        imagesPresenter = Main.getImagesService();
        imagesPresenter.setImageController(this);
    }

    @FXML
    protected void onMainImageClicked(MouseEvent mouseEvent) {
        imagesPresenter.mainImageClicked(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
    }

    @FXML
    protected void onSecondaryImageClicked(MouseEvent mouseEvent) {
        imagesPresenter.secondaryImageClicked(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
    }

    public void setMainImage(WritableImage writableImage) {
        mainImageView.setImage(writableImage);
    }

    public void setSecondaryImage(WritableImage writableImage) {
        secondaryImageView.setImage(writableImage);
    }

    public void showNewImage(ArrayList<WritableImage> images) {
        // There's probably a better way to do this.
        ArrayList<ImageAndSaveHBox> prettyImages = new ArrayList<>(images.size());
        for(int i = 0; i < images.size(); i++) {
            prettyImages.add(new ImageAndSaveHBox(images.get(i), i));
        }
        try {
            HBox hBox = new HBox();
            hBox.getChildren().addAll(prettyImages);
            Stage stage = new Stage();
            stage.setScene(new Scene(hBox));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to load hsv_images.xml", e);
        }
    }

    public void showHistogram(int[] values) {
        try {
            HBox hBox = new HBox();
            hBox.getChildren().add(new ImageHistogram(values));
            Stage stage = new Stage();
            stage.setScene(new Scene(hBox));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to show histogram", e);
        }
    }
}
