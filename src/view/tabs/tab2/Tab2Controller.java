package view.tabs.tab2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import presenter.ImagesPresenter;
import sample.Main;

import java.io.IOException;

public class Tab2Controller extends Tab {
    private ImagesPresenter imagesPresenter;
    public Tab2Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_2.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("");
        }
        imagesPresenter = Main.getImagesPresenter();
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
}
