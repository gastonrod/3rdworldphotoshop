package view.tabs.tab1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import presenter.ImagesService;
import sample.Main;
import view.Utils;

import java.io.IOException;

public class Tab1Controller extends Tab {

    private ImagesService imagesPresenter;

    @FXML
    public Text mainImagePixelValueText;

    @FXML
    public Text secondaryImagePixelValueText;

    @FXML
    public Text averagesInfoText;

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
            throw new RuntimeException("Error loading tab_1,fxml", e);
        }
        imagesPresenter = Main.getImagesService();
        imagesPresenter.setTab1Controller(this);
    }

    @FXML
    protected void modifyPixel(ActionEvent event) {
        int value = Utils.sanitizeNumberInput(pixelModifyTextField.getText(), Utils.L);
        if(value == -1) {
            return;
        }
        imagesPresenter.modifyPixel(value);
    }

    @FXML
    protected void copyFromMainToSec(ActionEvent event) {
        imagesPresenter.copyFromMainToSec();
    }

    @FXML
    protected void getAverageAndPaint(ActionEvent event) {
        imagesPresenter.getAverageAndPaint();
    }

    @FXML
    protected void showHSV() {
        imagesPresenter.showHSV();
    }

    public void setMainImagePixelValueText(String s) {
        mainImagePixelValueText.setText(s);
    }

    public void setSecondaryImagePixelValueText(String s) {
        secondaryImagePixelValueText.setText(s);
    }

    public void setAveragesInfoText(String s) {
        averagesInfoText.setText(s);
    }
}
