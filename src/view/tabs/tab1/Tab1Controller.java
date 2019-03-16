package view.tabs.tab1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import presenter.ImagesPresenter;
import sample.Main;

import java.io.IOException;

public class Tab1Controller extends Tab {

    private ImagesPresenter imagesPresenter;

    @FXML
    public Text mainImagePixelValueText;

    @FXML
    public Text secondaryImagePixelValueText;

    @FXML
    public TextField pixelModifyTextField;

    @FXML
    public Button pixelModifyButton;

    public Tab1Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_1.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("");
        }
        imagesPresenter = Main.getImagesPresenter();
        imagesPresenter.setTab1Controller(this);
    }

    @FXML
    protected void modifyPixel(ActionEvent event) {
        int value = Integer.parseInt(pixelModifyTextField.getText());
        if(value > 255 || value < 0){
            pixelModifyButton.setText("Invalid No");
            return;
        }
        pixelModifyButton.setText("Modify pixel");
        imagesPresenter.modifyPixel(value);
    }

    @FXML
    protected void copyFromMainToSec(ActionEvent event) {
        imagesPresenter.copyFromMainToSec();
    }

    public void setMainImagePixelValueText(String s) {
        mainImagePixelValueText.setText(s);
    }

    public void setSecondaryImagePixelValueText(String s) {
        secondaryImagePixelValueText.setText(s);
    }
}
