package view.images;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.images.CustomImage;
import presenter.ImagesPresenter;
import sample.Main;

import java.awt.*;
import java.io.IOException;

public class ImageController extends VBox {
    @FXML
    public ImageView mainImageView;

    @FXML
    public ImageView secondaryImageView;
    private ImagesPresenter imagesPresenter;

    public ImageController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("images_area.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("");
        }
        imagesPresenter = Main.getImagesPresenter();
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

    public void showHSV(CustomImage hsvRepresentation) {
    }
}
