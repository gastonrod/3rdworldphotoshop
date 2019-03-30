package view.menu.createimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import presenter.ImagesService;
import sample.Main;

import java.io.IOException;

public class CreateController extends Menu {
    private ImagesService imagesPresenter;
    public CreateController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create_menu.fxml"));
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
    protected void createCircle(ActionEvent actionEvent){
        imagesPresenter.createCircle();
    }

    @FXML
    void createSquare(ActionEvent actionEvent) {
        imagesPresenter.createSquare();
    }

    @FXML
    void createBlackAndWhiteGradient(ActionEvent actionEvent) {
        imagesPresenter.createBlackAndWhiteGradient();
    }

    @FXML
    void createColorGradient(ActionEvent actionEvent) {
        imagesPresenter.createColorGradient();
    }

    @FXML
    void createBlack(ActionEvent actionEvent) {
        imagesPresenter.createBlackImage();
    }
    @FXML
    void createWhite(ActionEvent actionEvent) {
        imagesPresenter.createWhiteImage();
    }
}
