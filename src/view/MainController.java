package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import presenter.ImagesPresenter;
import sample.Main;


public class MainController {


    private ImagesPresenter imagesPresenter;

    public MainController() {
        imagesPresenter = Main.getImagesPresenter();
        imagesPresenter.setController(this);
    }

}
