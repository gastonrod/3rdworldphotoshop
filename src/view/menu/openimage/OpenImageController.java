package view.menu.openimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import presenter.ImagesService;
import sample.Main;

import java.io.File;
import java.io.IOException;

public class OpenImageController extends Menu {
    private ImagesService imagesPresenter;
    public OpenImageController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("open_menu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("Error loading create_menu,fxml", e);
        }
        imagesPresenter = Main.getImagesService();
    }


    @FXML
    protected void openLenaBW(ActionEvent actionEvent){
        imagesPresenter.openImage(new File("images/lena_256_256.raw"), true);
    }

    @FXML
    void openLenaColor(ActionEvent actionEvent) {
        imagesPresenter.openImage(new File("images/lena2.ppm"), true);
    }

    @FXML
    void openBoat(ActionEvent actionEvent) {
        imagesPresenter.openImage(new File("images/barco_290_207.raw"), true);
    }

    @FXML
    void openShapes(ActionEvent actionEvent) {
        imagesPresenter.openImage(new File("images/shapes.pgm"), true);
    }
}
