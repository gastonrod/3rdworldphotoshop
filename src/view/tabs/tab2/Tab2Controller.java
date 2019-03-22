package view.tabs.tab2;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.input.DragEvent;
import javafx.scene.text.Text;
import presenter.ImagesService;
import sample.Main;

import java.io.IOException;

public class Tab2Controller extends Tab {

    private ImagesService imagesService;

    @FXML
    public Text errorsText;

    @FXML
    public Slider contrastSlider;

    @FXML
    public Slider umbralSlider;

    public Tab2Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_2.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("");
        }
        imagesService = Main.getImagesService();
        imagesService.setTab2Controller(this);
    }

    public void setErrorsText(String s) {
        errorsText.setText(s);
    }

    @FXML
    protected void addImages(ActionEvent event) {
        imagesService.addImages();
    }

    @FXML
    protected void subtractImages(ActionEvent event) {
        imagesService.subtractImages();
    }

    @FXML
    protected void drc(ActionEvent event) {
        imagesService.drc();
    }

    @FXML
    protected void negative(ActionEvent event) {
        imagesService.imageNegative();
    }

    @FXML
    protected void contrastDrag(Event event) {
        imagesService.setContrast((int)contrastSlider.getValue());
    }

    @FXML
    protected void umbralDrag(Event event) {
        System.out.println(umbralSlider.getValue());
        imagesService.setUmbral((int)umbralSlider.getValue());
    }
}
